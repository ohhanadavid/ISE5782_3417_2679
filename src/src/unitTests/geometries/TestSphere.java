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

    }

    /**
     * Test method for {@link src.geometries.Sphere#getNormal(Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Sphere sphere = new Sphere(new Point (1,0,1),2);
        assertEquals(new Vector(0,0,1),sphere.getNormal(new Point(1,0,2)),"Uncoorect normal");


    }
}