package org.reggiemcdonald.service;


import org.reggiemcdonald.exception.ImageTooSmallToScaleException;
import org.reggiemcdonald.exception.ScalingServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.concurrent.CompletableFuture;

@Service
public class ScalingService {

    private static final Logger logger = LoggerFactory.getLogger(ScalingService.class);


    public CompletableFuture<double[][]> centerAndScale(double[][] original) throws ScalingServiceException {
        if (original.length < 28 || original[0].length < 28)
            throw new ImageTooSmallToScaleException();
        if (original.length == 28 && original[0].length == 28)
            return CompletableFuture.completedFuture(original);
        // Remove all rows and columns that are completely black
        int minX = 0;
        int maxX = original.length - 1;
        int minY = 0;
        int maxY = original.length - 1;
        while (minX < maxX && rowSum(original[minX]) == 0) minX++;
        while (maxX > minX && rowSum(original[maxX]) == 0) maxX--;
        while (minY < maxY && colSum(original, minY) == 0) minY++;
        while (maxY > minY && colSum(original, maxY) == 0) maxY--;
        int xDim = (maxX - minX) + 1;
        int yDim = (maxY - minY) + 1;

        // Produce the original image
        BufferedImage preScaledCenteredImage = new BufferedImage(yDim, xDim, BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster raster = preScaledCenteredImage.getRaster();
        int[] pixel = { 0 };
        for (int i = minX ; i <= maxX ; i++) {
            for (int j = minY ; j <= maxY ; j++) {
                pixel[0] = (int) original[i][j];
                raster.setPixel(j-minY, i-minX, pixel);
            }
        }

        // produce the scaled image
        BufferedImage scaledCenteredImage =
                imageToScale(preScaledCenteredImage, 20, 20, BufferedImage.TYPE_BYTE_GRAY);


        // To 2D double array
        double[][] finalImage = new double[28][28];
        raster = scaledCenteredImage.getRaster();
        for (int i = 4; i < 24; i++) {
            for (int j = 4; j < 24; j++) {
                finalImage[i][j] = raster.getSample(j-4, i-4, 0);
            }
        }
        return CompletableFuture.completedFuture(finalImage);
    }

    public double[][] invert(double[][] original) {
        for (int i = 0 ; i < original.length ; i++) {
            for (int j = 0 ; j < original[i].length ; j++) {
                original[i][j] = 255 - original[i][j];
            }
        }
        return original;
    }

    private double rowSum(double[] row) {
        double sum = 0.;
        for (double r : row)
            sum += r;
        return sum;
    }

    private double colSum(double[][] mat, int col) {
        double sum = 0.;
        for (int i = 0; i < mat.length; i++)
            sum += mat[i][col];
        return sum;
    }

    private BufferedImage imageToScale(BufferedImage img, int desiredWidth, int desiredHeight, int desiredType) {
        BufferedImage scaled = new BufferedImage(desiredWidth, desiredHeight, desiredType);
        Graphics2D graphics = scaled.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(img, 0, 0, desiredWidth, desiredHeight, null);
        graphics.dispose();
        return scaled;
    }
}
