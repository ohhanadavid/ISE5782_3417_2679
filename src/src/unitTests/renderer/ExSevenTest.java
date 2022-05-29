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
                .setVPSize(900, 900).setVPDistance(1000);

//        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0,-1), new Vector(0, 1, 0)) //
//               .setVPSize(600, 600).setVPDistance(1000);
        Color Pyramid = new Color(245,245,245);
        Color Sky = new Color(135,206,235);
        Color LightSky = new Color(135,206,250);
        Color Sand1 = new Color(238,232,170);
        Color Sand2 = new Color(194, 190, 128);
        Color Sand3 = new Color(194,183,128);
        Color Sand4 = new Color(194, 178, 128);
        Color Sand5 = new Color(194, 171, 128);
        Color Sand6 = new Color(194, 164, 128);
        Color dune1 =new Color(248,240,164);
        Color dune2 = new Color(226,209,186);
        Color dune3 = new Color(239,219,188);

        Color Cloud1 = new Color(245,245,245);
        Color Cloud2 = new Color(248,248,255);
        Color Cloud3 = new Color(255,250,240);
        Color marble =new Color(240, 235, 215);
        scene.background= LightSky;

        Point p3=new Point(-2013.136284832225783,357.051460824943433,-5.133775151772667),//
                q3=new Point(-1942.03008605314858,190.915033619817848,39.868082062413876),//
                r3=new Point(-2037.592215409018536,148.499807574943901,34.275994001136567),//
                s3= new Point(-2109.323689339737484,316.097161977662836,-11.121588811899208),//
                w=new Point(-1996.428962433538118, 331.422367649602563, -106.485801820063017),
                v3=new Point(-1926.792077806360794,169.958220746950815,-61.576772490359375),//
                w3=new Point(-2022.35420716223075,127.542994702076911,-67.168860551636655),//
                u=new Point(-2092.262816215224575, 289.903367482975739, -112.473615480189551);

//        Polygon a= new Polygon(r3,q3,w3,v3,r3);
//        Polygon b=new Polygon(r3,q3,s3,p3,r3);
//        Polygon c=new Polygon(s3,p3,w,u,s3);
//        Polygon d=new Polygon(w3,v3,w,u);
//        Polygon e=new Polygon(q3,p3,w,v3);
//        Polygon f=new Polygon(w3,r3,s3,u);



        //-------------------------pyramids--------------------------------//
