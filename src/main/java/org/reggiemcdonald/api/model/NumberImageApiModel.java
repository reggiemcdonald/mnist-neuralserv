package org.reggiemcdonald.api.model;

public class NumberImageApiModel {
    private double[][] image;
    private Integer expectedLabel;

    public double[][] getImage() {
        return image;
    }

    public void setImage(double[][] image) {
        this.image = image;
    }

    public Integer getExpectedLabel() {
        return expectedLabel;
    }

    public void setExpectedLabel(Integer expectedLabel) {
        this.expectedLabel = expectedLabel;
    }

    public Double[][] toDoulbeArray() {
        Double[][] darr = new Double[image.length][];
        for (int i = 0 ; i < image.length ; i++) {
            Double[] row = new Double[image[i].length];
            darr[i] = row;
            for (int j = 0; j < row.length; j++)
                row[j] = image[i][j];
        }
        return darr;
    }
}
