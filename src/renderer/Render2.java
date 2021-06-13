package renderer;

import primitives.*;
import elements.*;

import java.util.LinkedList;
import java.util.List;
import java.util.MissingResourceException;

/**
 * Renderer class is responsible for generating pixel color map from a graphic
 * scene, using ImageWriter class
 * 
 * @author Dan
 *
 */
public class Render2 {
	private Camera camera;
	private ImageWriter imageWriter;
	private RayTracerBase tracer;
	private static final String RESOURCE_ERROR = "Renderer resource not set";
	private static final String RENDER_CLASS = "Render";
	private static final String IMAGE_WRITER_COMPONENT = "Image writer";
	private static final String CAMERA_COMPONENT = "Camera";
	private static final String RAY_TRACER_COMPONENT = "Ray tracer";

	private int threadsCount = 0;
	private static final int SPARE_THREADS = 2; // Spare threads if trying to use all the cores
	private boolean print = false; // printing progress percentage

	/**
	 * Set multi-threading <br>
	 * - if the parameter is 0 - number of cores less 2 is taken
	 * 
	 * @param threads number of threads
	 * @return the Render object itself
	 */
	public Render2 setMultithreading(int threads) {
		if (threads < 0)
			throw new IllegalArgumentException("Multithreading parameter must be 0 or higher");
		if (threads != 0)
			this.threadsCount = threads;
		else {
			int cores = Runtime.getRuntime().availableProcessors() - SPARE_THREADS;
			this.threadsCount = cores <= 2 ? 1 : cores;
		}
		return this;
	}

	/**
	 * Set debug printing on
	 * 
	 * @return the Render object itself
	 */
	public Render2 setDebugPrint() {
		print = true;
		return this;
	}

	/**
	 * Pixel is an internal helper class whose objects are associated with a Render
	 * object that they are generated in scope of. It is used for multithreading in
	 * the Renderer and for follow up its progress.<br/>
	 * There is a main follow up object and several secondary objects - one in each
	 * thread.
	 * 
	 * @author Dan
	 *
	 */
	private class Pixel {
		private long maxRows = 0;
		private long maxCols = 0;
		private long pixels = 0;
		public volatile int row = 0;
		public volatile int col = -1;
		private long counter = 0;
		private int percents = 0;
		private long nextCounter = 0;

		/**
		 * The constructor for initializing the main follow up Pixel object
		 * 
		 * @param maxRows the amount of pixel rows
		 * @param maxCols the amount of pixel columns
		 */
		public Pixel(int maxRows, int maxCols) {
			this.maxRows = maxRows;
			this.maxCols = maxCols;
			this.pixels = (long) maxRows * maxCols;
			this.nextCounter = this.pixels / 100;
			if (Render2.this.print)
				System.out.printf("\r %02d%%", this.percents);
		}

		/**
		 * Default constructor for secondary Pixel objects
		 */
		public Pixel() {
		}

