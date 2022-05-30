//
//package src.renderer;
//
//import src.geometries.Intersectable.*;
//import src.primitives.Color;
//import src.primitives.Point;
//import src.primitives.Ray;
//import src.primitives.Vector;
//import src.scene.Scene;
//import java.util.List;
//import src.geometries.Intersectable.GeoPoint;
//import src.lighting.*;
//import src.primitives.*;
//
//import static src.primitives.Util.*;
//
///**
// *  RayTracerBasic class extends RayTracerBase and implement the abstract function traceRay
// */
//public class RayTracerBasic extends RayTracerBase {
//    /**
//     * constant number for size moving first rays for recursion stop conditions
//     */
//    private static final Double3 INITIAL_K = new Double3(1.0);
//    private static final int MAX_CALC_COLOR_LEVEL = 10;
//    private static final double MIN_CALC_COLOR_K = 0.001;
//    private static final double EPS = 0.1;
//    private int glossinessRays = 10;
//
//    /**
//     * This is the constructor for the RayTracerBasic class. It calls the constructor for the RayTracerBase class.
//     */
//    public RayTracerBasic(Scene scene) {
//        super(scene);
//    }
//
//    /**
//     * Given a ray, find the closest point of intersection with the scene, and return the color of that point
//     *
//     * @param ray The ray that we're tracing.
//     * @return The color of the closest point.
//     */
//    @Override
//    public Color traceRay(Ray ray) {
//        GeoPoint closestPoint = findClosestIntersection(ray);
//        if (closestPoint == null)
//            return scene.background;
//
//        return calcColor(closestPoint, ray);
//    }
//
//    /**
//     * > Calculate the color of a given point on the scene, by calculating the local effects (diffuse, specular, etc.) and
//     * the global effects (reflection, refraction, etc.)
//     * <p>
//     * The function receives the following parameters:
//     * <p>
//     * * `intersection` - the point on the scene we want to calculate the color for
//     * * `ray` - the ray that hit the scene at the given point
//     * * `level` - the recursion level of the ray (how many times it has been reflected/refracted)
//     * * `k` - the attenuation factor of the ray (how much light is left after passing through a transparent object)
//     * <p>
//     * The function returns the color of the given point
//     *
//     * @param intersection The intersection point of the ray and the geometry.
//     * @param ray          the ray that intersects the geometry
//     * @param level        the recursion level.
//     * @param k            the color of the current pixel
//     * @return The color of the intersection point.
//     */
//    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
//        if (intersection == null)
//            return scene.background;
//
//        Color color = intersection.geometry.getEmission()
//                .add(calcLocalEffects(intersection, ray, k));
//        return level==1   ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
//
//    /**
//     * The function calculates the color of a point on a surface of a geometry in the scene, by calculating the color of
//     * the point by the light sources in the scene, and the color of the point by the reflected rays from the other
//     * geometries in the scene
//     *
//     * @param closestPoint The closest point to the ray's head.
//     * @param ray          the ray that was sent from the camera to the scene
//     * @return The color of the closest point.
//     */
//    private Color calcColor(GeoPoint closestPoint, Ray ray) {
//        return calcColor(closestPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
//                .add(this.scene.ambientLight.getIntensity());
//    }
//
//    /**
//     * It calculates the color of a point on a geometry, by calculating the color of the light sources that affect it
//     *
//     * @param geoPoint  The point on the geometry that the ray intersected with.
//     * @param ray the ray that hit the geometry
//     * @param k   The attenuation factor of the ray (how much light is left after passing through a transparent object)
//     * @return The color of the point.
//     */
//    private Color calcLocalEffects(GeoPoint geoPoint, Ray ray, Double3 k) {
//        Color color = Color.BLACK;
//        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
//        Vector v = ray.getDir();
//        double nv = alignZero(n.dotProduct(v));
//        if (nv == 0)
//            return color;
//
//        Material material = geoPoint.geometry.getMaterial();
//        for (LightSource lightSource : scene.lights) {
//            Vector l = lightSource.getL(geoPoint.point);
//            double nl = alignZero(n.dotProduct(l));
//            double specularN=1;
//            if(lightSource instanceof SpotLight)
//            {
//                specularN=((SpotLight) lightSource).getSpecularN();
//            }
//
////                if (ktr * k > MIN_CALC_COLOR_K) {
////                    Color lightIntensity = lightSource.getIntensity(intersection.point).scale(ktr);
////                    color = color.add(calcDiffusive(kd,l,n, lightIntensity), calcSpecular( ks,l,n, nl, v, nShininess, lightIntensity,specularN));
////                }
//
//
//            if (checkSign(nl,nv)) { // sign(nl) == sing(nv)
//                Double3 ktr = transparency(geoPoint, lightSource, l, n);
//                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
//                    Color iL = lightSource.getIntensity(geoPoint.point).scale(ktr);
//                    color = color.add(iL.scale(calcDiffusive(material, nl)))
//                            .add(iL.scale(calcSpecular(material, n, l, nl, v)));
//                }
//            }
//        }
//        return color;
//    }
//
//    /**
//     * If the dot product of the normal and light vector is negative, make it positive and scale it by the diffuse color of
//     * the material.
//     *
//     * @param material The material of the object that the ray hit.
//     * @param nl       the dot product of the normal and the light vector
//     * @return The diffuse color of the material.
//     */
//    private Double3 calcDiffusive(Material material, double nl) {
//        if (nl < 0)
//            nl *= -1;
//        return material.kD.scale(nl);
//    }
//
//    public RayTracerBasic setGlossinessRays(int glossinessRays) {
//        if (glossinessRays <= 0) {
//            throw new IllegalArgumentException("number of glossiness rays should be greater than 0");
//        }
//
//        this.glossinessRays = glossinessRays;
//        return this;
//    }
//
//    /**
//     * > Calculate the specular component of the light reflected from a point on a surface
//     *
//     * @param material The material of the object that is being shaded.
//     * @param normal        normal vector
//     * @param lightSourceDirection        the direction of the light source
//     * @param normalDotProdactLight       the dot product of the normal and the light vector
//     * @param intersectionFromCamera        the vector from the intersection point to the camera
//     * @return The specular component of the light.
//     */
//    private Double3 calcSpecular(Material material, Vector normal, Vector lightSourceDirection, double normalDotProdactLight, Vector intersectionFromCamera) {
//        Vector reduceVec = lightSourceDirection.subtract(normal.scale(normalDotProdactLight).scale(2));
//        double minus_vr = intersectionFromCamera.scale(-1).dotProduct(reduceVec);
//        if (minus_vr <= 0)
//            return Double3.ZERO;
//        return material.kS.scale(Math.pow(minus_vr, material.nShininess));
//    }
//
//    /**
//     * If the ray from the point to the light source intersects with any geometry, then the point is shaded
//     *
//     * @param geoPoint The point on the geometry that we're currently shading.
//     * @param lightSource The light source that we are checking if it is unshaded.
//     * @param l  The vector from the point to the light source.
//     * @param normal  The normal vector to the surface at the intersection point.
//     * @return the color of the point.
//     */
//    private Double3 transparency(GeoPoint geoPoint, LightSource lightSource, Vector l, Vector normal) {
//        Vector lightDirection = l.scale(-1); // from point to light source
//
//        Ray lightRay = new Ray(geoPoint.point, lightDirection, normal);
//        Double3 ktDotProdactr = new Double3(1);
//
//        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null)
//            return ktDotProdactr;
//
//        double lightDistance = lightSource.getDistance(lightRay.getP0());
//        for (var geoPointFromIntersection : intersections)
//            if (geoPointFromIntersection.point.distance(lightRay.getP0()) < lightDistance)
//                ktDotProdactr = geoPointFromIntersection.geometry.getMaterial().kT.product(ktDotProdactr);
//
//        return ktDotProdactr;
//    }
//
//
//    /**
//     * If the ray from the point to the light source intersects with any geometry, then the point is shaded
//     *
//     * @param lightSource The light source that we are checking if it is unshaded.
//     * @param geoPoint          The point on the geometry that we're currently shading.
//     * @param fromPointToLihghtVec           The vector from the point to the light source.
//     * @param n           The normal vector to the surface at the intersection point.
//     * @param nv          the dot product of the normal vector and the vector from the camera to the point.
//     * @return the color of the point.
//     */
//    private boolean unshaded(LightSource lightSource, GeoPoint geoPoint, Vector fromPointToLihghtVec, Vector n, double nv) {
//        Vector lightDirection = fromPointToLihghtVec.scale(-1); // from point to light source
//        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
//
//        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(lightRay);
//        if (intersections == null)
//            return true;
//
//        double lightDistance = lightSource.getDistance(lightRay.getP0());
//        for (var geoPointFromIntersection : intersections)
//            if (geoPointFromIntersection.point.distance(lightRay.getP0()) < lightDistance &&
//                    geoPointFromIntersection.geometry.getMaterial().kT == Double3.ZERO)
//                return false;
//
//        return true;
//    }
//
//
//    /**
//     * It calculates the color of a point on a geometry, taking into account the global effects of reflection and
//     * refraction
//     *
//     * @param geoPoint The closest intersection point of the ray with the scene.
//     * @param ray the ray that was sent from the camera to the scene
//     * @param level the recursion level.
//     * @param k the color of the light that is reflected from the current point.
//     * @return The color of the point.
//     */
//    private Color calcGlobalEffects(GeoPoint geoPoint, Ray ray, int level, Double3 k) {
//        Color color = Color.BLACK;
//        Vector n = geoPoint.geometry.getNormal(geoPoint.point);
//
//        // the reflection effect
//        Double3 kr = geoPoint.geometry.getMaterial().kR;
//        Double3 kkr = kr.product(k);
//        if (!kkr.lowerThan(MIN_CALC_COLOR_K)) {
//            Ray reflectedRay = constructReflectedRay(n, geoPoint.point, ray);
//            GeoPoint reflectedPoint = findClosestIntersection(reflectedRay);
//            color = color.add(calcColor(reflectedPoint, reflectedRay, level - 1, kkr).scale(kr));
//        }
//
//        // the refraction effect
//        Double3 kt = geoPoint.geometry.getMaterial().kT;
//        Double3 kkt = kt.product(k);
//        if (!kkt.lowerThan(MIN_CALC_COLOR_K)) {
//            Ray refractedRay = constructReflectedRay(n, geoPoint.point, ray);
//            GeoPoint refractedPoint = findClosestIntersection(refractedRay);
//            color = color.add(calcColor(refractedPoint, refractedRay, level - 1, kkt).scale(kt));
//        }
//
//        return color;
//    }
//
//    /**
//     * "Construct a ray that is reflected off of a surface at a given point."
//     *
//     * The function takes in a normal vector, a point, and a ray. It then calculates the reflected ray and returns it
//     *
//     * @param n The normal vector of the point of intersection.
//     * @param point The point of intersection between the ray and the object.
//     * @param ray the ray that hit the object
//     * @return A ray that is reflected off the surface of the object.
//     */
//    private Ray constructReflectedRay(Vector n, Point point, Ray ray) {
//        Vector v = ray.getDir();
//        Vector vn = n.scale(-2 * v.dotProduct(n));
//        Vector r = v.add(vn);
//
//        return new Ray(point, r, n);
//    }
//
////    /**
////     * Construct a refracted ray from the given point, ray, and normal.
////     *
////     * @param n the normal vector of the surface
////     * @param point The point of intersection between the ray and the surface.
////     * @param ray the ray that hit the object
////     * @return A new ray with the same direction as the original ray, but with a new origin and a new normal.
////     */
////    private Ray constructRefractedRay(Vector n, Point point, Ray ray) {
////        return new Ray(point, ray.getDir(), n);
////    }
//
//    /**
//     * It finds the closest intersection point of the reflected ray with the scene's geometries
//     *
//     * @param reflectedRay The ray that is reflected from the point of intersection.
//     * @return The closest intersection point of the reflected ray with the scene.
//     */
//    private GeoPoint findClosestIntersection(Ray reflectedRay) {
//        List<GeoPoint> intersections = this.scene.geometries.findGeoIntersections(reflectedRay);
//        if (intersections == null)
//            return null;
//        return reflectedRay.findClosestGeoPoint(intersections);
//    }
//}