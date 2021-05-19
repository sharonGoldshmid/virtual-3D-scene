package renderer;

import elements.*;
import primitives.Color;
import primitives.Ray;
import scene.*;

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
		for(int i = 0; i< nY; i++)
		{	
			for(int j= 0;j < nX; j++) //all columns
			{
				//find color of pixel
				Ray ray = camera.ConstructRayThroughPixel(nX, nY, j, i);
				//RayTracerBasic rayTracer =  RayTracerBasic(scene);
                imageWriter.writePixel(j, i, rayTracer.traceRay(ray));
                
			}
		}
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
