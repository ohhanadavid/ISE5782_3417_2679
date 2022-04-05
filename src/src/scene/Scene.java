package src.scene;

import src.geometries.Geometries;
import src.lighting.AmbientLight;
import src.primitives.Color;


public class Scene {
   public String name;
   public Color background= Color.BLACK;;
   public AmbientLight ambientLight; // = new AmbientLight(new Color(192,192,192),1);
   public Geometries geometries;

   public Scene(String sceneName) {
       geometries = new Geometries();
       name= sceneName;
   }

    public Scene setAmbientLight(AmbientLight ambientLightSetting) {
        ambientLight = ambientLightSetting;
        return this;
    }

    public Scene setBackground(Color color) {
        background = color;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
