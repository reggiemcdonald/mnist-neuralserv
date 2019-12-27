package org.reggiemcdonald.api;

import com.reggiemcdonald.neural.feedforward.net.Network;
import org.reggiemcdonald.api.model.NumberImageApiModel;
import org.reggiemcdonald.exception.NotFoundException;
import org.reggiemcdonald.persistence.NumberImageDto;
import org.reggiemcdonald.persistence.service.NumberImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/number")
public class NumberImageController {

    @Resource
    NumberImageService service;
    Network network;

    @PostConstruct
    public void initialize() {
        network = Network.load("src/main/java/org/reggiemcdonald/api/network_state.nerl");
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<NumberImageDto> getNumberImage(@PathVariable(value = "id") Integer id) throws NotFoundException {
        NumberImageDto dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    /**
     * Takes a post request with the following json
     * {
     *     expectedLabel: int | null
     *     image: double[][]
     * }
     * @param body
     * @return
     * @throws Exception
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Integer> postNumberImage(@RequestBody NumberImageApiModel model) throws Exception {

        Integer expectedLabel = model.getExpectedLabel();
        double[][] imageWeights = model.getImage();
        Double[][] dImageWeights = model.toDoulbeArray();

        double[] output = network
                .input(imageWeights)
                .propagate()
                .output();

        int label = network.result(output);
        int id = service.insert(label, expectedLabel, dImageWeights);
        return ResponseEntity.ok(id);
    }
}
