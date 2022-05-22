package src.renderer;

import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import src.scene.Scene;

import java.util.MissingResourceException;

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
    ImageWriter imageWriter;
    RayTracerBase rayTracer;
    /**
     * The number of rays sent by the camera.
     */
    private int numOfRays = 0;
    private boolean focus = false;

    public Camera setP0(Point p0) {
        this.p0 = p0;
        return this;
    }

    private Point focalPix = null;
    public double disFocal = 0;

    /**
     * set the focus
     *
     * @param fp     point
     * @param length
     * @return the camera itself.
     */
    public Camera setFocus(Point fp, double length) {
        focalPix = fp;
        disFocal = length;
        focus = true;
        return this;
    }

    /**
     * check if it's focus
     * @param j
     * @param i
     * @return
     */
    private boolean isFocus(int j, int i) {
        return focalPix.getX() <= j &&
                j <= focalPix.getX() + disFocal &&
                focalPix.getY() <= i &&
                i <= focalPix.getY() + disFocal;
    }

    public boolean isFocus() {
        return focus;
    }

    public Camera setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
        return this;
    }

    public int getNumOfRays() {
        return numOfRays;
    }

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

    public Camera (Point p, Vector x, Vector z, double height, double width){
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



    public  Camera setImageWriter(ImageWriter imageWriterT) {
        this.imageWriter = imageWriterT;
        return this;
    }
    public  Camera setRayTracer(RayTracerBase rayTracerT) {
        this.rayTracer = rayTracerT;
        return this;
    }


    public Camera renderImage(){

        //check that all the parameters OK
        try {

            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }

            //Rendering the image
            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            for (int i = 0; i < nY; i++) {
                for (int j = 0; j < nX; j++) {
                    imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
                }
            }
        }
        catch (MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet " + e.getClassName());
        }
        return this;

    }

    private Color castRay(int nX, int nY, int j, int i)
    {
        Ray ray = constructRayThroughPixel(nX, nY, j, i);
        return rayTracer.traceRay(ray);
    }

    /**
     * The function make the grid lines
     * @param interval between the lines
     * @param color of the lines
     */
    public void printGrid(int interval, Color color){
        if(imageWriter==null) {
            throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {

                if( i % interval == 0 || j% interval ==0) {
                    imageWriter.writePixel(j,i,color);
                }
            }
        }
    }
    //Turn on the function of the imageWriter writeToImage
    public void writeToImage(){

        if(imageWriter==null) {
            throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
        }
        imageWriter.writeToImage();

    }
    /**
     * Adds the given amount to the camera's position
     *
     * @return the current camera
     */
    public Camera move(Vector amount) {
        p0 = p0.add(amount);
        return this;
    }

    /**
     * Adds x, y, z to the camera's position
     *
     * @return the current camera
     */
    public Camera move(double x, double y, double z) {
        return move(new Vector(x, y, z));
    }

    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param amount vector of angles
     * @return the current camera
     */
    public Camera rotate(Vector amount) {
        return rotate(amount.getX(), amount.getY(), amount.getZ());
    }


    /**
     * Rotates the camera around the axes with the given angles
     *
     * @param x angles to rotate around the x axis
     * @param y angles to rotate around the y axis
     * @param z angles to rotate around the z axis
     * @return the current camera
     */
    public Camera rotate(double x, double y, double z) {
        vTo.rotateX(x).rotateY(y).rotateZ(z);
        vUp.rotateX(x).rotateY(y).rotateZ(z);
        vRight = vTo.crossProduct(vUp);

        return this;
    }



}
