package com.example.rates.viewmodel;

import android.util.Log;


import androidx.lifecycle.ViewModel;

import com.example.rates.view.MainActivity;
import com.example.rates.model.entity.Rate;
import com.example.rates.model.repository.RateRepository;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class RateVM extends ViewModel {


    public RateRepository rateRepository;
    MainActivity _mainActivity;
    public RateVM(MainActivity mainActivity) {
        _mainActivity = mainActivity;
        rateRepository = new RateRepository(_mainActivity);
    }

    public void callApi(){
        rateRepository.callApi();
    }

    public ArrayList<Rate> getList(){
        return rateRepository.getList();
    }


    public ArrayList<Rate>  convert(Double baseValue,String base ) {

        Double original_base, temp = 0.0, temp_convert;
        ArrayList<Rate> _rate = new ArrayList<Rate>();
//        ArrayList<Rate> _rates = new ArrayList<Rate>();
//           rateRepository.getAll();
//           Log.println(Log.ASSERT,"",rateRepository.getValue(base).getValue());

         temp = Double.valueOf(String.valueOf(rateRepository.getValue(base).getValue()));




        for(Rate rate: getList()){
           original_base = Double.valueOf(String.valueOf(rateRepository.getValue(rate.getId()).getValue()));
            temp_convert= Double.parseDouble(new DecimalFormat("##.####").format(original_base/temp));
            rate.setValue(String.valueOf(temp_convert*baseValue));
           _rate.add(rate);
        }
       return _rate;
    }

}
