package com.example.rates.model;

public class Rate {
    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }

    String id;

    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    String value;
}
