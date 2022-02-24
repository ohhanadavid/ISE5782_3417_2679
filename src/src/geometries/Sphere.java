package src.geometries;

import src.primitives.Point;
import src.primitives.Vector;

public class Sphere implements Geometry {
    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Point getCenter() {
        return center;
    }

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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
