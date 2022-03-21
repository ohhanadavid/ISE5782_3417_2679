package src.renderer;

import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import static src.primitives.Util.*;
import static src.primitives.Double3.*;

public class Camera {

    private  Point p0;
    private  Vector to ;
    private  Vector up;
    private  Vector right;
    private  double high;
    private  double width;
    private  double distance;

    /**
     * @param point -> the camera position
     * @param x -> vector to
     * @param z -> vector up
     */
    public Camera (Point point, Vector x , Vector z){
        if(!isZero(z.dotProduct(x)))
            throw new IllegalArgumentException("those two vector not orthogonal");
        p0 = point;
        to = x.normalize();
        up = z.normalize();
        right =x.crossProduct(z).normalize();


    }




    public Point getP0() {
        return p0;
    }

    public Vector getTo() {
        return to;
    }

    public Vector getUp() {
        return up;
    }

    public Vector getRight() {
        return right;
    }

    public double getHigh() {
        return high;
    }

    public double getWidth() {
        return width;
    }

    public double getDistance() {
        return distance;
    }

    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    public Camera setVPSize(double width , double height) {
        this.width=width;
        this.high=height;
        return this;
    }

    public Ray constructRay(int nX,int nY, int j , int i) {
        return  null;
    }
}
