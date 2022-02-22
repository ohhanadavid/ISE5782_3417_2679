package src.primitives;

public class Vector extends Point {

   public Vector (Point point){
      super(point.xyz.d1,point.xyz.d2,point.xyz.d3);
   }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "Vector{}";
    }

    public Vector add(Vector vector){
        return new Vector( super.add(vector));
    }

    public Vector scale(double scale){
        return new Vector(this.xyz.d1*scale,this.xyz.d2*scale,this.xyz.d3*scale);
    }

    public Vector(double d1,double d2, double d3) {
        super(d1,d2,d3);

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



}
