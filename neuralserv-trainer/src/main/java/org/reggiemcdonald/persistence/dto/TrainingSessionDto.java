package org.reggiemcdonald.persistence.dto;

import java.sql.Timestamp;

public class TrainingSessionDto {
    private int id;
    private int internalTrainingSize;
    private int externalTrainingSize;
    private int internalNumberCorrect;
    private int externalNumberCorrect;
    private Timestamp trainingDate;

    public TrainingSessionDto(
            int _id,
            int _internalTrainingSize,
            int _externalTrainingSize,
            int _internalNumberCorrect,
            int _externalNumberCorrect,
            Timestamp _trainingDate) {
        id =  _id;
        internalTrainingSize = _internalTrainingSize;
        externalTrainingSize = _externalTrainingSize;
        internalNumberCorrect = _internalNumberCorrect;
        externalNumberCorrect = _externalNumberCorrect;
        trainingDate = _trainingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getInternalTrainingSize() {
        return internalTrainingSize;
    }

    public void setInternalTrainingSize(int internalTrainingSize) {
        this.internalTrainingSize = internalTrainingSize;
    }

    public int getExternalTrainingSize() {
        return externalTrainingSize;
    }

    public void setExternalTrainingSize(int externalTrainingSize) {
        this.externalTrainingSize = externalTrainingSize;
    }

    public int getInternalNumberCorrect() {
        return internalNumberCorrect;
    }

    public void setInternalNumberCorrect(int internalNumberCorrect) {
        this.internalNumberCorrect = internalNumberCorrect;
    }

    public int getExternalNumberCorrect() {
        return externalNumberCorrect;
    }

    public void setExternalNumberCorrect(int externalNumberCorrect) {
        this.externalNumberCorrect = externalNumberCorrect;
    }

    public Timestamp getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Timestamp trainingDate) {
        this.trainingDate = trainingDate;
    }
}
