package src.unitTests.geometries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import src.geometries.*;
import src.primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TestTube {

    /**
     * Test method for {@link src.geometries.Tube#Tube(Ray,double)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        try {
            new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 1);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Failed constructor of the correct Tube");
        }

        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        assertThrows(IllegalArgumentException.class,
                ()->new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 0),
                "Constructed a Tube while a radius can not be 0");

        //TC03:Test when the radius negative, -1
        assertThrows(IllegalArgumentException.class,
                ()->new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), -1),
                "Constructed a Tube while a radius can not be negative");

    }

    /**
     * Test method for {@link Tube#getNormal(Point)}
     */
    @Test
    public void testGetNormal() {
        Tube tube = new Tube(new Ray(new Point(2, 2, 2), new Vector(1, 1, 1)), 5);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Wrong normal calculation (in case the point is not across the ray.p0)
        assertEquals(new Vector(Math.sqrt(1/2d),-1 * Math.sqrt(1/2d),0), tube.getNormal(new Point(12,2,7))
                ,"getNormal() - does not work correctly");

        // =============== Boundary Values Tests ==================
        // TC01: Wrong normal calculation (in case the point is across the ray.p0)
        assertEquals(new Vector(Math.sqrt(1/2d),-1 * Math.sqrt(1/2d),0), tube.getNormal(new Point(7, -3, 2))
                ,"getNormal() - does not work correctly (Boundary test)");

    }
}