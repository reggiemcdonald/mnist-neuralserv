package org.reggiemcdonald.exception;

public class TrainingSessionNotFoundException extends NotFoundException {
    public TrainingSessionNotFoundException(long id) {
        super(String.format("The training session %d was not found", id));
    }
}
