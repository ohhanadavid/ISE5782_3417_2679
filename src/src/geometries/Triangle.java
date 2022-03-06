package src.geometries;

import src.primitives.Point;

/**
 * A triangle is a polygon with three vertices
 * @author David Ochana & Aviad Klein
 */
public class Triangle extends Polygon{

    /**
     * Creates a new triangle from a given vertices of the triangle.
     * @params vertices  - points on the plane.
     * @exception IllegalArgumentException When two of the given vertices are equals.
     */
    public Triangle(Point... vertices) {

        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle: "+this.plane+this.vertices;
    }

}
