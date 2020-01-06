package org.reggiemcdonald.api.model.request;

import javax.validation.constraints.NotNull;

public class NumberImagePutRequestModel extends NumberImageRequestModel {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

}
