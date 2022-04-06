package com.example.rates.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.LogPrinter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.rates.R;
import com.example.rates.model.entity.Rate;
import com.example.rates.viewmodel.RateVM;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    Boolean ifGetData = false, exit = false;
    RateAdapter rateAdapter;
    AlertDialog builder ;
    ArrayList<String> codesRate;
    String _value;
    String _rate;
    RateVM rateVM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_activity);


        builder = new AlertDialog.Builder(this)
        .setView(R.layout.loading_alert)
        .setCancelable(false)
        .show();

        rateVM = new RateVM(this);
        rateVM.callApi();
        TextView txtDate = findViewById(R.id.date);


        long millis = System.currentTimeMillis();

        // creating a new object of the class Date
        java.util.Date date = new java.util.Date(millis);

        txtDate.setText(date.toString());







        _value = (String) getIntent().getSerializableExtra("value");
        _rate = (String) getIntent().getSerializableExtra("rate");


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.menu);
        ImageButton btnSetting = findViewById(R.id.setting);
        ImageButton btn_convert = findViewById(R.id.btn_convert);

        btn_convert.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View view) {
                //rateVM.callApi();
                updateUI(rateVM.getList());
                long millis = System.currentTimeMillis();
                java.util.Date date = new java.util.Date(millis);

                txtDate.setText(date.toString());


            }
        });





        btnSetting.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeBaseActivity.class);
                intent.putExtra("codes", codesRate);
                MainActivity.this.startActivity(intent);
                finish();

            }
        });
    }

    public void showRates(ArrayList<Rate> list){

        codesRate = new ArrayList<String>();

        for (Rate item: list) {
            codesRate.add(item.getId());
        }
        if(_rate != null || _value != null){

            if(_rate == null){

                ifGetData = false;
                updateUI(rateVM.convert(Double.valueOf(_value), "USD"));
            }
            else if(_value == null){

                ifGetData = false;
                updateUI(rateVM.convert(1.0, _rate));
            }
            else
            ifGetData = false;
            updateUI(rateVM.convert(Double.valueOf(_value), _rate));

        }

    }

    public void updateUI(ArrayList<Rate> rates){
        builder.dismiss();

        if (ifGetData == false){
            RecyclerView recyclerView = findViewById(R.id.rateRecyclerView);

            rateAdapter = new RateAdapter(rates);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(rateAdapter);

            ifGetData =true;
        }
        else {
            rateAdapter.notifyDataSetChanged();
        }

    }

    public void showAlertIfNoConnection(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
       builder.setIcon(R.drawable.ic_no_wifi);
       builder.setTitle("Error");
       builder.setMessage("Check your internet connection and try again.");
       builder.setCancelable(false);
       builder.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               finish();
               startActivity(getIntent());
           }
       });

       builder.show();

    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month;
    }

}



