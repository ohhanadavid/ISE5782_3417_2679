package src.lighting;

import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Vector;

/**
 * Class spotlight
 *
 * @author David Ochana & Aviad Klein
 */
public class SpotLight extends PointLight{
    /**
     * direction vector of the spot light
     */
    private Vector direction;

    /**
     * specular component (default=1, no affection on the sharp of the original spot)
     */
    private double specularN=1;
    /**
     * create the intensity and position of the light
     *
     * @param intensity of the light
     * @param position  of the light
     */
    public SpotLight(Color intensity, Point position) {
        super(intensity, position);
    }

    /**
     * create the intensity, direction and position of the light
     * @param intensity of the light
     * @param direction of the light
     * @param position of the light
     */
    public SpotLight(Color intensity, Point position,Vector direction) {
        super(intensity,position);
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double factor=Math.max(0,direction.dotProduct(getL(p)));
        return super.getIntensity(p).scale(factor);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p);
    }

    /**
     * @param specularN specular component
     * @return this spot light
     */
    public SpotLight setSpecularN(double specularN) {
        this.specularN = specularN;
        return this;
    }

    /**
     * @return specularN
     */
    public double getSpecularN() {
        return specularN;
    }


}
