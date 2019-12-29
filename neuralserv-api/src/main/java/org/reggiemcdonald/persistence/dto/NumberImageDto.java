package org.reggiemcdonald.persistence.dto;

public class NumberImageDto {

    private int id;
    private int sessionId;
    private int label;
    private Integer expectedLabel;
    private Double[][] imageWeights;

    public NumberImageDto(int _id, int _sessionId, int _label, Integer _expectedLabel, Double[][] _image) {
        id = _id;
        sessionId = _sessionId;
        label = _label;
        expectedLabel = _expectedLabel;
        imageWeights = _image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

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
