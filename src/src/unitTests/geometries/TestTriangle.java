package src.unitTests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.primitives.*;
import  src.geometries.*;

class TestTriangle {
    /**
     * Test method for {@link src.geometries.Triangle#getNormal(Point))}.
     */
    @Test
    public void testGetNormal() {
        Triangle triangle = new Triangle
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );
        double n = Math.sqrt(1d / 3);
        assertEquals(new Vector(n, n, n), triangle.getNormal(new Point(0, 0, 1)), "Bad normal to triangle");

    }

    /**
     * Test method for {@link src.geometries.Triangle#Triangle(Point,Point,Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try{
            new Triangle
                    (
                            new Point(1,0,0),
                            new Point(0,1,0),
                            new Point(0,0,1)
                    );
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct triangle");
        }
        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to b point.
        assertThrows(IllegalArgumentException.class,()->new Triangle
                (
                        new Point(1, 0, 0),
                        new Point(1, 0, 0),
                        new Point(0, 0, 1)
                ),"Constructed a triangle while a point equal to b point");

        //TC03: Test when a point equal to c point.
        assertThrows(IllegalArgumentException.class,()->new Triangle
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(1, 0, 0)
                ),"Constructed a triangle while a point equal to c point");
        //TC04: Test when b point equal to c point.
        assertThrows(IllegalArgumentException.class,()->new Triangle
                (
                        new Point(0, 1, 0),
                        new Point(1, 0, 0),
                        new Point(1, 0, 0)
                ),"Constructed a triangle while c point equal to b point");
    }
}