Triangle pyramidAWigA = new Triangle(new Point(0,-200,0),new Point(200,0,0),new Point(0,0,200));
Triangle pyramidAWigB =new Triangle(new Point(200,0,0),new Point(0,200,0),new Point(0,0,200));
Triangle pyramidAWigC = new Triangle(new Point(0,200,0),new Point(-200,0,0),new Point(0,0,200));
Triangle pyramidAWigD = new Triangle(new Point(-200,0,0),new Point(0,0,200),new Point(0,-200,0));
        Cylinder ab=new Cylinder(new Ray(new Point(200,0,0),new Vector(-200,0,200)),0.10,282.84271247461902);
        Cylinder bc=new Cylinder(new Ray(new Point(0,200,0),new Vector(0,-200,200)),0.10,282.84271247461902);
        Cylinder cd=new Cylinder(new Ray(new Point(-200,0,0),new Vector(200,0,200)),4,282.84271247461902);
        Cylinder da=new Cylinder(new Ray(new Point(0,-200,0),new Vector(0,200,200)),0.10,282.84271247461902);

        Triangle pyramidBWigA = new Triangle(new Point(-589.994341128794986,450.286959177999051,0)
                ,new Point(-694.538033043532778, 1117.952116537043594, 0)
                ,new Point(-976.098765765686153, 731.847691900152427, 675.800374275156287));
        Triangle pyramidBWigB =new Triangle(new Point(-694.538033043532778, 1117.952116537043594, 0)
                ,new Point(-1362.20319040257732, 1013.408424622305802, 0)
                ,new Point(-976.098765765686153, 731.847691900152427, 675.800374275156287));
        Triangle pyramidBWigC = new Triangle(new Point(-1257.659498487839528, 345.743267263261259, 0)
                ,new Point(-589.994341128794986,450.286959177999051,0)
                ,new Point(-976.098765765686153, 731.847691900152427, 675.800374275156287));
        Triangle pyramidBWigD = new Triangle(new Point(-1362.20319040257732,1013.408424622305802,0)
                ,new Point(-1257.659498487839528, 345.743267263261259, 0)
                ,new Point(-976.098765765686153, 731.847691900152427, 675.800374275156287));

        Triangle pyramidCWigA = new Triangle(new Point(-1000,-1000,0)
                ,new Point(-1500, -1000, -0.000000000000061)
                ,new Point(-1250, -750, 500));
        Triangle pyramidCWigB =new Triangle(new Point(-1500, -1000, -0.000000000000061)
                ,new Point(-1500, -500, -0.000000000000061)
                ,new Point(-1250, -750, 500));
        Triangle pyramidCWigC = new Triangle(new Point(-1500, -500, -0.000000000000061)
                ,new Point(-1000, -500, 0)
                ,new Point(-1250, -750, 500));
        Triangle pyramidCWigD = new Triangle(new Point(-1000, -500, 0)
                ,new Point(-1000,-1000,0)
                ,new Point(-1250, -750, 500));

        Triangle pyramidDWigA = new Triangle(new Point(903.617579714595195,564.402427344720309,0)
                ,new Point(617.524146711446519,529.32719915949292,0)
                ,new Point(743.033249120407163, 689.911529753680952, 288.235535699144634));
        Triangle pyramidDWigB =new Triangle(new Point(617.524146711446519,529.32719915949292,0)
                ,new Point(582.448918526219131,815.420632162641596,0)
                ,new Point(743.033249120407163, 689.911529753680952, 288.235535699144634));
        Triangle pyramidDWigC = new Triangle(new Point(582.448918526219131,815.420632162641596,0)
                ,new Point(868.542351529367807,850.495860347868984,0)
                ,new Point(743.033249120407163, 689.911529753680952, 288.235535699144634));
        Triangle pyramidDWigD = new Triangle(new Point(868.542351529367807,850.495860347868984,0)
                ,new Point(903.617579714595195,564.402427344720309,0)
                ,new Point(743.033249120407163, 689.911529753680952, 288.235535699144634));

        Triangle pyramidEWigA = new Triangle(new Point(1160.643651708754987,-884.212750300565176,0)
                ,new Point(1144.615547311208502,-612.421415193659982,0)
                ,new Point(1288.525267063434285,-740.303030548339393,272.263530370434808));
        Triangle pyramidEWigB =new Triangle(new Point(1144.615547311208502,-612.421415193659982,0)
                ,new Point(1416.406882418113582,-596.393310796113497,0)
                ,new Point(1288.525267063434285,-740.303030548339393,272.263530370434808));
        Triangle pyramidEWigC = new Triangle(new Point(1416.406882418113582,-596.393310796113497,0)
                ,new Point(1432.434986815660068,-868.184645903018691,0)
                ,new Point(1288.525267063434285,-740.303030548339393,272.263530370434808));
        Triangle pyramidEWigD = new Triangle(new Point(1432.434986815660068,-868.184645903018691,0)
                ,new Point(1160.643651708754987,-884.212750300565176,0)
                ,new Point(1288.525267063434285,-740.303030548339393,272.263530370434808));

        //-----------------------------------sky-----------------------------------//

        Sphere sun = new Sphere(new Point(400,500,600),100);

        Sphere cloudA1=new Sphere(new Point(-400,100,900),100);
        Sphere cloudA2=new Sphere(new Point(-420,170,950),90);
        Sphere cloudA3=new Sphere(new Point(-380,270,920),120);
        Sphere cloudA4=new Sphere(new Point(-430,280,975),85);
        Sphere cloudA5=new Sphere(new Point(-440,350,920),110);

        Sphere cloudB1=new Sphere(new Point(2400,700,900),190);
        Sphere cloudB2=new Sphere(new Point(2490,850,850),150);
        Sphere cloudB3=new Sphere(new Point(2550,550,750),220);
        Sphere cloudB4=new Sphere(new Point(2300,1000,800),170);
        Sphere cloudB5=new Sphere(new Point(2250,1200,700),200);

        Sphere cloudC1=new Sphere(new Point(-1200,-500,600),80);
        Sphere cloudC2=new Sphere(new Point(-1300,-550,600),100);
        Sphere cloudC3=new Sphere(new Point(-1350,-600,600),90);
        Sphere cloudC4=new Sphere(new Point(-1400,-650,600),110);
        Sphere cloudC5=new Sphere(new Point(-1450,-700,600),70);

        Sphere cloudD1=new Sphere(new Point(1700,1620,1500),100);
        Sphere cloudD2=new Sphere(new Point(1550,1720,1650),150);
        Sphere cloudD3=new Sphere(new Point(1750,1950,1750),100);
        Sphere cloudD4=new Sphere(new Point(1800,1450,1700),125);
        Sphere cloudD5=new Sphere(new Point(1400,2000,1550),190);

        Sphere cloudE1=new Sphere(new Point(3000,-600,1000),100);
        Sphere cloudE2=new Sphere(new Point(3100,-500,1100),200);
        Sphere cloudE3=new Sphere(new Point(3200,-590,900),90);
        Sphere cloudE4=new Sphere(new Point(3150,-600,950),120);
        Sphere cloudE5=new Sphere(new Point(300,-700,800),100);
        //-------------------------------Dunes---------------------------------------//
        Sphere a1= new Sphere(new Point(519.070390744984252,440.107162582189176,-10),50);
        Sphere a2= new Sphere(new Point(568.560487306058349,330.381775783034186,-20),100);
        Sphere a3=new Sphere(new Point(10507.452522044068246,192.73059510366852,-300),650);
        Sphere a4=new Sphere(new Point(200500.713789922087472,78.895843857937507,-200),1450);
        Sphere a5=new Sphere(new Point(1500.713789922087472,78.895843857937507,-60),117.774);
        Sphere a6=new Sphere(new Point(10531.970492287884554,-117.758343954578777,-250),320);
        Sphere a7=new Sphere(new Point(10555.040695576198686,-2075.299697925167038,-50),92.138);
        Sphere a8=new Sphere(new Point(-10626.427425256983042,450.751787923952179,-65),102.8875);
        Sphere a9= new Sphere(new Point(1600,440.107162582189176,-100),250);
        Sphere a10= new Sphere(new Point(-40,330.381775783034186,-5),25);
        Sphere a11=new Sphere(new Point(-507.452522044068246,300,-40),65);
        Sphere a12=new Sphere(new Point(-160,78.895843857937507,-80),100);
        Sphere a13=new Sphere(new Point(-700,78.895843857937507,-250),300);
        Sphere a14=new Sphere(new Point(-650,-150,-50),90);
        Sphere a15=new Sphere(new Point(-90,-300,0),92);
        Sphere a16=new Sphere(new Point(-150,-150.751787923952179,0),16);
        Sphere a17= new Sphere(new Point(-25,34,0),5);
        Sphere a18= new Sphere(new Point(90,450,0),19);
        Sphere a19=new Sphere(new Point(420,250,0),65);
        Sphere a20=new Sphere(new Point(1640,10,0),75);
        Sphere a21=new Sphere(new Point(1800,150,-50),118);
        Sphere a22=new Sphere(new Point(-670,117,0),73);
        Sphere a23=new Sphere(new Point(325,-75,-60),92.138);
        Sphere a24=new Sphere(new Point(-348,-162,-60),102);
        Sphere a25= new Sphere(new Point(-400,440.107162582189176,0),16);
        Sphere a26= new Sphere(new Point(-385,721,0),64);
        Sphere a27=new Sphere(new Point(-95,100,0),38);
        Sphere a28=new Sphere(new Point(-26,30,0),40);
        Sphere a29=new Sphere(new Point(1000,78.895843857937507,-15),62);
        Sphere a30=new Sphere(new Point(-42,-100,0),34);
        Sphere a31=new Sphere(new Point(-269,-742,0),79);
        Sphere a32=new Sphere(new Point(-430,150,0),23);

        Sphere a33=new Sphere(new Point(-1150,150,-15),50);
        Sphere a34= new Sphere(new Point(-900,340,-350),400);
        Sphere a35= new Sphere(new Point(10090,2450,-1300),1550);
        Sphere a36=new Sphere(new Point(-1420,2500,-500),600);
        Sphere a37=new Sphere(new Point(-1640,-100,-50),75);
        Sphere a38=new Sphere(new Point(-1800,150,-150),200);
        Sphere a39=new Sphere(new Point(-1670,-1107,-650),730);
        Sphere a40=new Sphere(new Point(-1325,-750,-850),900);
        Sphere a41=new Sphere(new Point(-1348,-162,-75),102);
        Sphere a42= new Sphere(new Point(-1400,440.107162582189176,-7),16);
        Sphere a43= new Sphere(new Point(-1385,721,-250),320);
        Sphere a44=new Sphere(new Point(10195,-3000,-1490),1800);
        Sphere a45=new Sphere(new Point(-126,300,-340),400);
        Sphere a46=new Sphere(new Point(-1300,-780,-600),620);
        Sphere a47=new Sphere(new Point(-1942,-100,-240),280);
        Sphere a48=new Sphere(new Point(-1430,150,-180),235);
        Sphere a49=new Sphere(new Point(-1269,-742,-350),451);
        Sphere a50=new Sphere(new Point(-1430,150,-190),230);

