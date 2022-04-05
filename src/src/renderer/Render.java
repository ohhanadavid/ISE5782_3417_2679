package src.renderer;

import src.primitives.Color;
import src.primitives.Ray;
import src.scene.Scene;

import java.util.MissingResourceException;

/**
 * Render class make from the scene the color matrix
 */
public class Render {

    ImageWriter imageWriter;
    Scene scene;
    Camera camera;
    RayTracerBase rayTracerBase;

    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return  this;
    }

    public Render setScene(Scene scene){
        this.scene=scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }


}
