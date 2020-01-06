package org.reggiemcdonald.api.model.api;

import org.reggiemcdonald.persistence.entity.NumberImageEntity;

public class NumberImageApiModel {

    private final long id;
    private int label;
    private Integer expectedLabel;
    private Double[][] imageWeights;

    public NumberImageApiModel(NumberImageEntity dao) {
        id = dao.getId();
        label = dao.getLabel();
        expectedLabel = dao.getExpectedLabel();
        imageWeights = dao.getImageWeights();
    }

    public NumberImageApiModel(int _id, int _label, Integer _expectedLabel, Double[][] _imageWeights) {
        id = _id;
        label = _label;
        expectedLabel = _expectedLabel;
        imageWeights = _imageWeights;
    }

    public long getId() {
        return id;
    }

    public Integer getExpectedLabel() {
        return expectedLabel;
    }

    public void setExpectedLabel(Integer expectedLabel) {
        this.expectedLabel = expectedLabel;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public Double[][] getImageWeights() {
        return imageWeights;
    }

    public void setImageWeights(Double[][] imageWeights) {
        this.imageWeights = imageWeights;
    }
}
