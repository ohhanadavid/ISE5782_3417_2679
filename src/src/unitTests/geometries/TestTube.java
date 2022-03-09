package src.unitTests.geometries;
import org.junit.jupiter.api.Test;
import src.geometries.*;
import src.primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class testTube {

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
        try {
            new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 0);
            fail("Constructed a Tube while a radius can not be 0");
        } catch (IllegalArgumentException ignored) { }
        //TC03:Test when the radius negative, -1
        try {
            new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), -1);
            fail("Constructed a Tube while a radius can not be negative");
        } catch (IllegalArgumentException ignored) {}
    }

    /**
     * Test method for {@link Tube#getNormal(Point)}
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Tube tube= new Tube( new Ray(new Point(1,1,0),new Vector(0,0,1)),1d);

        assertEquals(new Vector(0,-1,0), tube.getNormal(new Point(1,0,2)), "Bad normal to tube");

    }


}