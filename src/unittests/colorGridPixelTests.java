package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
import elements.SpotLight;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import renderer.Render;
import renderer.Render2;
import scene.Scene;

public class colorGridPixelTests {
	private Scene scene = new Scene("Test scene");
	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
			.setVpSize(200, 200).setVpDistance(1000);

	/**
	 * Produce a picture of a sphere and triangle with point light and shade
	 */
	@Test
	public void sphereTriangleInitial() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200),60) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setkL(1E-5).setkQ(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("gridPixelSphereTriangleInitial", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		
		render.setImpovement(9);
		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void final_test()
	{
		Camera camera = new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, 1, 0));
		camera.setVpDistance(1000).setVpSize(200, 200);
		
		Scene scene = new Scene("Test scene");
		scene.setBackground(new Color(java.awt.Color.LIGHT_GRAY));
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		scene.geometries.add(
				new Sphere(new Point3D(0, 0, 50),50) //
					.setEmmission(new Color(java.awt.Color.BLUE)) //
					.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(100).setKT(0.3)), //
				new Sphere(new Point3D(0, 0, 50),25) //
					.setEmmission(new Color(java.awt.Color.RED)) //
					.setMaterial(new Material().setKD(0.4).setKS(0.3).setShininess(100)), //
				new Triangle(new Point3D(1500, 1500, 1500), new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)) //
					.setEmmission(new Color(java.awt.Color.GRAY)) //
					.setMaterial(new Material().setKR(1)) //
		);
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, -50, 0), new Vector(0, 0, 1)) //
					.setkL(4E-5).setkQ(2E-7));
		scene.lights.add(new DirectionalLight(new Color(500, 300, 0), new Vector(1, -1, 1)));
			
		
		Render render = new Render()//
				.setImageWriter(new ImageWriter("gridPixel final test", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		
		render.setImpovement(3);
		render.renderImage();
		render.writeToImage();
	}
}
