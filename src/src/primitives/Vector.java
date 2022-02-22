package src.primitives;

public class Vector extends Point {

   public Vector (Point point){

       super(point.xyz.d1,point.xyz.d2,point.xyz.d3);
   }
   public Vector (Double3 double3){
       super(double3);
   }
    public Vector(double d1,double d2, double d3) {
        super(d1,d2,d3);

    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Vector:"+super.toString();
    }

    public Vector add(Vector vector){
        return new Vector( super.add(vector));
    }

    public Vector scale (double scale) {
        return new Vector(xyz.scale(scale));
    }

    public Vector crossProduct(Vector edge2) {
        var x=this.xyz.d2*edge2.xyz.d3-this.xyz.d3*edge2.xyz.d2;
        var y=this.xyz.d1*edge2.xyz.d3-this.xyz.d3*edge2.xyz.d1;
        var z=this.xyz.d1*edge2.xyz.d2-this.xyz.d2*edge2.xyz.d1;
        return new Vector(x,y,z);
    }

    public double dotProduct(Vector vector){
        return this.xyz.d1*vector.xyz.d1+
                this.xyz.d2*vector.xyz.d2+
                this.xyz.d3*vector.xyz.d3;
    }

    public double length(){
       return this.xyz.d1*this.xyz.d1+this.xyz.d2*this.xyz.d2+this.xyz.d3*this.xyz.d3;
    }

    public double lengthSquared(){
       return Math.sqrt(this.length());
    }

    public Vector normalize(){
       var len=lengthSquared();
       return  new Vector(this.xyz.d1/len,this.xyz.d2/len,this.xyz.d3/len);
    }

    public double dotProdact(Vector vector){
       var prodact=this.xyz.product(vector.xyz);
       return prodact.d1+prodact.d3+prodact.d2;
    }


}
