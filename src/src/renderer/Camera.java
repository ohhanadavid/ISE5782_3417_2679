package src.renderer;

import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import src.scene.Scene;

import java.util.LinkedList;
import java.util.List;
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
    private int numOfRays = 20;
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
 * Constructs a camera with p0, to and up vectors.
 * The right vector is being calculated by the to and up vectors.
 *
 * @param point -> the camera p0
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
     * Returns the camera p0.
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
    /**
     * The function calculate the center point of the pixel.
     *
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return the center point in the pixel.
     */
    private Point CalculateCenterPointInPixel(int nX, int nY, int j, int i) {
        Point pC = p0.add(vTo.scale(distance));
        Point pIJ = pC;

        double rY = height / nY;
        double rX = width / nX;

        double yI = -(i - (nY - 1) / 2d) * rY;
        double xJ = (j - (nX - 1) / 2d) * rX;

        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }
        return pIJ;
    }
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pC = p0.add(vTo.scale(distance));
        Point pIJ=pC;

        double rY = alignZero(height / nY);
        double rX = alignZero(width / nX);

        double yI = alignZero(-(i - (nY - 1) / 2d) * rY);
        double xJ = alignZero((j - (nX - 1) / 2d) * rX);

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
//                    if(checkColor(nX,nY,j,i))
                  // imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
//                    else
                                   imageWriter.writePixel(j,i,castRaysAntiAliasing(nX,nY,j,i));
                }
            }
        }
        catch (MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet " + e.getClassName());
        }
        return this;

    }
    private Color castRaysAntiAliasing(int nX, int nY, int j, int i) {
        List<Ray> beam = constructBeam(nX, nY, j, i);
        return averageColor(beam);
    }
    public Color averageColor(List<Ray> rays){
        Color color=Color.BLACK;
        for( Ray ray:rays){
            color=color.add(rayTracer.traceRay(ray));
        }
        return color.reduce(Double.valueOf(rays.size()));
    }
//    private boolean checkColor(int nX, int nY, int j, int i) {
//
//
//    }
    private Color castRay(int nX, int nY, int j, int i){

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
//    /**
//     * Constructs a ray through a given pixel on the view plane.
//     *
//     * @param nX Total number of pixels in the x dimension.
//     * @param nY Total number of pixels in the y dimension.
//     * @param j  The index of the pixel on the x dimension.
//     * @param i  The index of the pixel on the y dimension.
//     * @return A ray going through the given pixel.
//     */
//    public List<Ray> constructBeam(int nX, int nY, int j, int i) {
//        Point pIJ = CalculateCenterPointInPixel(nX, nY, j, i);
//        List<Ray> lr = null;
//        Vector vIJ = pIJ.subtract(p0);
//        lr.add(new Ray(p0, vIJ));
//        if (focus && !isFocus(j, i)) {
//            lr = CalculatCornerRayInPixel(pIJ, nX, nY, j, i);
//        }
//        return lr;
//    }


    public List<Ray> constructBeam(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));
        double rx = alignZero(width / nX);
        double ry = alignZero(height / nY);
        Point pij=CalculateCenterPointInPixel(nX, nY, j, i);
        Point sPoint;
        Ray sRay;
        double randx;
        double randy;
        List<Ray> beam = new LinkedList<>();

        for(int k=0;k<numOfRays;k++){
            randx=random(-rx/2,rx/2);
            randy=random(-ry/2,ry/2);
            sPoint= new Point(pij.getX()+randx, pij.getY()+randy, pij.getZ());
            sRay=new Ray(this.p0,sPoint.subtract(this.p0));
            beam.add(sRay);
        }
        return beam;
    }
      //  double yi = -(i - ((nY - 1) / 2.0)) * ry;
       // double xi = (j - ((nX - 1) / 2.0)) * rx;
