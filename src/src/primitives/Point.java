package src.primitives;

/**
 * A point is a vector from the origin to a point in space
 * @author David Ochana & Aviad Klein
 */
public class Point {
  final  Double3 xyz;

  public Point(double d1, double d2, double d3){
      this.xyz=new Double3(d1,d2,d3);

  }

    public Point(Double3 xyz) {
        this.xyz = xyz;
    }

    /**
     * Returns the xyz coordinates of the atom
     *
     * @return The xyz vector.
     */
    public Double3 getXyz(){
        return this.xyz;
    }

    @Override
    public String toString() {
        return "("+xyz.d1+","+xyz.d2+","+xyz.d3+")";
    }

    /**
     * Given a point, return the vector from this point to the origin
     *
     * @param vertex The point to subtract from this point.
     * @return A vector.
     */
    public Vector subtract(Point vertex) {

      return  new Vector(xyz.d1-vertex.xyz.d1,xyz.d2-vertex.xyz.d2,xyz.d3-vertex.xyz.d3);
    }


    /**
     * Add a vector to a point
     *
     * @param point the point to add to this point
     * @return A new Point object.
     */
    public Point add (Vector point){
      return new Point(xyz.d1+point.xyz.d1,xyz.d2+point.xyz.d2,xyz.d3+point.xyz.d3);
    }

//    public Point twoDotNumber()
//    {
//        return new Point(Util.twoDotNumber(xyz.d1), Util.twoDotNumber(xyz.d2), Util.twoDotNumber(xyz.d3));
//
//    }
    public double getX() {
        return xyz.d1;
    }

    public double getY() {
        return xyz.d2;
    }

    public double getZ() {
        return xyz.d3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        if(!(o instanceof Point other)) return false;
        return  xyz.equals(other.xyz) ;
    }


}
