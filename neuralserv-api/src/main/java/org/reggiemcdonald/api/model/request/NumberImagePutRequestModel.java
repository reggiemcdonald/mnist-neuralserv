package org.reggiemcdonald.api.model.request;

import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
public class NumberImagePutRequestModel extends NumberImageRequestModel {

    @NotNull
    private Long id;

    public NumberImagePutRequestModel(Long id, double[][] img, Integer expectedLabel) {
        super(img, expectedLabel);
        this.id = id;
    }


    public Long getId() {
        return id;
    }

}
