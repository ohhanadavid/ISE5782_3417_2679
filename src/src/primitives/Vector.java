package src.primitives;
import src.primitives.Util.*;

import static src.primitives.Util.isZero;

/**
 * A vector is a point with an additional attribute, a direction
 * @author David Ochana & Aviad Klein
 */
public class Vector extends Point {

   // Creating a vector from a point.
   public Vector (Point point){

       super(point.xyz.d1,point.xyz.d2,point.xyz.d3);
       checkingVector(point.xyz);

       }

    // Creating a vector from a double3.
    public Vector (Double3 double3){
        super(double3);
        checkingVector(double3);
    }

    // Creating a vector from three numbers.
    public Vector(double d1,double d2, double d3) {
        super(d1,d2,d3);
        checkingVector(getXyz());
    }

    /**
     * It checks if the vector is zero.
     *
     * @param xyz the vector to be checked.
     */
    private void checkingVector(Double3 xyz) {
        if( xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException("This is the zero vector");}
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Vector:"+super.toString();
    }

    /**
     * Given a vector, add it to this vector and return the result
     *
     * @param addVector the vector to add to this vector.
     * @return A new vector.
     */
    public Vector add(Vector addVector){
        return new Vector(super.add(addVector));
    }

    /**
     * This function takes a double and returns a new Vector
     *
     * @param scale the scale factor
     * @return A new Vector object.
     */
    public Vector scale (double scale) {
        if(isZero(scale))
            throw new IllegalArgumentException("scale cant be 0!");
        return new Vector(xyz.scale(scale));
    }

    /**
     * Given two vectors, return the vector that is perpendicular to both
     *
     * @param edge2 the second edge to use in the cross product
     * @return The cross product of the two vectors.
     */
    public Vector crossProduct(Vector edge2) {
        double x = (xyz.d2 * edge2.xyz.d3) - (xyz.d3 * edge2.xyz.d2);
        double y = (xyz.d3 * edge2.xyz.d1) - (xyz.d1 * edge2.xyz.d3);
        double z = (xyz.d1 * edge2.xyz.d2) - (xyz.d2 * edge2.xyz.d1);
        return new Vector(x,y,z);
    }


    /**
     * The dot product of two vectors is the sum of the products of the corresponding components
     *
     * @param vector the vector to be dotted with this vector
     * @return The dot product of the two vectors.
     */
    public double dotProduct(Vector vector){
        return xyz.d1*vector.xyz.d1+
                xyz.d2*vector.xyz.d2+
                xyz.d3*vector.xyz.d3;
    }

    //
    /**
     * Returns the length of the vector
     *
     * @return The length of the vector.
     */
    public double length(){
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns the length of the vector squared
     *
     * @return The length of the vector.
     */
    public double lengthSquared(){
        return this.dotProduct(this);
    }

    /**
     * Given a vector, return a new vector with the same direction but with a length of 1
     *
     * @return Nothing is being returned.
     */
    public Vector normalize(){
        var len = length();
        return new Vector(xyz.reduce(len));
    }


}
