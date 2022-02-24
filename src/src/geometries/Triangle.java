package src.geometries;

import src.primitives.Point;

public class Triangle extends Polygon{
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle: "+this.plane+this.vertices;
    }

}
