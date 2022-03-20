package src.geometries;

import src.primitives.*;

import java.util.List;

import static src.primitives.Util.isZero;


/**
 * A tube is a cylinder with a circular cross section
 * @author David Ochana & Aviad Klein
 */
public class Tube implements Geometry {
    final Ray axisRay;
    final double radius;

    public Tube(Ray axisRay, double radius) {
        if(radius<=0)
            throw new IllegalArgumentException("This radius is not legal!");

        this.axisRay = axisRay;
        this.radius = radius;
    }

    /**
     * @param point
     * @return the normal vector of the point on this tube.
     */
    @Override
    public Vector getNormal(Point point) {
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v= axisRay.getDir();
        Point p0 =axisRay.getP0();

        double t= v.dotProduct(point.subtract(p0));

        //if t=0, then t*v is the zero vector and o=p0.
        Point o=p0;

        if(!isZero(t))
        {
            o=p0.add(v.scale(t));
        }

        return point.subtract(o).normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (/*o == null ||*/ !(o instanceof Tube tube)) return false;
        return this.axisRay.equals(tube.axisRay)&&this.radius==tube.radius;
    }



    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    /**
     * @return The radius of the circle.
     */
    public Double getRadius() {
        return radius;
    }

    /**
     * @return The axisRay is being returned.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    /**
     * @param ray
     * @return a list of points that are the intersections of the ray and the tube.
     */
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
