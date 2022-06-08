package src.unitTests.renderer;

import src.geometries.Intersectable;
import src.geometries.Polygon;
import src.geometries.Sphere;
import src.lighting.AmbientLight;
import src.lighting.DirectionalLight;
import src.lighting.PointLight;
import org.junit.jupiter.api.Test;
import src.primitives.*;
import src.renderer.Camera;
import src.renderer.ImageWriter;
import src.renderer.RayTracerBasic;
import src.scene.Scene;

public class integrationRefractionReflectionTest {

    /**
     * It creates a bookshelf with books on it, a window with transparency, and lights it with a few lights
     */
    @Test
    public void bookShelf() {
        Material rBookMat = new Material().setKd(0.5).setKs(0.2).setShininess(30);
        Material yBookMat = new Material().setKd(0.5).setKs(0.2).setShininess(30);
        Material gBookMat = new Material().setKd(0.5).setKs(0.2).setShininess(30);
        Material wallsMat = new Material().setKd(0.5).setKs(0.2).setShininess(30);
        Material material = new Material().setKd(0.5).setKs(0.2).setShininess(30);
        Material bookShelfMat = new Material().setKd(0.2).setKs(0.2).setShininess(30);
        Material windowMat = new Material().setKd(0.5).setKs(0.5).setShininess(100).setKt(0.3);
        Color shelfColor = new Color(78, 53, 36).reduce(3d / 2);
        Color gBookColor = new Color(java.awt.Color.GREEN).reduce(3d / 2);
        Color yBookColor = new Color(java.awt.Color.YELLOW).reduce(3d / 2);
        Color rBookColor = new Color(java.awt.Color.RED).reduce(3d / 2);
        Color wightTop = new Color(java.awt.Color.WHITE).reduce(3d / 2);
        Color lWallColor = new Color(java.awt.Color.PINK).reduce(2);
        Color rWallColor = new Color(java.awt.Color.PINK).reduce(3d / 2);
        Color windowColor = new Color(200, 238, 238).reduce(2);
        Intersectable
                shelfTop = new Polygon(
                        new Point(500, 300, 0),
                        new Point(-500, 300, 0),
                        new Point(-500, -300, 0),
                        new Point(500, -300, 0))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                shelfLeft = new Polygon(
                        new Point(-500, 300, -50),
                        new Point(-500, 300, 0),
                        new Point(-500, -300, 0),
                        new Point(-500, -300, -50))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                shelfFront = new Polygon(
                        new Point(500, -300, -50),
                        new Point(500, -300, 0),
                        new Point(-500, -300, 0),
                        new Point(-500, -300, -50))
                        .setEmission(shelfColor)
                        .setMaterial(bookShelfMat),
                gBookFront = new Polygon(
                        new Point(150, 260, 600),
                        new Point(150, 260, 0),
                        new Point(150, -220, 0),
                        new Point(150, -220, 600))
                        .setEmission(gBookColor)
                        .setMaterial(gBookMat),
                gBookSide = new Polygon(
                        new Point(150, -220, 600),
                        new Point(150, -220, 0),
                        new Point(250, -220, 0),
                        new Point(250, -220, 600))
                        .setEmission(gBookColor)
                        .setMaterial(gBookMat),
                gBookBack = new Polygon(
                        new Point(250, 260, 600),
                        new Point(250, 260, 0),
                        new Point(250, -220, 0),
                        new Point(250, -220, 600))
                        .setEmission(gBookColor)
                        .setMaterial(gBookMat),
                gBookTop = new Polygon(
                        new Point(150, 250, 585),
                        new Point(250, 250, 585),
                        new Point(250, -220, 585),
                        new Point(150, -220, 585))
                        .setEmission(wightTop)
                        .setMaterial(gBookMat),
                yBookFront = new Polygon(
                        new Point(255, 260, 700),
                        new Point(255, 260, 0),
                        new Point(255, -260, 0),
                        new Point(255, -260, 700))
                        .setEmission(yBookColor)
                        .setMaterial(yBookMat),
                yBookSide = new Polygon(
                        new Point(255, -260, 700),
                        new Point(255, -260, 0),
                        new Point(300, -260, 0),
                        new Point(300, -260, 700))
                        .setEmission(yBookColor)
                        .setMaterial(yBookMat),
                yBookBack = new Polygon(
                        new Point(300, 260, 700),
                        new Point(300, 260, 0),
                        new Point(300, -260, 0),
                        new Point(300, -260, 700))
                        .setEmission(yBookColor)
                        .setMaterial(yBookMat),
                yBookTop = new Polygon(
                        new Point(255, 260, 690),
                        new Point(300, 260, 690),
                        new Point(300, -260, 690),
                        new Point(255, -260, 690))
                        .setEmission(wightTop)
                        .setMaterial(yBookMat),
                rBookFront = new Polygon(
                        new Point(100, 230, 419),
                        new Point(100, -150, 419),
                        new Point(-50, -150, 19),
                        new Point(-50, 230, 19))
                        .setEmission(rBookColor)
                        .setMaterial(rBookMat),
                rBookSide = new Polygon(
                        new Point(100, -150, 419),
                        new Point(150, -150, 400),
                        new Point(0, -150, 0),
                        new Point(-50, -150, 19))
                        .setEmission(rBookColor)
                        .setMaterial(rBookMat),
                rBookBack = new Polygon(
                        new Point(150, 230, 400),
                        new Point(150, -150, 400),
                        new Point(0, -150, 0),
                        new Point(0, 230, 0))
                        .setEmission(rBookColor)
                        .setMaterial(rBookMat),
                rBookTop = new Polygon(
                        new Point(100, 230, 412),
                        new Point(150, 230, 393),
                        new Point(150, -150, 393),
                        new Point(100, -150, 412))
                        .setEmission(wightTop)
                        .setMaterial(rBookMat),
                wall11 = new Polygon(
                        new Point(-2000,300,1200),
                        new Point(650,300, 1200),
                        new Point(650,300,3000),
                        new Point(-2000,300,3000))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                wall12 = new Polygon(
                        new Point(-2000,300,-2000),
                        new Point(650,300, -2000),
                        new Point(650,300,500),
                        new Point(-2000,300,500))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                wall13 = new Polygon(
                        new Point(100,300,-2000),
                        new Point(650,300, -2000),
                        new Point(650,300,3000),
                        new Point(100,300,3000))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                wall14 = new Polygon(
                        new Point(-2000,300,-2000),
                        new Point(-650,300, -2000),
                        new Point(-650,300,3000),
                        new Point(-2000,300,3000))
                        .setEmission(lWallColor)
                        .setMaterial(wallsMat),
                window = new Polygon(
                        new Point(100,300,1200),
                        new Point(100,300, 500),
                        new Point(-650,300,500),
                        new Point(-650,300,1200))
                        .setEmission(windowColor)
                        .setMaterial(windowMat),
                wall2 = new Polygon(
                        new Point(650,-600,-2000),
                        new Point(650,300, -2000),
                        new Point(650,300,3000),
                        new Point(650,-600,3000))
                        .setEmission(rWallColor)
                        .setMaterial(wallsMat),
                sphere = new Sphere(
                        new Point(100,600,300),
                        200)
                        .setEmission(new Color(210,150,150).reduce(2))
                        .setMaterial(material);

        Scene scene = new Scene("Test scene bookshelf");
        Camera camera1 = new Camera(
                new Point(-5900,-6000,6150),
                new Vector(1,1,-1),
                new Vector(1,1,2))
                .setVPSize(200, 200).setVPDistance(1500)
                .setRayTracer(new RayTracerBasic(scene));

        scene.geometries.add(shelfTop, shelfLeft, shelfFront,
                gBookFront, gBookSide, gBookBack, gBookTop,
                yBookFront, yBookSide, yBookBack, yBookTop,
                rBookFront, rBookSide, rBookBack, rBookTop,
                sphere,
                wall11, wall12, wall13, wall14, wall2, window);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.01)));

        scene.lights.add(
                new PointLight(
                        new Color(1800, 1600, 1600),
                        new Point(-800, -300, 700))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new PointLight(
                        new Color(1800, 1600, 1600),
                        new Point(100, -600, 1500))
                        .setKl(4E-4).setKq(2E-5));
        scene.lights.add(
                new DirectionalLight(
                        new Color(1200, 1200, 1200),
                        new Vector(1, -1, -2)));


        camera1.setImageWriter(new ImageWriter("bookshelf", 600, 600)) //
                .renderImage()
                .writeToImage();
    }
}
