package src.unitTests.geometries;

import org.junit.jupiter.api.Test;
import src.geometries.*;
import src.primitives.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link src.geometries.Plane} class.
 */
class TestPlane {

    /**
     * Test method for {@link Plane#Plane(Point,Point,Point)}.
     */
    @Test
    void testConstractor(){

        // ============ Equivalence Partitions Tests ==============
        // TC01: Test for a proper result.
        try{
            new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        }catch (IllegalArgumentException e){
            // This is a fail test. It is a test that is expected to fail.
            fail("The constarctor throw error for nothing");
        }
        // ============ Boundary Values Tests =============
        // TC02: Test when a point equal to all points.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(1,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC03: Test when b point equal to a point.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(1,0,0),new Point(2,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC04: Test when a point equal to c point.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(2,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC05: Test when a point equal to all points.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(1,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");
        // TC06: Test when b point equal to c point.
        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(2,0,0),new Point(1,0,0),new Point(1,0,0)),
                "The constractor dont throw error exception for the same three points");

        assertThrows(IllegalArgumentException.class,
                ()->new Plane(new Point(1,0,0),new Point(2,0,0),new Point(3,0,0)),
                "The constractor dont throw error exception for the  three points in the same line");

    }


    /**
     * Test method for {@link Plane#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        Plane plane = new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        double n = Math.sqrt(1d/3);
        assertEquals(new Vector(n,n,n),
                plane.getNormal(new Point(0,0,1)),
                "This normal bad for plane");
    }

    @Test

    /**
     * Test method for {@link Plane.FindIntersections}
     */
    void testFindIntersections() {
        Plane plane = new Plane
                (
                        new Point(1, 0, 0),
                        new Point(0, 1, 0),
                        new Point(0, 0, 1)
                );
        List<Point> result;

        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray intersects the plane (1 points)
        result=plane.findIntersections(new Ray(new Point(0,1,1),new Vector(0,0,-1)));
        assertEquals(result.size(),1,"Wrong number of points");
      //  assertEquals(new Point(0,1,0),result.get(0).twoDotNumber(),"Ray intersects the plane");

        // TC02: Ray doesn't intersect the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0,1,1),new Vector(0,0,1))),"Ray doesn't intersect the plane");

        // =============== Boundary Values Tests ==================
        //**** Group: Ray is parallel to the plane
        //TC11: Ray is included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,-1,0))),"Ray is included in the plane. Ray is parallel to the plane");

        //TC12: Ray isn't included in the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,2), new Vector(1,-1,0))),"Ray isn't included in the plane. Ray is parallel to the plane");

        //**** Group: Ray is orthogonal to the plane
        //TC13: Ray starts before the plane (1 points)
        result=plane.findIntersections(new Ray(new Point(-1,-1,-1),new Vector(1,1,1)));
        double n = 0.33;    //(1/3)

        assertEquals(result.size(),1,"Wrong number of points");
       assertEquals(new Point(n,n,n), result.get(0)," Ray starts before the plane. Ray is orthogonal to the plane");

        //TC14: Ray starts inside the plane
        assertNull(plane.findIntersections(new Ray(new Point(0,0,1), new Vector(1,1,1))),"Ray starts inside the plane. Ray is orthogonal to the plane");

        //TC15: Ray starts after the plane
        assertNull(plane.findIntersections(new Ray(new Point(2,2,2),new Vector(1,1,1))),"Ray starts after the plane. Ray is orthogonal to the plane");

        //**** Group: Special case
        //TC16: Ray begins at the plane (p0 is in the plane, but not the ray)
        assertNull(plane.findIntersections(new Ray(new Point(1,0,0),new Vector(0,0,-1))),"Ray begins at the plane (p0 is in the plane, but not the ray)");

        //TC17: Ray begins in the plane's reference point
        assertNull(plane.findIntersections(new Ray(plane.getP0(),new Vector(1,0,0))),"Ray begins in the plane's reference point");
    }

}