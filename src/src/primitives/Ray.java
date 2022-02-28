package src.primitives;

import java.util.Objects;

public class Ray {
  final  Point p0;
   final Vector dir;

    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir;
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


    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}
