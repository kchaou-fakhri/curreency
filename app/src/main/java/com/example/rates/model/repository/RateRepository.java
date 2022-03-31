package com.example.rates.model.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.example.rates.model.data.AppDatabase;
import com.example.rates.model.data.RateDAO;
import com.example.rates.view.MainActivity;

import com.example.rates.model.data.Methods;
import com.example.rates.model.data.RetrofitClient;
import com.example.rates.model.entity.Rate;

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


   private ArrayList<Rate> rates;
    private MainActivity mainActivity;
    private HashMap<String, String> data = new HashMap<String, String>();
    static HashMap<String, String> rateName = new HashMap<>() ;
    Methods methods = RetrofitClient.getInstance().create(Methods.class);
    Call<Rate> call = methods.getAllData();
    AppDatabase db;



    public RateRepository(MainActivity mainActivity){
        rates = new ArrayList<>();
        this.mainActivity = mainActivity;
        db= Room.databaseBuilder(mainActivity,
                AppDatabase.class, "ratedb").allowMainThreadQueries().build();
        getAllCurrencies();

    }

    public ArrayList<Rate> getList() {
        return rates;
    }

    public void callApi(){

        RateDAO rateDAO = db.rateDAO();
        call.clone().enqueue(new Callback<Rate>() {
            @Override
            public void onFailure(Call<Rate> call, Throwable t) {

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Insert Data
                        //rates.addAll(rateDAO.getAll());
                           Log.println(Log.ASSERT, "response : ", "oijoiejrqg");

                        for (Rate item : rateDAO.getAll()){
                            data.put(item.getId(), item.getValue());
                        }
                        updateData(data);


                        if (data.size() == 0){
                            mainActivity.showAlertIfNoConnection();
                            return;
                        }




                    }


                });


             //   Log.println(Log.ASSERT, "response : ", String.valueOf(rates.get(1).getValue()));

            }

            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {

                data.putAll(response.body().getData());
//                for(Map.Entry<String, String> entry : response.body().data.entrySet()) {
////                  Log.println(Log.ASSERT, "response : ", entry.getValue());
//                    Rate rate = new Rate();
//                    rate.setId(entry.getKey());
//                    rate.setName(rateName.get(entry.getKey()));
//                    rate.setValue(entry.getValue());
//                    rate.setLast_value(entry.getValue());
//                    rates.add(rate);
//
//                    AsyncTask.execute(new Runnable() {
//                        @Override
//                        public void run() {
//                            // Insert Data
//                            rateDAO.insert(rate);
//
//                            // Get Data
//
//                        }
//                    });
//
//                }


               updateData(data);


            }



        });


    }

    public Rate getValue(String id){

        RateDAO rateDAO = db.rateDAO();


        return rateDAO.getById(id);
    }

    private void updateData(HashMap<String ,String > data){

        RateDAO rateDAO = db.rateDAO();

        if(rates.size() == 0){
            for(Map.Entry<String, String> entry : data.entrySet()) {
                if(rateName.get(entry.getKey()) !=null){
                    Rate rate = new Rate();
                    rate.setId(entry.getKey());
                    rate.setName(rateName.get(entry.getKey()));
                    rate.setValue(entry.getValue());
                    rate.setLast_value(entry.getValue());
                    rates.add(rate);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            // Insert Data
                            rateDAO.insert(rate);

                            // Get Data

                        }
                    });


                }


            }
            if (rates.size() == 0){
                mainActivity.showAlertIfNoConnection();
                return;
            }
            else {
                mainActivity.updateUI(rates);
                mainActivity.showRates(rates);
            }

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


    public LiveData<ArrayList<Rate>> getAll(){
        RateDAO rateDAO = db.rateDAO();
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.setValue(rateDAO.getAll());
        return mutableLiveData;
    }


}
