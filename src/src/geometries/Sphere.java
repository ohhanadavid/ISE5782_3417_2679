package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import static src.primitives.Util.*;
import java.util.List;

/**
 * A sphere is a geometric object that is defined by a center point and a radius
 * @author David Ochana & Aviad Klein
 */
public class Sphere extends Geometry {
    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {
        if(radius<=0)
            throw new IllegalArgumentException("Radius can't be equal or low then 0 !");

        this.center = center;
        this.radius = radius;
    }

    /**
     * @return The center of the circle.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * @return The radius of the circle.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Sphere)) return false;

        Sphere sphere = (Sphere) o;


        return this.center.equals(sphere.center)&&this.radius==sphere.radius;
    }



    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    /**
     * @param point
     * @return the normal vector of the sphere at the given point.
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }



    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        Vector distanceVec;

        try {
            distanceVec = center.subtract(p0);
        } catch (IllegalArgumentException e) {
            return List.of(new GeoPoint(this,ray.getPoint(radius)));
        }
        //distance=dir*distanceVec the distance between p0 and the point with makes 90 degrees with the center
        double distance = alignZero(dir.dotProduct(distanceVec));
//        if(distance-maxDistance<=0)
//            return null;
        //d=distanceVec^2+distance^2 distance between the center and the point that  makes 90 degrees with the center
        double cap;
        if (distance == 0)
            cap = distanceVec.lengthSquared();
        else {
            cap = distanceVec.lengthSquared() - distance * distance;
        }
        double thsquared = alignZero(radius * radius - cap);

        if (thsquared <= 0) return null;
        //th=radius^2-distance^2 between p1 and center
        double th = alignZero(Math.sqrt(thsquared));
        if (th == 0 ) return null;

        double t1 = alignZero(distance - th);
        double t2 = alignZero(distance + th);
        if (t1 <= 0 && t2 <= 0) return null;

        if (t1 > 0 && t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1)),new GeoPoint(this, ray.getPoint(t2))); //P1 , P2
        if (t1 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t1))); //just one point
        if (t2 > 0)
            return List.of(new GeoPoint(this,ray.getPoint(t2)));
        return null;
    }
}
