package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class rate extends AppCompatActivity {
    TextView forignMoney;
    EditText myMoney;
    double JPY_rate = 0.06422;
    double USD_rate = 7.095;
    double THB_rate = 0.2167;
    double GBP_rate = 8.434;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        forignMoney = findViewById(R.id.tv_forign);
        myMoney = findViewById(R.id.et_imput);
    }

    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public double Exchange(String str) {
        if (myMoney.getText() == null) {
            return 0;
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

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.setting){
            OpenNew();
        }
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

