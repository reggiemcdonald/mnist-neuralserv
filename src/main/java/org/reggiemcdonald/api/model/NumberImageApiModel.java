package org.reggiemcdonald.api.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;


public class NumberImageApiModel {

    @NotEmpty
    private double[][] image;

    @Min(value = 0)
    @Max(value = 9)
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
