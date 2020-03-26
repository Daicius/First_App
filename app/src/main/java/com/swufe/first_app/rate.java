package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class rate extends AppCompatActivity {
    TextView forignMoney;
    EditText myMoney;

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
        String chn = myMoney.getText().toString();
        if (isNumber(chn)) {
            double CHN = Double.parseDouble(chn);
            if (str.equals("USD")) {
                return CHN * 7.095;
            } else if (str.equals("GBP")) {
                return CHN * 8.434;
            } else if (str.equals("JPY")) {
                return CHN * 0.06422;
            } else {
                return CHN * 0.2167;
            }
        } else {
            throw new NumberFormatException("请输入数字");
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
}

