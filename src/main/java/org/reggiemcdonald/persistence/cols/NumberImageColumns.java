package org.reggiemcdonald.persistence.cols;

import com.reggiemcdonald.neural.feedforward.res.NumberImage;

public enum NumberImageColumns {

    ID("ID"),
    SESSION_ID("session_id"),
    LABEL("label"),
    EXPECTED_LABEL("expected_label"),
    IMAGE_WEIGHTS("image_weights");

    private String name;
    private NumberImageColumns(String _name) {
        name = _name;
    }


}
