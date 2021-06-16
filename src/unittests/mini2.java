//package unittests;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import elements.AmbientLight;
//import elements.Camera;
//import elements.DirectionalLight;
//import elements.SpotLight;
//import geometries.Sphere;
//import geometries.Triangle;
//import primitives.Color;
//import primitives.Material;
//import primitives.Point3D;
//import primitives.Vector;
//import renderer.ImageWriter;
//import renderer.RayTracerBasic;
//import renderer.Render;
//import renderer.Render2;
//import scene.Scene;
//
//public class mini2 {
//	private Scene scene = new Scene("Test scene");
//	private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//			.setVpSize(200, 200).setVpDistance(1000);
//
//	/**
//	 * Produce a picture of a sphere and triangle with point light and shade
//	 */
//	@Test
//	public void sphereTriangleInitial() {
//		scene.geometries.add( //
//				new Sphere(new Point3D(0, 0, -200),60) //
//						.setEmmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)), //
//				new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
//						.setEmmission(new Color(java.awt.Color.BLUE)) //
//						.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(30)) //
//		);
//		scene.lights.add( //
//				new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
//						.setkL(1E-5).setkQ(1.5E-7));
//
//		Render2 render = new Render2(). //
//				setImageWriter(new ImageWriter("min2", 400, 400)) //
//				.setCamera(camera) //
//				.setRayTracer(new RayTracerBasic(scene));
//		
//		//render.setImpovement(10);
//		render.setMultithreading(3);
//		render.setDebugPrint();
//		render.renderImage();
//		render.writeToImage();
//	}
//	
//	
//}
//

package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import elements.Camera;
import elements.PointLight;
import elements.SpotLight;
import geometries.Plane;
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


public class mini2 {

