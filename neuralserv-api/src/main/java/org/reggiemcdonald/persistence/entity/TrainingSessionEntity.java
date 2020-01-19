package org.reggiemcdonald.persistence.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "training_session")
public class TrainingSessionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    private int internalTestingSize;

    @Column
    @NotNull
    private int externalTestingSize;

    @Column
    @NotNull
    private int internalNumberCorrect;

    @Column
    @NotNull
    private int externalNumberCorrect;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date trainingDate;

    public TrainingSessionEntity() {}

    public TrainingSessionEntity(int _internalTrainingSize, int _externalTrainingSize, int _internalNumberCorrect,
                                 int _externalNumberCorrect, Date _trainingDate) {
        internalTestingSize = _internalTrainingSize;
        externalTestingSize = _externalTrainingSize;
        internalNumberCorrect = _internalNumberCorrect;
        externalNumberCorrect = _externalNumberCorrect;
        trainingDate = _trainingDate;
    }

    public Long getId() {
        return id;
    }

    public int getInternalTestingSize() {
        return internalTestingSize;
    }

    public void setInternalTestingSize(int internalTrainingSize) {
        this.internalTestingSize = internalTrainingSize;
    }

    public int getExternalTestingSize() {
        return externalTestingSize;
    }

    public void setExternalTestingSize(int externalTrainingSize) {
        this.externalTestingSize = externalTrainingSize;
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

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }
}
