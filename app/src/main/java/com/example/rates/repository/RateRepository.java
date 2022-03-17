package com.example.rates.repository;

import android.util.Log;

import com.example.rates.MainActivity;

import com.example.rates.data.Methods;
import com.example.rates.data.RetrofitClient;
import com.example.rates.model.ApiRate;
import com.example.rates.model.Rate;

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RateRepository {


    ArrayList<Rate> rates;
    MainActivity mainActivity;
    HashMap<String, String> data = new HashMap<String, String>();
    static HashMap<String, String> rateName = new HashMap<>() ;
    Methods methods = RetrofitClient.getInstance().create(Methods.class);
    Call<ApiRate> call = methods.getAllData();


    public RateRepository(MainActivity mainActivity){
        rates = new ArrayList<>();
        this.mainActivity = mainActivity;
        getAllCurrencies();
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
                 mainActivity.updateUI(rates);

            }

            @Override
            public void onFailure(Call<ApiRate> call, Throwable t) {
            }
        });
    }

    public String getValueOfMap(String id){
        return data.get(id);
    }

    private void updateData(HashMap<String ,String > data){


        if(rates.size() == 0){
            for(Map.Entry<String, String> entry : data.entrySet()) {
                if(rateName.get(entry.getKey()) !=null){
                    Rate rate = new Rate();
                    rate.setId(entry.getKey());
                    rate.setName(rateName.get(entry.getKey()));
                    rate.setValue(entry.getValue());
                    rate.setLast_value(entry.getValue());
                    rates.add(rate);
                }


            }

           mainActivity.showRates(rates);
        }
        else {
            for (Rate rate: rates){
                rate.setLast_value(rate.getValue());
                rate.setValue(data.get(rate.getId()));
            }

        }

    }


    public static Set<Currency> getAllCurrencies()
    {
        Set<Currency> toret = new HashSet<Currency>();
        Locale[] locs = Locale.getAvailableLocales();

        for(Locale loc : locs) {
            try {
                Currency currency = Currency.getInstance( loc );

                if ( currency != null ) {
                   rateName.put(currency.getCurrencyCode(), currency.getDisplayName());

                }
            } catch(Exception exc)
            {
                // Locale not found
            }
        }

        return toret;
    }

}
