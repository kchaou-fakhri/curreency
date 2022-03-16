package com.example.rates.repository;

import android.util.Log;

import com.example.rates.MainActivity;

import com.example.rates.data.Methods;
import com.example.rates.data.RetrofitClient;
import com.example.rates.model.ApiRate;
import com.example.rates.model.Rate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateRepository {


    ArrayList<Rate> rates;
    MainActivity mainActivity;
    HashMap<String, String> data = new HashMap<String, String>();

    Methods methods = RetrofitClient.getInstance().create(Methods.class);
    Call<ApiRate> call = methods.getAllData();


    public RateRepository(MainActivity mainActivity){
        rates = new ArrayList<>();
        this.mainActivity = mainActivity;
    }

    public ArrayList<Rate> getList() {
        return rates;
    }

    public void getData(){
        call.clone().enqueue(new Callback<ApiRate>() {
            @Override
            public void onResponse(Call<ApiRate> call, Response<ApiRate> response) {
                data.put("USA","1");
                data.putAll(response.body().getData());
                for(Map.Entry<String, String> entry : data.entrySet()) {
                    Rate rate = new Rate();
                    rate.setId(entry.getKey());
                    rate.setValue(entry.getValue());
                    rates.add(rate);

                }

             //   Log.e("Main","-------------------------"+rates.get(1).getValue()+ " "+rates.size());

                    mainActivity.showRates(rates);
                 mainActivity.updateUI(rates);
            }

            @Override
            public void onFailure(Call<ApiRate> call, Throwable t) {

                Log.e("Main","------------------------- not working");
            }
        });
    }

    public String getValueOfMap(String id){
        return data.get(id);
    }


}
