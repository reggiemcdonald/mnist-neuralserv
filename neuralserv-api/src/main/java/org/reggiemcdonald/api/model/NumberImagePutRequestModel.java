package org.reggiemcdonald.api.model;

import javax.validation.constraints.NotNull;

public class NumberImagePutRequestModel extends NumberImageRequestModel {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

}
