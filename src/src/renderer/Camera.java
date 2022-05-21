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









//
//
//
//
//
//
//
//        package src.renderer;
//
//        import src.primitives.Color;
//        import src.primitives.Point;
//        import src.primitives.Ray;
//        import src.primitives.Vector;
//
//        import java.util.LinkedList;
//        import java.util.List;
//        import java.util.MissingResourceException;
//
//        import static src.primitives.Util.isZero;
//        import static src.primitives.Util.random;
//
///**
// * Camera object in 3d scene for creating rays through pixels.
// *
//
// */
//public class Camera {
//
//
//    /**
//     * Camera's location.
//     */
//    private Point p0;
//    /**
//     * Camera's upper direction.
//     */
//    private final Vector vUp;
//    /**
//     * Camera's forward direction.
//     */
//    private final Vector vTo;
//    /**
//     * Camera's right direction
//     */
//    private Vector vRight;
//    /**
//     * View plane's width.
//     */
//    private double width;
//    /**
//     * View plane's height.
//     */
//    private double height;
//    private boolean focus = false;
//    private Point focalPix = null;
//    public double disFocal = 0;
//    /**
//     * The distance between the camera and the view plane.
//     */
//    private double distance;
//    /**
//     * The number of rays sent by the camera.
//     */
//    private int numOfRays = 0;
//
//    ImageWriter imageWriter;
//    RayTracerBase rayTracer;
//    /**
//     * Constructs a camera with location, to and up vectors.
//     * The right vector is being calculated by the to and up vectors.
//     *
//     * @param p0  The camera's location.
//     * @param vTo The direction to where the camera is looking.
//     * @param vUp The direction of the camera's upper direction.
//     * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
//     */
//    public Camera(Point p0, Vector vTo, Vector vUp) {
//        if (!isZero(vTo.dotProduct(vUp))) {
//            throw new IllegalArgumentException("Up vector is not Orthogonal with To vector");
//        }
//        this.p0 = p0;
//        this.vTo = vTo.normalize();
//        this.vUp = vUp.normalize();
//        vRight = vTo.crossProduct(vUp);
//    }
//
//    /**
//     * Returns the camera location.
//     */
//    public Point getP0() {
//        return p0;
//    }
//
//    /**
//     * set 3 double number of the point
//     */
//    public Camera setP0(double x, double y, double z) {
//        this.p0 = new Point(x, y, z);
//        return this;
//
//    }
//
//    /**
//     * Returns the camera's forward direction.
//     */
//    public Vector getvTo() {
//        return vTo;
//    }
//
//    /**
//     * Returns the camera's upper direction.
//     */
//    public Vector getvUp() {
//        return vUp;
//    }
//
//    /**
//     * Returns the camera's right direction.
//     */
//    public Vector getvRight() {
//        return vRight;
//    }
//
//    /**
//     * Returns the view plane's width.
//     */
//    public double getWidth() {
//        return width;
//    }
//
//    /**
//     * Returns the view plane's height.
//     */
//    public double getHeight() {
//        return height;
//    }
//
//    /**
//     * Returns the distance between the camera and the view plane.
//     */
//    public double getDistance() {
//        return distance;
//    }
//
//    /**
//     * Set the new view plane's width.
//     *
//     * @throws IllegalArgumentException When width illegal.
//     */
//    public Camera setWidth(double width) {
//        if (width <= 0) {
//
//            throw new IllegalArgumentException("Illegal value of width");
//        }
//        this.width = width;
//        return this;
//    }
//
//    /**
//     * Set the new view plane's height.
//     *
//     * @throws IllegalArgumentException When height illegal.
//     */
//    public Camera setHeight(double height) {
//        if (height <= 0) {
//
//            throw new IllegalArgumentException("Illegal value of width");
//        }
//        this.height = height;
//        return this;
//    }
//
//    /**
//     * Chaining method for setting the view plane's size.
//     *
//     * @param width  The new view plane's width.
//     * @param height The new view plane's height.
//     * @return The camera itself.
//     */
//    public Camera setVPSize(double width, double height) {
//        setWidth(width);
//        setHeight(height);
//        return this;
//    }
//
//    /**
//     * Chaining method for setting the distance between the camera and the view plane.
//     *
//     * @param distance The new distance between the camera and the view plane.
//     * @return The camera itself.
//     * @throws IllegalArgumentException When distance illegal.
//     */
//    public Camera setVPDistance(double distance) {
//        if (distance <= 0) {
//            throw new IllegalArgumentException("Illegal value of distance");
//        }
//
//        this.distance = distance;
//        return this;
//    }
//
//    /**
//     * Constructs a ray through a given pixel on the view plane.
//     *
//     * @param nX Total number of pixels in the x dimension.
//     * @param nY Total number of pixels in the y dimension.
//     * @param j  The index of the pixel on the x dimension.
//     * @param i  The index of the pixel on the y dimension.
//     * @return A ray going through the given pixel.
//     */
//
//
//    public List<Ray> constructRayThroughPixel(int nX, int nY, int j, int i) {
//        Point pIJ = CalculateCenterPointInPixel(nX, nY, j, i);
//        List<Ray> lr = null;
//        Vector vIJ = pIJ.subtract(p0);
//        lr.add(new Ray(p0, vIJ));
//        if (focus && !isFocus(j, i)) {
//            lr = CalculatCornerRayInPixel(pIJ, nX, nY, j, i);
//        }
//        return lr;
//    }
//
//    /**
//     * Calculate the corner ray in pixel
//     *
//     * @param center point
//     * @param nX     Total number of pixels in the x dimension.
//     * @param nY     Total number of pixels in the y dimension.
//     * @param j      The index of the pixel on the x dimension.
//     * @param i      The index of the pixel on the y dimension.
//     * @return List of rays
//     */
//    private List<Ray> CalculatCornerRayInPixel(Point center, int nX, int nY, int j, int i) {
//
//        Point p = center;
//        List<Ray> lcorner = new LinkedList<>();
//
//        //up
//        double yu = nY / (height * 2);
//        //right
//        double xr = nX / (width * 2);
//
//
//        //left up
//        if (!isZero(xr)) {
//            p = center.add(vRight.scale(-xr));
//        }
//        if (!isZero(yu)) {
//            p = center.add(vUp.scale(yu));
//        }
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //right up
//        p = center.add(vRight.scale(xr));
//        p = center.add(vUp.scale(yu));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //left down
//        p = center.add(vRight.scale(-xr));
//        p = center.add(vUp.scale(-yu));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //right down
//        p = center.add(vRight.scale(xr));
//        p = center.add(vUp.scale(-yu));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //left middle
//        p = center.add(vRight.scale(-xr));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //right middle
//        p = center.add(vRight.scale(xr));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //middle up
//        p = center.add(vUp.scale(yu));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//        //middle down
//        p = center.add(vUp.scale(-yu));
//        lcorner.add(new Ray(p0, p.subtract(p0)));
//        p = center;
//
//
//        return lcorner;
//
//    }
//
//    /**
//     * Constructs a ray through a given pixel on the view plane.
//     *
//     * @param X Total number of pixels in the x dimension.
//     * @param Y Total number of pixels in the y dimension.
//     * @param j The index of the pixel on the x dimension.
//     * @param i The index of the pixel on the y dimension.
//     * @return
//     */
//    public Ray constructOneRayPixel(int X, int Y, int j, int i) {
//        Point pCenterPixel = CalculateCenterPointInPixel(X, Y, j, i);
//        return new Ray(p0, pCenterPixel.subtract(p0));
//    }
//
//    /**
//     * The function calculate the center point of the pixel.
//     *
//     * @param nX Total number of pixels in the x dimension.
//     * @param nY Total number of pixels in the y dimension.
//     * @param j  The index of the pixel on the x dimension.
//     * @param i  The index of the pixel on the y dimension.
//     * @return the center point in the pixel.
//     */
//    private Point CalculateCenterPointInPixel(int nX, int nY, int j, int i) {
//        Point pC = p0.add(vTo.scale(distance));
//        Point pIJ = pC;
//
//        double rY = height / nY;
//        double rX = width / nX;
//
//        double yI = -(i - (nY - 1) / 2d) * rY;
//        double xJ = (j - (nX - 1) / 2d) * rX;
//
//        if (!isZero(xJ)) {
//            pIJ = pIJ.add(vRight.scale(xJ));
//        }
//        if (!isZero(yI)) {
//            pIJ = pIJ.add(vUp.scale(yI));
//        }
//        return pIJ;
//    }
//
//
//    /**
//     * Chaining method for setting the  number of rays constructed by the camera.
//     *
//     * @param numOfRays The number of rays constructed.
//     * @return The camera itself.
//     */
//    public Camera setNumOfRays(int numOfRays) {
//        this.numOfRays = numOfRays;
//        return this;
//    }
//
//
//    /**
//     * Adds the given amount to the camera's position
//     *
//     * @return the current camera
//     */
//    public Camera move(Vector amount) {
//        p0 = p0.add(amount);
//        return this;
//    }
//
//    /**
//     * Adds x, y, z to the camera's position
//     *
//     * @return the current camera
//     */
//    public Camera move(double x, double y, double z) {
//        return move(new Vector(x, y, z));
//    }
//
//    /**
//     * Rotates the camera around the axes with the given angles
//     *
//     * @param amount vector of angles
//     * @return the current camera
//     */
//    public Camera rotate(Vector amount) {
//        return rotate(amount.getX(), amount.getY(), amount.getZ());
//    }
//
//
//    /**
//     * Rotates the camera around the axes with the given angles
//     *
//     * @param x angles to rotate around the x axis
//     * @param y angles to rotate around the y axis
//     * @param z angles to rotate around the z axis
//     * @return the current camera
//     */
//    public Camera rotate(double x, double y, double z) {
//        vTo.rotateX(x).rotateY(y).rotateZ(z);
//        vUp.rotateX(x).rotateY(y).rotateZ(z);
//        vRight = vTo.crossProduct(vUp);
//
//        return this;
//    }
//
//    /**
//     * Constructs a ray through a given pixel on the view plane for AA.
//     *
//     * @param nX Total number of pixels in the x dimension.
//     * @param nY Total number of pixels in the y dimension.
//     * @param j  The index of the pixel on the x dimension.
//     * @param i  The index of the pixel on the y dimension.
//     * @return List of rays.
//     */
//    public LinkedList<Ray> constructRayPixelAA(int nX, int nY, int j, int i) {
//        if (isZero(distance))
//            throw new IllegalArgumentException("distance can't be 0");
//
//        LinkedList<Ray> rays = new LinkedList<>();
//
//        double rX = width / nX;
//        double rY = height / nY;
//
//        double randX, randY;
//
//        Point pCenterPixel = CalculateCenterPointInPixel(nX, nY, j, i);
//        rays.add(new Ray(p0, pCenterPixel.subtract(p0)));
//        if (focus && !isFocus(j, i))
//            rays.addAll(CalculatCornerRayInPixel(pCenterPixel, nX, nY, j, i));
//
//        Point pInPixel;
//        for (int k = 0; k < numOfRays; k++) {
//            randX = random(-rX / 2, rX / 2);
//            randY = random(-rY / 2, rY / 2);
//            pInPixel = new Point(pCenterPixel.getX() + randX, pCenterPixel.getY() + randY, pCenterPixel.getZ());
//            rays.add(new Ray(p0, pInPixel.subtract(p0)));
//        }
//        return rays;
//    }
//
//    /**
//     * set the focus
//     *
//     * @param fp     point
//     * @param length
//     * @return the camera itself.
//     */
//    public Camera setFocus(Point fp, double length) {
//        focalPix = fp;
//        disFocal = length;
//        focus = true;
//        return this;
//    }
//
//    /**
//     * check if it's focus
//     * @param j
//     * @param i
//     * @return
//     */
//    private boolean isFocus(int j, int i) {
//        return focalPix.getX() <= j &&
//                j <= focalPix.getX() + disFocal &&
//                focalPix.getY() <= i &&
//                i <= focalPix.getY() + disFocal;
//    }
//
//    public  Camera setImageWriter(ImageWriter imageWriterT) {
//        this.imageWriter = imageWriterT;
//        return this;
//    }
//    public  Camera setRayTracer(RayTracerBase rayTracerT) {
//        this.rayTracer = rayTracerT;
//        return this;
//    }
//
//
//    public Camera renderImage() {
//
//        //check that all the parameters OK
//        try {
//
//            if (imageWriter == null) {
//                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
//            }
//            if (rayTracer == null) {
//                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
//            }
//
//            //Rendering the image
//            int nX = imageWriter.getNx();
//            int nY = imageWriter.getNy();
//
//            for (int i = 0; i < nY; i++) {
//                for (int j = 0; j < nX; j++) {
//                    imageWriter.writePixel(j, i, castRay(nX, nY, j, i));
//                }
//            }
//        } catch (MissingResourceException e) {
//            throw new UnsupportedOperationException("Not implemented yet " + e.getClassName());
//        }
//        return this;
////    }
////        private Color castRay(int nX, int nY, int j, int i)
////    {
////        Ray ray = constructRayThroughPixel(nX, nY, j, i);
////        return rayTracer.traceRay(ray);
////    }
//        RayTracerBase rayTracerBase = null;
//        /**
//         * Casts a ray through a given pixel and writes the color to the image.
//         *
//         * @param nX  the number of columns in the picture
//         * @param nY  the number of rows in the picture
//         * @param col the column of the current pixel
//         * @param row the row of the current pixel
//         */
//        private void castRay(int nX, int nY, int col, int row) {
//            LinkedList<Ray> rays = constructRayPixelAA(nX, nY, col, row);
//            Color pixelColor = rayTracerBase.averageColor(rays);
//            imageWriter.writePixel(col, row, pixelColor);
//        }
//
//    //Turn on the function of the imageWriter writeToImage
//    public void writeToImage(){
//
//        if(imageWriter==null) {
//            throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
//        }
//        imageWriter.writeToImage();
//
//    }
//}
//
//


