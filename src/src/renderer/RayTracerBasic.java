
package src.renderer;

import src.geometries.Intersectable.*;
import src.primitives.Color;
import src.primitives.Point;
import src.primitives.Ray;
import src.primitives.Vector;
import src.scene.Scene;
import static java.lang.Math.random;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import src.geometries.Intersectable.GeoPoint;
import src.lighting.*;
import src.primitives.*;

import static src.primitives.Util.*;

/**
 *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
 */
public class RayTracerBasic extends RayTracerBase {


    /**
     * constant number for size moving first rays for recursion stop conditions
     */
    private static final Double3 INITIAL_K = new Double3(1.0);
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    private static final double EPS = 0.1;
    private int glossinessRays = 10;

    /**
     * This is the constructor for the RayTracerBasic class. It calls the constructor for the RayTracerBase class.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Given a ray, find the closest point of intersection with the scene, and return the color of that point
     *
     * @param ray The ray that we're tracing.
     * @return The color of the closest point.
     */
    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        if (closestPoint == null)
            return scene.background;

        return calcColor(closestPoint, ray);
    }

    /**
     * > Calculate the color of a given point on the scene, by calculating the local effects (diffuse, specular, etc.) and
     * the global effects (reflection, refraction, etc.)
     * <p>
     * The function receives the following parameters:
     * <p>
     * * `intersection` - the point on the scene we want to calculate the color for
     * * `ray` - the ray that hit the scene at the given point
     * * `level` - the recursion level of the ray (how many times it has been reflected/refracted)
     * * `k` - the attenuation factor of the ray (how much light is left after passing through a transparent object)
     * <p>
     * The function returns the color of the given point
     *
     * @param intersection The intersection point of the ray and the geometry.
     * @param ray          the ray that intersects the geometry
     * @param level        the recursion level.
     * @param k            the color of the current pixel
     * @return The color of the intersection point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        if (intersection == null)
            return scene.background;

        Color color = intersection.geometry.getEmission()
                .add(calcLocalEffects(intersection, ray, k));
        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
    }


    /**
     * The function calculates the color of a point on a surface of a geometry in the scene, by calculating the color of
     * the point by the light sources in the scene, and the color of the point by the reflected rays from the other
     * geometries in the scene
     *
     * @param closestPoint The closest point to the ray's head.
     * @param ray          the ray that was sent from the camera to the scene
     * @return The color of the closest point.
     */
    private Color calcColor(GeoPoint closestPoint, Ray ray) {
        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(this.scene.ambientLight.getIntensity());
    }

    /**
     * It calculates the color of a point on a geometry, by calculating the color of the light sources that affect it
     *
     * @param gp  The point on the geometry that the ray intersected with.
     * @param ray the ray that hit the geometry
     * @param k   The attenuation factor of the ray (how much light is left after passing through a transparent object)
     * @return The color of the point.
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray, Double3 k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Vector v = ray.getDir();
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;

        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
                    color = color.add(iL.scale(calcDiffusive(material, nl)))
                            .add(iL.scale(calcSpecular(material, n, l, nl, v)));
                }
            }
        }
        return color;
    }

    /**
     * If the dot product of the normal and light vector is negative, make it positive and scale it by the diffuse color of
     * the material.
     *
     * @param material The material of the object that the ray hit.
     * @param nl       the dot product of the normal and the light vector
     * @return The diffuse color of the material.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        if (nl < 0) nl *= -1;
        return material.kD.scale(nl);
    }

    /**
     * > Calculate the specular component of the light reflected from a point on a surface
     *
     * @param material The material of the object that is being shaded.
     * @param n        normal vector
     * @param l        the direction of the light source
     * @param nl       the dot product of the normal and the light vector
     * @param v        the vector from the intersection point to the camera
     * @return The specular component of the light.
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(nl).scale(2));
        double minus_vr = v.scale(-1).dotProduct(r);
        if (minus_vr <= 0)
            return Double3.ZERO;
        return material.kS.scale(Math.pow(minus_vr, material.nShininess));
    }

    /**
     * If the ray from the point to the light source intersects with any geometry, then the point is shaded
     *
     * @param gp The point on the geometry that we're currently shading.
     * @param ls The light source that we are checking if it is unshaded.
     * @param l  The vector from the point to the light source.
     * @param n  The normal vector to the surface at the intersection point.
     * @return the color of the point.
     */
    private Double3 transparency(GeoPoint gp, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source

        Ray lightRay = new Ray(gp.point, lightDirection, n);
        Double3 ktr = new Double3(1);

        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return ktr;

        double lightDistance = ls.getDistance(lightRay.getP0());
        for (var geoPoint : intersections)
            if (geoPoint.point.distance(lightRay.getP0()) < lightDistance)
                ktr = geoPoint.geometry.getMaterial().kT.product(ktr);

        return ktr;
    }


    /**
     * If the ray from the point to the light source intersects with any geometry, then the point is shaded
     *
     * @param lightSource The light source that we are checking if it is unshaded.
     * @param gp          The point on the geometry that we're currently shading.
     * @param l           The vector from the point to the light source.
     * @param n           The normal vector to the surface at the intersection point.
     * @param nv          the dot product of the normal vector and the vector from the camera to the point.
     * @return the color of the point.
     */
    private boolean unshaded(LightSource lightSource, GeoPoint gp, Vector l, Vector n, double nv) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);

        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null)
            return true;

        double lightDistance = lightSource.getDistance(lightRay.getP0());
        for (var geoPoint : intersections)
            if (geoPoint.point.distance(lightRay.getP0()) < lightDistance &&
                    gp.geometry.getMaterial().kT == Double3.ZERO)
                return false;

        return true;
    }



    /**
     * Calculates the reflection and the refraction
     * at a given intersection point.
     *
     * @param gp    the intersection point
     * @param ray   the ray that caused the intersection
     * @param level the number of the recursive calls
     *              to calculate the next reflections and
     *              refractions
     * @param k     the effect's strength by the reflection and refraction
     * @return the color on the intersection point
     */
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Color color = Color.BLACK;
        Material material = gp.geometry.getMaterial();

        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        if (v.dotProduct(n) > 0) {
            n = n.scale(-1);
        }

        // adds the reflection effect
        Double3 kkr = k .product( material.kR);
        if (!kkr.lowerThan( MIN_CALC_COLOR_K)) {
            Ray[] reflectedRays = constructReflectedRays(gp.point, v, n, material.kG, glossinessRays);
            for (Ray reflectedRay : reflectedRays) {
                color = color.add(calcGlobalEffect(reflectedRay, level, material.kR, kkr)
                        .scale(1d / reflectedRays.length));
            }
        }

        // adds the refraction effect
        Double3 kkt = k.product( material.kT);
        if (!kkt.lowerThan( MIN_CALC_COLOR_K)) {
            // Creating a new array of rays that are refracted from the point of intersection.
            Ray[] refractedRays = constructRefractedRays(gp.point, v, n.scale(-1), material.kG, glossinessRays);
            for (Ray refractedRay : refractedRays) {
                color = color.add(calcGlobalEffect(refractedRay, level, material.kT, kkt)
                        .scale(1d / refractedRays.length));
            }
        }

        return color;
    }

    /**
     *help function to the recursion
     * @param ray from the geometry
     * @param level of recursion
     * @param kx parameter of the recursion
     * @param kkx parameter of the recursion
     * @return the calculate color
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint gp = findClosestIntersection(ray);
        return (gp == null ? scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * construct a reflected ray from the geometry
     * @param n normal vector of the point on the geometry
     * @param point on the geometry
     * @param ray from the geometry
     * @return new reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point point, Ray ray) {
        Vector v = ray.getDir();
        Vector vn = n.scale(-2 *  v.dotProduct(n));
        Vector r = v.add(vn);
        // use the constructor with 3 arguments to move the head
        return new Ray(point, r, n);
    }
    /**
     * construct the refracted ray of the point on the geometry
     * @param n normal vector
     * @param point on the geometry
     * @param ray from the geometry
     * @return new ray
     */
    private Ray constructRefractedRay(Vector n, Point point, Ray ray) {
        return new Ray(point, ray.getDir(), n);
    }

    /**
     * Constructs randomized reflection rays at the intersection point according to kG.
     * If kG is 1 then only one ray is returned with the specular vector
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @param kG    the glossiness parameter in range of [0,1], where 0 - matte, 1 - glossy
     * @return randomized reflection rays
     */
    private Ray[] constructReflectedRays(Point point, Vector v, Vector n, double kG, int numOfRays) {
        Vector n2vn = n.scale(-2 * v.dotProduct(n));
        Vector r = v.add(n2vn);

        // If kG is equals to 1 then return only 1 ray, the specular ray (r)
        if (isZero(kG - 1)) {
            return new Ray[]{new Ray(point, r, n)};
        }

        Vector[] randomizedVectors = createRandomVectorsOnSphere(n, numOfRays);

        // If kG is equals to 0 then select all the randomized vectors
        if (isZero(kG)) {
            return Arrays.stream(randomizedVectors)
                    .map(vector -> new Ray(point, vector, n))
                    .toArray(Ray[]::new);
        }

        // If kG is in range (0,1) then move the randomized vectors towards the specular vector (v)
        return Arrays.stream(randomizedVectors)
                .map(vector -> new Ray(point,
                        vector.scale(1 - kG).add(r.scale(kG)), n))
                .toArray(Ray[]::new);
    }

    private GeoPoint findClosestIntersection(Ray reflectedRay) {
        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(reflectedRay);
        if (intersections == null)
            return null;
        return reflectedRay.findClosestGeoPoint(intersections);
    }

    public RayTracerBasic setGlossinessRays(int glossinessRays) {
        if (glossinessRays <= 0) {
            throw new IllegalArgumentException("number of glossiness rays should be greater than 0");
        }

        this.glossinessRays = glossinessRays;
        return this;
    }


    /**
     * Constructs randomized refraction rays at the intersection point according to kG.
     * If kG is 1 then only one ray is returned with the vector v (which is the specular vector).
     *
     * @param point the intersection point
     * @param v     the intersection's ray direction
     * @param n     the normal at the intersection point
     * @param kG    the glossiness parameter in range of [0,1], where 0 - matte, 1 - glossy
     * @return randomized refraction rays
     */
    private Ray[] constructRefractedRays(Point point, Vector v, Vector n, double kG, int numOfRays) {
        // If kG is equals to 1 then return only 1 ray, the specular ray (v)
        if (isZero(kG - 1)) {
            return new Ray[]{new Ray(point, v, n)};
        }

        Vector[] randomizedVectors = createRandomVectorsOnSphere(n, numOfRays);

        // If kG is equals to 0 then select all the randomized vectors
        if (isZero(kG)) {
            return Arrays.stream(randomizedVectors)
                    .map(vector -> new Ray(point, vector, n))
                    .toArray(Ray[]::new);
        }

        // If kG is in range (0,1) then move the randomized vectors towards the specular vector (v)
        return Arrays.stream(randomizedVectors)
                .map(vector -> new Ray(point,
                        vector.scale(1 - kG).add(v.scale(kG)), n))
                .toArray(Ray[]::new);
    }

    /**
     * Creates random vectors on the unit hemisphere with a given normal on the hemisphere's bottom.<br>
     * source: https://my.eng.utah.edu/~cs6958/slides/pathtrace.pdf#page=18
     *
     * @param n normal to the hemisphere's bottom
     * @return the randomized vectors
     */
    private Vector[] createRandomVectorsOnSphere(Vector n, int numOfVectors) {
        // pick axis with smallest component in normal
        // in order to prevent picking an axis parallel
        // to the normal and eventually creating zero vector
        Vector axis;
        if (Math.abs(n.getX()) < Math.abs(n.getY()) && Math.abs(n.getX()) < Math.abs(n.getZ())) {
            axis = new Vector(1, 0, 0);
        } else if (Math.abs(n.getY()) < Math.abs(n.getZ())) {
            axis = new Vector(0, 1, 0);
        } else {
            axis = new Vector(0, 0, 1);
        }

        // find two vectors orthogonal to the normal
        Vector x = n.crossProduct(axis);
        Vector z = n.crossProduct(x);

        Vector[] randomVectors = new Vector[numOfVectors];
        for (int i = 0; i < numOfVectors; i++) {
            // pick a point on the hemisphere bottom
            double u, v, u2, v2;
            do {
                u = random() * 2 - 1;
                v = random() * 2 - 1;
                u2 = u * u;
                v2 = v * v;
            } while (u2 + v2 >= 1);

            // calculate the height of the point
            double w = Math.sqrt(1 - u2 - v2);

            // create the new vector according to the base (x, n, z) and the coordinates (u, w, v)
            randomVectors[i] = x.scale(u)
                    .add(z.scale(v))
                    .add(n.scale(w));
        }

        return randomVectors;
    }
    public Color averageColor(LinkedList<Ray> rays){
        Color color=Color.BLACK;
        for( Ray ray:rays){
            color=color.add(traceRay(ray));
        }
        return color.reduce(Double.valueOf(rays.size()));
    }

}