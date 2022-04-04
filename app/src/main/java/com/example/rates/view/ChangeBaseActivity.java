package com.example.rates.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.example.rates.R;

import java.util.ArrayList;

public class ChangeBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_base);

        Button back_btn = findViewById(R.id.btn_back);

        back_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
               finish();

            }
        });
        AutoCompleteTextView autoCompleteTextView = findViewById(R.id.menu);
        ArrayList<String> codesRate = (ArrayList<String>) getIntent().getSerializableExtra("codes");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item,
                codesRate);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.menu);

        textView.setAdapter(adapter);

    }
}