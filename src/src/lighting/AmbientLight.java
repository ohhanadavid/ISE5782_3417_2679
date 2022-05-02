package src.lighting;

import src.primitives.*;
import static src.primitives.Double3.*;

/**
 * AmbientLight of the scene
 *
 * @author David Ohhana & Aviad Klein
 */
public class AmbientLight extends Light {


        /**
         * default constructor that create ambientLight in black
         */
        public AmbientLight(){
            super(Color.BLACK);
        }

        /**
         * create AmbientLight of the scene
         * @param Ia the color of ambientLight
         * @param Ka factor of the ambientLight
         */
        public AmbientLight(Color Ia , Double3 Ka) {
            super(Ia.scale(Ka));
        }

        public AmbientLight(Color in) {
            super(in);
        }
}
