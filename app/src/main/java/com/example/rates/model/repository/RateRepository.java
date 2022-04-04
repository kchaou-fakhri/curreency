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
import java.util.Collections;
import java.util.Comparator;
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


    private ArrayList<Rate> rates;                                          /******** Create List of currency *****************************/
    private MainActivity mainActivity;                                      /******** get Context  ****************************************/
    private HashMap<String, String> data = new HashMap<String, String>();                /******** to Save Currency in temp Hashmap ********************/
    static HashMap<String, String> rateName = new HashMap<>() ;             /******** to Save name of century in temp Hashmap *************/
    Methods methods = RetrofitClient.getInstance().create(Methods.class);   /******** Create Instance of RetrofitClient *******************/
    Call<Rate> call = methods.getAllData();                                 /******** Get data from getAllData methode of RetrofitClient **/
    AppDatabase db;                                                         /******** Create Instance of Database *************************/


    public RateRepository(MainActivity mainActivity){
        rates = new ArrayList<>();
        this.mainActivity = mainActivity;
        db= Room.databaseBuilder(mainActivity,
                AppDatabase.class, "ratedb").allowMainThreadQueries().build();
        getAllCurrencies();

    }

    /******** this function to get data from API  *********************/
    public void callApi(){

        RateDAO rateDAO = db.rateDAO();
        call.clone().enqueue(new Callback<Rate>() {
            @Override
            public void onFailure(Call<Rate> call, Throwable t) {

                mainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Insert Data
                        for (Rate item : rateDAO.getAll()){
                            data.put(item.getId(), item.getValue());
                        }

                        if (data.size() == 0){
                            mainActivity.showAlertIfNoConnection();
                            return;
                        }
                        else
                        {
                            updateData(data);
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call<Rate> call, Response<Rate> response) {
                data.putAll(response.body().getData());
               updateData(data);
            }
        });
    }

    /******** this function to get data from API  *********************/
    public Rate getValue(String id){
        RateDAO rateDAO = db.rateDAO();
        return rateDAO.getById(id);
    }

    /******** this function to update data in Hashmap and insert new data to database  *********************/
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
                    rate.setPropriety(getPropriety(entry.getKey()));
                    rates.add(rate);

                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            /******Insert Rate to database *****/
                            rateDAO.insert(rate);
                        }
                    });
                }
            }
            Collections.sort(rates, new Rate());


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

    /******** this function to get name of currency *********************/
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

    /******** this function to get all currency from database ************/
    public LiveData<ArrayList<Rate>> getAll(){
        RateDAO rateDAO = db.rateDAO();
        MutableLiveData mutableLiveData = new MutableLiveData();
        mutableLiveData.setValue(rateDAO.getAll());
        return mutableLiveData;
    }

    /******** this function to return list of currency ******************/
    public ArrayList<Rate> getList() {
        return rates;
    }

    /******** this function to return value of currency from HashMap *****/
    public String getValueOfMap(String id){
        return data.get(id);
    }


    private int getPropriety(String id){
        int prop =3;
        if(id.equals("USD") || id.equals("EUR")  )
        {
            prop = 0;
        }
        if(id.equals("TND") || id.equals("CAD") || id.equals("SAR") || id.equals("JPY") || id.equals("AUD")){
            prop = 1;
        }
        if(id.equals("KRW") || id.equals("CAD") || id.equals("SAR")  || id.equals("EGP") || id.equals("BRL")){
            prop = 2;
        }
        return prop;
    }

}
