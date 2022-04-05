
package src.renderer;

import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Ray;
import src.scene.Scene;
import java.util.List;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * A builder
     * @param scene that the ray cross
     */
    public RayTracerBasic(Scene scene){
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<Point> intersections= scene.geometries.findIntersections(ray);
        if(intersections != null){
            Point closePoint=ray.findClosestPoint(intersections);
            return calcColor(closePoint);
        }
        //no intersections
        return scene.background;
    }

    private Color calcColor(Point point){
        return scene.ambientLight.getIntensity();
    }
}
