package unittests;

import org.junit.Test;

//import static org.junit.jupiter.api.Assertions.*;

//import org.junit.jupiter.api.Test;

import elements.AmbientLight;
import elements.Camera;
import elements.DirectionalLight;
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


public class mini1 {
	 @Test
	 public void test()
	 {
		 Scene scene = new Scene("test");
		 Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0));
		 camera.setVpDistance(1500);
		 camera.setVpSize(200,250);
		 scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0));
		 scene.setBackground(Color.BLACK);
		
		 
		 Plane plane = new Plane(new Point3D(0, 0, -300), new Vector(0, 0, 1));

		 double i=15, j=3;
		 for(int x=-90, y=140; x<0; x+=j, y-=i, i+=1.5, j+=0.2)
		 {
		 scene.lights.add(new SpotLight(new Color(400, 300, 300),new Point3D(x, y, 150),camera.getVTo()).setkC(1).setkL(0.005).setkQ(0.0005));
		 }
		 i=15;
		 j=-3;
		 for(int x=90, y=140; x>0; x+=j, y-=i, i+=1.5, j-=0.2)
		 {
		 scene.lights.add(new SpotLight(new Color(400, 300, 300),new Point3D(x, y, 150),camera.getVTo()).setkC(1).setkL(0.005).setkQ(0.0005));
		 }
		 
		 //DUBI
		 scene.geometries.add(
		
		 new Sphere(new Point3D(4, 8 ,18),25).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		
		 new Sphere(new Point3D(26, -11, 18),8).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		
		 new Sphere(new Point3D(-14,-12, 18),8).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		
		 new Sphere(new Point3D(-21,16,18),8).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		
		 new Sphere(new Point3D(27,16,18),8).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		 new Sphere(new Point3D(3,39,18),18).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		 new Sphere(new Point3D(11,43,35.71),2.2).setEmmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0).setKR(0)),
		 new Sphere(new Point3D(-4,43,35.94),2.2).setEmmission(new Color(java.awt.Color.BLACK)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0).setKR(0)),
		 //nose
		 //new Triangle(new Point3D(0.2,36.6,37.6),new Point3D(5.7,36.85,37.70),new Point3D(3.33,31.8,36.65)).setEmission(new Color(java.awt.Color.gray)).setMaterial(new Material().setkD(0.3).setkS(0.7).setnShininess(100).setkT(0).setkR(0)),
		 new Sphere(new Point3D(2.699,37.57,37.94),2.5).setEmmission(new Color(java.awt.Color.gray)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0).setKR(0)),
		
		 new Sphere(new Point3D(-8,58,18),7).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0)),
		 new Sphere(new Point3D(14,58,18),7).setEmmission(new Color(77,38,0)).setMaterial(new Material().setKD(0.3).setKS(0.7).setShininess(100).setKT(0.5).setKR(0))
		
		 );
		
		 scene.geometries.add(plane.setEmmission(new Color(255,153,153)).setMaterial(new Material() .setKD(0.5).setKS(0.5).setShininess(1200).setKT(0).setKR(0)));
		 /////////////mirror
		 scene.geometries.add(new Plane(new Point3D(0, -20, 0), new Vector(0, -40, 0)).setEmmission(new Color(0,40,60)).setMaterial(new Material() .setKR(1)));
		
		 
		 
		 DirectionalLight direction_light = new DirectionalLight(new Color(0, 0, 1), new Vector(1, -1, 1));
		 SpotLight spot_light = new SpotLight(new Color(0, 1, 0), new Point3D(4,8,18), new Vector(0,0, 1));//1, 4E-4, 2E-5,;
		 PointLight point_light = new PointLight(new Color(400, 300, 300), new Point3D(0,145,50));
		 //on mirror
		
		 ImageWriter imageWriter = new ImageWriter("mini1", 600, 500);
		
		 scene.lights.add(point_light.setkC(1).setkL(0.05).setkQ(0.00005));
		 scene.lights.add(direction_light);
		 scene.lights.add(spot_light.setkC(1).setkL(4E-4).setkQ(2E-5));
		
		
		 Render render = new Render() //
		 .setImageWriter(imageWriter) //
		 .setCamera(camera) //
		 .setRayTracer(new RayTracerBasic(scene));
		 //render.setImpovement(9);
		
		 render.renderImage();
		 render.writeToImage();
	 }
}