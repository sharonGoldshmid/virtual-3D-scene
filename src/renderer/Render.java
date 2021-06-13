package renderer;

import java.util.LinkedList;


import java.util.List;
import elements.*;
import geometries.Intersectable;
import primitives.Color;
import primitives.Ray;
import scene.*;
import unittests.colorGridPixelTests;

public class Render {

	//Scene scene;
	Camera camera;
	ImageWriter imageWriter;
	RayTracerBasic rayTracer;
	
	
	//seters
	//public Scene setScene(Scene s) { return scene = s; }
	public Render setCamera(Camera c)
	{
		camera = c;
		return this;
	}
	public Render setImageWriter(ImageWriter i)
	{
		imageWriter = i;
		return this;
	}
	public Render setRayTracer(RayTracerBasic r)
	{
		rayTracer = r;
		return this;
	}

	
	
	//nun of rays to send for improvement
	int numbergrid = 0;
	public void setImpovement(int n)
	{
		numbergrid = n;
	}
	
	/*
	 * check if all the fields is set
	 * and paint the picture (geometries and background)
	 */
	
	
	public void renderImage()
	{	 
		if(camera==null || imageWriter==null || rayTracer==null)
			throw new IllegalArgumentException("a field isnt set");
		
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		//all lines
		for(int i = 0; i < nY; i++) //all rows
		{	
			for(int j= 0;j < nX; j++) //all columns
			{    
//				if(i==130 && j==130)
//					System.out.println("jj");
				
				if(numbergrid <= 1) {
					//find color of pixel
					Ray ray = camera.ConstructRayThroughPixel(nX, nY, j, i);
					//RayTracerBasic rayTracer =  RayTracerBasic(scene);
	                imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
				}
				else 
	            	//paint pixel
	            	imageWriter.writePixel(j, i, gridPixel(nX,nY,j,i));
			}
		}
	}
	
	

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
			sumColors = sumColors.add(rayTracer.traceRay(ray));
				
		return sumColors.reduce(rays.size());
	}
	
	
	
	/*
	 * paint the pixels of grid in "color"
	 */
	public void printGrid(int interval, Color color)
	{
		try {
			renderImage();
		} 
		catch (Exception e) { return; }
		
		int nX = imageWriter.getNx();
		int nY = imageWriter.getNy();
		//all lines
		for(int i = 0; i< nY; i++)
		{	
			for(int j= 0;j < nX; j++) //all columns
			{
				//interval is num of pixels in one square
				//color is color of grid
				//paint the grid
				if(j % interval==0 || i % interval == 0)
						imageWriter.writePixel(i, j, color);
			}
		}
	}
	
	
	public void writeToImage()
	{
		if(imageWriter==null) throw new IllegalArgumentException("there is no picture");
		imageWriter.writeToImage();	
	}

}
