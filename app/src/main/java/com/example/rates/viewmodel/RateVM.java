package com.example.rates.viewmodel;

import com.example.rates.view.MainActivity;
import com.example.rates.model.entity.Rate;
import com.example.rates.model.repository.RateRepository;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class RateVM {


    public RateRepository rateRepository;

    public RateVM(MainActivity mainActivity) {
        rateRepository = new RateRepository(mainActivity);
    }

    public void callApi(){
        rateRepository.callApi();
    }

    public ArrayList<Rate> getList(){
        return rateRepository.getList();
    }


    public ArrayList<Rate>  convert(Double baseValue,String base ) {

        Double original_base, temp = 0.0, temp_convert;
        temp = Double.valueOf(rateRepository.getValueOfMap(base));


        ArrayList<Rate> _rate = new ArrayList<Rate>();
        for(Rate rate: getList()){
           original_base = Double.valueOf(rateRepository.getValueOfMap(rate.getId()));
            temp_convert= Double.parseDouble(new DecimalFormat("##.####").format(original_base/temp));
            rate.setValue(String.valueOf(temp_convert*baseValue));
           _rate.add(rate);
        }
       return _rate;
    }

}
