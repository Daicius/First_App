package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class temperature extends AppCompatActivity {
TextView F;
EditText C;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);
       F = (TextView)findViewById(R.id.tv1);
       C = (EditText)findViewById(R.id.et1);
    }
    public void btnChange(View btn){
        changeTemperature();
    }
    private void changeTemperature(){
        Log.i("show","cen");
        String c = C.getText().toString();
        double centidegree = Double.parseDouble(c);
        double Fahrenhei = centidegree*1.8 + 32;
        F.setText(""+Fahrenhei);
        Log.i("show","cen"+centidegree);
    }

}
