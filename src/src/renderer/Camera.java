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

    private Point p0;
    private Vector vTo;
    private Vector vUp;
    private Vector vRight;
    private double height;
    private double width;
    private double distance;
    ImageWriter imageWriter;
    RayTracerBase rayTracer;
    /**
     * The number of rays sent by the camera.
     */
    private int numOfRays = 1;
    private boolean focus = false;
    private Point focalPix = null;
    public double disFocal = 0;
    /**
     * ThreadPool of the scene
     */
    private ThreadPool<Pixel> threadPool = null;
    /**
     * Next pixel of the scene
     */
    private Pixel nextPixel = null;

    /**
     * Last percent of the image to render
     */
    public static int lastPercent = -1;




    /**
     * Constructs a camera with p0, to and up vectors.
     * The right vector is being calculated by the to and up vectors.
     *
     * @param point -> the camera p0
     * @param x     -> The direction to where the camera is looking.
     * @param z     -> The direction of the camera's upper direction.
     * @throws IllegalArgumentException When to and up vectors aren't orthogonal.
     */
    public Camera(Point point, Vector x, Vector z) {
        if (!isZero(z.dotProduct(x)))
            throw new IllegalArgumentException("those two vector not orthogonal");
        p0 = point;
        vTo = x.normalize();
        vUp = z.normalize();
        vRight = x.crossProduct(z);
    }

    /**
     * Returns the camera p0.
     */
    public Point getP0() {
        return p0;
    }

    public Camera setP0(Point p0) {
        this.p0 = p0;
        return this;
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
     * Set the new view plane's width.
     *
     * @throws IllegalArgumentException When width illegal.
     */
    public Camera setWidth(double width) {
        if (width <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.width = width;
        return this;
    }

    /**
     * Set the new view plane's height.
     *
     * @throws IllegalArgumentException When height illegal.
     */
    public Camera setHeight(double height) {
        if (height <= 0) {

            throw new IllegalArgumentException("Illegal value of width");
        }
        this.height = height;
        return this;
    }

    /**
     * Method for setting the view plane's size.
     *
     * @param width  The new view plane's width.
     * @param height The new view plane's height.
     * @return The camera itself.
     * Builder design pattern.
     * @throws IllegalArgumentException When height/width illegal.
     */
    public Camera setVPSize(double width, double height) {
        if (width <= 0) {
            throw new IllegalArgumentException("Illegal value of width");
        }
        this.width = width;

        if (height <= 0) {
            throw new IllegalArgumentException("Illegal value of height");
        }
        this.height = height;
        return this;
    }

    /**
     * Method for setting the distance between the camera and the view plane.
     *
     * @param distance The new distance between the camera and the view plane.
     * @return The camera itself.
     * @throws IllegalArgumentException When distance illegal.
     *                                  Builder design pattern.
     */
    public Camera setVPDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Illegal value of distance");
        }
        this.distance = distance;
        return this;
    }


    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
        Point pC = p0.add(vTo.scale(distance));
        Point pIJ = pC;

        double rY = alignZero(height / nY);
        double rX = alignZero(width / nX);

        double yI = alignZero(-(i - (nY - 1) / 2d) * rY);
        double xJ = alignZero((j - (nX - 1) / 2d) * rX);

        if (!isZero(xJ)) {
            pIJ = pIJ.add(vRight.scale(xJ));
        }
        if (!isZero(yI)) {
            pIJ = pIJ.add(vUp.scale(yI));
        }

        Vector vIJ = pIJ.subtract(p0);

        return new Ray(p0, vIJ);
    }

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

    private List<Ray> CalculatCornerRayInPixel(Point center, int nX, int nY, int j, int i) {

        Point p = center;
        List<Ray> lcorner = new LinkedList<>();

        //up
        double yu = nY / (height * 2);
        //right
        double xr = nX / (width * 2);


        //left up
        if (!isZero(xr)) {
            p = center.add(vRight.scale(-xr));
        }
        if (!isZero(yu)) {
            p = center.add(vUp.scale(yu));
        }
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //right up
        p = center.add(vRight.scale(xr));
        p = center.add(vUp.scale(yu));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //left down
        p = center.add(vRight.scale(-xr));
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //right down
        p = center.add(vRight.scale(xr));
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //left middle
        p = center.add(vRight.scale(-xr));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //right middle
        p = center.add(vRight.scale(xr));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //middle up
        p = center.add(vUp.scale(yu));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;

        //middle down
        p = center.add(vUp.scale(-yu));
        lcorner.add(new Ray(p0, p.subtract(p0)));
        p = center;


        return lcorner;

    }

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


    /**
     * Chaining method for setting the  number of rays constructed by the camera.
     *
     * @param numOfRays The number of rays constructed.
     * @return The camera itself.
     */
    public Camera setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
        return this;
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

    /**
     * Constructs a ray through a given pixel on the view plane for AA.
     *
     * @param nX Total number of pixels in the x dimension.
     * @param nY Total number of pixels in the y dimension.
     * @param j  The index of the pixel on the x dimension.
     * @param i  The index of the pixel on the y dimension.
     * @return List of rays.
     */
    public LinkedList<Ray> constructRayPixelAA(int nX, int nY, int j, int i) {
        if (isZero(distance))
            throw new IllegalArgumentException("distance can't be 0");

        LinkedList<Ray> rays = new LinkedList<>();

        double rX = width / nX;
        double rY = height / nY;

        double randX, randY;

        Point pCenterPixel = CalculateCenterPointInPixel(nX, nY, j, i);
        rays.add(new Ray(p0, pCenterPixel.subtract(p0)));
        if (focus && !isFocus(j, i))
            rays.addAll(CalculatCornerRayInPixel(pCenterPixel, nX, nY, j, i));

        Point pInPixel;
        for (int k = 0; k < numOfRays; k++) {
            randX = random(-rX / 2, rX / 2);
            randY = random(-rY / 2, rY / 2);
            pInPixel = new Point(pCenterPixel.getX() + randX, pCenterPixel.getY() + randY, pCenterPixel.getZ());
            rays.add(new Ray(p0, pInPixel.subtract(p0)));
        }
        return rays;
    }

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

    public int getNumOfRays() {
        return numOfRays;
    }


    /**
     * check if it's focus
     *
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


    public Camera setImageWriter(ImageWriter imageWriterT) {
        this.imageWriter = imageWriterT;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracerT) {
        this.rayTracer = rayTracerT;
        return this;
    }

//    public Camera renderImage(){
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
////                    if(checkColor(nX,nY,j,i))
// //                  imageWriter.writePixel(j, i, castRay(nX,nY,j,i));
//  //                  else
//                                   imageWriter.writePixel(j,i,castRaysAntiAliasing(nX,nY,j,i));
//                }
//            }
//        }
//        catch (MissingResourceException e){
//            throw new UnsupportedOperationException("Not implemented yet " + e.getClassName());
//        }
//        return this;



    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("Missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracer == null) {
                throw new MissingResourceException("Missing resource", RayTracerBase.class.getName(), "");
            }

            int nX = imageWriter.getNx();
            int nY = imageWriter.getNy();

            //rendering the image with multi-threaded
            if (threadPool != null) {
                nextPixel = new Pixel(0, 0);
                threadPool.execute();

                printPercentMultithreaded(); // blocks the main thread until finished and prints the progress

                threadPool.join();
                return this;
            }

            // rendering the image when single-threaded
            adaptive(0, nY / 2, nX / 2, 0, nX, nY, 1);

            LinkedList<Ray> rays;

            // prints the 100% percent
            printPercent(nX * nY, nX * nY, lastPercent);
        } catch (MissingResourceException e) {
            throw new UnsupportedOperationException("Render didn't receive " + e.getClassName());
        }
        return this;
    }

    public Color averageColor(List<Ray> rays) {
        Color color = Color.BLACK;
        for (Ray ray : rays) {
            color = color.add(rayTracer.traceRay(ray));
        }
        return color.reduce(Double.valueOf(rays.size()));
    }

//    private Color castRay(int nX, int nY, int j, int i) {
//
//        Ray ray = constructRayThroughPixel(nX, nY, j, i);
//        return rayTracer.traceRay(ray);
//    }
//
//    /**
//     * The function make the grid lines
//     *
//     * @param interval between the lines
//     * @param color    of the lines
//     */
//    public void printGrid(int interval, Color color) {
//        if (imageWriter == null) {
//            throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
//        }
//
//        int nX = imageWriter.getNx();
//        int nY = imageWriter.getNy();
//
//        for (int i = 0; i < nY; i++) {
//            for (int j = 0; j < nX; j++) {
//
//                if (i % interval == 0 || j % interval == 0) {
//                    imageWriter.writePixel(j, i, color);
//                }
//            }
//        }
//    }

    //Turn on the function of the imageWriter writeToImage
    public void writeToImage() {

        if (imageWriter == null) {
            throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
        }
        imageWriter.writeToImage();

    }


    public List<Ray> constructBeam(int nX, int nY, int j, int i) {
        Point pc = p0.add(vTo.scale(distance));
        double rx = alignZero(width / nX);
        double ry = alignZero(height / nY);
        Point pij = CalculateCenterPointInPixel(nX, nY, j, i);
        Point sPoint;
        Ray sRay;
        double randx;
        double randy;
        List<Ray> beam = new LinkedList<>();

        for (int k = 0; k < numOfRays; k++) {
            randx = random(-rx / 2, rx / 2);
            randy = random(-ry / 2, ry / 2);
            sPoint = new Point(pij.getX() + randx, pij.getY() + randy, pij.getZ());
            sRay = new Ray(this.p0, sPoint.subtract(this.p0));
            beam.add(sRay);
        }
        return beam;
    }


    /**
     * using in simple anti alysing for calculate color in a point in a sub-square
     *
     * @param pixStart up left corner of the sub-square
     * @param col      the column
     * @param row      the row
     * @return random ray inside the range
     */
    private Ray randomPointRay(Point pixStart, double col, double row) {
        Point point = pixStart;
        if (!isZero(col)) {
            //only move on X axis
            point = point.add(vRight.scale(random(0, col)));
        }
        if (!isZero(row)) {
            //only move on Y axis
            point = point.add(vUp.scale(random(row, 0)));
        }
        return new Ray(getP0(), point.subtract(getP0()));
    }

    /**
     * Prints the progress in percents only if it is greater than the last time printed the progress.
     *
     * @param currentPixel the index of the current pixel
     * @param pixels       the number of pixels in the image
     * @param lastPercent  the percent of the last time printed the progress
     * @return If printed the new percent, returns the new percent. Else, returns {@code lastPercent}.
     */
    private int printPercent(int currentPixel, int pixels, int lastPercent) {
        int percent = currentPixel * 100 / pixels;
        if (percent > lastPercent) {
            System.out.printf("%02d%%\n", percent);
            System.out.flush();
            return percent;
        }
        return lastPercent;
    }

    /**
     * Help function that check how many of the pixel has the same color, get the index (j,i) of 9 pixels
     *
     * @param nX the number of columns in the picture
     * @param nY the number of rows in the picture
     * @return the number of pixel with the same color
     */
    private int sameColor(int j1, int i1, int j2, int i2, int j3, int i3, int j4, int i4, int j5, int i5, int j6, int i6, int j7, int i7, int j8, int i8, int j9, int i9, int nX, int nY) {
        Color c1 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j1, i1));
        Color c2 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j2, i2));
        Color c3 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j3, i3));
        Color c4 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j4, i4));
        Color c5 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j5, i5));
        Color c6 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j6, i6));
        Color c7 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j7, i7));
        Color c8 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j8, i8));
        Color c9 = rayTracer.traceRay(constructOneRayPixel(nX, nY, j9, i9));
        int sum = 0;
        if (c1 == c2)
            sum++;
        if (c2 == c3)
            sum++;
        if (c3 == c4)
            sum++;
        if (c4 == c5)
            sum++;
        if (c5 == c6)
            sum++;
        if (c6 == c7)
            sum++;
        if (c7 == c8)
            sum++;
        if (c8 == c9)
            sum++;
        return sum;
    }

    /**
     * A function that improves the performance of the renderer, by dividing the grid into parts
     * for parts with no objects, reduces the amount of rays.
     *
     * @param nX    the number of columns in the picture
     * @param nY    the number of rows in the picture
     * @param level Grid level of division
     */
    public void adaptive(int j1, int i1, int j2, int i2, int nX, int nY, int level) {
        int numOfSame = sameColor(j1, i1, j2, i2, j2 * 2, i1, j2, i1 * 2, j2, i1, j2 / 2, i1, j2 + j2 / 2, i1, j2, i1 / 2, i1 + nX / (level * 2), i1 + nY / (level * 2), nX, nY);
        //if all the pixels has the same color
        if (numOfSame == 8) {
            LinkedList<Ray> rays;
            rays = constructRayPixelAA(nX, nY, j1, i1);
            Color c = averageColor(rays);
            System.out.println(level);
            //color all the pixels
            for (int i = i2; i < i2 + nY / level; i++) {
                for (int j = j1; j < j1 + nX / level; j++) {
                    int currentPixel = i * nX + j;
                    lastPercent = printPercent(currentPixel, nX * nY, lastPercent);
                    imageWriter.writePixel(j, i, c);
                }
            }
        }
        //different color low level
        else if (numOfSame > 6) {
            adaptive(j1, i1 / 2, j2 / 2, i2, nX, nY, level * 2);
            adaptive(j2, j2 / 2, j2 + j2 / 2, i2, nX, nY, level * 2);
            adaptive(j1, i1 + i1 / 2, j2 / 2, i1, nX, nY, level * 2);
            adaptive(j2, i1 + i1 / 2, j2 + j2 / 2, i1, nX, nY, level * 2);
        } else {
            LinkedList<Ray> rays;
            //pass through each pixel and calculate the color
            for (int i = i2; i < i2 + nY / level; i++) {
                for (int j = j1; j < j1 + nX / level; j++) {
                    int currentPixel = i * nX + j;
                    lastPercent = printPercent(currentPixel, nX * nY, lastPercent);
                    castRay(nX, nY, j, i);
                }
            }
        }
    }

    /**
     * Must run on the main thread.
     * Prints the percent on multithreaded rendering.
     */
    private void printPercentMultithreaded() {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        int pixels = nX * nY;
        int lastPercent = -1;

        while (nextPixel.row < nY) {
            // waits until got update from the rendering threads
            synchronized (this) {
                try {
                    wait();
                } catch (InterruptedException e) {
                }
            }

            int currentPixel = nextPixel.row * nX + nextPixel.col;
            lastPercent = printPercent(currentPixel, pixels, lastPercent);
        }
    }


    /**
     * Chaining method for setting number of threads.
     * If set to 1, the render won't use the thread pool.
     * If set to greater than 1, the render will use the thread pool with the given threads.
     * If set to 0, the thread pool will pick the number of threads.
     *
     * @param threads number of threads to use
     * @return the current render
     * @throws IllegalArgumentException when threads is less than 0
     */
    public void setMultithreading(int threads) {
        if (threads < 0) {
            throw new IllegalArgumentException("threads can be equals or greater to 0");
        }

        // run as single threaded without the thread pool
        if (threads == 1) {
            threadPool = null;
            return;
        }

        threadPool = new ThreadPool<Pixel>() // the thread pool choose the number of threads (in0 case threads is 0)
                .setParamGetter(this::getNextPixel)
                .setTarget(this::renderImageMultithreaded);
        if (threads > 0) {
            threadPool.setNumThreads(threads);
        }

        return;
    }

    /**
     * Returns the next pixel to draw on multithreaded rendering.
     * If finished to draw all pixels, returns {@code null}.
     */
    private synchronized Pixel getNextPixel() {

        // notifies the main thread in order to print the percent
        notifyAll();


        Pixel result = new Pixel();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();

        // updates the row of the next pixel to draw
        // if got to the end, returns null
        if (nextPixel.col >= nX) {
            if (++nextPixel.row >= nY) {
                return null;
            }
            nextPixel.col = 0;
        }

        result.col = nextPixel.col++;
        result.row = nextPixel.row;
        return result;
    }

    /**
     * Renders a given pixel on multithreaded rendering.
     * If the given pixel is null, returns false which means kill the thread.
     *
     * @param p the pixel to render
     */
    private boolean renderImageMultithreaded(Pixel p) {
        if (p == null) {
            return false; // kill the thread
        }

        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        castRay(nX, nY, p.col, p.row);

        return true; // continue the rendering
    }
    /**
     * Casts a ray through a given pixel and writes the color to the image.
     *
     * @param nX  the number of columns in the picture
     * @param nY  the number of rows in the picture
     * @param col the column of the current pixel
     * @param row the row of the current pixel
     */
    private void castRay(int nX, int nY, int col, int row) {


        LinkedList<Ray> rays = constructRayPixelAA(nX, nY, col, row);
        Color pixelColor = averageColor(rays);
        imageWriter.writePixel(col, row, pixelColor);
    }



    /**
     * Adds a grid to the image.
     *
     * @param interval num of the grid's lines
     * @param color    the color of the grid's lines
     */
    public void printGrid(int interval, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }
    /**
     * Helper class to represent a pixel to draw in a multithreading rendering.
     */
    private class Pixel {
        public int col, row;

        public Pixel(int col, int row) {
            this.col = col;
            this.row = row;
        }

        public Pixel() {
        }



//        /**
//         * Saves the image according to image writer.
//         */
//        public void writeToImage() {
//            imageWriter.writeToImage();
//        }






    }

    /**
     * Pixel is an internal helper class whose objects are associated with a Render
     * object that they are generated in scope of. It is used for multithreading in
     * the Renderer and for follow up its progress.<br/>
     * There is a main follow up object and several secondary objects - one in each
     * thread.
     */
}