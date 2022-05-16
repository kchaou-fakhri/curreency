package com.example.rates.model.data.remote;


import com.example.rates.model.entity.Rate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("latest?apikey=yrV3N3wgb8MMRMiq4hV6zDPod9QMW4G1UuWzTkbu")
    Call<Rate> getAllData();
}
