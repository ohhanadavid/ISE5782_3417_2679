package src.geometries;

import src.primitives.Point;
import src.primitives.Vector;

/**
 * A plane is defined by a point and a normal vector
 * @author David Ochana & Aviad Klein
 */
public class Plane implements Geometry{
  final Point q0;
   final Vector normal;


    public Plane(Point p1, Point p2, Point p3) {
      q0=p1;
    if(p1.equals(p2)||p2.equals(p3)||p1.equals(p3))
          throw new IllegalArgumentException("All point should be defendant's ");
      Vector v1=p1.subtract(p2);
      Vector v2=p2.subtract(p3);

      try{
          normal=v1.crossProduct(v2).normalize();
      }catch (IllegalArgumentException e){
          throw new IllegalArgumentException("This three points in the same line!");
      }

    }

    public Plane(Point q0, Vector normal) {
        this.q0 = q0;
        this.normal = normal;
    }

    /**
     * Returns the initial point of the quadratic
     *
     * @return The method returns a Point object that is a copy of the Point object stored in the q0 field.
     */
    public Point getP0() {
        return q0;
    }

    /**
     * Returns the normal vector of the plane
     *
     * @return The normal vector of the plane.
     */
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
        return normal;
    }
}
