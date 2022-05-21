package src.primitives;
/**
 * The material kind of the object
 *
 * @author David Ohhana & Aviad Klein
 */
public class Material {

    /**
     * factors
     */
    public double kD= 0;//Diffuse
    public double kS= 0;//Specular
    public double kT= 0.0;//Transparency
    public double kR= 0.0;//Reflection

    public double kG= 1;


    public Material setKg(double kG) {
        this.kG = kG; //new Double3( kG);
        return this;
    }

    public Material setKt(double kT) {
        this.kT = kT;  //new Double3( kT);
        return this;
    }

    public Material setKr(double kR) {
        this.kR = kR; //new Double3( kR);
        return this;
    }

    public double getKt() {
        return kT;
    }

    public double getKr() {
        return kR;
    }

    public double getKg() {
        return kG;
    }



    /**
     * factor of the shininess
     */
    public int nShininess=0;




    /**
     * @param nShininess factor of the reflection of the material
     * @return this Material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * @param kS factor of the specular affect of the material
     * @return this Material
     */

    public Material setKs(double kS) {
        this.kS = kS;// new Double3(kS);
        return this;
    }
    /**
     * @param kD factor of the diffuse affect of the material
     * @return this Material
     */

    public Material setKd(double kD) {
        this.kD =kD; //new Double3( kD);
        return this;
    }
//
//    public Material setKg(Double3 kG) {
//        this.kG = kG;
//        return this;
//    }
//    /**
//     * @param kT factor of the Transparency of the material
//     * @return this Material
//     */
//    public Material setKt(Double3 kT) {
//        this.kT = kT;
//        return this;
//    }
//    /**
//     * @param kR factor of the reflection of the material
//     * @return this Material
//     */
//    public Material setKr(Double3 kR) {
//        this.kR = kR;
//        return this;
//    }

    public int getShininess(){
        return nShininess;
    }

}
