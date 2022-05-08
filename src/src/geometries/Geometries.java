package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
* Geometries class is a collection of intersectables and can calculate their intersections.
* It is using Composite design.
* @author David Ochana & Aviad Klein
*/
public class Geometries extends Intersectable {

    private final List<Intersectable> geometries;

    /**
     * Default constructor.
     * Creates an empty list of geometries.
     */
    public Geometries() {
        geometries = new LinkedList<>();
    }

    /**
     * Creates a list of given geometries.
     *
     * @param geometries List of Intersectables
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     * Adds a list of given geometries to the current list.
     *
     * @param geometries List of intersectables to add
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }



    /**
     * If the ray intersects with one of the geometries, add the intersection points to the list of intersection points
     *
     * @param ray The ray that intersects the geometry.
     * @return A list of GeoPoints
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> intersection = null;
        for (var geometry : this.geometries) {
            List<GeoPoint> geometryIntersection = geometry.findGeoIntersections(ray);

            if (geometryIntersection != null) {
                if (intersection == null)
                    intersection = new LinkedList<>();
                intersection.addAll(geometryIntersection);
            }
        }
        return intersection;
    }
}

