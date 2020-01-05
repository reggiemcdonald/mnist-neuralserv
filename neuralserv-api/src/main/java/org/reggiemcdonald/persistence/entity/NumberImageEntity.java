package org.reggiemcdonald.persistence.entity;

import com.reggiemcdonald.neural.feedforward.res.NumberImage;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
public class NumberImageEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column
    Long sessionId;

    @Column
    Integer label;

    @Column
    Integer expectedLabel;

    @Column
    @Type(type = "org.reggiemcdonald.persistence.entity.type.TwoDimDoubleArrayUserType")
    Double[][] imageWeights;

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

    public Integer getExpectedLabel() {
        return expectedLabel;
    }

    public Double[][] getImageWeights() {
        return imageWeights;
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
