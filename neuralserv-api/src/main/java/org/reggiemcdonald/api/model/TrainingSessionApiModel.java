package org.reggiemcdonald.api.model;

import org.reggiemcdonald.persistence.dto.TrainingSessionDto;

import java.sql.Timestamp;
import java.util.Date;

public class TrainingSessionApiModel {

    private int id;
    private int internalTrainingSize;
    private int externalTrainingSize;
    private int internalNumberCorrect;
    private int externalNumberCorrect;
    private Date trainingDate;

    public TrainingSessionApiModel(TrainingSessionDto dto) {
        id = dto.getId();
        internalTrainingSize = dto.getInternalTrainingSize();
        externalTrainingSize = dto.getExternalTrainingSize();
        internalNumberCorrect = dto.getInternalNumberCorrect();
        externalNumberCorrect = dto.getExternalNumberCorrect();
        trainingDate = new Date(dto.getTrainingDate().getTime());
    }

    public int getId() {
        return id;
    }

    public int getInternalTrainingSize() {
        return internalTrainingSize;
    }

    public int getExternalTrainingSize() {
        return externalTrainingSize;
    }

    public int getInternalNumberCorrect() {
        return internalNumberCorrect;
    }

    public int getExternalNumberCorrect() {
        return externalNumberCorrect;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }
}
