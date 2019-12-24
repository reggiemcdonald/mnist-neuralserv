package org.reggiemcdonald.api;

import org.reggiemcdonald.persistence.NumberImageDto;
import org.reggiemcdonald.persistence.service.NumberImageService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
}
