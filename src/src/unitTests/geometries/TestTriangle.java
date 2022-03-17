package src.unitTests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import src.primitives.*;
import  src.geometries.*;

import java.util.List;

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

    @Test

    /**
     * Test method for {@link Triangle.FindIntersections}
     */
    void testFindIntersections() {
        Triangle triangle = new Triangle(
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(0, 0, 1));
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        //TC01: Ray intersects the triangle
        result = triangle.findIntersections(new Ray(new Point(-1, -1, -2), new Vector(1, 1, 2)));

        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(new Point(0.25d, 0.25d, 0.5d), result.get(0), "Ray doesn't intersect the triangle");

        //TC02:Ray outside against vertex
        assertNull(triangle.findIntersections(new Ray(new Point(-2, -2, -2), new Vector(1, 1, 2))), "Ray isn't outside against vertex");

        //TC03: Ray outside against edge
        assertNull(triangle.findIntersections(new Ray(new Point(-1, -2, -2), new Vector(1, 1, 2))), "Ray isn't outside against edge");

        //TC04:Ray inside the triangle
        assertNull(triangle.findIntersections(new Ray(new Point(0.5, 0.5, 0.2), new Vector(0.5, 0.5, 1.8d))), "Ray  isn't inside the triangle");

        // ============ Boundary Values Tests =============
        //TC11: Ray On edge
        assertNull(triangle.findIntersections(new Ray(new Point(0,0.5d,0.5d),new Vector(-2.9d,0.85d,-0.5d))),"Ray On edge");
        //TC12: Ray in vertex
        assertNull(triangle.findIntersections(new Ray(new Point(1,0,0),new Vector(0.32d,-0.09d,0))),"Ray On vertex");
        //TC13: Ray On edge's continuation
        assertNull(triangle.findIntersections(new Ray(new Point(0,-0.5d,1.5d),new Vector(-2.31d,-1d,-1.5d))),"Ray On edge's continuation");

    }
}