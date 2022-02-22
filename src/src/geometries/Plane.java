package src.geometries;

import src.primitives.Point;
import src.primitives.Vector;

public class Plane implements Geometry{
    Point p0;
    Vector normal;

    public Plane(Point point, Point point1, Point point2) {

    }

    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
