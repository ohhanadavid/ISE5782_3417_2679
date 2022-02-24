package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;

public class Tube implements Geometry {
    final Ray axisRay;

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    final Double radius;

    public Tube(Ray axisRay, Double radius) {
        this.axisRay = axisRay;
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Tube)) return false;

        Tube tube = (Tube) o;


        return this.axisRay.equals(tube.axisRay)&&this.radius.equals(tube.radius);
    }



    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    public Double getRadius() {
        return radius;
    }

    public Ray getAxisRay() {
        return axisRay;
    }
}
