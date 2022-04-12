package com.example.rates.model.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.rates.model.data.local.RateDAO;
import com.example.rates.model.entity.Rate;

@Database(entities = {Rate.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract RateDAO rateDAO();
}