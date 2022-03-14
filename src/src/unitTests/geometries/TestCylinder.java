package src.unitTests.geometries;


import org.junit.jupiter.api.Test;
import src.geometries.Cylinder;

import src.primitives.*;


import static org.junit.jupiter.api.Assertions.*;

class TestCylinder {
    /**
     * Test method for {@link src.geometries.Cylinder#Cylinder(Ray , double , double) }.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        try {
            new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 2d, 3);
        } catch (IllegalArgumentException error) {
            throw new IllegalArgumentException("Failed constructor of the correct cylinder");
        }

        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        try {
            new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 0d,5);
            fail("Constructed a cylinder while a radius can not be 0");
        } catch (IllegalArgumentException ignored) {
        }
        //TC03:Test when the radius negative, -1
        try {
            new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), -1d,5);
            fail("Constructed a cylinder while a radius can not be negative");
        } catch (IllegalArgumentException ignored) {}
        //TC04: Test when the height 0
        try {
            new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 5d,0);
            fail("Constructed a cylinder while a height can not be 0");
        } catch (IllegalArgumentException ignored) { }
        //TC03:Test when the height negative, -1
        try {
            new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 5d,-1);
            fail("Constructed a cylinder while a height can not be negative");
        } catch (IllegalArgumentException ignored) {}
    }




    /**
     * Test methode for {@link src.geometries.Cylinder#getNormal(src.primitives.Point)}
     */
    @Test
    void testGetNormal() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(1,1,0),new Vector(0,0,1)),1d,3d);

        // ============ Equivalence Partitions Tests ==============
        //TC01: Test with point on the top of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 1, 3)),"Bad normal to the top of the cylinder");
        //TC02: Test with point on the bottom of the cylinder
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(1, 1, 0)), "Bad normal to the bottom of the cylinder");

        //TC03: Test with point on the side of the cylinder
        assertEquals(new Vector(0, -1, 0), cylinder.getNormal(new Point(1, 0, 1)), "Bad normal to the side of the cylinder");

        // =============== Boundary Values Tests ==================
        //TC04: Test with point on the top edge of the cylinder
        assertEquals(new Vector(0, 0, 1), cylinder.getNormal(new Point(1, 0, 3)), "Bad normal to the top-edge of the cylinder");

        //TC05: Test with point on the bottom edge of the cylinder
        assertEquals(new Vector(0, 0, -1), cylinder.getNormal(new Point(0, 1, 0)), "Bad normal to the bottom-edge of the cylinder");

    }



}