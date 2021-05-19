
package unittests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import geometries.*;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Unit tests for geometries.Plane class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class GeometriesTest {

@Test
public void testFindIntersections() {
		 // =============== Boundary Values Tests ==================
		 //Empty body collection
		 Geometries  geometries= new Geometries();
		 assertNull("there are no geometries",
		 geometries.findIntersections(new Ray(new Point3D(2, 0, 0), new Vector(1,0, 0))));
		 // No cut shape
		 geometries.add((new Sphere (new Point3D(0.5,0,0),0.5)),(new Triangle (new Point3D(0,1,0),new Point3D(1,0,1),new Point3D(2,0,-2))),(new Sphere (new Point3D(1.5,0.5,0),0.4)));
		 assertNull("there are no intsersections",
		 geometries.findIntersections(new Ray(new Point3D(1,1.5,2), new Vector(-1,1,-2))));
		 // Only one shape is cut
		 Point3D p1 = new Point3D(0.5,0,0.5);
		 Point3D p2 = new Point3D(0.5,0,-0.5);
		 List<Point3D> result =geometries.findIntersections(new Ray(new Point3D(0.5,0,2),
                                                               new Vector(0,0,-2)));
		 assertEquals("Wrong number of points", 2, result.size());
		 if (result.get(0).getx() > result.get(1).getx())
			 result = List.of(result.get(1), result.get(0));
		 assertEquals("Ray crosses sphere", List.of(p1, p2), result);
		 // All shapes are cut
		 List<Point3D> result2 =geometries.findIntersections(new Ray(new Point3D(2.808766329350798,0.704056056795037,0),
                    new Vector(-4.288964655995434,-1.769913205044708,0)));
         assertEquals("Wrong number of points", 5, result2.size());
         // ============ Equivalence Partitions Tests ==============
         // Some (but not all) shapes are cut
         List<Point3D> result3 =geometries.findIntersections(new Ray(new Point3D(3.534625862853762, 0.630864188791841, -0.576790661118888),
                    new Vector(-3,-0.5,0)));
         assertEquals("Wrong number of points", 1, result3.size());
	}

}