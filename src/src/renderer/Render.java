
        package src.renderer;
        import src.scene.Scene;

/**
 * Render class make from the scene the color matrix
 */
public class Render {

    ImageWriter imageWriter = null;
    Scene scene = null;
    Camera camera = null;
    RayTracerBase rayTracerBase = null;


    public Render setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Render setScene(Scene scene) {
        this.scene = scene;
        return this;
    }

    public Camera getCamera() {
        return camera;
    }

    public Render setCamera(Camera camera) {
        this.camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * Saves the image according to image writer.
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

}
