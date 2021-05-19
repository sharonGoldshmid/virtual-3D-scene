package unittests;

import static org.junit.Assert.*;
import java.util.List;

import org.junit.Test;
import static primitives.Util.*;
import primitives.*;
import geometries.*;

/**
 * Unit tests for geometries.Tube class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class TubeTest {

	
	/**
	 * Test method for {@link geometries.Tube#getNormal(Point3D)}.
	 */
	@Test
	public void testGetNormal() {
		
		Tube pl = new Tube(new Ray(new Point3D(0, 0, 0), new Vector(0, 0, 1)), 6);
		// ============ Equivalence Partitions Tests ==============
	      // TC01: There is a simple single test here
		Vector tnormal = pl.getNormal(new Point3D(1, 0, 1));
		Vector right = new Vector(1, 0, 0);		
//	      assertEquals("Bad normal to Tube", new Vector(1, 0, 0),
//		     pl.getNormal(new Point3D(1, 0, 10)));
	    assertTrue("Bad normal to Tube", tnormal.equals(right));
	    
	    // TC01: test when the point is front p0
	      try {
	    	  pl.getNormal(new Point3D(0, 1, 0));
	    	  fail("Bad normal to Tube - t does not throw an exception");
	        } catch (IllegalArgumentException e) {}
	}
}
