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

    public void callApi(){
        call.clone().enqueue(new Callback<ApiRate>() {
            @Override
            public void onResponse(Call<ApiRate> call, Response<ApiRate> response) {

                data.putAll(response.body().getData());

                updateData(data);


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

    private void updateData(HashMap<String ,String > data){


        if(rates.size() == 0){
            for(Map.Entry<String, String> entry : data.entrySet()) {
                Rate rate = new Rate();
                rate.setId(entry.getKey());
                rate.setValue(entry.getValue());
                rate.setLast_value(entry.getValue());
                rates.add(rate);

            }
        }
        else {
            for (Rate rate: rates){
                rate.setLast_value(rate.getValue());
                rate.setValue(data.get(rate.getId()));
            }

        }

    }

}
