package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;

import java.util.List;

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
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result =plane.findGeoIntersectionsHelper(ray);
        if(result == null)
            return null;
        result.get(0).geometry=this;

        Point P0=ray.getP0();
        Vector v=ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(P0);//(P0->p1)
        Vector v2 = p2.subtract(P0);//(P0->p2)
        Vector v3 = p3.subtract(P0);//(P0->p3)

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double s1 = v.dotProduct(n1);
        double s2 = v.dotProduct(n2);
        double s3 = v.dotProduct(n3);

        if((s1>0 && s2>0 && s3>0 )|| (s1<0 && s2<0 && s3<0))
        {
            return result;
        }
        return super.findGeoIntersectionsHelper(ray);
    }
}
