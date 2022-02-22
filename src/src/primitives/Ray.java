package src.primitives;

import java.util.Objects;

public class Ray {
    Point p0;
    Vector dir;

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
