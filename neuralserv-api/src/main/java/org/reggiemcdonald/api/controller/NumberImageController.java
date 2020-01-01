package org.reggiemcdonald.api.controller;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.reggiemcdonald.api.model.NumberImageApiModel;
import org.reggiemcdonald.api.model.NumberImageRequestModel;
import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.reggiemcdonald.persistence.service.NumberImageService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/number")
@ConditionalOnProperty(name = "api.isEnabled", havingValue = "true")
public class NumberImageController {

    @Resource
    NumberImageService service;
    Network network;

    @PostConstruct
    public void initialize() {
        try {
            network = Network.load("/Library/Application Support/reggiemcdonald/mnist-neuralserv/network-state.nerl");
        } catch (Exception e) {
            // TODO: Update to retrain network and remove runtime exception
            throw new RuntimeException("Failed to load neural network");
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<NumberImageApiModel>> getNumberImages(
            @RequestParam(value = "sessionId") Integer sessionId,
            @RequestParam(value = "page", required = false) Integer page) {
        page = (page == null) ? 0 : page - 1;
        List<NumberImageDto> dtos = service.findBySession(sessionId, page);
        List<NumberImageApiModel> models = new LinkedList<>();
        for (NumberImageDto dto : dtos)
            models.add(new NumberImageApiModel(dto));
        return ResponseEntity.ok(models);
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NumberImageApiModel> getNumberImage(@PathVariable(value = "id") Integer id) throws NumberImageNotFoundException {
        NumberImageDto dto = service.findById(id);
        return ResponseEntity.ok(new NumberImageApiModel(dto));
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
    public ResponseEntity<Integer> postNumberImage(@Valid @RequestBody NumberImageRequestModel model) throws Exception {
        Integer expectedLabel = model.getExpectedLabel();
        double[][] imageWeights = model.getImage();
        Double[][] dImageWeights = model.toDoubleArray();
        double[] output = network
                .input(imageWeights)
                .propagate()
                .output();

        int label = network.result(output);
        int testSessionId = 0; // TODO: Remove this
        int id = service.insert(testSessionId, label, expectedLabel, dImageWeights);
        return ResponseEntity.ok(id);
    }
}