		/**
		 * Internal function for thread-safe manipulating of main follow up Pixel object
		 * - this function is critical section for all the threads, and main Pixel
		 * object data is the shared data of this critical section.<br/>
		 * The function provides next pixel number each call.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return the progress percentage for follow up: if it is 0 - nothing to print,
		 *         if it is -1 - the task is finished, any other value - the progress
		 *         percentage (only when it changes)
		 */
		private synchronized int nextP(Pixel target) {
			++col;
			++this.counter;
			if (col < this.maxCols) {
				target.row = this.row;
				target.col = this.col;
				if (Render2.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			++row;
			if (row < this.maxRows) {
				col = 0;
				target.row = this.row;
				target.col = this.col;
				if (Render2.this.print && this.counter == this.nextCounter) {
					++this.percents;
					this.nextCounter = this.pixels * (this.percents + 1) / 100;
					return this.percents;
				}
				return 0;
			}
			return -1;
		}

		/**
		 * Public function for getting next pixel number into secondary Pixel object.
		 * The function prints also progress percentage in the console window.
		 * 
		 * @param target target secondary Pixel object to copy the row/column of the
		 *               next pixel
		 * @return true if the work still in progress, -1 if it's done
		 */
		public boolean nextPixel(Pixel target) {
			int percent = nextP(target);
			if (Render2.this.print && percent > 0)
				synchronized (this) {
					notifyAll();
				}
			if (percent >= 0)
				return true;
			if (Render2.this.print)
				synchronized (this) {
					notifyAll();
				}
			return false;
		}

		/**
		 * Debug print of progress percentage - must be run from the main thread
		 */
		public void print() {
			if (Render2.this.print)
				while (this.percents < 100)
					try {
						synchronized (this) {
							wait();
						}
						System.out.printf("\r %02d%%", this.percents);
						System.out.flush();
					} catch (Exception e) {
					}
		}
	}

	/**
	 * Camera setter
	 * 
	 * @param camera to set
	 * @return renderer itself - for chaining
	 */
	public Render2 setCamera(Camera camera) {
		this.camera = camera;
		return this;
	}

	/**
	 * Image writer setter
	 * 
	 * @param imgWriter the image writer to set
	 * @return renderer itself - for chaining
	 */
	public Render2 setImageWriter(ImageWriter imgWriter) {
		this.imageWriter = imgWriter;
		return this;
	}

	/**
	 * Ray tracer setter
	 * 
	 * @param tracer to use
	 * @return renderer itself - for chaining
	 */
	public Render2 setRayTracer(RayTracerBase tracer) {
		this.tracer = tracer;
		return this;
	}

	/**
	 * Produce a rendered image file
	 */
	public void writeToImage() {
		if (imageWriter == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

		imageWriter.writeToImage();
	}

	/**
	 * Cast ray from camera in order to color a pixel
	 * @param nX resolution on X axis (number of pixels in row)
	 * @param nY resolution on Y axis (number of pixels in column)
	 * @param col pixel's column number (pixel index in row)
	 * @param row pixel's row number (pixel index in column)
	 */
	private void castRay(int nX, int nY, int col, int row) {
		Ray ray = camera.ConstructRayThroughPixel(nX, nY, col, row);
		Color color = tracer.traceRay(ray);
		imageWriter.writePixel(col, row, color);
	}
	
	/**
	 * print pixel by call to recursive function
	 * this function find color of 4 corners, call the recursive, then write the pixel
	 */
	private void castRayRecursive(int nX, int nY, int col, int row) {		
		//call function for get list of 4 colors of 4 corners
		List<Color> corners = colors4Corners(nX, nY, col, row);
		
		Point3D center = camera.getCenterOfPixel(nX, nY, col, row);
		Color color = castRayRecursive(camera.getRx(nX),camera.getRy(nY),depth,center,corners);
		
		imageWriter.writePixel(col, row, color);
	}
	
	/**
	 * the recursive function
	 */
	private Color castRayRecursive(double Rx, double Ry,  int level, Point3D pc, List<Color> corners) {		

		Color sumColors = Color.BLACK;
		if(level <= 0 || ( corners.get(0).equals(corners.get(1)) && 
				corners.get(0).equals(corners.get(2)) && corners.get(0).equals(corners.get(3)))) {
			for(var color : corners) sumColors = sumColors.add(color);
			return sumColors.reduce(4);
		}

		//call function for get list of 5 colors of 5 centers
		List<Color> centers = colors5Centers(pc,Rx,Ry);
		
		//recursive calls:
		sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
				pc.add(camera.getVRight().scale(-Rx/4).add(camera.getVUp().scale(Ry/4))),
				List.of(corners.get(0),centers.get(0),centers.get(1),centers.get(2))));
		
		sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
				pc.add(camera.getVRight().scale(Rx/4).add(camera.getVUp().scale(Ry/4))),
				List.of(centers.get(0),corners.get(1),centers.get(2),centers.get(3))));
		
		sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
				pc.add(camera.getVRight().scale(-Rx/4).add(camera.getVUp().scale(-Ry/4))),
				List.of(centers.get(1),centers.get(2),corners.get(2),centers.get(4))));
		
		sumColors = sumColors.add(castRayRecursive(Rx/2,Ry/2,level-1,
				pc.add(camera.getVRight().scale(Rx/4).add(camera.getVUp().scale(-Ry/4))),
				List.of(centers.get(2),centers.get(3),centers.get(4),corners.get(3))));
		
		return sumColors.reduce(4);
	}
	
	
	/**
	 * calculate the colors of 4 corners of pixel
	 */
	private List<Color> colors4Corners(int nX, int nY, int col, int row) {
		List<Ray> rays = camera.colors4Corners(nX,nY,col,row);
		List<Color> colors = new LinkedList<Color>();
		
		for(var ray : rays)
			colors.add(tracer.traceRay(ray));
				
		return colors;
	}
	
	/**
	 * calculate the colors of 5 centers on pixel
	 */
	private List<Color> colors5Centers(Point3D pc, double Rx, double Ry) {
		List<Ray> rays = camera.colors5Centers(pc, Rx, Ry);
		List<Color> colors = new LinkedList<Color>();
		
		for(var ray : rays)
			colors.add(tracer.traceRay(ray));
				
		return colors;
	}
	

	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object - with multi-threading
	 */
	private void renderImageThreaded() {
		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		final Pixel thePixel = new Pixel(nY, nX);
		// Generate threads
		Thread[] threads = new Thread[threadsCount];
		for (int i = threadsCount - 1; i >= 0; --i) {
			threads[i] = new Thread(() -> {
				Pixel pixel = new Pixel();
				while (thePixel.nextPixel(pixel))
					castRayRecursive(nX, nY, pixel.col, pixel.row);
					//castRay(nX, nY, pixel.col, pixel.row);
			});
		}
		// Start threads
		for (Thread thread : threads)
			thread.start();

		// Print percents on the console
		thePixel.print();

		// Ensure all threads have finished
		for (Thread thread : threads)
			try {
				thread.join();
			} catch (Exception e) {
			}

		if (print)
			System.out.print("\r100%");
	}

	
	//nun of rays to send for improvement
	int numbergrid = 0;
	public void setImpovement(int n)
	{
		numbergrid = n;
	}
	
	//depth of recursion
	int depth = 3;
	
	/*
	 * mini project 1 - calculate average of colors from one pixel by numbergrid^2 rays
	 */
	private Color gridPixel(int nX,int nY,int x,int y) {
		Color sumColors = Color.BLACK;
		List<Ray> rays = new LinkedList<Ray>();
		
		for(int i = 0; i< numbergrid; i++) //all rows
		{	
			for(int j = 0;j < numbergrid; j++) //all columns
			{
				Ray ray = camera.ConstructRayThroughPixelGrid(nX, nY, x, y, j, i, numbergrid);
				
                rays.add(ray);
			}
		}
		//sumColors = sumColors.add(rayTracer.traceRay(ray));
		for(var ray : rays)
			sumColors = sumColors.add(tracer.traceRay(ray));
				
		return sumColors.reduce(rays.size());
	}
	
	/**
	 * This function renders image's pixel color map from the scene included with
	 * the Renderer object
	 */
	public void renderImage() {
		if (imageWriter == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);
		if (camera == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, CAMERA_COMPONENT);
		if (tracer == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, RAY_TRACER_COMPONENT);

		final int nX = imageWriter.getNx();
		final int nY = imageWriter.getNy();
		if (threadsCount == 0)
			for (int i = 0; i < nY; ++i) {
				for (int j = 0; j < nX; ++j) {
					//castRay(nX, nY, j, i);
					
					if(numbergrid <= 1) {
		                castRay(nX, nY, j, i);
					}
					else 
		            	//paint pixel
		            	imageWriter.writePixel(j, i, gridPixel(nX,nY,j,i));
				}
				System.out.printf("\r %02d%%", i);
			}
		else
			renderImageThreaded();
	}

	/**
	 * Create a grid [over the picture] in the pixel color map. given the grid's
	 * step and color.
	 * 
	 * @param step  grid's step
	 * @param color grid's color
	 */
	public void printGrid(int step, Color color) {
		if (imageWriter == null)
			throw new MissingResourceException(RESOURCE_ERROR, RENDER_CLASS, IMAGE_WRITER_COMPONENT);

		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();

		for (int i = 0; i < nY; ++i)
			for (int j = 0; j < nX; ++j)
				if (j % step == 0 || i % step == 0)
					imageWriter.writePixel(j, i, color);
	}
}