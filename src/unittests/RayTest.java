package unittests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import primitives.*;

import org.junit.Test;

import geometries.Intersectable.GeoPoint;
import geometries.Sphere;


/**
 * Unit tests for geometries.Plane class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class RayTest {

	/**
     * Test method for {@link primitives.Ray#findClosestPoint(java.util.List)}
     */
	@Test
	public void testFindClosestPoint() {
		List<Point3D> points;
		Point3D result;
		Ray ray = new Ray(new Point3D(0,0,0), new Vector(0,0,1));
				
        // ============ Equivalence Partitions Tests ==============

        // TC01: the point on the middle
		points = List.of(new Point3D(9,8,7),new Point3D(1,1,1),new Point3D(2,2,2),new Point3D(6,4,9));
		result = ray.findClosestPoint(points);
		assertFalse("return null", result.equals(null));
		assertTrue("return wrong point", result.equals(new Point3D(1,1,1)));
		
        // =============== Boundary Values Tests ==================
        // TC02: the point on start
		points = List.of(new Point3D(1,1,1),new Point3D(9,8,7),new Point3D(2,2,2),new Point3D(6,4,9));
		result = ray.findClosestPoint(points);
		assertFalse("return null", result.equals(null));
		assertTrue("return wrong point", result.equals(new Point3D(1,1,1)));
		
        // TC03: the point on finish
		points = List.of(new Point3D(9,8,7),new Point3D(6,4,9),new Point3D(2,2,2),new Point3D(1,1,1));
		result = ray.findClosestPoint(points);
		assertFalse("return null", result.equals(null));
		assertTrue("return wrong point", result.equals(new Point3D(1,1,1)));
		
        // TC03: empty list
		List<Point3D> points2 = List.of();
		result = ray.findClosestPoint(points2);
		assertTrue("not return null", result==null);		
		
	}

	
	@Test
	public void testFindClosestGeoPoint() {
		List<Point3D> points;
		Point3D result;
		Ray ray = new Ray(new Point3D(0,0,0), new Vector(0,0,1));
				
        // ============ Equivalence Partitions Tests ==============

        // TC01: the point on the middle
		points = List.of(new Point3D(9,8,7),new Point3D(1,1,1),new Point3D(2,2,2),new Point3D(6,4,9));
		result = ray.findClosestPoint(points);
		assertFalse("return null", result.equals(null));
		assertTrue("return wrong point", result.equals(new Point3D(1,1,1)));
		
        // =============== Boundary Values Tests ==================
        // TC02: the point on start
		points = List.of(new Point3D(1,1,1),new Point3D(9,8,7),new Point3D(2,2,2),new Point3D(6,4,9));
		result = ray.findClosestPoint(points);
		assertFalse("return null", result.equals(null));
		assertTrue("return wrong point", result.equals(new Point3D(1,1,1)));
		
        // TC03: the point on finish
		points = List.of(new Point3D(9,8,7),new Point3D(6,4,9),new Point3D(2,2,2),new Point3D(1,1,1));
		result = ray.findClosestPoint(points);
		assertFalse("return null", result.equals(null));
		assertTrue("return wrong point", result.equals(new Point3D(1,1,1)));
		
        // TC03: empty list
		List<Point3D> points2 = List.of();
		result = ray.findClosestPoint(points2);
		assertTrue("not return null", result==null);		
	}
	
	@Test
	public void findClosestGeoPoint() {
		List<GeoPoint> listPoint1= new LinkedList();;
		Ray ray =new Ray (new Point3D (0,0,0),new Vector (1,0,0));
		Sphere s=new Sphere(new Point3D (0,0,0),6);
		// ============ Equivalence Partitions Tests ==============
		//T01--The list is empty
		assertNull("The list is empty",
		ray.findClosestGeoPoint(listPoint1));
		// =============== Boundary Values Tests ==================
		    //T02--The nearest point in the middle of the list
		listPoint1.add(0,  new GeoPoint (s,new Point3D(4,0,0)));
		listPoint1.add(0,   new GeoPoint (s,new Point3D(3,0,0)));
		listPoint1.add(0,   new GeoPoint (s,new Point3D(5,0,0)));
		assertEquals("The nearest point in the middle of the list",ray.findClosestGeoPoint(listPoint1), new GeoPoint (s,new Point3D(3,0,0)));
		    //T03--The closest point at the beginning
		listPoint1.add(0,   new GeoPoint (s,new Point3D(2,0,0)));
		assertEquals("The closest point at the beginning",ray.findClosestGeoPoint(listPoint1),  new GeoPoint (s,new Point3D(2,0,0)));
		    //T04--The closest point at the end
		listPoint1.add(4,  new GeoPoint (s,new Point3D(1,0,0)));
		assertEquals("The closest point at the end",ray.findClosestGeoPoint(listPoint1),  new GeoPoint (s,new Point3D(1,0,0)));
	}

}
