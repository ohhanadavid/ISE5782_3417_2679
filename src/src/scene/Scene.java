package src.scene;

import src.geometries.Geometries;
import src.lighting.AmbientLight;
import src.lighting.LightSource;
import src.primitives.Color;

import java.util.LinkedList;
import java.util.List;


public class Scene {
   public String name;
   public Color background= Color.BLACK;;
   public AmbientLight ambientLight = new AmbientLight(); // = new AmbientLight(new Color(192,192,192),1);
   public Geometries geometries;
   public List<LightSource> lights=new LinkedList<LightSource>();


    /**
     * create Scene
     * @param sceneName of the scene
     */
    public Scene(String sceneName) {
       geometries = new Geometries();
       name= sceneName;
   }
    /**
     * @param ambientLightSetting, set the ambient Light  of the scene
     * @return the scene
     */
    public Scene setAmbientLight(AmbientLight ambientLightSetting) {
        ambientLight = ambientLightSetting;
        return this;
    }
    /**
     * @param color, set the background color of the scene
     * @return the scene
     */
    public Scene setBackground(Color color) {
        background = color;
        return this;
    }
    /**
     * @param geometries, set the geometries of the scene
     * @return the scene
     */
    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
    /**
     * @param lights, set a list of light source of the scene
     * @return the scene
     */
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
