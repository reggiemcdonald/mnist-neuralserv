package org.reggiemcdonald.api.model.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class NumberImageRequestModel {

    @NotNull(message = "is required")
    @Size(max=28, min=28, message = "must be a 28 X 28 2D double array")
    private double[][] image;

    @Min(value = 0, message = "must be between 0 and 9")
    @Max(value = 9, message = "must be between 0 and 9")
    private Integer expectedLabel = null;

    public double[][] getImage() {
        return image;
    }

    public Integer getExpectedLabel() {
        return expectedLabel;
    }

    public Double[][] toDoubleArray() {
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
