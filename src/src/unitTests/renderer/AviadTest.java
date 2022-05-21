package src.unitTests.renderer;

import org.junit.jupiter.api.Test;
import src.geometries.*;
import src.lighting.AmbientLight;
import src.lighting.DirectionalLight;
import src.lighting.PointLight;
import src.lighting.SpotLight;
import src.primitives.*;
import src.renderer.Camera;
import src.renderer.ImageWriter;
import src.renderer.RayTracerBasic;
import src.scene.Scene;

import static java.awt.Color.*;

public class AviadTest {

    private Scene scene = new Scene("Aviad scene").setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));
    private Camera camera = new Camera(new Point(0, 0, 2500), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setVPSize(500, 500) //
            .setVPDistance(1000);

    private Point[] p = { // The Triangles' vertices:
            new Point(-110, -110, -150), // the shared left-bottom
            new Point(95, 100, -150), // the shared right-top
            new Point(110, -110, -150), // the right-bottom
            new Point(-75, 78, 100) }; // the left-top

    private Material material = new Material().setKd(0.5).setKs(0.5).setShininess(300);
    private Geometry sphere = new Sphere(new Point(0, 0, -50), 50d) //
            .setEmission(new Color(BLUE).reduce(2)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300));
    private Geometry triangle = new Triangle(p[0], p[1], p[2]).setMaterial(material);





    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void aviad() {
        scene.geometries.add(
                new Sphere(new Point(0, 0, -25), 25) //
                        .setEmission(new Color(BLUE).reduce(2)) //
                .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(300).setKt(0.3)),

                new Sphere(new Point(0, 0, -25), 23) //
                        .setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),


                new Cylinder(new Ray(new Point(0, 0, -25), new Vector(0,1,0)),25, 25)
                        .setEmission(new Color(BLUE).reduce(2)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100)),


                new Cylinder(new Ray(new Point(0, 0, -25), new Vector(0,1,0)),25, 25)
                        .setEmission(new Color(BLUE).reduce(2)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),

                new Cylinder(new Ray(new Point(0, 0, -75), new Vector(0,-10,0)),3, 60)
                        .setEmission(new Color(BLUE).reduce(2)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(300)),

        new Triangle(new Point(-70, -40, -75), new Point(-40, -70,-80), new Point(-68, -68, -80)) //
                .setEmission(new Color(GREEN)) //
                .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
            new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
            .setMaterial(new Material().setKs(0.8).setShininess(60)), //
            new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
            .setMaterial(new Material().setKs(0.8).setShininess(60)), //
            new Sphere(new Point(0, 0, -11), 30d) //
            .setEmission(new Color(java.awt.Color.BLUE)) //
            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add(new SpotLight(new Color(800, 500, 0), new Point(-50, -50, 25), new Vector(1, 1, -0.5)).setKl(0.001).setKq(0.0001));
        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        ImageWriter imageWriter = new ImageWriter("Aviad", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage(); //
    }

//
//    new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
//            .setMaterial(new Material().setKs(0.8).setShininess(60)), //
//            new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
//            .setMaterial(new Material().setKs(0.8).setShininess(60)), //
//            new Sphere(new Point(0, 0, -11), 30d) //
//            .setEmission(new Color(java.awt.Color.BLUE)) //
//            .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //

