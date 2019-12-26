package org.reggiemcdonald.api.exception;

public class NotFoundException extends Exception {
    public NotFoundException(int id) {
        super(String.format("Number with ID %d not found.", id));
    }
}
