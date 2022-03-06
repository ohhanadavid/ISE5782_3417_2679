package src.unitTests.primitives;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import src.primitives.*;
import static org.junit.jupiter.api.Assertions.*;
import static src.primitives.Util.isZero;

/**
 * It tests the Vector class.
 * Unit tests for {@link src.primitives.Vector } class.
 *
 */
class TestVector {

    @Test
    @DisplayName("Add two vectors")
    void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        Vector v0 = new Vector(2,-1,4);
        Vector v1 = new Vector(3,2,4);
        Vector v2 = v0.add(v1);
        Vector v = new Vector(5,1,8);
        assertEquals(v2,v, "add() wrong result of adding");
        // =============== Boundary Values Tests ==================
        //not checks the Zero vector because can not build the zero vector
    }

    @Test
    @DisplayName("scale vector in number")
    void testScale() {
        // ============ Equivalence Partitions Tests ==============

        Vector v0=new Vector(1,-3,-2);
        Vector v1=new Vector(-1,3,2);
        Vector v2=new Vector(0.5,-1.5,-1);
        Vector v3=new Vector(5,-15,-10);

        //multiply in number<0
        Vector v=v0.scale(-1);
        assertEquals(v1,v,"scale() wrong result");

        //multiply in 0<number<1
        v=v0.scale(0.5);
        assertEquals(v2,v,"scale() wrong result");

        //multiply in 1<number
        v=v0.scale(5);
        assertEquals(v3,v, "scale() wrong result");


        //=============== Boundary Values Tests ==================
        //multiply in zero
        try {
            v = v0.scale(0);
            assertTrue(v.IsZero(), "scale() wrong result");
            fail("Didn't throw zero exception!");
        } catch ( IllegalArgumentException e) {
            assertTrue(true);
        }
        //multiply in 1
        v=v0.scale(1);
        assertEquals(v,v0, "scale() wrong result");
    }

    @Test
    @DisplayName("cross two vectors")
    void testCrossProduct() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(0, 3, -2);
        Vector v3 = new Vector(-2, -4, -6);
        Vector v5 = new Vector(-5, -1, 0);
        Vector v6 = new Vector(0, 0, 4);
        // ============ Equivalence Partitions Tests ==============

        Vector vr = v1.crossProduct(v2);

        // TC01: Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals( v1.length() * v2.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // TC02: Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v2)), "crossProduct() result is not orthogonal to 2nd operand");

        assertFalse(v1.crossProduct(v2).IsZero(), "ERROR: dotProduct() wrong value in opposite direction");//opposite direction
        assertFalse(v1.crossProduct(v6).IsZero(), "ERROR: dotProduct() wrong value in acute angle");//acute angle
        assertFalse(v1.crossProduct(v5).IsZero(), "ERROR: dotProduct() wrong value in obtuse angle");//obtuse angle

        // =============== Boundary Values Tests ==================
        //check if the vectors is parallel
        try {
            v1.crossProduct(v3);
            fail("crossProduct() for parallel vectors does not throw an exception");
        } catch (Exception ignored) {}

        vr = (v5.crossProduct(v3));
        try {//same vector
            vr =v1.crossProduct(v1);
            assertTrue(vr.IsZero(), "ERROR: crossProduct() of vector with himself");
            fail("Didn't throw parallel exception!");
        } catch ( IllegalArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    @DisplayName("the length of the vector")
    void testLength() {
        Vector v1 = new Vector(1, 2, 3);
        assertEquals(v1.lengthSquared(),14,0.00001, "ERROR: lengthSquared() wrong value");
        assertEquals(new Vector(0, 3, 4).length() , 5,0.00001, "ERROR: length() wrong value");
    }

    @Test
    void testNormalize() {
        Vector v = new Vector(1, 2, 3);
        Vector vNormalize = v.normalize();
        //check if the vector returned is the unit vector
        assertTrue(isZero(vNormalize.length() - 1), "ERROR: normalize() result is not a unit vector");
    }

    @Test
    void testDotProdact() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(-2, -4, -6);
        Vector v3 = new Vector(1, -5, 0);
        Vector v4 = new Vector(3, 6, 9);
        Vector v5 = new Vector(-5, -1, 0);
        Vector v6 = new Vector(0, 0, 4);
        // ============ Equivalence Partitions Tests ==============
        assertTrue(isZero(v1.dotProduct(v2) + 28),"ERROR: dotProduct() wrong value in opposite direction");//opposite direction
        assertTrue(isZero(v1.dotProduct(v4)-42),"ERROR: dotProduct() wrong value in same direction");//same direction
        assertTrue(isZero(v1.dotProduct(v6)-12),"ERROR: dotProduct() wrong value in acute angle");//acute angle
        assertTrue(isZero(v1.dotProduct(v5)+7),"ERROR: dotProduct() wrong value in obtuse angle");//obtuse angle

        // =============== Boundary Values Tests ==================
        assertTrue(isZero(v5.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");//orthogonal vectors
        assertTrue(isZero(v1.dotProduct(v1)-14),"ERROR: dotProduct() of vector with himself");//same vector

    }
}