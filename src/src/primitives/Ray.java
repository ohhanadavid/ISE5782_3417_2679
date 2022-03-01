package src.primitives;

import java.util.Objects;

public class Ray {
  final  Point p0;
   final Vector dir;

    // A constructor. It is a method that is called when an object is created.
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null ) return false;
        if(!(o instanceof Ray ray)) return false;
        return this.dir.equals(ray.dir)&&this.p0.equals(ray.p0);
    }

    @Override
    public String toString(){
        return "Point:"+p0.toString()+"\n"+"Vector:"+dir.toString();
    }


    /**
     * Returns the value of the first point
     *
     * @return The method returns the value of the instance variable p0.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the particle
     *
     * @return The direction vector of the particle.
     */
    public Vector getDir() {
        return dir;
    }
}