//----------------------------------------------------------------------------------------------------------//
        Cylinder wathera1=new Cylinder
                (new Ray(new Point(-1700,-260,0),new Vector(0,0, 1))
                        ,50,0.01);
        Cylinder wathera2=new Cylinder
                (new Ray(new Point(-1650,-300,0),new Vector(0,0, 1))
                        ,40,0.01);
        Cylinder wathera3=new Cylinder
                (new Ray(new Point(-1680,-340,0),new Vector(0,0, 1))
                        ,60,0.01);


        Cylinder marblePillar= new Cylinder( new Ray(new Point(-1980, 160, -15),new Vector(0.9,-2.1,0.55)),35,230);
//        Polygon base=new Polygon(new Point(-2022.35420716223075, 127.542994702076911, -67.168860551636655)
//                ,new Point(-1926.792077806360794, 169.958220746950815, -61.576772490359375)
//                ,new Point(-1997.305901611361378, 334.710590535864412, -106.203726046940972)
//                ,new Point(-2093.49330611887126, 293.756291688580518, -112.191539707066681)
//                ,new Point(-2109.323689339737484, 316.097161977662836, -11.121588811899208)
//                ,new Point(-2037.592215409018536, 148.499807574943901, 34.275994001136567)
//                ,new Point(-1942.03008605314858, 190.915033619817848, 39.868082062413876)
//                ,new Point(-2013.136284832225783, 357.051460824943433, -5.133775151772667));
      //  Polygon qube1=new Polygon()
        //Sphere boolInMarble=new Sphere(new Point(-1981.96880580567904, 164.593880213250714, -16.203159103470433),35);


        scene.geometries.add(
                new Plane(new Point(0,-600,0),new Point(0,-650,0),new Point(-400,0,0)).setEmission(Sand1),
              //  new Plane(new Point(0,0,1500),new Point(1500,0,0),new Point(1500,200,0)).setEmission(LightSky),
                pyramidAWigA.setEmission(Sand1),
                pyramidAWigB.setEmission(Sand2 ),
                pyramidAWigC.setEmission(Sand3),
                pyramidAWigD.setEmission(Sand2),
                cd.setEmission(Sand3),
//                bc.setEmission(new Color(WHITE)),
                ab.setEmission(Sand3),
                //  da.setEmission(new Color(WHITE))

                pyramidBWigA.setEmission(Sand2),
                pyramidBWigB.setEmission(Sand2),
                pyramidBWigC.setEmission(Sand2),
                pyramidBWigD.setEmission(Sand2),

                pyramidCWigA.setEmission(Sand2),
                pyramidCWigB.setEmission(Sand3),
                pyramidCWigC.setEmission(Sand3),
                pyramidCWigD.setEmission(Sand3),

                pyramidDWigA.setEmission(Sand4),
                pyramidDWigB.setEmission(Sand4),
                pyramidDWigC.setEmission(Sand4),
                pyramidDWigD.setEmission(Sand4),

                pyramidEWigA.setEmission(Sand4),
                pyramidEWigB.setEmission(Sand4),
                pyramidEWigC.setEmission(Sand4),
                pyramidEWigD.setEmission(Sand4),


//                sun.setEmission(new Color(java.awt.Color.orange))
//                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(200).setKt(0.2).setKr(0.70)),
                a1.setEmission(dune1),
                a2.setEmission(dune2),
                a3.setEmission(dune1),
                a4.setEmission(dune3),
                a5.setEmission(dune1),
                a6.setEmission(dune2),
                a7.setEmission(dune3),
                a8.setEmission(dune2),
                a9.setEmission(dune1),
                a10.setEmission(dune3),
                a11.setEmission(dune2),
                a12.setEmission(dune3),
                a13.setEmission(dune1),//a12
                a14.setEmission(dune2),
                a15.setEmission(dune3),
                a16.setEmission(dune2),
                a17.setEmission(dune1),
                a18.setEmission(dune2),
                a19.setEmission(dune3),
                a20.setEmission(dune2),
                a21.setEmission(dune2),
                a22.setEmission(dune1),//a22
                a23.setEmission(dune3),
                a24.setEmission(dune1),
                a25.setEmission(dune2),
                a26.setEmission(dune3),
                a27.setEmission(dune2),
                a28.setEmission(dune3),
                a29.setEmission(dune2),//
                a30.setEmission(dune1),
                a31.setEmission(dune3),
                a32.setEmission(dune2),

                a33.setEmission(dune2),//
                a34.setEmission(dune1),
                a35.setEmission(dune3),
                a36.setEmission(dune2),
                a37.setEmission(dune1),//
                a38.setEmission(dune2),//
                a39.setEmission(dune3),
                a40.setEmission(dune2),
                a41.setEmission(dune2),//
                a42.setEmission(dune1),
                a43.setEmission(dune3),
                a45.setEmission(dune2),
                a44.setEmission(dune1),//a12
                a46.setEmission(dune3),
                a47.setEmission(dune3),
                a48.setEmission(dune2),
                a49.setEmission(dune1),
                a50.setEmission(dune1),


                cloudA1.setEmission(Cloud1).setMaterial(new Material().
                        setKd(0.2).setKs(0.2).setShininess(10).setKr(0.3)),
                cloudA2.setEmission(Cloud3),
                cloudA3.setEmission(Cloud1),
                cloudA4.setEmission(Cloud3),
                cloudA5.setEmission(Cloud2),

                cloudB1.setEmission(Cloud1),
                cloudB2.setEmission(Cloud3),
                cloudB3.setEmission(Cloud2),
                cloudB4.setEmission(Cloud1),
                cloudB5.setEmission(Cloud2),

                cloudC1.setEmission(Cloud2),
                cloudC2.setEmission(Cloud1),
                cloudC3.setEmission(Cloud2),
                cloudC4.setEmission(Cloud3),
                cloudC5.setEmission(Cloud1),

                cloudD1.setEmission(Cloud3),
                cloudD2.setEmission(Cloud1),
                cloudD3.setEmission(Cloud2),
                cloudD4.setEmission(Cloud1),
                cloudD5.setEmission(Cloud3),

                cloudE1.setEmission(Cloud1),
                cloudE2.setEmission(Cloud3),
                cloudE3.setEmission(Cloud2),
                cloudE4.setEmission(Cloud2),
                cloudE5.setEmission(Cloud3),

                wathera1.setEmission(new Color(CYAN)),
                wathera2.setEmission(new Color(CYAN)),
                wathera3.setEmission(new Color(CYAN)),


                marblePillar.setEmission(marble)
//                a.setEmission(new Color(blue)),
//                b.setEmission(new Color(yellow)),
//                c.setEmission(new Color(orange)),
//                d.setEmission(new Color(pink)),
//                e.setEmission(new Color(white)),
//                f.setEmission(new Color(black))




        );


        scene.lights.add(
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2))
                        .setKl(0.0004).setKq(0.0000006));
       scene.lights.add( new SpotLight(new Color(600, 226, 227),new Point(-1700,-260,500),new Vector(0,0,-1)));

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







