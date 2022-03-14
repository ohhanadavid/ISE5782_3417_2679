package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;

import java.util.ArrayList;
import java.util.List;

public class Geometrys implements Intersectable{
    private List<Intersectable> geometrys;

    public Geometrys() {
        this.geometrys =new ArrayList<>();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
