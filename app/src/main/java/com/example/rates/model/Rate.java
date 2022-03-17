package com.example.rates.model;

public class Rate {


    private String last_value;
    private String id;
    private String value;


    public String getLast_value() {
        return last_value;
    }

    public void setLast_value(String last_value) {
        this.last_value = last_value;
    }


    public String getId() {
        return id;
    }

    public String getValue() {
        return value;
    }



    public void setId(String id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
