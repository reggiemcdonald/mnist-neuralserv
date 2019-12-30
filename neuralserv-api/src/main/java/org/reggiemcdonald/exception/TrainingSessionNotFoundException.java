package org.reggiemcdonald.exception;

public class TrainingSessionNotFoundException extends NotFoundException {
    public TrainingSessionNotFoundException(int id) {
        super(String.format("The training session %d was not found", id));
    }
}