	@Test
	public void test() {
		  Scene scene = new Scene("Mini Project Final");
		Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
		.setVpSize(200, 200).setVpDistance(1000);
		scene.geometries.add(
		//train pass-Rails
		
		new Triangle(new Point3D(-150,-70,-130), new Point3D(150,-70,-130), new Point3D(150,-75,-130)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-150,-70,-130), new Point3D(-150,-75,-130), new Point3D(150,-75,-130)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-150,-50,-100), new Point3D(150,-50,-100), new Point3D(150,-45,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-150,-50,-100), new Point3D(-150,-45,-100), new Point3D(150,-45,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-90,-70,-130), new Point3D(-85,-70,-130), new Point3D(-75,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-90,-70,-130), new Point3D(-80,-50,-100), new Point3D(-75,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-40,-70,-130), new Point3D(-35,-70,-130), new Point3D(-26,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(-40,-70,-130), new Point3D(-31,-50,-100), new Point3D(-26,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(10,-70,-130), new Point3D(15,-70,-130), new Point3D(21,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(10,-70,-130), new Point3D(16,-50,-100), new Point3D(21,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(60,-70,-130), new Point3D(65,-70,-130), new Point3D(71,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		new Triangle(new Point3D(60,-70,-130), new Point3D(66,-50,-100), new Point3D(71,-50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(150,75,0)),
		//Wheels
		new Sphere(new Point3D(-60,-44,-80),20) //
		       .setEmmission(new Color(java.awt.Color.GRAY)) //
		     .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0)),
		     new Sphere(new Point3D(-59,-44,-60),10) //
		       .setEmmission(new Color(java.awt.Color.BLACK)) //
		     .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0)),
		   new Sphere(new Point3D(40,-40,-80),25) //
		       .setEmmission(new Color(java.awt.Color.GRAY)) //
		     .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0)),
		     new Sphere(new Point3D(39,-40,-60),12.5) //
		       .setEmmission(new Color(java.awt.Color.BLACK)) //
		     .setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0)),
		
		   
		     //red box
		     new Triangle(new Point3D(-90,-44,-90), new Point3D(70,-44,-90), new Point3D(70,10,-90)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.RED)),
		new Triangle(new Point3D(-90,-44,-90), new Point3D(-90,10,-90), new Point3D(70,10,-90)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.RED)),
		new Triangle(new Point3D(70,-44,-90), new Point3D(80,-34,-110), new Point3D(70,10,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.RED)),
		new Triangle(new Point3D(70,10,-100), new Point3D(80,-34,-110), new Point3D(80,20,-110)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.RED)),
		new Triangle(new Point3D(-90,10,-90), new Point3D(70,10,-100), new Point3D(80,20,-110)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.RED)),
		new Triangle(new Point3D(-90,10,-90), new Point3D(-80,20,-110), new Point3D(80,20,-110)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.RED)),
		
		// blue box
		new Triangle(new Point3D(70,10,-100), new Point3D(80,20,-110), new Point3D(70,50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(50,0,100)),
		new Triangle(new Point3D(80,60,-110), new Point3D(80,20,-110), new Point3D(70,50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(50,0,100)),
		new Triangle(new Point3D(80,60,-110), new Point3D(0-20,50,-100), new Point3D(70,50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(50,0,100)),
		new Triangle(new Point3D(80,60,-110), new Point3D(-20,50,-100), new Point3D(-10,60,-110)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(50,0,100)),
		new Triangle(new Point3D(70,10,-100), new Point3D(70,50,-100), new Point3D(-20,10,-90)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(50,0,100)),
		new Triangle(new Point3D(-20,10,-90), new Point3D(70,50,-100), new Point3D(-20,50,-90)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(50,0,100)),
		
		//steam
		new Triangle(new Point3D(-70,-5,-100), new Point3D(-50,-5,-100), new Point3D(-70,50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.PINK)),
		new Triangle(new Point3D(-70,50,-100), new Point3D(-50,-5,-100), new Point3D(-50,50,-100)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.PINK)),
		new Triangle(new Point3D(-50,0,-100), new Point3D(-50,50,-100), new Point3D(-45,55,-105)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.PINK)),
		new Triangle(new Point3D(-45,55,-105), new Point3D(-50,0,-100), new Point3D(-45,15,-110)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.PINK)),
		new Triangle(new Point3D(-70,50,-100), new Point3D(-50,50,-100), new Point3D(-45,55,-105)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.PINK)),
		new Triangle(new Point3D(-70,50,-100), new Point3D(-65,55,-105), new Point3D(-45,55,-105)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.PINK)),
		//Smoke cloud
		new Sphere(new Point3D(-55,57,-106),9) //
		.setEmmission(new Color(java.awt.Color.GRAY)) //
		.setMaterial(new Material().setShininess(50).setKT(0.4)),
		new Sphere(new Point3D(-45,65,-110),8) //
		.setEmmission(new Color(java.awt.Color.GRAY)) //
		.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0.4)),
		new Sphere(new Point3D(-33,73,-110),13) //
		.setEmmission(new Color(java.awt.Color.GRAY)) //
		.setMaterial(new Material().setKD(0.2).setKS(0.2).setShininess(50).setKT(0.4)),
		
		
		//windows
		new Triangle(new Point3D(-5,40,-80), new Point3D(17.5,40,-80), new Point3D(17.5,20,-80)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(0,200,200)),
		new Triangle(new Point3D(-5,40,-80), new Point3D(-5,20,-80), new Point3D(17.5,20,-80)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(0,200,200)),
		new Triangle(new Point3D(32.5,40,-80), new Point3D(55,40,-80), new Point3D(55,20,-80)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(0,200,200)),
		new Triangle(new Point3D(32.5,40,-80), new Point3D(32.5,20,-80), new Point3D(55,20,-80)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0.24)).setEmmission(new Color(0,200,200)),
		
		
		//Stick
		new Triangle(new Point3D(-59,-45,-50), new Point3D(-59,-39,-50), new Point3D(45,-42,-50)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.YELLOW)),
		new Triangle(new Point3D(-59,-39,-50), new Point3D(45,-36,-50), new Point3D(45,-42,-50)) //
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(60).setKT(0)).setEmmission(new Color(java.awt.Color.YELLOW)),
		new Plane(new Point3D(-150, -150, -250), new Point3D(-100, 100, -250), new Point3D(75, 75, -250))
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20).setKT(0.5)).setEmmission(new Color(java.awt.Color.gray)),
		new Plane(new Point3D(-100,-100,-100), new Point3D(100,-150,-10),new Point3D(0,-50,-200))
		.setMaterial(new Material().setKD(0.5).setKS(0.5).setShininess(20).setKR(0.1)).setEmmission(new Color(java.awt.Color.BLACK))
		);
		/*scene.Light.add(new SpotLight(new Color(400, 200, 200), new Point3D(50,100,-70), new Vector(-1,0, -1)) //
		.setKl(0.00001).setKq(0.00005));*/
		
		scene.lights.add(new PointLight(new Color(java.awt.Color.WHITE), new Point3D(-91,0,-80)) //
		.setkL(0.001).setkQ(0.0005));
		scene.lights.add(new PointLight(new Color(100, 40, 80), new Point3D(-100, 150, 0)) //
		.setkL(4E-5).setkQ(2E-8));
		
		
		ImageWriter imageWriter = new ImageWriter("Mini2", 600, 600);
		Render2 render = new Render2() //
		.setImageWriter(imageWriter) //
		.setCamera(camera) //
		.setRayTracer(new RayTracerBasic(scene)).setMultithreading(3).setDebugPrint();
		//.setRayTracer(new RayTracerBasic(scene));
		//render.setImpovement(10);
		render.renderImage();
		render.writeToImage();
	}

}