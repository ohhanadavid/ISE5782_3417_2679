//package src.unitTests.renderer;
//        import src.renderer.Camera;
//        import src.geometries.Intersectable;
//        import src.geometries.Plane;
//        import src.geometries.Sphere;
//        import src.geometries.Triangle;
//        import org.junit.jupiter.api.Test;
//
//        import src.primitives.Point;
//        import src.primitives.Ray;
//        import src.primitives.Vector;
//
//        import java.util.ArrayList;
//        import java.util.Collections;
//        import java.util.List;
//
//        import static org.junit.jupiter.api.Assertions.assertEquals;
//
///**
// * Integration tests for {@link Camera} class.
// */
//public class CameraIntegrationTest {
//
//    /**
//     * Integration tests of a camera and a sphere.
//     */
//    @Test
//    public void testSphereAndCamera() {
//        Sphere[] spheres = new Sphere[]{
//                //TC01: the sphere is in front of the view plane(2).
//                new Sphere(new Point(0, 0, -2.5), 1),
//                //TC02: the view plane is inside the sphere, all rays should intersect twice(18).
//                new Sphere(new Point(0, 0, -2.5), 2.5),
//                //TC03: the view plane cross the sphere (10).
//                new Sphere(new Point(0, 0, -2), 2),
//                //TC04: the camera is inside the sphere,all rays should intersect only once(9).
//                new Sphere(new Point(0, 0, -1), 4),
//                //TC05: the sphere is behind the camera , no ray should intersect(0).
//                new Sphere(new Point(0, 0, 1), 0.5)
//        };
//        int[] expected = new int[]{2, 18, 10, 9, 0};
//
//        testIntersectsAndCamera(expected, spheres, 3, 3);
//    }
//
//    /**
//     * Integration tests of a camera and a plane.
//     */
//    @Test
//    public void testPlaneAndCamera() {
//        Plane[] planes = new Plane[]{
//                //TC01: the plane is parallel with the view plane, all rays should intersect(9).
//                new Plane(new Point(0, 0, -2), new Vector(0, 0, 1)),
//                //TC02: the plane is in front of the view plane and cross, all rays should intersect(9).
//                new Plane(new Point(0, 0, -1.5), new Vector(0, -0.5, 1)),
//                //TC03: the plane is above the view plane's third row (6).
//                new Plane(new Point(0, 0, -3), new Vector(0, -1, 1))
//        };
//        int[] expected = new int[]{9, 9, 6};
//
//        testIntersectsAndCamera(expected, planes, 3, 3);
//    }
//
//    /**
//     * Integration tests of a camera and a triangle.
//     */
//    @Test
//    public void testTriangleAndCamera() {
//        Triangle[] triangles = new Triangle[]{
//                //TC01:only the center ray should intersect(1).
//                new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2)),
//                //TC02: only the center ray and the top-middle ray should intersect(2).
//                new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2))
//        };
//
//        int[] expected = new int[]{1, 2};
//
//        testIntersectsAndCamera(expected, triangles, 3, 3);
//    }
//
//    /**
//     * Helper method for testing intersectables and a camera, now with 3x3 view plane
//     * @param expectedIntersections All expected intersections for the intersectables (in the same order of intersectables).
//     * @param intersectables Intersectables to check the number of intersections for each one.
//     * @param nX
//     * @param nY
//     */
//    private void testIntersectsAndCamera(int[] expectedIntersections, Intersectable[] intersectables, int nX, int nY) {
//        Camera cam = new Camera(new Point(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, 1, 0))
//                .setVPDistance(1)
//                .setVPSize(3, 3);
//
//        List<List<Point>> intersectionsListOfLists = new ArrayList<>(Collections.nCopies(intersectables.length, null));
//
//        for (int i = 0; i < nY; ++i) {
//
//            for (int j = 0; j < nX; ++j) {
//
//                // creating new ray in every pixel
//                Ray pixelRay = cam.constructRayThroughPixel(nX, nY, j, i);
//
//                // checking every intersectable to find intersections with each one.
//                for (int k = 0; k < intersectables.length; k++) {
//                    List<Point> intersectionsList = intersectables[k].findIntersections(pixelRay);
//
//                    if (intersectionsList == null) {
//                        continue;
//                    }
//                    // make place
//                    if (intersectionsListOfLists.get(k) == null) {
//                        intersectionsListOfLists.set(k, new ArrayList<>());
//                    }
//
//                    intersectionsListOfLists.get(k).addAll(intersectionsList);
//                }
//
//            }
//
//        }
//
//        // checking each intersectable to assert the number of intersections.
//        for (int id = 0; id < intersectables.length; id++) {
//            int sumOfIntersection = 0;
//
//            if (intersectionsListOfLists.get(id) != null) {
//                sumOfIntersection = intersectionsListOfLists.get(id).size();
//            }
//            assertEquals(sumOfIntersection, expectedIntersections[id], "Wrong number of intersectables");
//        }
//    }
//
//}