package org.reggiemcdonald.api.controller;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.reggiemcdonald.api.model.NumberImageApiModel;
import org.reggiemcdonald.api.model.NumberImageRequestModel;
import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/number")
public class NumberImageController {

    @Autowired
    NumberImageRepository repository;
    Network network;

    @PostConstruct
    public void initialize() {
        try {
            network = Network.loadWithException("/Users/reginaldmcdonald/Library/Application Support/reggiemcdonald/mnist-neuralserv/network-state.nerl");
        } catch (IOException e) {
            // TODO: Update to retrain network and remove runtime exception
            throw new RuntimeException("Failed to load neural network");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<NumberImageApiModel>> getNumberImages(
            @RequestParam(value = "sessionId") Integer sessionId,
            @RequestParam(value = "page", required = false) Integer page) {
        return null;
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NumberImageApiModel> getNumberImage(@PathVariable(value = "id") Integer id) throws NumberImageNotFoundException {
        NumberImageApiModel dto = new NumberImageApiModel(repository.findById(id));
        return ResponseEntity.ok(dto);
    }

    /**
     * Takes a post request with the following json
     * {
     *     expectedLabel: int | null
     *     image: double[][]
     * }
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<NumberImageApiModel> postNumberImage(@Valid @RequestBody NumberImageRequestModel model) throws Exception {
        Integer expectedLabel = model.getExpectedLabel();
        double[][] imageWeights = model.getImage();
        Double[][] dImageWeights = model.toDoubleArray();
        double[] output = network
                .input(imageWeights)
                .propagate()
                .output();

        int label = network.result(output);
        long testSessionId = 0L; // TODO: Remove this
        NumberImageEntity entity = new NumberImageEntity(testSessionId, label, expectedLabel, dImageWeights);
        repository.save(entity);
        return ResponseEntity.ok(new NumberImageApiModel(entity));
    }
}
