package com.example.rates.view;

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

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    Boolean ifGetData = false, exit = false;


    TextView txtDate;
    RateAdapter rateAdapter;
    AlertDialog builder;
    ArrayList<String> codesRate;
    RateVM rateVM;
    private SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_activity);

        rateVM = new RateVM(this);

        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.menu);
        EditText base = findViewById(R.id.base);
        EditText rate = findViewById(R.id.menu);
        ImageButton btnSetting = findViewById(R.id.setting);
        Button btnConvert = findViewById(R.id.btn_convert);
        txtDate = findViewById(R.id.date);
        swipeRefresh = findViewById(R.id.swiperefresh);
        TextView txtDate = findViewById(R.id.date);
        long millis = System.currentTimeMillis();

        /********* Show Progress Bar when callApi   ****************/
        builder = new AlertDialog.Builder(this)
                .setView(R.layout.loading_alert)
                .setCancelable(false)
                .show();


        /********* get Data from API *****************************/
        rateVM.callApi();


        /********* get current Date *******************************/
        java.util.Date date = new java.util.Date(millis);
        txtDate.setText(date.toString().substring(0, 4) + getMonth(date.toString().substring(4, 7)) + " " + date.toString().substring(8, 19) + " " + Calendar.getInstance().get(Calendar.YEAR));

        /********* On click of button convert *******************************/
        btnConvert.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Double _base = Double.valueOf(base.getText().toString());
                String _rate = rate.getText().toString();

                updateUI(rateVM.convert(_base, _rate));


            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeBaseActivity.class);
                intent.putExtra("codes", codesRate);
                MainActivity.this.startActivity(intent);


            }
        });
        /********* On refresh List view *******************************/
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rateVM = new RateVM(MainActivity.this);
                        rateVM.callApi();
                        swipeRefresh.setRefreshing(false);
                        long millis = System.currentTimeMillis();
                        java.util.Date date = new java.util.Date(millis);

                        txtDate.setText(date.toString().substring(0, 4) + getMonth(date.toString().substring(4, 7)) + " " + date.toString().substring(8, 19) + " " + Calendar.getInstance().get(Calendar.YEAR));


                    }
                }, 1000);


            }
        });
    }

    /********* Show Code of the rate in AutoCompleteTextVIew *******************************/
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

    /********* Send data to RateAdapter to show list of rates *******************************/
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

    /********* Display Alert if no Connection and no database *******************************/
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

    /********* return the complete name of month *******************************/
    public static String getMonth(String month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String _month = "";
        switch (month) {
            case "Jan":
                _month = monthNames[0];
                break;
            case "Feb":
                _month = monthNames[1];
                break;
            case "Mar":
                _month = monthNames[2];
                break;
            case "Apr":
                _month = monthNames[3];
                break;
            case "May":
                _month = monthNames[4];
                break;
            case "Jun":
                _month = monthNames[5];
                break;
            case "Lul":
                _month = monthNames[6];
                break;
            case "Aug":
                _month = monthNames[7];
                break;
            case "Sep":
                _month = monthNames[8];
                break;
            case "Oct":
                _month = monthNames[9];
                break;
            case "Nov":
                _month = monthNames[10];
                break;
            case "Dec":
                _month = monthNames[11];
                break;
        }
        return _month;
    }

    public TextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(String txtDate) {
        this.txtDate.setText(txtDate);
    }


}



