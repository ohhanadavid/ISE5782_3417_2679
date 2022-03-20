
package src.geometries;
import src.primitives.Point;
import src.primitives.Vector;

/**
* Interface for geometrics object
* @author David Ochana & Aviad Klein
*/
public interface Geometry extends Intersectable {
    /**
     * @param point on geometrics object
     * @return normal vector on this point
     */
    public Vector getNormal(Point point);
}
