package src.geometries;

import src.primitives.Point;
import src.primitives.Vector;

/**
 * A triangle is a polygon with three vertices
 * @author David Ochana & Aviad Klein
 */
public class Triangle extends Polygon{

    /**
     * Creates a new triangle from a given vertices of the triangle.
     * @params Point  - points on the plane.
     * @exception IllegalArgumentException When two of the given vertices are equals.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    @Override
    public String toString() {
        return "Triangle: "+this.plane+this.vertices;
    }

    @Override
    public Vector getNormal(Point point) {
       return super.getNormal(point);
    }
}
