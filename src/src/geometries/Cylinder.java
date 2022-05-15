package src.geometries;


import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static src.primitives.Util.isZero;

/**
 * A cylinder is a tube with a height
 *@author David Ochana & Aviad Klein
 */
public class Cylinder extends Tube   {
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
    // Returning the normal of the cylinder at the point p.
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

//    @Override
//    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
//        return null;
//    }


    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        // The procedure is as follows:
        // P1 and P2 in the cylinder, the center of the bottom and upper bases
        Point p1 = axisRay.getP0();
        Point p2 = axisRay.getPoint(height);
        Vector Va = axisRay.getDir();


        List<GeoPoint> list = super.findGeoIntersectionsHelper(ray,maxDistance);

        // the intersections with the cylinder
        List<GeoPoint> result = new LinkedList<>();

        // Step 1 - checking if the intersections with the tube are points on the cylinder
        if (list != null) {
            for (GeoPoint p : list) {
                if (Va.dotProduct(p.point.subtract(p1)) > 0 && Va.dotProduct(p.point.subtract(p2)) < 0)
                    result.add(0, p);
            }
        }

        // Step 2 - checking the intersections with the bases

        // cannot be more than 2 intersections
        if(result.size() < 2) {
            //creating 2 planes for the 2 bases
            Plane bottomBase = new Plane(p1, Va);
            Plane upperBase = new Plane(p2, Va);
            GeoPoint p;

            // ======================================================
            // intersection with the bases:

            // intersections with the bottom bases
            list = bottomBase.findGeoIntersections(ray);

            if (list != null) {
                p = list.get(0);
                // checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p1) < radius * radius)
                    result.add(p);
            }

            // intersections with the upper bases
            list = upperBase.findGeoIntersections(ray);

            if (list != null) {
                p = list.get(0);
                //checking if the intersection is on the cylinder base
                if (p.point.distanceSquared(p2) < radius * radius)
                    result.add(p);
            }
        }
        // return null if there are no intersections.
        return result.size() == 0 ? null : result;
    }



}
