package unittests;

import org.junit.Test;

import elements.*;
import geometries.*;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 * 
 * @author Dan
 */
public class ShadowTests {
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
				setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a Sphere
	 * producing a shading
	 */
	@Test
	public void trianglesSphere() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKS(0.8).setShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKS(0.8).setShininess(60)), //
				new Sphere(new Point3D(0, 0, -115),30) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
						.setkL(4E-4).setkQ(2E-5));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	/**
	 * we created that test to debug the test with two triangles and one sphere
	 */
	@Test
	public void OURtrianglesSphere() {
		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVpSize(200, 200).setVpDistance(6000);

		scene.geometries.add( //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKS(0.8).setShininess(60)), //
				new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
						.setMaterial(new Material().setKS(0.8).setShininess(60)), //
				new Sphere(new Point3D(0, 0, -115),30) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
						.setkL(4E-4).setkQ(2E-5));

		Render render = new Render() //
				.setImageWriter(new ImageWriter("OURshadowTrianglesSphere", 600, 600)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	
	/**
	 * all those tests is for trying move the triangle and the light:
	 */
	@Test
	public void sphereTriangleLightMove1() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200),60) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		
		Vector v = camera.getVRight().scale(-30).add(camera.getVUp().scale(-30)).add(camera.getVTo().scale(30));
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200).add(v), new Vector(1, 1, -3)) //
						.setkL(1E-5).setkQ(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowLightMove1", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void sphereTriangleLightMove2() {
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200),60) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)), //
				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		
		Vector v = new Vector(1, 1, -3).scale(-20);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200).add(v), new Vector(1, 1, -3)) //
						.setkL(1E-5).setkQ(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowLightMove2", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void sphereTriangleTriMove1() {
		Vector v = camera.getVRight().scale(15).add(camera.getVUp().scale(15));
		Point3D p1 = new Point3D(-70, -40, 0).add(v);
		Point3D p2 = new Point3D(-40, -70, 0).add(v);
		Point3D p3 = new Point3D(-68, -68, -4).add(v);
		
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200),60) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)), //
				//new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
				new Triangle(p1,p2,p3) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setkL(1E-5).setkQ(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowTriMove1", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
	
	@Test
	public void sphereTriangleTriMove2() {
		Vector v = camera.getVRight().scale(15).add(camera.getVUp().scale(30)).add(camera.getVTo().scale(50));
		Point3D p1 = new Point3D(-70, -40, 0).add(v);
		Point3D p2 = new Point3D(-40, -70, 0).add(v);
		Point3D p3 = new Point3D(-68, -68, -4).add(v);
		
		scene.geometries.add( //
				new Sphere(new Point3D(0, 0, -200),60) //
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)), //
				//new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
				new Triangle(p1,p2,p3)
						.setEmmission(new Color(java.awt.Color.BLUE)) //
						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
		);
		scene.lights.add( //
				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
						.setkL(1E-5).setkQ(1.5E-7));

		Render render = new Render(). //
				setImageWriter(new ImageWriter("shadowTriMove2", 400, 400)) //
				.setCamera(camera) //
				.setRayTracer(new RayTracerBasic(scene));
		render.renderImage();
		render.writeToImage();
	}
}
