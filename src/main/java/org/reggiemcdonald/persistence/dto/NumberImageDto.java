package org.reggiemcdonald.persistence.dto;

public class NumberImageDto {

    private int id;
    private int label;
    private Integer expectedLabel;
    private Double[][] imageWeights;

    public NumberImageDto(int _id, int _label, Integer _expectedLabel, Double[][] _image) {
        id = _id;
        label = _label;
        expectedLabel = _expectedLabel;
        imageWeights = _image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
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
}
