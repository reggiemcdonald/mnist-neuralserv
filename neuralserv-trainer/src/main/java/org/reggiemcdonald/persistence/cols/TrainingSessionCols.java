package org.reggiemcdonald.persistence.cols;

public enum TrainingSessionCols {

    ID("id"),
    INTERNAL_TRAINING_SIZE("internal_training_size"),
    EXTERNAL_TRAINING_SIZE("external_training_size"),
    INTERNAL_NUMBER_CORRECT("internal_number_correct"),
    EXTERNAL_NUMBER_CORRECT("external_number_correct"),
    TRAINING_DATE("training_date");

    private String name;

    TrainingSessionCols(String _name) {
        name = _name;
    }

    @Override
    public String toString() {
        return name;
    }
}
