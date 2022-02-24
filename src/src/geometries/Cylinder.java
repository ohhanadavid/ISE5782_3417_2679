package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;

public class Cylinder extends Tube  implements Geometry {
   final double height;

    public Cylinder(Ray axisRay, Double radius, double height) {
        super(axisRay, radius);
        this.height = height;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                super.toString()+
                "height=" + height +
                '}';
    }

    public double getHeight() {
        return height;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Cylinder)) return false;
        if (!super.equals(o)) return false;

        Cylinder cylinder = (Cylinder) o;

        return this.height==cylinder.height;
    }


}
