package src.primitives;

import java.util.Objects;

public class Ray {
    Point p0;
    Vector dir;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ray ray = (Ray) o;

        if (!p0.equals(ray.p0)) return false;
        return dir.equals(ray.dir);
    }


    public Point getP0() {
        return p0;
    }

    public Vector getDir() {
        return dir;
    }
}
