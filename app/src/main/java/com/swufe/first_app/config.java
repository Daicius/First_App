package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class config extends AppCompatActivity {
public final String TAG = "config";
    EditText USD;
    EditText JPY;
    EditText THB;
    EditText GBP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);
        Intent intent = getIntent();
        double USD_rate2 = intent.getDoubleExtra("USD",0);
        double JPY_rate2 = intent.getDoubleExtra("JPY",0);
        double GBP_rate2 = intent.getDoubleExtra("GBP",0);
        double THB_rate2 = intent.getDoubleExtra("THB",0);
        Log.i(TAG,"oncreat_USD"+USD_rate2);
        Log.i(TAG,"oncreat_JPY"+JPY_rate2);
        Log.i(TAG,"oncreat_THB"+THB_rate2);
        Log.i(TAG,"oncreat_GBP"+GBP_rate2);
        USD = findViewById(R.id.USD2);
        JPY = findViewById(R.id.JPY2);
        GBP = findViewById(R.id.GBP2);
        THB = findViewById(R.id.THB2);
        USD.setText(""+USD_rate2);
        JPY.setText(""+JPY_rate2);
        GBP.setText(""+GBP_rate2);
        THB.setText(""+THB_rate2);


    }
    public void save(View btn){
        Log.i(TAG,"save_successful");
        double new_USD_rate = Double.parseDouble(USD.getText().toString());
        double new_JPY_rate = Double.parseDouble(JPY.getText().toString());
        double new_GBP_rate = Double.parseDouble(GBP.getText().toString());
        double new_THB_rate = Double.parseDouble(THB.getText().toString());
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putDouble("new_GBP_rate",new_GBP_rate);
        bundle.putDouble("new_USD_rate",new_USD_rate);
        bundle.putDouble("new_THB_rate",new_THB_rate);
        bundle.putDouble("new_JPY_rate",new_JPY_rate);
        intent.putExtras(bundle);
        setResult(2,intent);
        finish();
    }

}
