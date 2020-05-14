package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class RateCalcActivity extends AppCompatActivity {
String TAG = "RateCalc";
Double rate ;
String title;
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_calc);
        rate = getIntent().getDoubleExtra("Detail",0);
        title = getIntent().getStringExtra("Title");
        Log.i(TAG,"rate ="+rate);
        ((TextView)findViewById(R.id.title2)).setText(title);
        editText = (EditText)findViewById(R.id.input);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                TextView show = (TextView)RateCalcActivity.this.findViewById(R.id.show);
                if (show.length() > 0){
                    double val = Double.parseDouble(s.toString());
                    show.setText(val+title+"==>"+"CHN"+val*rate);
                }else {
                    show.setText(" ");
                }
            }
        });
    }
}
