package src.unitTests.primitives;

import org.junit.jupiter.api.Test;
import src.primitives.*;

import static org.junit.jupiter.api.Assertions.*;

class TestRay {


    @Test

    /**
     * Test method for {@link Ray.GetPoint}
     */
    void testGetPoint() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: Tests when t = 1 and since the vector is normalized in the beam we multiply in length
        assertEquals(new Point(0d,0d,6d),new Ray(new Point(-12d,0d,0d),new Vector(12d,0d,6d)).getPoint(Math.sqrt(180)),"get point function dosn't return correct point!");

    }
}