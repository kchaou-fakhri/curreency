package com.example.rates.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rates.R;
import com.example.rates.model.entity.Rate;
import com.example.rates.viewmodel.RateVM;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Boolean ifGetData = false, exit = false;
    RateAdapter rateAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rate_activity);




        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.menu);

        EditText base = findViewById(R.id.base);
        EditText rate = findViewById(R.id.menu);

        Button btnConvert = findViewById(R.id.btn_convert);


        RateVM rateVM = new RateVM(this);

        Thread  t= new Thread() {
            public void run() {
                while(!exit){
                    try {
                        rateVM.callApi();
                        Thread.sleep(5000);


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        t.start();







        btnConvert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                exit = true;
               Double _base = Double.valueOf(base.getText().toString());
               String _rate = rate.getText().toString();
               updateUI( rateVM.convert(_base, _rate) );



            }
        });


//        Methods methods = RetrofitClient.getInstance().create(Methods.class);
//        Call<Rate> call = methods.getAllData();


    }




    public void showRates(ArrayList<Rate> list){

        ArrayList<String> _list = new ArrayList<String>();

        for (Rate item: list) {
            _list.add(item.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                _list);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.menu);

        textView.setAdapter(adapter);

    }

    public void updateUI(ArrayList<Rate> rates){

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





}