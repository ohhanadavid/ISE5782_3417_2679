package src.unitTests.geometries;
import org.junit.jupiter.api.Test;
import src.geometries.*;
import src.primitives.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TestTube {

    /**
     * Test method for {@link src.geometries.Tube#Tube(Ray,double)}
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Test for a proper result
        assertDoesNotThrow(()-> new Tube(new Ray(new Point(1, 2, 3), new Vector(1, 5, 4)), 1),"Failed constructor of the correct Tube");

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

    /**
     * Test method for {@link src.geometries.Tube#findIntersections(Ray)}.
     */
    @Test
    void testFindIntersections() {
//        Tube tube =new Tube(new Ray(new Point(0,1,0),new Vector(0,0,2)),3);
//        Ray r = null;
//        List<Point> result;
//        // ============ Equivalence Partitions Tests ==============
//        //TC01:Ray intersect the tube (2 points)
//        result=tube.findIntersections(new Ray(new Point(5,0,3),new Vector(-10,0,0)));
//        assertEquals(2 ,result.size(), "Wrong number of points");
//        if (result.get(0).getX() > result.get(1).getX())
//            result = List.of(result.get(1), result.get(0));
//
//       assertEquals(List.of(new Point(2.83d,0,3), new Point(-2.83d,0,3)), List.of(result.get(0),result.get(1)),"Wrong points");
//
//        //TC02:Ray outside the tube (0 points)
//        result=tube.findIntersections(new Ray(new Point(-5,0,3),new Vector(0,0,5)));
//        assertNull(result,"Wrong points");
//
//        //TC03:Ray inside the tube (0 points)
//        result=tube.findIntersections(new Ray(new Point(-2,0,3),new Vector(0,0,5)));
//        assertNull(result,"Wrong points");
//
//        //TC04:Ray start on the axis ray(1 points)
//        result=tube.findIntersections(new Ray(new Point(0,1,8),new Vector(5,-1,-5)));
//        assertEquals(1, result.size(), "Wrong number of points");
//        assertEquals(new Point(2.94d, 0.41d, 5.06d), result.get(0), "Wrong points");
//
//        // =============== Boundary Values Tests ==================
//        //TC05:Ray pass 0n tangent point(1 point)
//        result=tube.findIntersections(new Ray(new Point(-10.85d,0.48d,0),new Vector(10.41d,-2.45d,4.14d)));
//        assertEquals(1, result.size(), "Wrong number of points");
//        assertEquals(new Point(0.44d, -1.97d, 4.14d), result.get(0), "Wrong tangent point");
//
//        //TC06:Ray start on the tube(0 point)
//        result=tube.findIntersections(new Ray(new Point(-0.44d,-1.97d,4.14d),new Vector(-3.66d,-12.92d,-4.14d)));
//        assertNull(result,"Wrong point");

    }
}