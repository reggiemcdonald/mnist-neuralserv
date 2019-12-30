package org.reggiemcdonald.api.controller;

import org.reggiemcdonald.api.model.TrainingSessionApiModel;
import org.reggiemcdonald.exception.TrainingSessionNotFoundException;
import org.reggiemcdonald.persistence.dto.TrainingSessionDto;
import org.reggiemcdonald.persistence.service.TrainingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sun.util.calendar.BaseCalendar;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/training-sessions")
public class TrainingSessionController {

    @Resource
    TrainingSessionService service;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<TrainingSessionApiModel>> findByDate(
            @RequestParam(value = "startDate", required = false) Long start,
            @RequestParam(value = "endDate", required = false) Long end) {
        List<TrainingSessionDto> dtos;

        if (start == null && end == null) {
            dtos = service.getTrainingSessions();
        } else if (start == null) {
            start = BaseCalendar.Date.TIME_UNDEFINED;
            dtos = service.findByDate(new Timestamp(start), new Timestamp(end));
        } else if (end == null) {
            end = new Date().getTime();
            dtos = service.findByDate(new Timestamp(start), new Timestamp(end));
        } else {
            dtos = service.findByDate(new Timestamp(start), new Timestamp(end));
        }

        return ResponseEntity.ok(toModelList(dtos));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<TrainingSessionApiModel> findById(@PathVariable(value = "id") Integer id) throws TrainingSessionNotFoundException {
        TrainingSessionDto dto = service.findById(id);
        return ResponseEntity.ok(new TrainingSessionApiModel(dto));
    }

    private List<TrainingSessionApiModel> toModelList(List<TrainingSessionDto> dtos) {
        List<TrainingSessionApiModel> models = new LinkedList<>();
        for (TrainingSessionDto dto : dtos)
            models.add(new TrainingSessionApiModel(dto));
        return models;
    }
}
