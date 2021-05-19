package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

/**
 * Unit tests for geometries.Plane class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class PlaneTest {

	/**
     * Test method for {@link geometries.Plane#Plane(Point3D, Point3D, Point3D)}.
     */
	@Test
	public void testPlane() {
	    // TC01: point 1 and 2 are same
		try {
		      Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(0, 1, 0), new Point3D(0, 1, 0));
	            fail("constructor does not throw an exception- same 2 points");
	        } catch (IllegalArgumentException e) {}
	   // TC02: point on 1 line
	      try {
		      Plane p2 = new Plane(new Point3D(1, 2, 3), new Point3D(2, 3, 4), new Point3D(5, 6, 7));
	            fail("constructor does not throw an exception- all on 1 line");
	        } catch (IllegalArgumentException e) {}
	}
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point3D)}.
     */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
	      // TC01: There is a simple single test here
	      Plane pl = new Plane(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
	      double sqrt3 = Math.sqrt(1d / 3);
//	      assertEquals("Bad normal to Plane", new Vector(sqrt3, sqrt3, sqrt3),
//		     pl.getNormal(new Point3D(0, 0, 1)));
	      Vector right = new Vector(sqrt3, sqrt3, sqrt3);
	      Vector nor = pl.getNormal(new Point3D(0, 0, 1));
	      assertTrue("Bad normal to Plane", nor.equals(right) || nor.equals(right.scale(-1)));
	}
	
	 @Test
	 public void testFindIntersections()
	 {
		 Plane plane = new Plane(new Point3D(1, 0, 0), new Vector (0, 0, 1));
		 
		 // ============ Equivalence Partitions Tests ==============
		// TC01:Ray intersects the plane (1 point)
	        List<Point3D> result = plane.findIntersections(new Ray(new Point3D(-2, 0.5, -1), new Vector(1, 1, 1)));
	        assertEquals("Wrong number of points", 1,result.size());
	        assertEquals("Ray intersects the plane",List.of(new Point3D(-1, 1.5, 0)), result);
	        
	  // TC02:Ray does not intersect the plane (0 points)
	        assertEquals("Ray not intersects the plane", null,
	                plane.findIntersections(new Ray(new Point3D(3, 1, 0.5), new Vector(1, 1, 1))));
	        
	     // =============== Boundary Values Tests ==================
	        
	     // ****Group: Ray is parallel to the plane
	     // TC11: Ray included in the plane (0 points)
	        assertEquals("Ray in the plane", null,
	                plane.findIntersections(new Ray(new Point3D(3, 1, 0), new Vector(1, 1, 0))));
	        	        
	     // TC12: Ray not included in the plane (0 points)
	        assertEquals("Ray not included in the plane", null,
	                plane.findIntersections(new Ray(new Point3D(3, 1, 0.5), new Vector(1, 1, 0))));
	        
	    // ****Group: Ray is orthogonal to the plane
	    //TC13: Ray starts before the plane (1 ptions)
	         result = plane.findIntersections(new Ray(new Point3D(-4, 3, -2), new Vector(0, 0, 1)));
	         assertEquals("Wrong number of points", 1, result.size());
	         assertEquals("Ray is orthogonal to the plane and starts before the plane",List.of(new Point3D(-4, 3, 0)), result);
	        
	   //TC14: Ray starts in the plane (0 ptions)
	        assertEquals("Ray is orthogonal to the plane and starts in the plane", null,
	                plane.findIntersections(new Ray(new Point3D(3, 1, 0), new Vector(0, 0, 1))));
	        
	  //TC15: Ray starts after the plane (0 ptions)
	        assertEquals("Ray is orthogonal to the plane and starts after the plane", null,
	                plane.findIntersections(new Ray(new Point3D(3, 1, 1), new Vector(0, 0, 1))));
	        
	 // ****Group: Special cases
	 //TC16:Ray is neither orthogonal nor parallel to and begins at the plane (0 options)
	        assertEquals("Ray begins at the plane", null,
	                plane.findIntersections(new Ray(new Point3D(3, 1, 0), new Vector(0, 1, 1)))); 
	 //TC17:  
	       assertEquals("Ray begins at the plane", null,
	                plane.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 2)))); 
	        
		 
		 
	 }
}

