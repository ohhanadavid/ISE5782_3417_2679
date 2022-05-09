package src.geometries;

import src.primitives.*;

import java.util.List;
/**
* Gives interface for an object that is Intersectable.
* @author DavidOchana & AviadKlein
*/
public abstract class Intersectable {

    /**
     * @param ray
     * @return list of intersection points
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream().map(gp -> gp.point).toList();
    }
    /**
     * psd help static class
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
        /**
         * constructor
         * @param geometry
         * @param point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            GeoPoint geoPoint = (GeoPoint) o;

            if (geometry != null ? !geometry.equals(geoPoint.geometry) : geoPoint.geometry != null) return false;
            return point != null ? point.equals(geoPoint.point) : geoPoint.point == null;
        }

        @Override
        public String toString() {
            return "Intersect-able class";
        }
    }

    public final List<GeoPoint> findGeoIntersections (Ray ray){
        return findGeoIntersectionsHelper(ray);
    }

    public final List<GeoPoint> findGeoIntersections (Ray ray, double maxDistance){
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    protected abstract List<GeoPoint> findGeoIntersectionsHelper (Ray ray);

    public List<Point> findIntersection(Ray ray, double maxDistance) {
        var geoList=findGeoIntersectionsHelper(ray,maxDistance);
        return geoList==null?null
                :geoList.stream().map(gp->gp.point).toList();
    }

}


