package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class rate extends AppCompatActivity {
    TextView forignMoney;
    EditText myMoney;
    double JPY_rate = 0.06422;
    double USD_rate = 7.095;
    double THB_rate = 0.2167;
    double GBP_rate = 8.434;
    String TAG = "rate";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        forignMoney = findViewById(R.id.tv_forign);
        myMoney = findViewById(R.id.et_imput);
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
         USD_rate = Double.parseDouble(sharedPreferences.getString("USD_rate","0"));
         JPY_rate = Double.parseDouble(sharedPreferences.getString("JPY_rate","0"));
         GBP_rate = Double.parseDouble(sharedPreferences.getString("GBP_rate","0"));
         THB_rate = Double.parseDouble(sharedPreferences.getString("THB_rate","0"));
         Log.i(TAG,"onCreat_USD_rate"+USD_rate);
         Log.i(TAG,"onCreat_GBP_rate"+GBP_rate);
         Log.i(TAG,"onCreat_JPY_rate"+JPY_rate);
         Log.i(TAG,"onCreat_THB_rate"+THB_rate);
    }

    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public double Exchange(String str) {
        if (myMoney.getText() == null|| myMoney.getText().equals("")) {
            Toast.makeText(this,"请输入数字",Toast.LENGTH_SHORT).show();
            return  0;
        } else {
            String chn = myMoney.getText().toString();
            if (isNumber(chn)) {
                double CHN = Double.parseDouble(chn);
                if (str.equals("USD")) {
                    return CHN * USD_rate;
                } else if (str.equals("GBP")) {
                    return CHN * GBP_rate;
                } else if (str.equals("JPY")) {
                    return CHN * JPY_rate;
                } else {
                    return CHN * THB_rate;
                }
            } else {
                throw new NumberFormatException("请输入数字");
            }
        }
    }

    public void btnUSD(View btn) {
        double usd=Exchange("USD");
        forignMoney.setText(""+usd);
    }

    public void btnJPY(View btn) {
       double jap = Exchange("JPY");
        forignMoney.setText(""+jap);
    }

    public void btnGBP(View btn) {
        double gbp = Exchange("GBP");
        forignMoney.setText(""+gbp);
    }

    public void btnTHB(View btn) {
        double thb = Exchange("THB");
        forignMoney.setText(""+thb);
    }
    public void btnSwitchconfig(View btn){
      OpenNew();
    }


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            Bundle bundle = data.getExtras();
            USD_rate = bundle.getDouble("new_USD_rate",0);
            JPY_rate = bundle.getDouble("new_JPY_rate",0);
            THB_rate = bundle.getDouble("new_THB_rate",0);
            GBP_rate = bundle.getDouble("new_GBP_rate",0);
            Log.i("USD_new",""+USD_rate);
            Log.i("JPY_new",""+JPY_rate);
            Log.i("GPB_new",""+GBP_rate);
            Log.i("THB_new",""+THB_rate);
            SharedPreferences sharedPreferences = getSharedPreferences("myrate",Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("USD_rate", String.valueOf(USD_rate));
            editor.putString("JPY_rate", String.valueOf(JPY_rate));
            editor.putString("GBP_rate", String.valueOf(GBP_rate));
            editor.putString("THB_rate", String.valueOf(THB_rate));
            editor.commit();
            Log.i(TAG,"保存成功");
            Log.i(TAG,"onCreate USD_rate"+USD_rate);
            Log.i(TAG,"onCreate JPY_rate"+JPY_rate);
            Log.i(TAG,"onCreate GBP_rate"+GBP_rate);
            Log.i(TAG,"onCreate THB_rate"+THB_rate);

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
         return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }
    public  void OpenNew(){
        Log.i("open","open1");
        Intent config=new Intent(this,config.class);
        config.putExtra("USD",USD_rate);
        config.putExtra("JPY",JPY_rate);
        config.putExtra("GBP",GBP_rate);
        config.putExtra("THB",THB_rate);
        Log.i("USD",""+USD_rate);
        Log.i("JPY",""+JPY_rate);
        Log.i("GBP",""+GBP_rate);
        Log.i("THB",""+THB_rate);
//        startActivity(config);
        startActivityForResult(config,1);
    }

}