//
//@Test
//public void Bonus6() {
//    Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)); //
//
//    scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));
//
//    scene.geometries.add( //
//            new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
//                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //
//            new Triangle(new Point(-130, -130, -130), new Point(140, -140, -135), new Point(65, 65, -140)) //
//                    .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(java.awt.Color.cyan)), //
//            new Triangle(new Point(150, -150, -150), new Point(-150, 1500, -150),
//                    new Point(67, 67, 300)) //
//                    .setEmission(new Color(java.awt.Color.ORANGE)) //
//                    .setMaterial(new Material().setKr(1).setKt(0.5)),
//            new Triangle(new Point(150, -150, -150), new Point(-1500, 1500, -1500),
//                    new Point(-150, -150, -200)) //
//                    .setEmission(new Color(0, 120, 220)) //
//                    .setMaterial(new Material().setKr(1).setKt(0.5)),
//            new Sphere( new Point(140, -150, -100),25) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//            new Sphere( new Point(140, -60, -100),15) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//            new Sphere( new Point(140, 10, -100),25) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//            new Sphere( new Point(140, 80, -100),15) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//            new Sphere( new Point(-100, 150, 50),35) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(1)),
//            new Sphere( new Point(-75, 100, 50),45) //
//                    .setEmission(new Color(java.awt.Color.magenta)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
//            new Sphere( new Point(-55, 100, 50),35) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.3)),
//            new Sphere( new Point(-30, 100, 50),25) //
//                    .setEmission(new Color(java.awt.Color.magenta)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
//            new Sphere( new Point(-10, 100, 50),20) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.5)),
//            new Sphere( new Point(15, 100, 50),15) //
//                    .setEmission(new Color(java.awt.Color.magenta)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
//            new Sphere( new Point(40, 100, 50),10) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.6)),
//            new Sphere( new Point(50, 100, 50),5) //
//                    .setEmission(new Color(java.awt.Color.magenta)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
//            new Sphere( new Point(70, -10, -100),10) //
//                    .setEmission(new Color(java.awt.Color.cyan)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//            new Sphere( new Point(75, 75, 50),30) //
//                    .setEmission(new Color(java.awt.Color.yellow)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.6)),
//            new Sphere( new Point(-350, -300, -400),400) //
//                    .setEmission(new Color(0, 0, 100)) //
//                    .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
//            new Sphere( new Point(-350, -300, -400),200) //
//                    .setEmission(new Color(100, 120, 120)) //
//                    .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
//            new Polygon(new Point(100,100,0),new Point(-70, 70, -140),new Point(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
//                    .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6))
//
//    );
//
//    scene.lights.add(new SpotLight(new Color(700, 400, 400),new Vector(0, 0, -1), new Point(60, 50, 0)) //
//            .setkQ(2E-7)); //.setkQ(0.000005));
//    scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point(-750, -750, -150)));
//    ImageWriter imageWriter = new ImageWriter("bonus6", 600, 600);
//
//    camera.setImageWriter(imageWriter) //
//            .setRayTracer(new RayTracerBasic(scene)) //
//            .renderImage() //
//            .writeToImage();
//}
//
//    @Test
//    public void miniProject1() {
//        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(500, 500).setDistance(1000).setNumOfRays(40);
//
//        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), 0.17));
//
//        scene.geometries.add( //
//                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //
//                new Sphere( new Point(140, 30, -100),40) //
//                        .setEmission(new Color(java.awt.Color.cyan)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//
//                new Sphere( new Point(-100, 170, 50),45) //
//                        .setEmission(new Color(java.awt.Color.BLACK)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(1)),
//                new Sphere( new Point(-105, 120, 80),55) //
//                        .setEmission(new Color(java.awt.Color.RED)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5)),
//                new Sphere( new Point(-40, 120, 50),35) //
//                        .setEmission(new Color(java.awt.Color.pink)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//                new Sphere( new Point(15, 120, 70),25) //
//                        .setEmission(new Color(java.awt.Color.RED)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4).setKt(0.4)),
//                new Sphere( new Point(50, 140, -50),15) //
//                        .setEmission(new Color(java.awt.Color.pink)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.4)),
//                new Sphere( new Point(70, -10, -100),20) //
//                        .setEmission(new Color(java.awt.Color.cyan)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
//                new Sphere( new Point(75, 75, -100),40) //
//                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.5).setShininess(30)).setEmission(new Color(java.awt.Color.magenta)),
//                new Sphere( new Point(-350, -300, -400),500) //
//                        .setEmission(new Color(0, 0, 150)) //
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(60).setKt(0.3)),
//                new Sphere( new Point(-350, -300, -300),300) //
//                        .setEmission(new Color(200, 120, 120)) //
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(60).setKt(0.3)),
//                new Sphere( new Point(-350, -300, -300),150) //
//                        .setEmission(new Color(java.awt.Color.red)) //
//                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(60).setKt(0.3)),
//                new Polygon(new Point(200,100,0),new Point(-70, 70, -140),new Point(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));
//
//
//
//        scene.lights.add(new SpotLight(new Color(700, 400, 400),new Vector(0, 0, -1), new Point(60, 50, 0)) //
//                .setkQ(2E-7)); //.setkQ(0.000005));
//        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Vector(-1, -1, -4), new Point(-750, -750, -150)));
//        scene.lights.add(new DirectionalLight(new Color(400, 30, 0), new Vector(0.65, 0, -1)));
//        ImageWriter imageWriter = new ImageWriter("miniProject1", 500, 500);
//
//        camera.setImageWriter(imageWriter) //
//                .setRayTracer(new RayTracerBasic(scene)) //
//                .renderImage() //
//                .writeToImage();
//
//    }
}
