package src.lighting;
import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Vector;



/**
 * Class of directional light (like sun)
 *
 * @author TehilaNaki & MeravIzhaki
 */
public class DirectionalLight extends Light implements LightSource{

    /**
     * vector of direction of light
     */
    private Vector direction;



    /**
     * create the intensity and direction of the light
     * @param intensity of the light
     * @param direction of the light
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction=direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction;
    }
}
