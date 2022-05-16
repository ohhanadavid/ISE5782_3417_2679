package src.unitTests.renderer;


import org.testng.annotations.Test;
import src.geometries.*;
import src.lighting.AmbientLight;
import src.lighting.DirectionalLight;
import src.lighting.PointLight;
import src.lighting.SpotLight;
import src.primitives.*;
import src.renderer.Camera;
import src.renderer.ImageWriter;
import src.renderer.RayTracerBasic;
import src.renderer.Render;
import src.scene.Scene;

import static java.awt.Color.*;

@Test
public class ExSevenTest {

@Test
private void dc() {
        Camera camera = new Camera(
                new Point(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(200, 125)
                .setVPDistance(800)
                .setNumOfRays(70);

        Scene scene = new Scene("Test Scene");
    scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

        setLights(scene);

        setGeometries(scene);

        Render render = new Render() //
                .setCamera(camera) //
                .setRayTracer(new RayTracerBasic(scene).setGlossinessRays(20));

        int frames = 16;
        double angle = 360d / frames;
        double angleRadians = 2 * Math.PI / frames;

        double radius = camera.getP0().subtract(Point.ZERO).length();
        for (int i = 0; i < frames; i++) {
            System.out.println("Start frame " + (i + 1));
            camera.rotate(0, angle, 0);
            camera.setP0(new Point(
                    Math.sin(angleRadians * (i + 1)) * radius,
                    0,
                    Math.cos(angleRadians * (i + 1)) * radius));

            ImageWriter imageWriter = new ImageWriter("project" + (i + 1), 600, 450);
            camera.setImageWriter(imageWriter).
                    setRayTracer(new RayTracerBasic(scene))
                    .renderImage()
                    .writeToImage();
        }
    }




    private void setLights(Scene scene){
        scene.lights.add(
                new SpotLight(
                        new Color(400, 400, 400),
                        new Point(-50, 100, 100),
                        new Vector(-0.5, -1, -0.5)
                       )
                        .setKl(0.004)
                        .setKq(0.000006)
        );
        scene.lights.add(new SpotLight(new Color(0,250,350) ,new Point(-200, 100, 0), new Vector(1, 1, -2)).setSpecularN(40) //
                .setKl(0.00000005).setKq(0.000000005));
        scene.lights.add(new SpotLight(new Color(0,250,350), new Point(-200, 50, 0), new Vector(1, 0.5, -2)).setSpecularN(20) //
                .setKl(0.00000005).setKq(0.000000005));
        scene.lights.add(new SpotLight(new Color(0,250,350), new Point(-200, 55, 0), new Vector(1, 1, -2)).setSpecularN(10) //
                .setKl(0.00000005).setKq(0.000000005));


    }


    private void setGeometries(Scene scene) {
        //triangles
        Point h=new Point(60,-50,30);
        Point g=new Point(45,-30,0);
        Point a=new Point(30,-50,30);
        Point b=new Point(40,0,15);



        scene.geometries.add(
                //sphere

                new Sphere(new Point(80, -28, 0), 22)
                        .setEmission(new Color(30,40,50))
                        .setMaterial(new Material()
                                .setKr(0.8)),

                new Sphere(new Point(-45, -45, -5), 5)
                        .setEmission(new Color(0,60,0))
                        .setMaterial(new Material()
                                .setKr(0.8).setKg(0.95)),

                //triangles

                new Triangle(a,g,h)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4).setKt(0.6)
                                .setShininess(80)),
                new Triangle(a,b,h)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4).setKt(0.6)
                                .setShininess(80)),
                new Triangle(a,b,g)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4).setKt(0.6)
                                .setShininess(80)),
                new Triangle(g,b,h)
                        .setEmission(new Color(0, 75, 66))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4).setKt(0.6)
                                .setShininess(80)),

                //cylinder
                new Cylinder(new Ray(
                        new Point(-80, -45, 0),
                        new Vector(60, 85, 0)),
                        13, 50)
                        .setEmission(new Color(0,100,70))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50))
                ,
                new Cylinder(new Ray(
                        new Point(-70, -61, 0),
                        new Vector(1, 0, 0)),
                        11, 140)
                        .setEmission(new Color(0,51,102))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4).setKg(0.9)
                                .setShininess(50)),


                //square
                //1
                new Polygon(new Point(-25,-50,-30),
                        new Point(-25,-50,30),
                        new Point(15,-50,30),
                        new Point(15,-50,-30))
                        .setEmission(new Color(0,75,100))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                new Polygon(new Point(-25,-25,-30),
                        new Point(-25,-25,30),
                        new Point(15,-25,30),
                        new Point(15,-25,-30))
                        .setEmission(new Color(0,75,100))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                new Polygon(new Point(-25,-50,-30),
                        new Point(-25,-50,30),
                        new Point(-25,-25,30),
                        new Point(-25,-25,-30))
                        .setEmission(new Color(0,75,100))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                new Polygon(new Point(15,-50,-30),
                        new Point(15,-50,30),
                        new Point(15,-25,30),
                        new Point(15,-25,-30))
                        .setEmission(new Color(0,75,100))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                new Polygon(new Point(-25,-50,30),
                        new Point(15,-50,30),
                        new Point(15,-25,30),
                        new Point(-25,-25,30))
                        .setEmission(new Color(0,75,100))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                new Polygon(new Point(-25,-50,-30),
                        new Point(15,-50,-30),
                        new Point(15,-25,-30),
                        new Point(-25,-25,-30))
                        .setEmission(new Color(0,75,100))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                //2
                new Polygon(new Point(-15,-25,-20),
                        new Point(-15,-25,20),
                        new Point(5,-25,20),
                        new Point(5,-25,-20))
                        .setEmission(new Color(0,90,100))
                        .setMaterial(new Material()
                                .setKr(0.1).setKd(0.5).setKs(0.5).setKt(0.2)
                                .setShininess(60)),
                new Polygon(new Point(-15,-15,-20),
                        new Point(-15,-15,20),
                        new Point(5,-15,20),
                        new Point(5,-15,-20))
                        .setEmission(new Color(0,90,100))
                        .setMaterial(new Material()
                                .setKr(0.1).setKd(0.5).setKs(0.5).setKt(0.2)
                                .setShininess(60)),
                new Polygon(new Point(-15,-25,-20),
                        new Point(-15,-25,20),
                        new Point(-15,-15,20),
                        new Point(-15,-15,-20))
                        .setEmission(new Color(0,90,100))
                        .setMaterial(new Material()
                                .setKr(0.1).setKd(0.5).setKs(0.5).setKt(0.2)
                                .setShininess(60)),
                new Polygon(new Point(5,-25,-20),
                        new Point(5,-25,20),
                        new Point(5,-15,20),
                        new Point(5,-15,-20))
                        .setEmission(new Color(0,90,100))
                        .setMaterial(new Material()
                                .setKr(0.1).setKd(0.5).setKs(0.5).setKt(0.2)
                                .setShininess(60)),
                new Polygon(new Point(-15,-25,20),
                        new Point(5,-25,20),
                        new Point(5,-15,20),
                        new Point(-15,-15,20))
                        .setEmission(new Color(0,90,100))
                        .setMaterial(new Material()
                                .setKr(0.1).setKd(0.5).setKs(0.5).setKt(0.2)
                                .setShininess(30)),
                new Polygon(new Point(-15,-25,-20),
                        new Point(5,-25,-20),
                        new Point(5,-15,-20),
                        new Point(-15,-15,-20))
                        .setEmission(new Color(0,90,100))
                        .setMaterial(new Material()
                                .setKr(0.1).setKd(0.5).setKs(0.5).setKt(0.2)
                                .setShininess(60)),


                // surface
                new Polygon(
                        new Point(-100, -50, -150),
                        new Point(-100, -50, 200),
                        new Point(100, -50, 200),
                        new Point(100, -50, -150))
                        .setEmission(new Color(102, 153, 153))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4).setKt(0.2)
                                .setShininess(50)),
                //front block
                new Polygon(
                        new Point(0, -50, 75),
                        new Point(0, 30, 75),
                        new Point(38, 30, 75),
                        new Point(38, -50, 75))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setKt(1.0).setKg(0.8)),
                new Polygon(
                        new Point(42, -50, 75),
                        new Point(42, 30, 75),
                        new Point(80 ,30, 75),
                        new Point(80, -50, 75))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setKt(1.0).setKg(0.8))



        );
    }
    @Test
    public void project2() {
        Camera camera = new Camera(
                new Point(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(225, 150)
                .setVPDistance(800)
                .setNumOfRays(10)
                .setFocus(new Point(0, 0, 0), 500);

        Scene scene = new Scene("Test Scene");
        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));
        scene.lights.add(

                new SpotLight(
                        new Color(500, 500, 500),
                        new Point(-50, 100, 100),
                        new Vector(-0.5, -1, -0.5)
                )
                        .setKl(0.04)
                        .setKq(0.0006));
        scene.geometries.add(
                new Sphere(new Point(50, 0, 0), 50)
                        .setEmission(new Color(5, 5, 5))
                        .setMaterial(new Material().setKd(0.3).setKs(0.5)
                                .setKr(1.0).setKg(0.8)),
                new Cylinder(new Ray(
                        new Point(-90, -35, 0),
                        new Vector(60, 85, 0)),
                        25, 100)
                        .setEmission(new Color(100, 75, 0))
                        .setMaterial(new Material()
                                .setKd(0.6).setKd(0.4).setKs(0.4)
                                .setShininess(80)),
                new Polygon(
                        new Point(-100, -50, -150),
                        new Point(-100, -50, 150),
                        new Point(100, -50, 150),
                        new Point(100, -50, -150))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50)),
                new Polygon(
                        new Point(-100, -50, -150),
                        new Point(-100, 75, -150),
                        new Point(100, 75, -150),
                        new Point(100, -50, -150))
                        .setEmission(new Color(40, 40, 40))
                        .setMaterial(new Material()
                                .setKd(0.6).setKs(0.4)
                                .setShininess(50))
        );

        int frames = 16;
        double angle = 360d / frames;
        double angleRadians = 2 * Math.PI / frames;

        double radius = camera.getP0().subtract(Point.ZERO).length();

        for (int i = 0; i < frames; i++) {
            System.out.println("Start frame " + (i + 1));

            camera.rotate(0, angle, 0);
            camera.setP0(new Point(
                    Math.sin(angleRadians * (i + 1)) * radius,
                    0,
                    Math.cos(angleRadians * (i + 1)) * radius)
            );

            camera.setImageWriter(new ImageWriter("project", 750, 500))
                    .setRayTracer(new RayTracerBasic(scene)
                            .setGlossinessRays(20))
                    .renderImage()
                    .writeToImage();


        }
    }

    @Test
    private void dfdf(){
         Scene scene = new Scene("Test scene");
        Camera camera = new Camera(new Point(-2500, 0, 200), new Vector(10, 0, 0), new Vector(0, 0, 1)) //
                .setVPSize(600, 600).setVPDistance(1000);

//        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0,-1), new Vector(0, 1, 0)) //
//               .setVPSize(600, 600).setVPDistance(1000);

Triangle pyramidWigA = new Triangle(new Point(0,-200,0),new Point(200,0,0),new Point(0,0,200));
Triangle pyramidWigB =new Triangle(new Point(200,0,0),new Point(0,200,0),new Point(0,0,200));
Triangle pyramidWigC = new Triangle(new Point(0,200,0),new Point(-200,0,0),new Point(0,0,200));
Triangle pyramidWigD = new Triangle(new Point(-200,0,0),new Point(0,0,200),new Point(0,-200,0));

        Sphere sun = new Sphere(new Point(400,500,600),100);

        Sphere A= new Sphere(new Point(519.070390744984252,440.107162582189176,0),50);
        Sphere B= new Sphere(new Point(568.560487306058349,330.381775783034186,0),100);
        Sphere C=new Sphere(new Point(507.452522044068246,192.73059510366852,0),65);
        Sphere D=new Sphere(new Point(500.713789922087472,78.895843857937507,0),144.03253);
        Sphere E=new Sphere(new Point(500.713789922087472,78.895843857937507,0),117.774);
        Sphere F=new Sphere(new Point(531.970492287884554,-117.758343954578777,0),115.0555);
        Sphere G=new Sphere(new Point(555.040695576198686,-275.299697925167038,0),92.138);
        Sphere H=new Sphere(new Point(626.427425256983042,-450.751787923952179,0),102.8875);

       Cylinder AB=new Cylinder(new Ray(new Point(200,0,0),new Vector(-200,0,200)),0.10,282.84271247461902);
        Cylinder BC=new Cylinder(new Ray(new Point(0,200,0),new Vector(0,-200,200)),0.10,282.84271247461902);
        Cylinder CD=new Cylinder(new Ray(new Point(-200,0,0),new Vector(200,0,200)),4,282.84271247461902);
        Cylinder DA=new Cylinder(new Ray(new Point(0,-200,0),new Vector(0,200,200)),0.10,282.84271247461902);

        scene.geometries.add(
                new Plane(new Point(0,-600,0),new Point(0,-650,0),new Point(-400,0,0)).setEmission(new Color(BLUE)),
                new Plane(new Point(0,0,1500),new Point(1500,0,0),new Point(1500,200,0)).setEmission(new Color(java.awt.Color.cyan)),
                pyramidWigA.setEmission(new Color(RED)),
                pyramidWigB.setEmission(new Color(BLUE) ),
                pyramidWigC.setEmission(new Color(BLACK)),
                pyramidWigD.setEmission(new Color(WHITE)),
                sun.setEmission(new Color(java.awt.Color.orange))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.2).setKr(0.5)),
                A.setEmission(new Color(pink)),
                B.setEmission(new Color(pink)),
                C.setEmission(new Color(pink)),
                D.setEmission(new Color(pink)),
                E.setEmission(new Color(pink)),
                F.setEmission(new Color(pink)),
                G.setEmission(new Color(pink)),
                H.setEmission(new Color(pink)),

               CD.setEmission(new Color(RED)),
//                BC.setEmission(new Color(WHITE)),
                AB.setEmission(new Color(WHITE))
              //  DA.setEmission(new Color(WHITE))
        );


        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));
        scene.lights.add(
                new PointLight(new Color(235,236,166),sun.getCenter()).setKl(0.5).setKq(0.3));
        scene.lights.add(
                new SpotLight(new Color(yellow),new Point(-2500, 0, 200), new Vector(10, 0, 0)
                        ).setSpecularN(50));

        camera.setImageWriter(new ImageWriter("שבנ", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

}







