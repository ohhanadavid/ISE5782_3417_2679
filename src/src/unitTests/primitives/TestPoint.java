package src.unitTests.primitives;

import org.junit.jupiter.api.Test;
import src.primitives.Point;
import src.primitives.Vector;

import static java.lang.System.out;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestPoint {

    // Test operations with points and vectors

    @Test
    void testSubtract() {
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(2,3,4);
        assertEquals(new Vector(1, 1, 1),new Vector (p2.subtract(p1)), "ERROR: Point - Point does not work correctly");

    }

    @Test
    void testAdd() {
        Point p1 = new Point(1, 2, 3);
        assertEquals(p1.add(new Vector(-1, -2, -3)),new Point(0, 0, 0),"ERROR: Point + Vector does not work correctly");

    }
}