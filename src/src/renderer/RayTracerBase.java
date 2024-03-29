package src.renderer;

import src.primitives.Color;
import src.primitives.Ray;
import src.scene.Scene;

import java.util.LinkedList;

/**
 * RayTracerBase abstract class that use as an interface for RayTracerBasic
 */
public abstract class RayTracerBase {

    protected Scene scene;

    /**
     * A builder function that get a scene
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }

    /**
     * An abstract function that get a ray and return the color of the point that cross the ray
     * @param ray ray that intersect the scene
     * @return Color
     */
    public abstract Color traceRay(Ray ray);


    public abstract Color averageColor(LinkedList<Ray> rays);
}

