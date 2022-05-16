package com.example.rates.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rates.R;

import java.util.ArrayList;
import java.util.Locale;

public class ChangeBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_base);

        Button back_btn = findViewById(R.id.btn_back);
        Button change_base = findViewById(R.id.change_base);
        EditText rate = findViewById(R.id.language);
        TextView label_lang = findViewById(R.id.label_lang);


        change_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Context context =  LocaleHelper.setLocale(ChangeBaseActivity.this, "fr");
//                Resources resources = context.getResources();
//
//                label_lang.setText(resources.getString(R.string.setting));

                Locale myLocale = new Locale(rate.getText().toString().toLowerCase(Locale.ROOT).substring(0,2));
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                Intent refresh = new Intent(ChangeBaseActivity.this, MainActivity.class);
                finish();
                startActivity(refresh);



            }
        });

        back_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
            finish();




            }
        });
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.language);
        String langs[] = new String[] {"France","English","عربي"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item,
               langs);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.language);

        textView.setAdapter(adapter);

    }
}