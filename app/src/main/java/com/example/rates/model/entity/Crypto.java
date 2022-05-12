package com.example.rates.model.entity;

public class Crypto extends Rate{

    private String author;


    public Crypto( String id, String name, String value, int propriety, String auther){
        super(id, name, value, propriety);
        this.author = auther;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
