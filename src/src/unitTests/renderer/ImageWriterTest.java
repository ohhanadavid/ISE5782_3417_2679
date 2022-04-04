package src.unitTests.renderer;

import org.junit.jupiter.api.Test;
import src.primitives.Color;
import src.renderer.ImageWriter;

import static org.junit.jupiter.api.Assertions.*;

class ImageWriterTest {

    @Test

    /**
     * Test method for {@link ImageWriter.WriteToImage}
     */
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter("test", 800, 500);
        for (int i=0;i<imageWriter.getNx();i++)
        {
            for (int j=0;j<imageWriter.getNy();j++)
            {
                // 800/16=50, 500/10=50
                if(i % 50 == 0 || j % 50 == 0){
                    imageWriter.writePixel(i,j, Color.BLACK);
                }

                else {
                    imageWriter.writePixel(i,j, new Color(20,200,200));
                }

            }
        }
        imageWriter.writeToImage();
    }

    @Test

    /**
     * Test method for {@link ImageWriter.WritePixel}
     */
    void testWritePixel() {
    }
}