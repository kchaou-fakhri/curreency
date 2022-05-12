package com.example.rates.model.entity;

public class Devise extends Rate{
    private String paye;


    public Devise( String id, String name, String value, int propriety, String paye){
        super(id, name, value, propriety);
        this.paye = paye;
    }

    public String getPaye() {
        return paye;
    }

    public void setPaye(String paye) {
        this.paye = paye;
    }
}
