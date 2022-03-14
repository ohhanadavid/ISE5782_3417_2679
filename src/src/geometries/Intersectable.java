package src.geometries;

import src.primitives.*;

import java.util.List;
/**
* Gives interface for an object that is Intersectable.
* @author DavidOchana & AviadKlein
*/
public interface Intersectable  {

    /**
     * @param ray
     * @return list of intersection points
     */
    public List<Point> findIntersections (Ray ray);
}
