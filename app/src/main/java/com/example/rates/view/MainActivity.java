package com.example.rates.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

    private Boolean ifGetData = false, exit = false, ifInAllCurrency = true;
    private TextView txtDate, txtAllCurrency, txtCurrency, txtCrypto;
    private EditText base, rate;
    private AutoCompleteTextView autoCompleteTextView;
    private Button btnConvert;
    private RateAdapter rateAdapter;
    private AlertDialog builder;
    private RateVM rateVM;
    private SwipeRefreshLayout swipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        hideSystemUI();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
//        }
        setContentView(R.layout.rate_activity);

        rateVM = new RateVM(this);

        autoCompleteTextView = findViewById(R.id.menu);
        base = findViewById(R.id.base);
        rate = findViewById(R.id.menu);
        btnConvert = findViewById(R.id.btn_convert);
        txtDate = findViewById(R.id.date);
        txtAllCurrency = findViewById(R.id.all_currency);
        txtCurrency = findViewById(R.id.currency);
        txtCrypto = findViewById(R.id.cypto);
        swipeRefresh = findViewById(R.id.swiperefresh);
        txtDate = findViewById(R.id.date);

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
                if (base.getText().toString().length() != 0 || rate.getText().toString().length() != 0) {


                    Double _base = 1.0;
                    String _rate = "USD";
                    if (base.getText().toString().length() != 0) {
                        _base = Double.valueOf(base.getText().toString());
                    }
                    if (rate.getText().toString().length() != 0) {
                        _rate = rate.getText().toString();
                    }
                    if (ifInAllCurrency == true) {
                        ifGetData = false;
                    }
                    updateUI(rateVM.convert(_base, _rate));

                }
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);


            }
        });

//        btnSetting.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, ChangeBaseActivity.class);
//                intent.putExtra("codes", codesRate);
//                MainActivity.this.startActivity(intent);
//
//
//            }
//        });
        /********* On refresh List view *******************************/
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        rateVM = new RateVM(MainActivity.this);

                        swipeRefresh.setRefreshing(false);
                        long millis = System.currentTimeMillis();
                        java.util.Date date = new java.util.Date(millis);
                        ifGetData = false;
                        txtDate.setText(date.toString().substring(0, 4) + getMonth(date.toString().substring(4, 7)) + " " + date.toString().substring(8, 19) + " " + Calendar.getInstance().get(Calendar.YEAR));
                        rateVM.callApi();

                    }
                }, 1500);


            }
        });

        /********* Filter list only currency **************************/

        txtCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifGetData = false;
                ifInAllCurrency = false;
                updateUI(rateVM.filter("rate"));
                txtCurrency.setTextSize(16);
                txtCrypto.setTextSize(13);
                txtAllCurrency.setTextSize(13);

                txtCurrency.setTextColor(getResources().getColor(R.color.purple_200));
                txtAllCurrency.setTextColor(getResources().getColor(R.color.black_2));
                txtCrypto.setTextColor(getResources().getColor(R.color.black_2));


            }
        });

        /********* Display all list **************************/

        txtAllCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifGetData = false;
                ifInAllCurrency = true;
                updateUI(rateVM.getList());
                txtCurrency.setTextSize(13);
                txtCrypto.setTextSize(13);
                txtAllCurrency.setTextSize(16);

                txtCurrency.setTextColor(getResources().getColor(R.color.black_2));
                txtAllCurrency.setTextColor(getResources().getColor(R.color.purple_200));
                txtCrypto.setTextColor(getResources().getColor(R.color.black_2));

            }
        });

        /********* Filter list only crypto **************************/

        txtCrypto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ifGetData = false;
                ifInAllCurrency = false;
                txtCurrency.setTextSize(13);
                txtCrypto.setTextSize(16);
                txtAllCurrency.setTextSize(13);


                updateUI(rateVM.filter("crypto"));
                txtCurrency.setTextColor(getResources().getColor(R.color.black_2));
                txtAllCurrency.setTextColor(getResources().getColor(R.color.black_2));
                txtCrypto.setTextColor(getResources().getColor(R.color.purple_200));
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
        Log.println(Log.ASSERT, "", String.valueOf(rates.size()));
        if(rates.size() > 0){
            Log.println(Log.ASSERT, "", "pokpop");

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

    }

    /********* Display Alert if no Connection and no database *******************************/
    public void showAlertIfNoConnection() {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this,
                androidx.appcompat.R.style.Base_Theme_AppCompat_Light_Dialog_Alert);
        builder.setIcon(R.drawable.ic_no_wifi);
        builder.setTitle("Warning ");
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

    private void hideSystemUI() {

//        getWindow().getDecorView().setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}



