package org.reggiemcdonald.persistence.entity;

import com.reggiemcdonald.neural.feedforward.res.NumberImage;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class NumberImageEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Long sessionId;

    @Column
    private Integer label;

    @Column
    private Integer expectedLabel;

    @Column
    @Type(type = "org.reggiemcdonald.persistence.entity.type.TwoDimDoubleArrayUserType")
    private Double[][] imageWeights;

    public NumberImageEntity() {}

    public NumberImageEntity(int _label) {
        label = _label;
    }

    public NumberImageEntity(int _label, int _expectedLabel) {
        label = _label;
        expectedLabel = _expectedLabel;
    }

    public NumberImageEntity(int _label, int _expectedLabel, long _sessionId) {
        sessionId = _sessionId;
        label = _label;
        expectedLabel = _expectedLabel;
    }

    public NumberImageEntity(long _sessionId, int _label, int _expectedLabel, Double[][] _imageWeights) {
        sessionId = _sessionId;
        label = _label;
        expectedLabel = _expectedLabel;
        imageWeights = _imageWeights;
    }

    public Long getId() {
        return id;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public Integer getLabel() {
        return label;
    }

    public void setLabel(Integer label) {
        this.label = label;
    }

    public Integer getExpectedLabel() {
        return expectedLabel;
    }

    public void setExpectedLabel(Integer expectedLabel) {
        this.expectedLabel = expectedLabel;
    }

    public Double[][] getImageWeights() {
        return imageWeights;
    }

    public void setImageWeights(Double[][] imageWeights) {
        this.imageWeights = imageWeights;
    }

    @Override
    public String toString() {
        return String.format(
                "{ id: %d, session: %d, label: %d, expected: %d }",
                id, sessionId, label, expectedLabel);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NumberImageEntity))
            return false;
        NumberImageEntity other = (NumberImageEntity) o;
        return this.id.equals(other.id);
    }
}