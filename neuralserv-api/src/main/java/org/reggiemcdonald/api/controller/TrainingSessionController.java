package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.model.api.TrainingSessionApiModel;
import org.reggiemcdonald.api.model.request.TrainingSessionRequestModel;
import org.reggiemcdonald.api.util.ModelUtils;
import org.reggiemcdonald.exception.TrainingSessionNotFoundException;
import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;
import org.reggiemcdonald.persistence.repo.TrainingSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(name = "/training-session")
public class TrainingSessionController {

    @Autowired
    private TrainingSessionRepository repository;

    @RequestMapping(method = RequestMethod.GET)
    public List<TrainingSessionApiModel> findByDate(
            @RequestParam(value = "startDate", required = false) Date startDate,
            @RequestParam(value = "endDate", required = false) Date endDate) {
        if (startDate == null && endDate == null) {
            return ModelUtils.toTrainingSessionModelList(repository.findAll());
        } else if (startDate == null) {
            return ModelUtils.toTrainingSessionModelList(repository.findAllBeforeDate(endDate));
        } else if (endDate == null) {
            return ModelUtils.toTrainingSessionModelList(repository.finaAllAfterDate(startDate));
        } else {
            return ModelUtils.toTrainingSessionModelList(repository.findAllByTrainingDateBetween(startDate, endDate));
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TrainingSessionApiModel findById(@PathVariable("id") Long id)
            throws TrainingSessionNotFoundException {
        TrainingSessionEntity entity = repository.findOne(id);
        if (entity == null)
            throw new TrainingSessionNotFoundException(id);
        return new TrainingSessionApiModel(entity);
    }

    @RequestMapping(method = RequestMethod.POST)
    public TrainingSessionApiModel postTrainingSession(@Valid @RequestBody TrainingSessionRequestModel model) {
        TrainingSessionEntity entity = new TrainingSessionEntity(
                model.getInternalTrainingSize(),
                model.getExternalTrainingSize(),
                model.getInternalNumberCorrect(),
                model.getExternalNumberCorrect(),
                model.getTrainingDate()
        );
        repository.save(entity);
        return new TrainingSessionApiModel(entity);
    }
}
