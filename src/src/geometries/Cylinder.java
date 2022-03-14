package src.geometries;


import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;

import java.util.List;

import static src.primitives.Util.isZero;

/**
 * A cylinder is a tube with a height
 *@author David Ochana & Aviad Klein
 */
public class Cylinder extends Tube  implements Geometry {
   final double height;

    public Cylinder(Ray axisRay, double radius, double height) {
        super(axisRay, radius);
        if(radius<0)
            throw new IllegalArgumentException("The radius low then zero!");
        if(height<=0)
            throw new IllegalArgumentException("The height low equal to zero!");

        this.height = height;
    }

    @Override
    public Vector getNormal(Point p) {
        // Finding the normal:
        // n = normalize(p - o)
        // t = v * (p - p0)
        // o = p0 + t * v

        Vector v= axisRay.getDir();
        Point p0 =axisRay.getP0();

        //if p=p0, then (p-p0) is zero vector
        //returns the vector of the base as a normal
        if(p.equals(p0)){
            return v.scale(-1);
        }

        double t= v.dotProduct(p.subtract(p0));
        //check if the point on the bottom
        if(isZero(t)){
            return v.scale(-1);
        }
        //check if the point on the top
        if(isZero(t-height)){
            return v;
        }

        Point o=p0.add(v.scale(t));
        return p.subtract(o).normalize();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                super.toString()+
                "height=" + height +
                '}';
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return The height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Cylinder)) return false;
        if (!super.equals(o)) return false;

        Cylinder cylinder = (Cylinder) o;

        return this.height==cylinder.height;
    }


    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
