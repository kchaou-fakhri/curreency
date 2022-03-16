package com.example.rates.data;


import com.example.rates.model.ApiRate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("latest?apikey=afa8f360-9993-11ec-bd40-4bfba891ba9b")
    Call<ApiRate> getAllData();
}
