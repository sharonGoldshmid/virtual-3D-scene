package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.*;

public class finalTest {

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
				.setImageWriter(new ImageWriter("final test", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	
}