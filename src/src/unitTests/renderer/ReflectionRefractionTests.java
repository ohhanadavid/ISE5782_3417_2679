/**
 *
 */
package src.unitTests.renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import src.lighting.*;
import src.geometries.*;
import src.primitives.*;
import src.renderer.Camera;
import src.renderer.ImageWriter;
import src.renderer.RayTracerBasic;
import src.scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
	private Scene scene = new Scene("Test scene");

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheres() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(150, 150).setVPDistance(1000);

		scene.geometries.add( //
				new Sphere(new Point(0, 0, -50), 50d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
				new Sphere(new Point(0, 0, -50), 25d).setEmission(new Color(RED)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
		scene.lights.add( //
				new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
						.setKl(0.0004).setKq(0.0000006));

		camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a sphere lighted by a spot light
	 */
	@Test
	public void twoSpheresOnMirrors() {
		Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(2500, 2500).setVPDistance(10000); //

		scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), new Double3(0.1)));

		scene.geometries.add( //

				new Sphere(new Point(-950, -900, -1000), 400d).setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
				new Sphere(new Point(-950, -900, -1000), 200d).setEmission(new Color(100, 20, 20)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500), new Point(670, 670, 3000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(1)),
				new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
						new Point(-1500, -1500, -2000)) //
						.setEmission(new Color(20, 20, 20)) //
						.setMaterial(new Material().setKr(0.5)));

		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
				.setKl(0.00001).setKq(0.000005));

		ImageWriter imageWriter = new ImageWriter("444reflectionTwoSpheresMirrored", 500, 500);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	/**
	 * Produce a picture of a two triangles lighted by a spot light with a partially
	 * transparent Sphere producing partial shadow
	 */
	@Test
	public void trianglesTransparentSphere() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(200, 200).setVPDistance(1000);

		scene.setAmbientLight(new AmbientLight(new Color(WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
				new Sphere(new Point(60, 50, -50), 30d).setEmission(new Color(BLUE)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
				.setKl(4E-5).setKq(2E-7));

		ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
		camera.setImageWriter(imageWriter) //
				.setRayTracer(new RayTracerBasic(scene)) //
				.renderImage() //
				.writeToImage();
	}

	@Test
	public void Bonus() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(600, 600).setVPDistance(1000).setNumOfRays(2).move(0,0,30);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), new Double3(0.15)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //
				new Triangle(new Point(-130, -130, -130), new Point(140, -140, -135), new Point(65, 65, -140)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(java.awt.Color.cyan)), //
				new Triangle(new Point(150, -150, -150), new Point(-150, 1500, -150),
						new Point(67, 67, 300)) //
						.setEmission(new Color(java.awt.Color.ORANGE)) //
						.setMaterial(new Material().setKr(1).setKt(0.5)),
				new Triangle(new Point(150, -150, -150), new Point(-1500, 1500, -1500),
						new Point(-150, -150, -200)) //
						.setEmission(new Color(0, 120, 220)) //
						.setMaterial(new Material().setKr(1).setKt(0.5)),
				new Sphere( new Point(140, -150, -100),25) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(140, -60, -100),15) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(140, 10, -100),25) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(140, 80, -100),15) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(-100, 150, 50),35) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(1)),
				new Sphere( new Point(-75, 100, 50),45) //
						.setEmission(new Color(java.awt.Color.magenta)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Sphere( new Point(-55, 100, 50),35) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.3)),
				new Sphere( new Point(-30, 100, 50),25) //
						.setEmission(new Color(java.awt.Color.magenta)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Sphere( new Point(-10, 100, 50),20) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.5)),
				new Sphere( new Point(15, 100, 50),15) //
						.setEmission(new Color(java.awt.Color.magenta)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Sphere( new Point(40, 100, 50),10) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.6)),
				new Sphere( new Point(50, 100, 50),5) //
						.setEmission(new Color(java.awt.Color.magenta)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)),
				new Sphere( new Point(70, -10, -100),10) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(75, 75, 50),30) //
						.setEmission(new Color(java.awt.Color.yellow)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.6).setKg(0.4)),
				new Sphere( new Point(-350, -300, -400),400) //
						.setEmission(new Color(0, 0, 100)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5).setKg(0.3)),
				new Sphere( new Point(-350, -300, -400),200) //
						.setEmission(new Color(100, 120, 120)) //
						.setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
				new Polygon(new Point(100,100,0),new Point(-70, 70, -140),new Point(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6))

		);

		scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0),new Vector(0, 0, -1)) //
				.setKq(2E-7)); //.setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)));
		ImageWriter imageWriter = new ImageWriter("bonus", 1000, 1000);

				camera.setImageWriter(imageWriter)
				.setRayTracer(new RayTracerBasic(scene))
						.renderImage()
						.writeToImage();
	}

	@Test
	public void miniProject1() {
		Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
				.setVPSize(500, 500).setVPDistance(1000).setNumOfRays(40);

		scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.white), new Double3(0.17)));

		scene.geometries.add( //
				new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150)) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)).setEmission(new Color(java.awt.Color.magenta)), //
				new Sphere( new Point(140, 30, -100),40) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),

				new Sphere( new Point(-100, 170, 50),45) //
						.setEmission(new Color(java.awt.Color.BLACK)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(1)),
				new Sphere( new Point(-105, 120, 80),55) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKt(0.5)),
				new Sphere( new Point(-40, 120, 50),35) //
						.setEmission(new Color(java.awt.Color.pink)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(15, 120, 70),25) //
						.setEmission(new Color(java.awt.Color.RED)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4).setKt(0.4)),
				new Sphere( new Point(50, 140, -50),15) //
						.setEmission(new Color(java.awt.Color.pink)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.4)),
				new Sphere( new Point(70, -10, -100),20) //
						.setEmission(new Color(java.awt.Color.cyan)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(10).setKr(0.4)),
				new Sphere( new Point(75, 75, -100),40) //
						.setMaterial(new Material().setKd(0.5).setKs(0.5).setKt(0.5).setShininess(30)).setEmission(new Color(java.awt.Color.magenta)),
				new Sphere( new Point(-350, -300, -400),500) //
						.setEmission(new Color(0, 0, 150)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(60).setKt(0.3)),
				new Sphere( new Point(-350, -300, -300),300) //
						.setEmission(new Color(200, 120, 120)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(60).setKt(0.3)),
				new Sphere( new Point(-350, -300, -300),150) //
						.setEmission(new Color(java.awt.Color.red)) //
						.setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(60).setKt(0.3)),
				new Polygon(new Point(200,100,0),new Point(-70, 70, -140),new Point(140, -140, -125)).setEmission(new Color(java.awt.Color.blue)) //
						.setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));



		scene.lights.add(new SpotLight(new Color(700, 400, 400),new Point(60, 50, 0),new Vector(0, 0, -1) ) //
				.setKq(2E-7)); //.setKq(0.000005));
		scene.lights.add(new SpotLight(new Color(1020, 400, 400),new Point(-750, -750, -150), new Vector(-1, -1, -4) ));
		scene.lights.add(new DirectionalLight(new Color(400, 30, 0), new Vector(0.65, 0, -1)));
		ImageWriter imageWriter = new ImageWriter("miniProject1", 500, 500);

			camera.setImageWriter(imageWriter) //
					 .setRayTracer(new RayTracerBasic(scene))
					.renderImage()
					.writeToImage();
	}
}
