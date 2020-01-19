package org.reggiemcdonald.api.model.api;

import org.reggiemcdonald.persistence.entity.TrainingSessionEntity;

import java.util.Date;

public class TrainingSessionApiModel {

    private long id;

    private int internalTestingSize;

    private int externalTestingSize;

    private int internalNumberCorrect;

    private int externalNumberCorrect;

    private Date trainingDate;

    public TrainingSessionApiModel(TrainingSessionEntity entity) {
        id = entity.getId();
        internalTestingSize = entity.getInternalTestingSize();
        externalTestingSize = entity.getExternalTestingSize();
        internalNumberCorrect = entity.getInternalNumberCorrect();
        externalNumberCorrect = entity.getExternalNumberCorrect();
        trainingDate = entity.getTrainingDate();
    }

    public long getId() {
        return id;
    }

    public int getInternalTestingSize() {
        return internalTestingSize;
    }

    public int getExternalTestingSize() {
        return externalTestingSize;
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
