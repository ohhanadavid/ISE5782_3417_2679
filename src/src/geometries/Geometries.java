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
public class Geometries implements Intersectable {

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
     * @param geometries List of Intersectables
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>(Arrays.asList(geometries));
    }

    /**
     * Adds a list of given geometries to the current list.
     * @param geometries List of intersectables to add
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(Arrays.asList(geometries));
    }

    /**
     * @param ray
     * @return a list of points that are intersections of the ray and the geometries that in "geometries" list.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = null;

        for (Intersectable geo : geometries) {
            List<Point> geoPoints = geo.findIntersections(ray);

            if (geoPoints != null) {

                if (result == null) {
                    result = new LinkedList<>();
                }

                result.addAll(geoPoints);
            }
        }

        return result;

    }
}