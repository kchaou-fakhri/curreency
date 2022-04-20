package com.example.rates.model.data.remote;


import com.example.rates.model.entity.Rate;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Methods {

    @GET("latest?apikey=zNigeB84JCVYzglqPR2pMzj4CxBi6Sb87OZWPsBf")
    Call<Rate> getAllData();
}
