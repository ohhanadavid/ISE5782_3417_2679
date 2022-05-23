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
    public Double3 kD=new Double3(0);//Diffuse
    public Double3 kS=new Double3(0);//Specular
    public Double3 kT=new Double3 (0.0);//Transparency
    public Double3 kR=new Double3(0.0);//Reflection

    public double kG=1.0;

    public double getKg() {
        return kG;
    }

    public Material setKg(double kG) {
        this.kG = Math.pow(kG, 0.5);
        return this;
    }


    public Material setKt(double kT) {
        this.kT =new Double3( kT);
        return this;
    }

    public Double3 getKt() {
        return kT;
    }

    public Double3 getKr() {
        return kR;
    }

    public Material setKr(double kR) {
        this.kR = new Double3( kR);
        return this;
    }

    /**
     * factor of the shininess
     */
    public int nShininess=0;
    /**
     * @param kD factor of the diffuse affect of the material
     * @return this Material
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }
    public Material setKd(double kD) {
        this.kD =new Double3( kD);
        return this;
    }

    /**
     * @param kS factor of the specular affect of the material
     * @return this Material
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    public Material setKs(double kS) {
        this.kS = new Double3(kS);
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
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * @param kR factor of the reflection of the material
     * @return this Material
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    public int getShininess(){
        return nShininess;
    }

}
