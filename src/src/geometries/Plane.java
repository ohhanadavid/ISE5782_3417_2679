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

    public Point getP0() {
        return q0;
    }

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
