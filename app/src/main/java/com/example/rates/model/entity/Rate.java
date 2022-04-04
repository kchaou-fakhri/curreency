package com.example.rates.model.entity;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Entity
public class Rate implements Comparator<Rate> {



    @PrimaryKey
    @NonNull
    private String id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "value")
    private String value;
    @Ignore
    private String last_value;

    public int getPropriety() {
        return propriety;
    }

    public void setPropriety(int propriety) {
        this.propriety = propriety;
    }

    public void setData(HashMap<String, String> data) {
        this.data = data;
    }

    @Ignore
    private int propriety;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    @Embedded
    public HashMap<String, String> data = new HashMap<String, String>();
    public HashMap<String, String> getData() {
        return  data;
    }

    @Override
    public String toString(){
        return name +"=========>" +id + " : "+value + " Property : "+propriety ;
    }


    @Override
    public int compare(Rate rate, Rate t1) {
        return rate.getPropriety() - t1.getPropriety();
    }


}
