package org.reggiemcdonald.api.model.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ScaleApiModel {

    @NotNull
    @Size(min = 28, message = "Array must be as large as the image being scaled to")
    double[][] imageWeights;

    public double[][] getImageWeights() {
        return imageWeights;
    }
}

