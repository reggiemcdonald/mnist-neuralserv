package org.reggiemcdonald.api.controller;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.reggiemcdonald.api.model.api.NumberImageApiModel;
import org.reggiemcdonald.api.model.request.NumberImagePutRequestModel;
import org.reggiemcdonald.api.model.request.NumberImageRequestModel;
import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/number")
public class NumberImageController {

    @Autowired
    private NumberImageRepository repository;

    @Autowired
    private ExecutorService executorService;

    private Network network;

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
    public ResponseEntity<List<NumberImageApiModel>> getNumberImages(@RequestParam(value = "sessionId") Long sessionId) {
        List<NumberImageEntity> entities = repository.findAllBySessionId(sessionId);
        return ResponseEntity.ok(toApiModel(entities));
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
        int label = classify(model);
        long testSessionId = 0L; // TODO: Remove this
        NumberImageEntity entity = new NumberImageEntity(testSessionId, label, expectedLabel, imageWeights);
        repository.save(entity);
        return ResponseEntity.ok(new NumberImageApiModel(entity));
    }

    /**
     * Takes a put request with the following json
     * {
     *     id: long,
     *     expectedLabel: int | null
     *     image: double[][]
     * }
     * @param model
     * @return
     * @throws NumberImageNotFoundException
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<NumberImageApiModel> putNumberImage(@Valid @RequestBody NumberImagePutRequestModel model)
            throws NumberImageNotFoundException {
        NumberImageEntity entity = repository.findById(model.getId()).get();
        if (entity == null)
            throw new NumberImageNotFoundException(model.getId());
        int label = classify(model);
        entity.setImageWeights(model.getImage());
        entity.setExpectedLabel(model.getExpectedLabel());
        entity.setLabel(label);
        repository.save(entity);
        return ResponseEntity.ok(new NumberImageApiModel(entity));
    }

    private int classify(NumberImageRequestModel model) {
        double[] output = network
                .input(model.getImage())
                .propagate()
                .output();
        return network.result(output);
    }

    private List<NumberImageApiModel> toApiModel(List<NumberImageEntity> entities) {
        List<NumberImageApiModel> models = new LinkedList<>();
        for (NumberImageEntity entity : entities)
            models.add(new NumberImageApiModel(entity));
        return models;
    }
}
