/**
 * 
 */
package src.unitTests.geometries;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import src.geometries.*;
import src.primitives.*;

import java.util.List;

/**
 * Testing Polygons
 * 
 * @author Dan
 *
 */
public class TestsPolygon {

	/**
	 * Test method for {@link src.geometries.Polygon#Polygon(src.primitives.Point...)}.
	 */
	@Test
	public void testConstructor() {
		// ============ Equivalence Partitions Tests ==============

		// TC01: Correct concave quadrangular with vertices in correct order
		assertDoesNotThrow(()->new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)),"Failed constructing a correct polygon");

		// TC02: Wrong vertices order
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
				"Constructed a polygon with wrong order of vertices");

		// TC03: Not in the same plane
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
				"Constructed a polygon with vertices that are not in the same plane");

		// TC04: Concave quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
						new Point(0.5, 0.25, 0.5)), //
				"Constructed a concave polygon");

		// =============== Boundary Values Tests ==================

		// TC10: Vertex on a side of a quadrangular
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0.5, 0.5)),
				"Constructed a polygon with vertix on a side");

		// TC11: Last point = first point
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
				"Constructed a polygon with vertice on a side");

		// TC12: Co-located points
		assertThrows(IllegalArgumentException.class, //
				() -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
				"Constructed a polygon with vertice on a side");

	}

	/**
	 * Test method for {@link src.geometries.Polygon#getNormal(src.primitives.Point)}.
	 */
	@Test
	public void testGetNormal() {
		// ============ Equivalence Partitions Tests ==============
		// TC01: There is a simple single test here
		Polygon pl = new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
		double sqrt3 = Math.sqrt(1d / 3);
		assertEquals(new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point(0, 0, 1)), "Bad normal to trinagle");
	}

	/**
	 * Test method for {@link src.geometries.Polygon#findIntersections(Ray)}.
	 */
    @Test
    void testFindIntersections() {
		Polygon polygon = new Polygon(
				new Point(1, 0, 0),
				new Point(0, 1, 0),
				new Point(-2, 0, 0),
				new Point(0,-1,0)
		);
		List<Point> result;
		// ============ Equivalence Partitions Tests ==============
		//TC01: Ray intersects the polygon
		result = polygon.findIntersections(new Ray(new Point(-0.5, -0.5, -1), new Vector(0.5, 0.5, 3)));

		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(new Point(-0.33d, -0.33d, 0d), result.get(0).twoDotNumber(), "Ray doesn't intersect the polygon");

		//TC02:Ray outside against vertex
		assertNull(polygon.findIntersections(new Ray(new Point(0, -2, 0), new Vector(0, 0, 4))), "Ray isn't outside against vertex");

		//TC03: Ray outside against edge
		assertNull(polygon.findIntersections(new Ray(new Point(-1, -1, 0), new Vector(0, 0, 3))), "Ray isn't outside against edge");

		//TC04:Ray inside the polygon
		assertNull(polygon.findIntersections(new Ray(new Point(0, 0, 0), new Vector(-1, 0, 0))), "Ray  isn't inside the polygon");

		// ============ Boundary Values Tests =============
		//TC11: Ray On edge
		result = polygon.findIntersections(new Ray(new Point(-2, 0, 3), new Vector(1.03d, 0.51d, -3)));
		assertEquals(1, result.size(), "Wrong number of points");
		assertEquals(new Point(-0.97d, 0.51d, 0d), result.get(0).twoDotNumber(), "Ray  isn't on edge of the polygon");

		///TC12: Ray in vertex
		assertNull(polygon.findIntersections(new Ray(new Point(0, 1, 0), new Vector(-2d, -1d, 3))),  "Ray  isn't on vertex of the polygon");

		//TC13: Ray On edge's continuation
		assertNull(polygon.findIntersections(new Ray(new Point(-1, 2, 0), new Vector(-1d, -2d, 3))), "Ray  isn't On edge's continuation");


	}
}
