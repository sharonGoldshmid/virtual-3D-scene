package unittests;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

/**
 * Unit tests for geometries.Sphere class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class SphereTest {

	/**
	 * Test method for {@link geometries.Sphere#getNormal(Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
	      // TC01: There is a simple single test here
		Sphere pl = new Sphere(new Point3D(7, 3, 4), 5);
	      assertEquals("Bad normal to Sphere", new Vector(0, 0.8, 0.6),
		     pl.getNormal(new Point3D(7, 7, 7)));
	}

	/**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Sphere sphere = new Sphere(new Point3D(1, 0, 0), 1);
        List<Point3D> result;
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                        sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.06515307716504659,0.35505102572168223, 0);
        Point3D p2 = new Point3D(1.5348469228349528, 0.8449489742783177, 0);
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),new Vector(3, 1, 0)));
        //Point3D gp1 = new Point3D(sphere, p1);
        //Point3D gp2 = new Point3D(sphere, p2);
        
        assertEquals("Wrong number of points", 2, result.size());
        if (result.get(0).getx() > result.get(1).getx())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere", List.of(p1, p2), result);
       
        // TC03: Ray starts inside the sphere (1 point)
        List<Point3D> result3 = sphere.findIntersections(new Ray(new Point3D(0.5, 0, 0), new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result3.size());
        assertEquals("Ray in sphere",List.of(new Point3D(2, 0, 0)), result3);
       
        // TC04: Ray starts after the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 1, 0))));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray in sphere",List.of(new Point3D(0.9999999999999998, 0.9999999999999998, 0)), result);
     
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray's line out of sphere", null, sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0.6, 0.8, 0))));
        
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 2, result.size());
        assertEquals("Ray in sphere",List.of(new Point3D(0, 0, 0),new Point3D(2, 0, 0)), result);
      
        // TC14: Ray starts at sphere and goes inside (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(0, 0, 0), new Vector(1, 0, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray in sphere",List.of(new Point3D(2, 0, 0)), result);
        
        
//        // TC15: Ray starts inside (1 points)
//        result = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0), new Vector(1, 0, 0)));
//        assertEquals("Wrong number of points", 1, result.size());
//        assertEquals("Ray start center",List.of(new Point3D(2, 0, 0)), result);

        
        // TC16: Ray starts at the center (1 points)
        result = sphere.findIntersections(new Ray(new Point3D(1, 0, 0), new Vector(0, 1, 0)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray start center",List.of(new Point3D(1, 1, 0)), result);
       
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertEquals("Ray start at sphere and goes outside", null, sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1, 0, 0))));
        
        // TC18: Ray starts after sphere (0 points)
        assertEquals("Ray start after sphere", null, sphere.findIntersections(new Ray(new Point3D(3, 0, 0), new Vector(1, 0, 0))));
        
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertEquals("Ray start before tanget point", null, sphere.findIntersections(new Ray(new Point3D(2, 0, -1), new Vector(0, 0, 1))));
        
        // TC20: Ray starts at the tangent point
        assertEquals("Ray start at tanget point", null, sphere.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(0, 0, 1))));
        
        // TC21: Ray starts after the tangent point
        assertEquals("Ray start after tanget point", null, sphere.findIntersections(new Ray(new Point3D(2, 0, 1), new Vector(0, 0, 1))));
        
        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertEquals("Ray's line is outside, ray is orthogonal to ray start to sphere's center line", null, sphere.findIntersections(new Ray(new Point3D(-2, 0, 0), new Vector(0, 0, 1))));

        
    }



}

