package src.unitTests.geometries;

import org.junit.jupiter.api.Test;
import src.geometries.Plane;
import src.primitives.Point;
import src.primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TestPlane {

    @Test
    void testConstractor(){
        try{
            new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        }catch (IllegalArgumentException e){
            // This is a fail test. It is a test that is expected to fail.
            fail("The constarctor throw error for nothing");
        }
        assertThrows(IllegalArgumentException.class,()->new Plane(new Point(1,0,0),new Point(1,0,0),new Point(1,0,0)),"The constractor dont throw error exception for the same three points");
        assertThrows(IllegalArgumentException.class,()->new Plane(new Point(1,0,0),new Point(2,0,0),new Point(3,0,0)),"The constractor dont throw error exception for the  three points in the same line");

    }



    @Test
    void testGetNormal() {
        Plane plane = new Plane(new Point(1,0,0),new Point(0,1,0),new Point(0,0,1));
        double n = Math.sqrt(1d/3);
        assertEquals(new Vector(n,n,n),plane.getNormal(new Point(0,0,1)),"This normal bad for plane");
    }
}