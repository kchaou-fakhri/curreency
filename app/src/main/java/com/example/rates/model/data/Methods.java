package com.example.rates.model.data;


import com.example.rates.model.entity.Rate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("latest?apikey=8qzmrfaRHqzplaHspgx3D1HRIf2MkHkxUfDhLNO7")
    Call<Rate> getAllData();
}
