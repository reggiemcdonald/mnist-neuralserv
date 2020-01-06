package org.reggiemcdonald.api.model.request;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class TrainingSessionRequestModel {

    @NotNull
    private int internalTrainingSize;

    @NotNull
    private int externalTrainingSize;

    @NotNull
    private int internalNumberCorrect;

    @NotNull
    private int externalNumberCorrect;

    private Date trainingDate = new Date();

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
