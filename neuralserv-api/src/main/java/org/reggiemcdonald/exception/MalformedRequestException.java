package org.reggiemcdonald.exception;

public class MalformedRequestException extends Exception {
    private String endpoint;

    public MalformedRequestException(String endpoint, String msg) {
        super(String.format("the request to %s could not be processed due to %s", endpoint, msg));
        this.endpoint = endpoint;
    }

    public String getEndpoint() {
        return endpoint;
    }
}
