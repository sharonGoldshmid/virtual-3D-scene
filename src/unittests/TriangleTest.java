package unittests;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

/**
 * Unit tests for geometries.Triangle class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() {
    	// ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        Triangle pl = new Triangle(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0));
        double sqrt3 = Math.sqrt(1d / 3);
        //assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3),
  	    //pl.getNormal(new Point3D(0, 0, 1)));
        Vector right = new Vector(sqrt3, sqrt3, sqrt3);
        Vector nor = pl.getNormal(new Point3D(0, 0, 1));
        assertTrue("Bad normal to trinagle", nor.equals(right) || nor.equals(right.scale(-1)));
    }
    
    public void testFindIntersections() {
		Triangle triangle = new Triangle(new Point3D(-1, 3, 0), new Point3D(-1, 0, 0), new Point3D(2, 0, 0));
		
		// ============ Equivalence Partitions Tests ==============
		// TC01:Ray inside triangle (1 ptions)
		List<Point3D> result = triangle.findIntersections(new Ray(new Point3D(-1, 8, -5), new Vector(1, -7, 5)));
        assertEquals("Wrong number of points", 1, result.size());
        assertEquals("Ray intersects the triangle",List.of(new Point3D(0, 1, 0)), result);
        
        //TC02:Ray outside against edge (0 ptions)
        assertEquals("Ray not included in the triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-3, -3, -1), new Vector(1, 5, 1))));
        
        //TC03:Ray outside against vertex (0 ptions)
        assertEquals("Ray not included in the triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-3, -3, -1), new Vector(1, 2, 1))));
        
        
     // =============== Boundary Values Tests ==================
        
     //TC11:Ray on edge (0 ptions)
        assertEquals("Ray not included in the triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-2, -3, -1), new Vector(1, 4, 1))));
        
    //TC12:Ray in vertex (0 ptions)
        assertEquals("Ray not included in the triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-2, -3, -1), new Vector(1, 6, 1))));
        
    //TC13: Ray on edge's continuation (0 ptions)
        assertEquals("Ray not included in the triangle", null,
                triangle.findIntersections(new Ray(new Point3D(-2, -3, -1), new Vector(-1, 8, 1))));
		
	}

}

