package org.reggiemcdonald.api.model;

import org.reggiemcdonald.persistence.dto.NumberImageDto;

public class NumberImageApiModel {

    private final int id;
    private int label;
    private Integer expectedLabel;
    private Double[][] imageWeights;

    public NumberImageApiModel(NumberImageDto dto) {
        id = dto.getId();
        label = dto.getLabel();
        expectedLabel = dto.getExpectedLabel();
        imageWeights = dto.getImageWeights();
    }

    public NumberImageApiModel(int _id, int _label, Integer _expectedLabel, Double[][] _imageWeights) {
        id = _id;
        label = _label;
        expectedLabel = _expectedLabel;
        imageWeights = _imageWeights;
    }

    public int getId() {
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
