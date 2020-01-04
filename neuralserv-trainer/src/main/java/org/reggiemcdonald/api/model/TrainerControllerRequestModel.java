package org.reggiemcdonald.api.model;

import org.apache.commons.validator.routines.UrlValidator;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

public class TrainerControllerRequestModel {

    @NotNull(message = "epochs is a required field")
    private Integer epochs;

    @NotNull(message = "batchSize is a required field")
    private Integer batchSize;

    @NotNull(message = "eta is a required field")
    private Double eta;

    @NotNull(message = "verbose is a required field")
    private Boolean verbose;

    private Boolean callback = false;

    private String url = null;

    @AssertTrue
    public boolean isValid() {
        final String[] schemes = { "http", "https" };
        UrlValidator validator = new UrlValidator(schemes, UrlValidator.ALLOW_LOCAL_URLS);
        if (callback) {
            boolean b = validator.isValid(url);
            return b;
        }
        return true;
    }

    public Integer getEpochs() {
        return epochs;
    }

    public void setEpochs(Integer epochs) {
        this.epochs = epochs;
    }

    public Integer getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(Integer batchSize) {
        this.batchSize = batchSize;
    }

    public Double getEta() {
        return eta;
    }

    public void setEta(Double eta) {
        this.eta = eta;
    }

    public Boolean getVerbose() {
        return verbose;
    }

    public void setVerbose(Boolean verbose) {
        this.verbose = verbose;
    }

    public Boolean getCallback() {
        return callback;
    }

    public void setCallback(Boolean callback) {
        this.callback = callback;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
