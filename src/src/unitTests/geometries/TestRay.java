package src.unitTests.geometries;

import org.junit.jupiter.api.Test;
import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.LinkedList;
import java.util.List;

/**
 * Unit tests for {@link src.primitives.Ray} class.
 */
public class TestRay {

    /**
     * Test method for {@link Ray#findClosestPoint(List)} .
     */
    @Test
    public void testFindClosestPoint() {
        Ray ray=new Ray(new Point(0,0,10), new Vector(1,10,-100));
        List<Point> lst;
        // ============ Equivalence Partitions Tests ==============
        //TC01: A point in the middle of the list is closest to the beginning of the ray.
        lst=new LinkedList<Point>();
        lst.add(new Point(1,1,-100));
        lst.add(new Point(-1,1,-99));
        lst.add(new Point(0,2,-10));
        lst.add(new Point(0.5,0,-100));

        assertEquals(lst.get(2),ray.findClosestPoint(lst),"");

        // =============== Boundary Values Tests ==================
        //TC01: the list is empty
        lst=null;

        assertNull(ray.findClosestPoint(lst),"The list is empty");

        //TC02: The first point is closest to the beginning of the ray
        lst=new LinkedList<Point>();
        lst.add(new Point(0,2,-10));
        lst.add(new Point(1,1,-100));
        lst.add(new Point(-1,1,-99));
        lst.add(new Point(0.5,0,-100));

        assertEquals(lst.get(0),ray.findClosestPoint(lst),"The first point isn't te closest to the beginning of the ray");

        // TC03: The first point is closest to the beginning of the ray
        lst=new LinkedList<Point>();
        lst.add(new Point(1,1,-100));
        lst.add(new Point(-1,1,-99));
        lst.add(new Point(0.5,0,-100));
        lst.add(new Point(0,2,-10));

        assertEquals(lst.get(3),ray.findClosestPoint(lst),"The last point isn't te closest to the beginning of the ray");

    }
}