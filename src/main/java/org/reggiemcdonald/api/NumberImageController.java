package org.reggiemcdonald.api;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.reggiemcdonald.api.model.NumberImageApiModel;
import org.reggiemcdonald.api.model.NumberImageRequestModel;
import org.reggiemcdonald.exception.NotFoundException;
import org.reggiemcdonald.persistence.dto.NumberImageDto;
import org.reggiemcdonald.persistence.service.NumberImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping("/number")
public class NumberImageController {

    @Resource
    NumberImageService service;
    Network network;

    @PostConstruct
    public void initialize() {
        try {
            network = Network.load("src/main/java/org/reggiemcdonald/api/network_state.nerl");
        } catch (Exception e) {
            // TODO: Update to retrain network and remove runtime exception
            throw new RuntimeException("Failed to load neural network");
        }
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NumberImageApiModel> getNumberImage(@PathVariable(value = "id") Integer id) throws NotFoundException {
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
        int id = service.insert(label, expectedLabel, dImageWeights);
        return ResponseEntity.ok(id);
    }
}
