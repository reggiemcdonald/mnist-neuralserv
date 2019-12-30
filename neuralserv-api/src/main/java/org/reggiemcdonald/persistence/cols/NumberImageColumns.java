package org.reggiemcdonald.persistence.cols;

public enum NumberImageColumns {

    ID("ID"),
    SESSION_ID("session_id"),
    LABEL("label"),
    EXPECTED_LABEL("expected_label"),
    IMAGE_WEIGHTS("image_weights");

    private String name;
    NumberImageColumns(String _name) {
        name = _name;
    }

    @Override
    public String toString() {
        return name;
    }

}
