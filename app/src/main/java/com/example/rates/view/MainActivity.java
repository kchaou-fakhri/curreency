package com.example.rates.view;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.rates.R;
import com.example.rates.model.entity.Rate;
import com.example.rates.viewmodel.RateVM;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.text.DateFormatSymbols;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    Boolean ifGetData = false, exit = false;


    TextView txtDate;
    RateAdapter rateAdapter;
    AlertDialog builder;
    ArrayList<String> codesRate;
    RateVM rateVM;
    private SwipeRefreshLayout swipeRefrech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_activity);


        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.menu);

        EditText base = findViewById(R.id.base);
        EditText rate = findViewById(R.id.menu);

        Button btnConvert = findViewById(R.id.btn_convert);

        builder = new AlertDialog.Builder(this)
                .setView(R.layout.loading_alert)
                .setCancelable(false)
                .show();
        txtDate = findViewById(R.id.date);
        swipeRefrech = findViewById(R.id.swiperefresh);

        swipeRefrech.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rateVM = new RateVM(MainActivity.this);
                        rateVM.callApi();
                        swipeRefrech.setRefreshing(false);
                        long millis = System.currentTimeMillis();
                        java.util.Date date = new java.util.Date(millis);

                        txtDate.setText(date.toString().substring(0, 19) + " " + date.toString().substring(23));


                    }
                }, 1000);

                //  swipeRefrech.setRefreshing(false);
            }
        });

        rateVM = new RateVM(this);
        rateVM.callApi();
        TextView txtDate = findViewById(R.id.date);


        long millis = System.currentTimeMillis();

        // creating a new object of the class Date
        java.util.Date date = new java.util.Date(millis);

        txtDate.setText(date.toString().substring(0, 19) + " " + date.toString().substring(23));


        btnConvert.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Double _base = Double.valueOf(base.getText().toString());
                String _rate = rate.getText().toString();

                updateUI(rateVM.convert(_base, _rate));


            }
        });


        ImageButton btnSetting = findViewById(R.id.setting);

        btnSetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeBaseActivity.class);
                intent.putExtra("codes", codesRate);
                MainActivity.this.startActivity(intent);


            }
        });
    }

    public void showRates(ArrayList<Rate> list) {

        ArrayList<String> _list = new ArrayList<String>();

        for (Rate item : list) {
            _list.add(item.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line,
                _list);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.menu);

        textView.setAdapter(adapter);

    }

    public void updateUI(ArrayList<Rate> rates) {
        builder.dismiss();

        if (ifGetData == false) {
            RecyclerView recyclerView = findViewById(R.id.rateRecyclerView);

            rateAdapter = new RateAdapter(rates);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(rateAdapter);

            ifGetData = true;
        } else {
            rateAdapter.notifyDataSetChanged();
        }

    }

    public void showAlertIfNoConnection() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
        builder.setIcon(R.drawable.ic_no_wifi);
        builder.setTitle("Sorry !");
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

    public TextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate.setText(txtDate);
    }


}



