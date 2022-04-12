package com.example.rates.model.data.remote;


import com.example.rates.model.entity.Rate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("latest?apikey=AcvroHC2Zcc2vbJYwa6g9ODUVI93zGwxyzdmUqSc")
    Call<Rate> getAllData();
}
