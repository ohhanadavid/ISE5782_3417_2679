package src.renderer;

import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import static src.primitives.Util.*;

/**
 * Camera object in 3d scene for creating rays through pixels.
 *
 * @author David Ochana & Aviad Klein
 */
public class Camera {

    private  Point p0;
    private  Vector vTo;
    private  Vector vUp;
    private  Vector vRight;
    private  double height;
    private  double width;
    private  double distance;

/**
 * Constructs a camera with location, to and up vectors.
 * The right vector is being calculated by the to and up vectors.
 *
 * @param point -> the camera location
 * @param x -> The direction to where the camera is looking.
 * @param z -> The direction of the camera's upper direction.
 * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
*/
    public Camera (Point point, Vector x , Vector z){
        if(!isZero(z.dotProduct(x)))
            throw new IllegalArgumentException("those two vector not orthogonal");
        p0 = point;
        vTo = x.normalize();
        vUp = z.normalize();
        vRight =x.crossProduct(z).normalize();
    }

    public Camera (Point p , Vector x,Vector z,double height,double width){
        if(!isZero(z.dotProduct(x)))
            throw new IllegalArgumentException("those two vector not orthogonal");
        if(height<=0||width<=0)
            throw new IllegalArgumentException("Height or width can't be equal or low then zero! ");
        p0 = p;
        vTo = x.normalize();
        vUp = z.normalize();
        vRight =x.crossProduct(z).normalize();

    }



    /**
     * Returns the camera location.
     */
    public Point getP0() {
        return p0;
    }
    /**
     * Returns the camera's forward direction.
     */
    public Vector getvTo() {
        return vTo;
    }
    /**
     * Returns the camera's upper direction.
     */
    public Vector getvUp() {
        return vUp;
    }
    /**
     * Returns the camera's right direction.
     */
    public Vector getvRight() {
        return vRight;
    }
    /**
     * Returns the view plane's height.
     */
    public double getHeight() {
        return height;
    }
    /**
     * Returns the view plane's width.
     */
    public double getWidth() {
        return width;
    }
    /**
     * Returns the distance between the camera and the view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Method for setting the distance between the camera and the view plane.
     *
     * @param distance The new distance between the camera and the view plane.
     * @return The camera itself.
     * @throws IllegalArgumentException When distance illegal.
     * Builder design pattern.
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }
        this.distance = distance;
        return this;
    }

    /**
     * Method for setting the view plane's size.
     * @param width  The new view plane's width.
     * @param height The new view plane's height.
     * @return The camera itself.
     * Builder design pattern.
     *
     * @throws IllegalArgumentException When height/width illegal.
     */
    public Camera setVPSize(double width , double height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Illegal value of width");
        }
        this.width=width;

        if (height <= 0) {
            throw new IllegalArgumentException("Illegal value of height");
        }
        this.height =height;
        return this;
    }

//    public Ray constructRay(int nX,int nY, int j , int i) {
//        return  null;
//    }
    /**
     * Constructs a ray through a given pixel on the view plane.
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return A ray going through the given pixel.
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pC = p0.add(vTo.scale(distance));
        Point pIJ=pC;

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        if(!isZero(xJ)){
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if(!isZero(yI)){
            pIJ = pIJ.add(vUp.scale(yI));
        }

        Vector vIJ=pIJ.subtract(p0);

        return new Ray(p0,vIJ);
    }

    public void renderImage() {


    }

    public void printGrid(int i, Color color) {
    }

    public void writeToImage() {
    }

    public  Camera setImageWriter(ImageWriter base_render_test) {
        return this;
    }

}
