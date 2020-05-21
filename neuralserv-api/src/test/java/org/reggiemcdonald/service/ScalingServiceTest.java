package org.reggiemcdonald.service;

import org.junit.Before;
import org.junit.Test;
import org.reggiemcdonald.exception.TooSmallToScaleException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class ScalingServiceTest {
    private ScalingService service;

    @Before
    public void runBefore() {
        service = new ScalingService();
    }

    @Test(expected = TooSmallToScaleException.class)
    public void testCenterAndScale_tooSmall() throws Exception {
        double[][] img = new double[10][10];
        double[][] scaled = service.centerAndScale(img).get();
        fail("should not have succeeded");
    }

    @Test
    public void testCenterAndScale_sameSize() throws Exception {
        double[][] img = new double[28][28];
        double[][] scaled = service.centerAndScale(img).get();
        assertSame(scaled, img);
    }

    @Test
    public void testCenterAndScale_bigger() throws Exception {
        double[][] img = toTestImg();
        double[][] inverted = service.invert(img);
        double[][] scaled = service.centerAndScale(inverted).get();
        assertEquals(28, scaled.length);
        assertEquals(28, scaled[0].length);
    }

    @Test
    public void testInvert() throws Exception {
        double[][] img = toTestImg();
        assertEquals(255, img[0][0], 0.01);
        double[][] inverted = service.invert(img);
        assertEquals(0, img[0][0], 0.01);
        assertEquals(img.length, inverted.length);
        assertEquals(img[0].length, inverted[0].length);
    }

    private double[][] toTestImg() throws IOException {
        BufferedImage img =
                ImageIO.read(new File("src/test/java/org/reggiemcdonald/test.png"));
        double[][] dImg = new double[img.getHeight()][img.getWidth()];
        for (int i = 0; i < dImg.length; i++) {
            for (int j = 0; j < dImg[i].length; j++) {
                int rgb = img.getRGB(j, i);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                int gray = (r + g + b) / 3;
                dImg[i][j] = gray;
            }
        }
        return dImg;
    }
}
