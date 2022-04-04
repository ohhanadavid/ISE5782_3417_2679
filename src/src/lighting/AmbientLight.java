package src.lighting;

import src.primitives.*;
import static src.primitives.Double3.*;


public class AmbientLight {

    private Color intensity;

    public AmbientLight(Color Ia, Double3 Ka) {

        intensity=Ia.scale(Ka);
    }

    public Color getIntensity() {
        return intensity;
    }

    public AmbientLight() {
        intensity = Color.BLACK;
    }

    public AmbientLight(Color in) {
        this.intensity = in;
    }
}
