package com.example.rates;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rates.model.Rate;
import com.example.rates.viewmodel.RateVM;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rate_activity);
        RateVM rateVM = new RateVM(this);

        Thread t = new Thread() {
            public void run() {
                while(true){


                    rateVM.getData();
                    try {
                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        };
        t.start();




        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.menu);








            EditText base = findViewById(R.id.base);
            EditText rate = findViewById(R.id.menu);

           ImageButton btnConvert = findViewById(R.id.btn_convert);


        btnConvert.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
        Log.e("Main","-------------------------"+list.get(1).getValue()+ " "+list.size());

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
        RecyclerView recyclerView = findViewById(R.id.rateRecyclerView);

        RateAdapter rateAdapter = new RateAdapter(rates);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(rateAdapter);
    }

}