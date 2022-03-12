package src.unitTests.geometries;

import org.junit.jupiter.api.Test;
import src.geometries.Sphere;
import src.primitives.Point;
import src.primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TestSphere {
    /**
     * Test methode for {@link src.geometries.Sphere#Sphere(Point, double)}
     */
    @Test
    void testConstractorSphere(){
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try {
            new Sphere(new Point(1, 2, 3), 5);
        } catch (IllegalArgumentException error) {
            fail("Failed constructor of the correct sphere");
        }

        // ============ Boundary Values Tests =============
        // TC02: Test when the radius is 0.
        assertThrows(IllegalArgumentException.class,()->new Sphere(new Point(1, 2, 3), 0),"Constructed a sphere while the radius is 0");

        // TC03: Test when the radius is negative,-1.
        assertThrows(IllegalArgumentException.class,()->new Sphere(new Point(1, 2, 3), -1),"Constructed a sphere while the radius is negative");


    }

    /**
     * Test method for {@link src.geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(new Point (1,0,1),2);
        assertEquals(new Vector(0,0,1),
                sphere.getNormal(new Point(1,0,2)),
                "Uncoorect normal");


    }
}