package org.reggiemcdonald.api.controller;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.reggiemcdonald.api.model.api.ScaleApiModel;
import org.reggiemcdonald.exception.MalformedRequestException;
import org.reggiemcdonald.exception.TooSmallToScaleException;
import org.reggiemcdonald.service.ScalingService;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(JUnit4.class)
public class ScalingControllerTest {
    private static final String TEST_INPUT_IMAGE = "test-input.png";

    private ScalingController controller;

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Before
    public void runBefore() {
        controller = new ScalingController(new ScalingService());
    }

    @Test(expected = MalformedRequestException.class)
    public void testScaleImage_tooSmall() throws Exception {
        File f = folder.newFile(TEST_INPUT_IMAGE);
        BufferedImage img =
                new BufferedImage(10, 10, BufferedImage.TYPE_BYTE_GRAY);
        ImageIO.write(img, "png", f);
        controller.scaleImage(new MockMultipartFile("too-small-test", Files.newInputStream(f.toPath())));
    }

    @Test
    public void testScaleImage_success() throws Exception {
        File f = folder.newFile(TEST_INPUT_IMAGE);
        BufferedImage img =
                new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        ImageIO.write(img, "png", f);
        controller.scaleImage(new MockMultipartFile("exact-size-test", Files.newInputStream(f.toPath())));
    }

    @Test
    public void testScaleImage_realFile() throws Exception {
        InputStream inputStream =
                Files.newInputStream(Paths.get("src/test/java/org/reggiemcdonald/test.png"));
        ResponseEntity<ScaleApiModel> entity =
                controller.scaleImage(new MockMultipartFile("real-test", inputStream));
        ScaleApiModel model = entity.getBody();
        assertNotNull(model);
        assertNotNull(model.getImage());
        double[][] img = model.getImage();
        assertEquals(img.length, 28);
        assertEquals(img[0].length, 28);
        // check that the colours are being inverted
        assertEquals(0, img[0][0], 0.01);
        assertEquals(0, img[27][27], 0.01);
    }
}

