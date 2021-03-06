package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.model.api.NumberImageApiModel;
import org.reggiemcdonald.api.model.request.NumberImagePutRequestModel;
import org.reggiemcdonald.api.model.request.NumberImageRequestModel;
import org.reggiemcdonald.exception.NeuralNetServiceException;
import org.reggiemcdonald.exception.NeuralservInternalServerException;
import org.reggiemcdonald.service.NeuralNetService;
import org.reggiemcdonald.exception.NumberImageNotFoundException;
import org.reggiemcdonald.persistence.entity.NumberImageEntity;
import org.reggiemcdonald.persistence.repo.NumberImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/number")
public class NumberImageController {

    private NumberImageRepository repository;
    private NeuralNetService service;

    @Autowired
    public NumberImageController(NumberImageRepository _repository, NeuralNetService _service) {
        repository = _repository;
        service = _service;
    }

    @RequestMapping(method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('PRIVILEGE_READ')")
    public ResponseEntity<List<NumberImageApiModel>> getNumberImages(@RequestParam(value = "sessionId") Long sessionId) {
        List<NumberImageEntity> entities = repository.findAllBySessionId(sessionId);
        return ResponseEntity.ok(toApiModel(entities));
    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('PRIVILEGE_EDIT')")
    public ResponseEntity<NumberImageApiModel> getNumberImage(@PathVariable(value = "id") Long id) throws NumberImageNotFoundException {
        NumberImageEntity entity = repository
                .findById(id)
                .orElseThrow(() -> new NumberImageNotFoundException(id));
        NumberImageApiModel dto = new NumberImageApiModel(entity);
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
    @PreAuthorize("hasAuthority('PRIVILEGE_EDIT')")
    public ResponseEntity<NumberImageApiModel> postNumberImage(@Valid @RequestBody NumberImageRequestModel model)
        throws NeuralservInternalServerException {
        try {
            Integer expectedLabel = model.getExpectedLabel();
            double[][] imageWeights = model.getImage();
            int label = service
                    .classify(model.getImage())
                    .get();
            NumberImageEntity entity = new NumberImageEntity(0L, label, expectedLabel, imageWeights);
            entity = repository.save(entity);
            return ResponseEntity.ok(new NumberImageApiModel(entity));
        } catch (InterruptedException | ExecutionException | NeuralNetServiceException e) {
            throw new NeuralservInternalServerException();
        }
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
    @PreAuthorize("hasAuthority('PRIVILEGE_EDIT')")
    @ResponseBody
    public ResponseEntity<NumberImageApiModel> putNumberImage(@Valid @RequestBody NumberImagePutRequestModel model)
            throws NumberImageNotFoundException, NeuralservInternalServerException {
        try {
            NumberImageEntity entity = repository
                    .findById(model.getId())
                    .orElseThrow(() -> new NumberImageNotFoundException(model.getId()));

            int label = service
                    .classify(model.getImage())
                    .get();
            entity.setImageWeights(model.getImage());
            entity.setExpectedLabel(model.getExpectedLabel());
            entity.setLabel(label);
            repository.save(entity);
            return ResponseEntity.ok(new NumberImageApiModel(entity));
        } catch (InterruptedException | ExecutionException | NeuralNetServiceException e) {
            throw new NeuralservInternalServerException();
        }

    }

    private List<NumberImageApiModel> toApiModel(List<NumberImageEntity> entities) {
        List<NumberImageApiModel> models = new LinkedList<>();
        for (NumberImageEntity entity : entities)
            models.add(new NumberImageApiModel(entity));
        return models;
    }
}
