package org.reggiemcdonald.persistence;

public class NumberImageDto {

    private int id;
    private int label;

    public NumberImageDto(int _id, int _label) {
        id = _id;
        label = _label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) { this.id = id; }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

}
