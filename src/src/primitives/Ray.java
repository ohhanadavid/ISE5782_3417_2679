package src.primitives;

import src.geometries.Intersectable.*;
import java.util.List;
import java.util.Objects;

import static src.primitives.Util.alignZero;
import static src.primitives.Util.isZero;

/**
 * A ray is a line that starts at a point and has a direction
 * @author David Ochana & Aviad Klein
 */
public class Ray {
    final  Point p0;
    final Vector dir;
    private static final double DELTA = 0.1;

    // A constructor. It is a method that is called when an object is created.
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();

    }

    /**
     * New constructor
     * @param head
     * @param head
     * @param direction
     * @param normal
     */
    public Ray(Point head, Vector direction, Vector normal) {
        if (direction.dotProduct(normal) == 0) {
            p0 = head;
            dir = direction;
        } else {
            int sign = 1;
            // if (direction.dotProduct(normal)>0)
            // sign=1;
            if (direction.dotProduct(normal) < 0)
                sign = -1;
            p0 = head.add(normal.scale(sign * DELTA));
            dir = direction;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        if(!(o instanceof Ray ray)) return false;
        return this.dir.equals(ray.dir)&&this.p0.equals(ray.p0);
    }

    @Override
    public String toString(){
        return "Point:"+p0.toString()+"\n"+"Vector:"+dir.toString();
    }


    /**
     * @return the value of the instance variable p0.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * @return The direction vector of the particle.
     */
    public Vector getDir() {
        return dir;
    }

//    /**
//     * Gets a point on the ray by calculating p0 + t*v.
//     * @param t A scalar to calculate the point.
//     * @return A point on the ray.
//     */
//    public Point getPoint (double t){
//        return p0.add(dir.scale(t));
//    }

    /**
     * Refactoring must be performed for the calculation code of a point on a ray:
     * P = p0 + t∙v.
     * Used wherever required in the implementations of findIntersections function.
     *
     * @param t The distance to be calculated for the ray from its head
     *
     * @return The 3D-point on the ray that is at a distance of t from the head of the ray
     */
    public Point getPoint(double t) {
        if (!isZero(alignZero(t))) {
            return getP0().add(dir.scale(alignZero(t)));
        }
        return getP0();
    }

    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ?null
                : findClosestGeoPoint(points.stream().map(p->new GeoPoint(null,p)).toList()).point;
    }

//    /**
//     *
//     * @param intersections
//     * @return
//     */
//    public GeoPoint findClosestGeoPoint(List<GeoPoint> intersections){
//        double minDistance = Double.MAX_VALUE;
//        double d;
//        GeoPoint closePoint = null;
//
//        if(intersections==null){
//            return null;
//        }
//
//        for (GeoPoint geoP : intersections) {
//            d = geoP.point.distance(p0);
//            //check if the distance of p is smaller then minDistance
//            if (d < minDistance) {
//                minDistance = d;
//                closePoint = geoP;
//            }
//        }
//        return closePoint;
//    }
    /**
     * calculate and return closest GeoPoint from the list of GeoPoints to the head of the ray
     *
     * @param geoPoints list of GeoPoints
     *
     * @return closest GeoPoint from the list of GeoPoints to the head of the ray
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPoints){
        GeoPoint minPoint = null;
        if(geoPoints != null) {
            double distance = Double.POSITIVE_INFINITY;
            for (GeoPoint p : geoPoints) {
                double temp = p.point.distance(p0);
                if (temp < distance) {
                    distance = temp;
                    minPoint = p;
                }
            }
        }
        return minPoint;
    }


}
