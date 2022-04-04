package src.scene;

import src.geometries.Geometries;
import src.lighting.AmbientLight;
import src.primitives.Color;


public class Scene {
   public String name;
   public Color background;
   public AmbientLight ambientLight;
   public Geometries geometries;

   public Scene(String sceneName) {
       geometries = new Geometries();
       name= sceneName;
       background= Color.BLACK;
   }

    public Scene setAmbientLight(AmbientLight ambientLightSeting) {
        ambientLight = ambientLightSeting;
        return this;
    }



    public Scene setBackground(Color color) {
        background = color;
        return this;
    }
}
