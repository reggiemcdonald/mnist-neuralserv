package org.reggiemcdonald.persistence.entity;

import lombok.NoArgsConstructor;

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
    private int internalTrainingSize;

    @Column
    @NotNull
    private int externalTrainingSize;

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
        internalTrainingSize = _internalTrainingSize;
        externalTrainingSize = _externalTrainingSize;
        internalNumberCorrect = _internalNumberCorrect;
        externalNumberCorrect = _externalNumberCorrect;
        trainingDate = _trainingDate;
    }

    public Long getId() {
        return id;
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

    public Date getTrainingDate() {
        return trainingDate;
    }

    public void setTrainingDate(Date trainingDate) {
        this.trainingDate = trainingDate;
    }
}
