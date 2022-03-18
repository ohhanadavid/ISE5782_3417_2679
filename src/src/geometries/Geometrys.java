package src.geometries;

import src.primitives.Point;
import src.primitives.Ray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Geometrys implements Intersectable{
    private List<Intersectable> geometrys;

    public Geometrys() {
        this.geometrys =new ArrayList<>();
    }
    public Geometrys(Intersectable...geometries){
        geometrys= Arrays.stream(geometries).toList();
    }
    public void add(Intersectable...geometris){

        for(var geo : geometris){
            geometrys.add(geo);
        }

    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}
