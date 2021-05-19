package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import elements.Camera;
import geometries.Geometries;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
/**
 * Unit tests for IntegrationTest class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 *
 */
public class IntegrationTest {

	/***
	 * Auxiliary function that calculates the number of points of intersection between the shape and the beam
	 * @param Nx
	 * @param Ny
	 * @param c
	 * @param s
	 * @return How many intersections are there in the function
	 */
	private int helpSphere(int Nx,int Ny,Camera c,Sphere s) 
	{
		 List<Point3D> results;
		 int count=0;
		 for (int i = 0; i < 3; ++i)
		 {
	           for (int j = 0; j < 3; ++j)
	           {
	               results = s.findIntersections(c.ConstructRayThroughPixel(Nx, Ny, j, i));
	               if (results != null)
	                   count += results.size();
	           }
	     }
		 return count;
	}
	/**Auxiliary function that calculates the number of points of intersection between the shape and the beam
	 * *@param Nx
	 * @param Ny
	 * @param c
	 * @param p
	 * @return How many intersections are there in the function
	 * */
	private int helpPlane(int Nx,int Ny,Camera c,Plane p) 
	{
		 List<Point3D> results;
		 int count=0;
		 for (int i = 0; i < 3; ++i)
		 {
	           for (int j = 0; j < 3; ++j)
	           {
	               results = p.findIntersections(c.ConstructRayThroughPixel(Nx, Ny, j, i));
	               if (results != null)
	                   count += results.size();
	           }
	     }
		 return count;
	}
	/**Auxiliary function that calculates the number of points of intersection between the shape and the beam
	 * *@param Nx
	 * @param Ny
	 * @param c
	 * @param t
	 * @return How many intersections are there in the function
	 * */
		 private int helpTriangle(int Nx,int Ny,Camera c,Triangle t) 
			{
				 List<Point3D> results;
				 int count=0;
				 for (int i = 0; i < 3; ++i)
				 {
			           for (int j = 0; j < 3; ++j)
			           {
			               results = t.findIntersections(c.ConstructRayThroughPixel(Nx, Ny, j, i));
			               if (results != null)
			                   count += results.size();
			           }
			     }
				 return count;
	}
		 /***
		  * The function checks whether a function for finding points of intersection with the rays from the camera works correctly
		  */
	@Test
	public void constructRayThroughPixelSPhere()
	{
		
		Sphere s= new Sphere(new Point3D(0,0,-3),1);
		Camera c=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0));
		c=c.setVpDistance(1);
		c=c.setVpSize(3,3);
		// TC01: (2 point)
          assertEquals("Wrong number of points", 2,helpSphere(3,3,c,s) );
          
          Sphere s1= new Sphere(new Point3D(0,0,-2.5),2.5d);
  		Camera c1=new Camera(new Point3D(0,0,0.5),new Vector(0,0,-1),new Vector(0,1,0));
  		c1=c1.setVpDistance(1);
  		c1=c1.setVpSize(3,3);
  	// TC02: (18 point)
  	  assertEquals("Wrong number of points", 18,helpSphere(3,3,c1,s1) );
  	  
  	  Sphere s2= new Sphere(new Point3D(0,0,-2),2);
  	// TC03: (10 point)
  	  assertEquals("Wrong number of points", 10,helpSphere(3,3,c1,s2) );
  	  
  	  Sphere s3= new Sphere(new Point3D(0,0,-2),4);
  	// TC04: (9 point)
  	  assertEquals("Wrong number of points", 9,helpSphere(3,3,c,s3) );
  	  
  	  
  	    Sphere s4= new Sphere(new Point3D(0,0,1),0.5);
  	// TC05: (0 point)
  	  assertEquals("Wrong number of points", 0,helpSphere(3,3,c,s4) );
	}
	/***
	 * The function checks whether a function for finding points of intersection with the rays from the camera works correctly
	 */
	@Test
	public void constructRayThroughPixelPlane()
	{
		Plane p= new Plane(new Point3D(-1,0,-5),new Point3D(5,0,-5),new Point3D(0,1,-5));
		Camera c=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0));
		c=c.setVpDistance(1);
		c=c.setVpSize(3,3);
		// TC06: (9 point)
		 assertEquals("Wrong number of points", 9,helpPlane(3,3,c,p) );
		 
		 Plane p1 =new Plane(new Point3D(0,-10,-4),new Point3D(5,0,-1),new Point3D(0,1,-5));
		// TC07: (9 point)
		 assertEquals("Wrong number of points", 9,helpPlane(3,3,c,p1) );
		 
		 
		 Plane p2 =new Plane(new Point3D(0,1,0),new Point3D(0,-1,-1),new Point3D(-3,0,4));
		// TC08: (6 point)
		 assertEquals("Wrong number of points", 6,helpPlane(3,3,c,p2) );
	}
    /***
     * The function checks whether a function for finding points of intersection with the rays from the camera works correctly
     */
	@Test
	public void constructRayThroughPixelTriangle()
	{
		Camera c=new Camera(new Point3D(0,0,0),new Vector(0,0,-1),new Vector(0,1,0));
		c=c.setVpDistance(1);
		c=c.setVpSize(3,3);
		Triangle t= new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
		// TC09: (1 point)
		assertEquals("Wrong number of points", 1,helpTriangle(3,3,c,t) );
		
		Triangle t1= new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
		// TC10: (2 point)
		assertEquals("Wrong number of points", 2,helpTriangle(3,3,c,t1) );
	}
}
