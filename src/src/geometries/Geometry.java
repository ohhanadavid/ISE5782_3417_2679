
package src.geometries;
import src.primitives.Color;
import src.primitives.Material;
import src.primitives.Point;
import src.primitives.Vector;

/**
* Interface for geometrics object
* @author David Ochana & Aviad Klein
*/
public abstract class Geometry extends Intersectable {
    protected Color emission=Color.BLACK;
    protected Material material=new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /***
     *
     * @return the emission color
     */
    public Color getEmission() {
        return emission;
    }

    public Geometry setEmission(Color newColor){
        emission = newColor;
        return this;
    }

    /**
     * @param point on geometrics object
     * @return normal vector on this point
     */
    public abstract Vector getNormal(Point point);
}
