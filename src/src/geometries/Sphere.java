package src.geometries;

import src.primitives.Point;
import src.primitives.Vector;

/**
 * A sphere is a geometric object that is defined by a center point and a radius
 * @author David Ochana & Aviad Klein
 */
public class Sphere implements Geometry {
    final Point center;
    final double radius;

    public Sphere(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    /**
     * Returns the center of the circle
     *
     * @return The center of the circle.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the radius of the circle
     *
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

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
