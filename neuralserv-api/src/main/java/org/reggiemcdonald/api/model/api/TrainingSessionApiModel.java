package org.reggiemcdonald.api.model.api;

import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;

import java.util.Date;

public class TrainingSessionApiModel {

    private long id;

    private int internalTrainingSize;

    private int externalTrainingSize;

    private int internalNumberCorrect;

    private int externalNumberCorrect;

    private Date trainingDate;

    public TrainingSessionApiModel(TrainingSessionEntity entity) {
        id = entity.getId();
        internalTrainingSize = entity.getInternalTrainingSize();
        externalTrainingSize = entity.getExternalTrainingSize();
        internalNumberCorrect = entity.getInternalNumberCorrect();
        externalNumberCorrect = entity.getExternalNumberCorrect();
        trainingDate = entity.getTrainingDate();
    }

    public long getId() {
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
