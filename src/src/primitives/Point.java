package src.primitives;

public class Point {
  final  Double3 xyz;

  public Point(double d1, double d2, double d3){
      this.xyz=new Double3(d1,d2,d3);
      if(xyz.equals(Double3.ZERO)) throw new IllegalArgumentException("This vector already define");
  }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    public Double3 getXyz(){
        return this.xyz;
    }

    public Vector subtract(Point vertex) {
        return null;
    }

    public Point add (Vector point){
      return new Point(this.xyz.d1+point.xyz.d1,this.xyz.d2+point.xyz.d2,this.xyz.d3+point.xyz.d3);
    }

}
