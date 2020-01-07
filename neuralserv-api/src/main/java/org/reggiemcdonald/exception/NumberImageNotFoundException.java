package org.reggiemcdonald.exception;

public class NumberImageNotFoundException extends NotFoundException {
    public NumberImageNotFoundException(long id) {
        super(String.format("Number Image ID %d not found", id));
    }
}
