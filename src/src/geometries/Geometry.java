
package src.geometries;
import src.primitives.Point;
import src.primitives.Vector;

public interface Geometry {
    public Vector getNormal(Point point);
}
