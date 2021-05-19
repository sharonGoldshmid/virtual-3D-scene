package unittests;

import org.junit.Test;


import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Test rendering a basic image
 * 
 * @author Dan
 */
public class RenderTests {
	private Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVpDistance(100) //
			.setVpSize(500, 500);

	/**
	 * Produce a scene with basic 3D model and render it into a png image with a
	 * grid
	 */
	@Test
	public void basicRenderTwoColorTest() {
		Scene scene = new Scene("Test scene");//
		scene.setAmbientLight(new AmbientLight(new Color(255, 191, 191), 1)); //
		scene.setBackground(new Color(75, 127, 90));

		scene.geometries.add(new Sphere(new Point3D(0, 0, -100),50),
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100)), // up
																													// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100)), // up
																													// right
				new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100)), // down
																														// left
				new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100))); // down
																													// right

		ImageWriter imageWriter = new ImageWriter("base render test", 1000, 1000);
		Render render = new Render(); //
		render.setImageWriter(imageWriter); //
		render.setCamera(camera); //
		render.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.YELLOW));
		render.writeToImage();
	}

	// For stage 6 - please disregard in stage 5
	/**
	 * Produce a scene with basic 3D model - including individual lights of the bodies 
	 * and render it into a png image with a grid
	 */
	@Test
	public void basicRenderMultiColorTest() {
		Scene scene = new Scene("Test scene");//
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.2)); //
		Geometries tempGeometries = new Geometries();
		
		Sphere geo1= new Sphere(new Point3D(0, 0, -100),50);
		geo1.setEmmission(new Color(java.awt.Color.CYAN));
		Triangle geo2= new Triangle(new Point3D(-100, 0, -100), new Point3D(0, 100, -100), new Point3D(-100, 100, -100));
		geo2.setEmmission(new Color(java.awt.Color.GREEN));
		Triangle geo3=new Triangle(new Point3D(100, 0, -100), new Point3D(0, 100, -100), new Point3D(100, 100, -100));
		Triangle geo4=new Triangle(new Point3D(-100, 0, -100), new Point3D(0, -100, -100), new Point3D(-100, -100, -100));
		geo4.setEmmission(new Color(java.awt.Color.RED));
		Triangle geo5=new Triangle(new Point3D(100, 0, -100), new Point3D(0, -100, -100), new Point3D(100, -100, -100));
		geo5.setEmmission(new Color(java.awt.Color.BLUE));
		tempGeometries.add(geo1,geo2,geo3,geo4,geo5); 

		scene.setGeometries(tempGeometries);
		
		ImageWriter imageWriter = new ImageWriter("color render test", 1000, 1000);
		Render render = new Render(); //
		render.setImageWriter(imageWriter); //
		render.setCamera(camera); //
		render.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.printGrid(100, new Color(java.awt.Color.WHITE));
		render.writeToImage();
	}
}