//        Point pointIJ = pc;
//        double raysFreqX = rx/(width +1);
//        double raysFreqY = ry/(height + 1);
//        xi -= (rx / 2 - raysFreqX);
//        yi += (ry / 2 - raysFreqY);
//        if (!isZero(xi))
//            pointIJ = pointIJ.add(vRight.scale(xi));
//        if (!isZero(yi))
//            pointIJ = pointIJ.add(vUp.scale(yi));
//        Vector dirVector = pointIJ.subtract(p0);
//        List<Ray> beam = new LinkedList<Ray>();
//        for (double hightGap = raysFreqY; hightGap < ry; hightGap += raysFreqY) {
//            for (double widthGap = raysFreqX; widthGap < rx; widthGap += raysFreqX) {
//                beam.add(new Ray(p0, dirVector));
//                dirVector = dirVector.add(vRight.scale(raysFreqX));
//            }
//            dirVector = dirVector.add(vRight.scale(-rx + raysFreqX));
//            dirVector = dirVector.add(vUp.scale(-raysFreqY));
//        }
//        return beam;
        



//    /**
//     * consruct a beam of rays around one ray, inside the pixel, according to the division it received,
//     * and inside each sub-square takes out a random point for checking the color
//     *
//     * @param nX amount of pixels in width
//     * @param nY amount of pixels in height
//     * @param j the pixel's column
//     * @param i the pixel's row
//     //* @param divide size of single pixels view plane
//     *
//     * @return list of internal rays
//     */
//    public LinkedList<Ray> constructBeam(int nX,  int nY, int j , int i, double divide) {
//
//        /**
//         * the image's center
//         */
//        Point Pc = getP0().add(vTo.scale(distance));
//
//        /**
//         * height of single pixel
//         */
//        double Ry = alignZero(height/nY);
//
//        /**
//         * width of single pixel
//         */
//        double Rx = alignZero(width/nX);
//
//        /**
//         * amount of pixels to move in y axis from pc to i
//         */
//        double Yi = alignZero(-(i - ((nY - 1) / 2d)) * Ry);
//
//        /**
//         * amount of pixels  to move in x axis from pc to j
//         */
//        double Xj = alignZero((j - ((nX - 1) / 2d)) * Rx);
//
//        Point Pij = Pc;
//
//        if(!isZero(Xj)) {
//            //only move on X axis
//            Pij = Pij.add(vRight.scale(Xj));
//        }
//
//
//        if(!isZero(Yi)) {
//            //only move on Y axis
//            Pij = Pij.add(vUp.scale(Yi));
//        }
//
//        var rayList = new LinkedList<Ray>();
//        rayList.add(constructRayThroughPixel(nX, nY, j, i));
//        /**
//         * up left corner of pixel
//         */
//        Point pixStart = Pij.add(vRight.scale(-Rx / 2)).add(vUp.scale(Ry / 2));
//        // The formation of the rays within the division of the pixel,
//        // in each square a point of intersection is selected at random
//        for (double row = 0; row < divide; row++) {
//            for (double col = 0; col < divide; col++) {
//                rayList.add(randomPointRay(pixStart, col/divide, -row/divide));
//            }
//        }
//        return rayList;
//    }
    /**
     * Calculate the corner ray in pixel
     *
     * @param center point
     * @param nX     Total number of pixels in the x dimension.
     * @param nY     Total number of pixels in the y dimension.
     * @param j      The index of the pixel on the x dimension.
     * @param i      The index of the pixel on the y dimension.
     * @return List of rays
     */
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

    /**
     * Constructs a ray through a given pixel on the view plane.
     *
     * @param X Total number of pixels in the x dimension.
     * @param Y Total number of pixels in the y dimension.
     * @param j The index of the pixel on the x dimension.
     * @param i The index of the pixel on the y dimension.
     * @return
     */
    public Ray constructOneRayPixel(int X, int Y, int j, int i) {
        Point pCenterPixel = CalculateCenterPointInPixel(X, Y, j, i);
        return new Ray(p0, pCenterPixel.subtract(p0));
    }

    /**
     * using in simple anti alysing for calculate color in a point in a sub-square
     *
     * @param pixStart up left corner of the sub-square
     * @param col the column
     * @param row the row
     *
     * @return random ray inside the range
     */
    private Ray randomPointRay(Point pixStart, double col, double row) {
        Point point = pixStart;
        if(!isZero(col)) {
            //only move on X axis
            point = point.add(vRight.scale(random(0, col)));
        }
        if(!isZero(row)) {
            //only move on Y axis
            point = point.add(vUp.scale(random(row, 0)));
        }
        return new Ray(getP0(), point.subtract(getP0()));
    }




}
