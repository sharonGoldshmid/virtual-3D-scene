/**
 * 
 */
package unittests;

import org.junit.Test;

import elements.*;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 * 
 * @author dzilb
 */
public class finalTestHadasa {
	private Scene scene = new Scene("Test scene");
	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(200, 200).setVpDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60)), //
				new Sphere(new Point3D(60, 50, -50), 30) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
				.setkL(4E-5).setkQ(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void AllShapesAndEffects() {
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) 
				.setVpSize(200, 200).setVpDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.PINK), 0.30));

		scene.geometries.add( 
				new Triangle(new Point3D(-90,-90,2), new Point3D(-90,0,2), new Point3D(0,-90,2)) 
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.5)).setEmmission(new Color(java.awt.Color.RED)), //
				new Triangle(new Point3D(-80,-80,-20), new Point3D(-80,10,-20), new Point3D(10,-80,-20)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.7)).setEmmission(new Color(java.awt.Color.RED)),//
				new Plane(new Point3D(-150, -150, -115), new Point3D(-100, 100, -140), new Point3D(75, 75, -150)) 
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20)),//
				new Sphere(new Point3D(70, 60, -50),50) //
						.setEmmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(30).setKT(0.6)));
		
		
		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(10, 70, 0), new Vector(0, 0, -1)) //
				.setkL(0.001).setkQ(0.0005));

		scene.lights.add(new PointLight(new Color(700, 400, 400), new Point3D(70, 60, -50)));
		//scene.Light.add(new PointLight(new Color(700, 400, 400), new Point3D(-90,0, -2)));
		
		ImageWriter imageWriter = new ImageWriter("AllShapesAndEffects", 600, 600);
		Render render = new Render() //
				.setImageWriter(imageWriter) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));

		render.renderImage();
		render.writeToImage();
	}
}
