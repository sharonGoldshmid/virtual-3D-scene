package unittests;

import static java.lang.System.out;
import static org.junit.Assert.*;

import org.junit.Test;

//import com.sun.tools.classfile.StackMapTable_attribute.verification_type_info;

import static primitives.Util.*;
import primitives.*;

/**
 * Unit tests for primitives.Vector class
 * @author Sharon goldshmid 212118731
 * @author Ruth Bar Dagan 208317735
 */
public class VectorTest {
	
	
	/**
	 * Test method for {@link primitives.Vector#add(Vector)}.
	 */
	@Test
	public void testAdd() {
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
       Vector addVector = v1.add(v2);
       
       Vector right = new Vector(1, 5, 1);
	    
	    // TC01: Test the function add return the right value
        //assertEquals("add() wrong value", addVector, right);
        assertTrue("add() wrong value", addVector.equals(right));

	}

	/**
	 * Test method for {@link primitives.Vector#subtract(Vector)}.
	 */
	@Test
	public void testSubtract() {
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
       Vector subVector = v1.subtract(v2);
       
       Vector right = new Vector(1, -1, 5);
	    
	    // TC01: Test the function sub return the right value
        //assertEquals("sub() wrong value", subVector, right);
        assertTrue("sub() wrong value", subVector.equals(right));

	}
	/**
	 * Test method for {@link primitives.Vector#scale(double)}.
	 */
	@Test
	public void testScale() {
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
       Vector scal = v1.scale(3);
       
       Vector right = new Vector(3, 6, 9);
	    
	    // TC01: Test the function scal return the right value
        //assertEquals("scale() wrong value", scal, right);
        assertTrue("scale() wrong value", scal.equals(right));
        
        // TC11: test the function throw an exception for scal = 0
        assertThrows("scale() does not throw an exception for scale = 0",
                IllegalArgumentException.class, () -> v1.scale(0));
	}

	/**
     * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
     */
    @Test
    public void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken
        // for simplicity)
        assertEquals("crossProduct() wrong result length", v1.length() * v2.length(), vr.length(), 0.00001);

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue("crossProduct() result is not orthogonal to 1st operand", isZero(vr.dotProduct(v1)));
        assertTrue("crossProduct() result is not orthogonal to 2nd operand", isZero(vr.dotProduct(v2)));

        // =============== Boundary Values Tests ==================
        // TC11: test zero vector from cross-productof co-lined vectors
        Vector v3 = new Vector(-2, -4, -6);
        assertThrows("crossProduct() for parallel vectors does not throw an exception",
                IllegalArgumentException.class, () -> v1.crossProduct(v3));
    }

	/**
	 * Test method for {@link primitives.Vector#dotProduct(Vector)}.
	 */
	@Test
	public void testDotProduct() {
		
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector v2 = new Vector(0, 3, -2);
        double dot2 = v1.dotProduct(v2);
        
        Vector v3 = new Vector(3, 2, 1);
        double dot3 = v1.dotProduct(v3);
	    
	    // TC01: Test that dotProduct() for orthogonal vectors is zero
        assertEquals("dotProduct() for orthogonal vectors is not zero", dot2, 0, 0.00001);

        // TC02: Test dotProduct() return the right value
        assertEquals("dotProduct() wrong value", dot3, 10, 0.00001);
	}

	/**
	 * Test method for {@link primitives.Vector#lengthSquared()}.
	 */
	@Test
	public void testLengthSquared() {
		
		Vector v1 = new Vector(1, 2, 3);

        // ============ Equivalence Partitions Tests ==============
       double len = v1.lengthSquared();
	    
	    // TC01: Test the function lengthSquared return the right value
        assertEquals("lengthSquared() wrong value", len, 14, 0.00001);
    }

	@Test
	/**
	 * Test method for {@link primitives.Vector#length()}.
	 */
	public void testLength() {
		Vector v1 = new Vector(0, 4, 3);

        // ============ Equivalence Partitions Tests ==============
       double len = v1.length();
	    
	    // TC01: Test the function length return the right value
        assertEquals("length() wrong value", len, 5, 0.00001);
	}

	@Test
	/**
	 * Test method for {@link primitives.Vector#normalize()}.
	 */
	public void testNormalize() {
		Vector v1 = new Vector(0, 4, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector vnormal = v1.normalize();
        Vector right = new Vector(0, 0.8, 0.6);

        // TC01: Test that length of vnormal is 1
        assertEquals("normalize() wrong result length", 1, vnormal.length(), 0.00001);

        // TC02: Test the function normalized return the right value
        //assertEquals("normalize() wrong value", right, vnormal);
        assertTrue("normalize() wrong value", right.equals(vnormal));

        // TC11: test the function normalized did change the vector
        //assertEquals("normalize() did not change the vector", right, v1);
        assertTrue("normalize() did not change the vector", right.equals(v1));

	}

	/**
	 * Test method for {@link primitives.Vector#normalized()}.
	 */
	@Test
	public void testNormalized() {
        Vector v1 = new Vector(0, 4, 3);

        // ============ Equivalence Partitions Tests ==============
        Vector vnormal = v1.normalized();
        Vector right = new Vector(0, 0.8, 0.6);

        // TC01: Test that length of vnormal is 1
        assertEquals("normalized() wrong result length", 1, vnormal.length(), 0.00001);

        // TC02: Test the function normalized return the right value
        //assertEquals("normalized() wrong value", right, vnormal);
        assertTrue("normalized() wrong value", right.equals(vnormal));

        // TC11: test the function normalized did not change the vector
        //assertNotEquals("normalized() did change the vector", right, v1);
        assertFalse("normalized() did change the vector", right.equals(v1));
        
	}
}


