package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.model.api.ScaleApiModel;
import org.reggiemcdonald.api.service.ScalingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/scaler")
public class ScalingController {

    ScalingService service;

    @Autowired
    public ScalingController(ScalingService _service) {
        service = _service;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ScaleApiModel> scale(@Valid @RequestBody ScaleApiModel model) {
        // Make Request
        return ResponseEntity.status(501).body(null);
    }

}
