package com.example.rates.viewmodel;


import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.example.rates.view.MainActivity;
import com.example.rates.model.entity.Rate;
import com.example.rates.model.repository.RateRepository;


import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class RateVM extends ViewModel {

    private RateRepository rateRepository;  /******** Instance of currency *****************************/
    private MainActivity _mainActivity;      /******** Instance of Context *****************************/

    public RateVM(MainActivity mainActivity) {
        _mainActivity = mainActivity;
        rateRepository = new RateRepository(_mainActivity);
    }

    /******** this function to Call methode "callApi" of Repository *********************/
    public void callApi(){
        rateRepository.callApi();
    }

    /******** this function to Call methode "callApi" of Repository *********************/
    public void updateRate(Rate rate){
        rateRepository.updateRate(rate);
    }

    /******** this function to Call methode "getList" of Repository to return list of currency *********************/
    public ArrayList<Rate> getList(){
        return rateRepository.getList();
    }

    /******** this function to convert currency and return list of currency *********************/
    public ArrayList<Rate>  convert(Double baseValue,String base ) {


        Double original_base, temp = 0.0, temp_convert;
        temp = Double.valueOf(rateRepository.getValueOfMap(base));
        ArrayList<Rate> _rate = new ArrayList<Rate>();



        for(Rate rate: getList()){

            original_base = Double.valueOf(rateRepository.getValueOfMap(rate.getId()));
            temp_convert= original_base/temp;
            rate.setValue(String.valueOf(temp_convert*baseValue));
            rate.setPropriety(rateRepository.getPropriety(rate.getId()));
            if (rate.getId().equals(base)){
                rate.setPropriety(-1);
            }
            _rate.add(rate);
            }

        Collections.sort(_rate, new Rate());
        Log.println(Log.ASSERT, "", _rate.toString());
        return _rate;
    }

    public ArrayList<Rate> filter(String type){
        ArrayList<Rate> _rate = new ArrayList<Rate>();

        for (Rate rate : getList()){
            if (rate.getType().equals(type)){
                _rate.add(rate);
            }
        }

        return _rate;
    }
}
