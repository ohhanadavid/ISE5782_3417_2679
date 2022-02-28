package src.primitives;

public class Vector extends Point {

   public Vector (Point point){

       super(point.xyz.d1,point.xyz.d2,point.xyz.d3);
       checkingVector(point.xyz);

       }

    private void checkingVector(Double3 xyz) {
        if( xyz.equals(Double3.ZERO)){
            throw new IllegalArgumentException();}
    }



   public Vector (Double3 double3){
       super(double3);
       checkingVector(double3);
       }

    public Vector(double d1,double d2, double d3) {
        super(d1,d2,d3);
        checkingVector(getXyz());
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
     * @param vector the vector to add to this vector.
     * @return A new vector.
     */
    public Vector add(Vector vector){
        return new Vector( add(vector));
    }

    public Vector scale (double scale) {
        return new Vector(xyz.scale(scale));
    }

    public Vector crossProduct(Vector edge2) {
        var x=xyz.d2*edge2.xyz.d3-xyz.d3*edge2.xyz.d2;
        var y=(xyz.d1*edge2.xyz.d3-xyz.d3*edge2.xyz.d1)*(-1);
        var z=xyz.d1*edge2.xyz.d2-xyz.d2*edge2.xyz.d1;
        return new Vector(x,y,z);
    }

    public double dotProduct(Vector vector){
        return xyz.d1*vector.xyz.d1+
                xyz.d2*vector.xyz.d2+
                xyz.d3*vector.xyz.d3;
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared(){
        return xyz.d1*xyz.d1+xyz.d2*xyz.d2+xyz.d3*xyz.d3;
    }

    public Vector normalize(){
       var len=length();
       return  new Vector(xyz.d1/len,xyz.d2/len,xyz.d3/len);
    }

    public double dotProdact(Vector vector){
       var prodact=xyz.product(vector.xyz);
       return prodact.d1+prodact.d3+prodact.d2;
    }



}
