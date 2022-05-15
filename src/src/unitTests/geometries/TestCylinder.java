package src.unitTests.geometries;


import org.junit.jupiter.api.Test;
import src.geometries.Cylinder;
import src.geometries.Intersectable.GeoPoint;

import src.geometries.Intersectable;
import src.primitives.*;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestCylinder {
    /**
     * Test method for {@link src.geometries.Cylinder#Cylinder(Ray , double , double) }.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        assertDoesNotThrow(()-> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 2d, 3),"Failed constructor of the correct cylinder");

        // =============== Boundary Values Tests ==================
        //TC02: Test when the radius 0
        assertThrows(IllegalArgumentException.class,()->new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 0d,5),"Constructed a cylinder while a radius can not be 0");
        //TC03:Test when the radius negative, -1
        assertThrows(IllegalArgumentException.class,()->new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), -1d,5),"Constructed a cylinder while a radius can not be negative");
        //TC04: Test when the height 0
        assertThrows(IllegalArgumentException.class,()-> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 5d,0),"Constructed a cylinder while a height can not be 0");
        //TC03:Test when the height negative, -1
        assertThrows(IllegalArgumentException.class,()-> new Cylinder(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 5d,-1),"Constructed a cylinder while a height can not be negative");
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

    /**
     * Test methode for {@link src.geometries.Cylinder#findIntersections(Ray)}(src.primitives.Point)}
     */
    @Test
    void testFindIntersections() {
        Cylinder cylinder = new Cylinder(new Ray(new Point(2,0,0), new Vector(0,0,1)), 1d, 2d);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray
        List<Point> result = cylinder.findIntersections(new Ray(new Point(5,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");


        //TC02 ray starts inside and parallel to the cylinder's ray
        result = cylinder.findIntersections(new Ray(new Point(2.5,0,1), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5,0,2)), result, "Bad intersection point");

        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(2.5,0,-1), new Vector(0,0,1)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(2.5,0,2)), result, "Bad intersection point");

        //TC04 ray starts from outside and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,0.5), new Point(1,0,0.5)), result, "Bad intersection points");

        //TC05 ray starts from inside and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,0.5)), result, "Bad intersection points");

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder
        result = cylinder.findIntersections(new Ray(new Point(5,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC07 ray starts from outside and crosses base and surface
        result = cylinder.findIntersections(new Ray(new Point(1,0,-1), new Vector(1,0,1)));
        assertEquals(2,result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,1), new Point(2,0,0)), result, "Bad intersection points");

        //TC08 ray starts from outside and crosses surface and base
        result = cylinder.findIntersections(new Ray(new Point(4,0,2), new Vector(-1,0,-1)));
        assertEquals(2,result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,1), new Point(2,0,0)), result, "Bad intersection points");


        // =============== Boundary Values Tests ==================

        //TC09 ray is on the surface of the cylinder (not bases)
        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC10 ray is on the base of the cylinder and crosses 2 times
        result = cylinder.findIntersections(new Ray(new Point(-1,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC11 ray is in center of the cylinder
        result = cylinder.findIntersections(new Ray(new Point(2,0,0), new Vector(0,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2,0,2)), result, "Bad intersection points");

        //TC12 ray is perpendicular to cylinder's ray and starts from outside the tube
        result = cylinder.findIntersections(new Ray(new Point(-2,0,0.5), new Vector(1,0,0)));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,0.5), new Point(1,0,0.5)), result, "Bad intersection points");

        //TC13 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)
        result = cylinder.findIntersections(new Ray(new Point(1.5,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,0.5)), result, "Bad intersection points");

        //TC14 ray is perpendicular to cylinder's ray and starts from the center of cylinder
        result = cylinder.findIntersections(new Ray(new Point(2,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,0.5)), result, "Bad intersection points");

        //TC15 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside
        result = cylinder.findIntersections(new Ray(new Point(1,0,0.5), new Vector(1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,0.5)), result, "Bad intersection points");

        //TC16 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside
        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC17 ray starts from the surface to outside
        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(1,1,1)));
        assertNull(result, "Wrong number of points");

        //TC18 ray starts from the surface to inside
        result = cylinder.findIntersections(new Ray(new Point(3,0,0.5), new Vector(-1,0,0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1,0,0.5)), result, "Bad intersection point");

        //TC19 ray starts from the center
        result = cylinder.findIntersections(new Ray(new Point(2,0,0), new Vector(1,0,1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3,0,1)), result, "Bad intersection point");

        //TC20 prolongation of ray crosses cylinder
        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(1,0,0)));
        assertNull(result, "Wrong number of points");

        //TC21 ray is on the surface starts before cylinder
        result = cylinder.findIntersections(new Ray(new Point(3,0,-1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC22 ray is on the surface starts at bottom's base
        result = cylinder.findIntersections(new Ray(new Point(3,0,0), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC23 ray is on the surface starts on the surface
        result = cylinder.findIntersections(new Ray(new Point(3,0,1), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");

        //TC24 ray is on the surface starts at top's base
        result = cylinder.findIntersections(new Ray(new Point(3,0,2), new Vector(0,0,1)));
        assertNull(result, "Wrong number of points");
    }
}