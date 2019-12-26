package org.reggiemcdonald.api;

import com.reggiemcdonald.neural.feedforward.net.Network;
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

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseEntity<Integer> postNumberImage(@RequestBody(required = true) Map<String, double[][]> body) throws Exception {
        final String VALUE_KEY = "image";

        // TODO: Add custom exception
        if (!body.containsKey(VALUE_KEY))
            throw new Exception("No number image provided");

        double[][] imageWeights = body.get(VALUE_KEY);
        double[] output = network
                .input(imageWeights)
                .propagate()
                .output();
        int label = network.result(output);
        int id = service.insert(label, imageWeights);
        return ResponseEntity.ok(id);
    }
}
