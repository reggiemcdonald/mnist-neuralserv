package org.reggiemcdonald.persistence;

public class NumberImageDto {

    private final int id;
    private int label;
    private String url;

    public NumberImageDto(int _id, int _label, String _url) {
        id = _id;
        label = _label;
        url = _url;
    }

    public int getId() {
        return id;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
