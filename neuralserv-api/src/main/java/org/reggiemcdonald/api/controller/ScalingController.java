package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.model.api.ScaleApiModel;
import org.reggiemcdonald.exception.*;
import org.reggiemcdonald.service.ScalingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/scale")
public class ScalingController {

    private static final String CONTROLLER_NAME = "scale";
    private ScalingService service;

    Logger logger = LoggerFactory.getLogger(ScalingController.class);

    @Autowired
    public ScalingController(ScalingService _service) {
        service = _service;
    }

    @RequestMapping(value = "image-scale", method = RequestMethod.POST)
    public ResponseEntity<ScaleApiModel> scaleImage(@RequestParam(value = "file") MultipartFile file)
            throws MalformedRequestException, NeuralservInternalServerException {
        try {
            logger.info("Began image scale request");
            ScaleApiModel model = new ScaleApiModel();
            double[][] weights = mapToArray(file);
            service.invert(weights);
            double[][] scaled = service.centerAndScale(weights).get();
            model.setImage(scaled);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(model);
        } catch (TooSmallToScaleException e) {
            logger.warn("image too small");
            throw new MalformedRequestException(CONTROLLER_NAME, e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new MalformedRequestException(CONTROLLER_NAME, "failed ");
        } catch (ExecutionException | InterruptedException | ScalingServiceException e) {
            logger.error(e.getMessage());
            throw new NeuralservInternalServerException();
        }

    }

    @RequestMapping(value = "array-scale", method = RequestMethod.POST)
    public ResponseEntity<ScaleApiModel> scaleArray(@Valid @RequestBody ScaleApiModel model)
            throws MalformedRequestException, NeuralservInternalServerException {
        try {
            logger.info("Began array scale request");
            double[][] weights = model.getImage();
            service.invert(weights);
            double[][] scaled = service.centerAndScale(weights).get();
            model.setImage(scaled);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(model);
        } catch (TooSmallToScaleException e) {
            throw new MalformedRequestException(CONTROLLER_NAME, e.getMessage());
        } catch (ExecutionException | InterruptedException | ScalingServiceException e) {
            throw new NeuralservInternalServerException();
        }
    }

    private double[][] mapToArray(MultipartFile file) throws IOException {
        try(InputStream in = file.getInputStream()) {
            BufferedImage img = ImageIO.read(in);
            double[][] weights = new double[img.getHeight()][img.getWidth()];
            for (int i = 0; i < weights.length ; i++) {
                for (int j = 0 ; j < weights[i].length ; j++) {
                    int rgb = img.getRGB(j, i);
                    int r = (rgb >> 16) & 0xFF;
                    int g = (rgb >> 8) & 0xFF;
                    int b = (rgb & 0xFF);
                    int gray = (r + g + b) / 3;
                    weights[i][j] = gray;
                }
            }
            return weights;
        }
    }

}
