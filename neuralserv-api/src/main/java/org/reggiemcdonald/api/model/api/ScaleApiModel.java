package org.reggiemcdonald.api.model.api;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ScaleApiModel {

    @NotNull
    @Size(min = 28, message = "Array must be as large as the image being scaled to")
    double[][] image;

    public double[][] getImage() {
        return image;
    }

    public void setImage(double[][] image) {
        this.image = image;
    }
}

