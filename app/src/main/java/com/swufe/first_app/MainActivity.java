package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
TextView score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score=(TextView) findViewById(R.id.score);
    }
    public  void btnAdd1(View btn){
        showScore(1);
    }
    public  void btnAdd2(View btn){
        showScore(2);
    }
    public  void btnAdd3(View btn){
        showScore(3);
    }
    public  void btnReset(View btn){
        score.setText("0");
    }
    private void showScore(int inc){
        Log.i("show","inc"+inc);
        String oldscore = (String) score.getText();
        int newScore  = Integer.parseInt(oldscore)+inc;
        score.setText(""+newScore);
    }


}
