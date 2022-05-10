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
    public double
            kD=0,//Diffuse
            kS=0,//Specular
            kT=0,//Transparency
            kR=0;//Reflection
    /**
     * factor of the shininess
     */
    public int nShininess=0;
    /**
     * @param kD factor of the diffuse affect of the material
     * @return this Material
     */
    public Material setKd(double kD) {
        this.kD = kD;
        return this;
    }

    /**
     * @param kS factor of the specular affect of the material
     * @return this Material
     */
    public Material setKs(double kS) {
        this.kS = kS;
        return this;
    }

    /**
     * @param nShininess factor of the reflection of the material
     * @return this Material
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    /**
     * @param kT factor of the Transparency of the material
     * @return this Material
     */
    public Material setKt(double kT) {
        this.kT = kT;
        return this;
    }
    /**
     * @param kR factor of the reflection of the material
     * @return this Material
     */
    public Material setKr(double kR) {
        this.kR = kR;
        return this;
    }

    public int getShininess(){
        return nShininess;
    }

}
