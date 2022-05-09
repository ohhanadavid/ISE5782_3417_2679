package src.lighting;

import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Vector;

/**
 * Interface of all the light
 *
 * @author David Ochana & Aviad Klein
 */
public interface LightSource {

    /**
     * @param p point on the geometry
     * @return intensity color on that point
     */
    public Color getIntensity(Point p);

    /**
     * @param p point on the geometry
     * @return the vector between p and position point
     */
    public Vector getL(Point p);


    double getDistance(Point point);
}