package org.reggiemcdonald.api;

import org.reggiemcdonald.persistence.NumberImageDto;
import org.reggiemcdonald.persistence.service.NumberImageService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/number")
public class NumberImageController {

    @Resource
    NumberImageService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public NumberImageDto getNumberImage(@PathVariable(value = "id") Integer id) throws Exception {
        // TODO: Add custom exception
        if (id == null)
            throw new Exception("Missing param");

        return service.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public int postNumberImage(@RequestBody Map<String, String> values) throws Exception {

        // TODO: Use learning library to classify digit
        return service.insert(0);
    }
}
