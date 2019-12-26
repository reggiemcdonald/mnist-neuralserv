package org.reggiemcdonald.exception;

public class NotFoundException extends Exception {
    public NotFoundException(int id) {
        super(String.format("Number Image ID %d not found", id));
    }
}
