package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Pattern;

import javax.security.auth.login.LoginException;

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
    public static boolean isNumber(String str){
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }
    private void changeTemperature(){
        String c = C.getText().toString();
        if(isNumber(c)&&c!=null) {
            double centidegree = Double.parseDouble(c);
            double Fahrenhei = centidegree * 1.8 + 32;
            F.setText("" + Fahrenhei);
            Log.i("show", "cen" + centidegree);
        }else{
            throw new NumberFormatException("请输入数字");
        }
    }
    public void btnSwitch1(View btn){
        Log.i("open","open1");
        Intent hello=new Intent(this,MainActivity.class);
        startActivity(hello);
    }

}
