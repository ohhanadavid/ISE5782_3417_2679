package src.geometries;

import src.primitives.Point;
import src.primitives.Vector;

public class Plane implements Geometry{
  final Point q0;
   final Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
      q0=p1;
      normal=null;
    }

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     * Returns the initial point of the quadratic
     *
     * @return The method returns a Point object that is a copy of the Point object stored in the q0 field.
     */
    public Point getP0() {
        return q0;
    }

    /**
     * Returns the normal vector of the plane
     *
     * @return The normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane:" +
                "q0=" + q0 +
                ", normal=" + normal ;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
