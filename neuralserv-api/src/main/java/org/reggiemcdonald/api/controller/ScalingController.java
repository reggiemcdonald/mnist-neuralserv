package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.model.api.ScaleApiModel;
import org.reggiemcdonald.api.service.NeuralNetService;
import org.reggiemcdonald.api.service.ScalingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/scale")
public class ScalingController {

    ScalingService service;

    @Autowired
    NeuralNetService netService;

    Logger logger = LoggerFactory.getLogger(ScalingController.class);

    @Autowired
    public ScalingController(ScalingService _service) {
        service = _service;
    }

    @RequestMapping(value = "convert", method = RequestMethod.POST)
    public ResponseEntity<ScaleApiModel> convert(@Valid @RequestBody ScaleApiModel model) throws Exception {
        // Make Request
        double[][] resized = service.scale(model.getImage()).get();
        resized = service.invert(resized);
        model.setImage(resized);
        int label = netService.classify(resized).get();
        logger.info(String.format("The network guessed %d", label));
        return ResponseEntity.ok(model);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ScaleApiModel> scale(@Valid @RequestBody ScaleApiModel model) throws Exception {
        double[][] resized = service.scale(model.getImage()).get();
        model.setImage(resized);
        int label = netService.classify(resized).get();
        logger.info(String.format("The network guessed %d", label));
        return ResponseEntity.ok(model);
    }

    @RequestMapping(value = "image-to-array", method = RequestMethod.POST)
    public ResponseEntity<ScaleApiModel> convertToArray(@RequestParam("file") MultipartFile file) {
        ScaleApiModel model = new ScaleApiModel();
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
            model.setImage(weights);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(model);
        }
        return ResponseEntity.ok(model);
    }

}
