package org.reggiemcdonald.api.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class ScalingService {

    private static final Logger logger = LoggerFactory.getLogger(ScalingService.class);

    @Async
    public CompletableFuture<double[][]> scale(double[][] original) {
        // perform scaling
        // TODO: Implement this
        int lenY = original.length;
        int lenX = original[0].length;
        BufferedImage img = new BufferedImage(lenX, lenY, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = img.getRaster();
        int[] pixel = { 0 };
        for (int i = 0 ; i < original.length ; i++) {
            for (int j = 0 ; j < original[i].length ; j++) {
                pixel[0] = (int) original[i][j];
                raster.setPixel(j, i, pixel);
            }
        }

        BufferedImage resizedImage = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g2 = resizedImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(img, 0, 0, 28, 28, null);
        g2.dispose();
        double[][] resizedDoubles = new double[28][28];
        raster = resizedImage.getRaster();
        for (int i = 0; i < 28 ; i++) {
            for (int j = 0 ; j < 28 ; j++) {
                int gray = raster.getSample(j, i, 0);
                resizedDoubles[i][j] = gray;
            }
        }
        try {
            ImageIO.write(img, "png", new File("original.png"));
            ImageIO.write(resizedImage, "png", new File("scaled.png"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        logger.info("Finished scaling the image");
//        System.out.println(Arrays.deepToString(resizedDoubles));
        return CompletableFuture.completedFuture(resizedDoubles);
    }

    public double[][] invert(double[][] original) {
        for (int i = 0 ; i < original.length ; i++) {
            for (int j = 0 ; j < original[i].length ; j++) {
                original[i][j] = 255 - original[i][j];
            }
        }
        return original;
    }
}
