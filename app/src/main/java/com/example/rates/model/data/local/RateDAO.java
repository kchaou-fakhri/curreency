package com.example.rates.model.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.rates.model.entity.Rate;

import java.util.List;

@Dao
public interface RateDAO {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    void insert(Rate rate);

    @Query("SELECT * FROM rate")
    List<Rate> getAll();

    @Query("SELECT * FROM rate where id = :id")
    Rate getById(String id);

    @Update
    void update(Rate rate);

    @Query("SELECT * FROM rate where favourite = 1")
    List<Rate> getFavorites();


}
