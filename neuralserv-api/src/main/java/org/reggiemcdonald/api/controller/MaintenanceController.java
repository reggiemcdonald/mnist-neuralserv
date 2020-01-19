package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.service.NeuralNetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;


@RestController
@RequestMapping(value = "/maint")
public class MaintenanceController {

    NeuralNetService service;

    @Autowired
    public MaintenanceController(NeuralNetService _service) {
        service = _service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<String> postTrainingSession(
            @RequestParam Integer epochs,
            @RequestParam Integer batchSize,
            @RequestParam Double eta,
            @RequestParam Boolean verbose) throws IOException, ExecutionException, InterruptedException {
        service.train(epochs, batchSize, eta, verbose);
        return ResponseEntity.ok("Training has begun");
    }
}
