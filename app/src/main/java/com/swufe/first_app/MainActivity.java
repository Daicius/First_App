package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public final String TAG = "MainActivity";
    TextView score;
    TextView score2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        score=(TextView) findViewById(R.id.score);
        score2=(TextView) findViewById(R.id.score2);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG,"onStart:");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG,"onRsume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG,"onRestart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG,"onStop");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        String scorea = score.getText().toString();
        String scoreb = score2.getText().toString();
        outState.putString("teama_score",scorea);
        outState.putString("teamb_score",scoreb);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String scorea = savedInstanceState.getString("teama_score","0");
        String scoreb = savedInstanceState.getString("teamb_score","0");
        score.setText(scorea);
        score2.setText(scoreb);
    }

    public  void btnAdd1(View btn){
        if(btn.getId()==R.id.btn_1){ showScore(1);
            }
        else{ showScore2(1);
        }
    }
    public  void btnAdd2(View btn){
        if(btn.getId()==R.id.btn_2){ showScore(2);
        }
        else{ showScore2(2);
        }
    }
    public  void btnAdd3(View btn){
        if(btn.getId()==R.id.btn_3){ showScore(3);
        }
        else{ showScore2(3);
        }
    }
    public  void btnReset(View btn){
        score.setText("0");
        score2.setText("0");
    }
    private void showScore(int inc){
        Log.i("show","inc"+inc);
        String oldscore = (String) score.getText();
        int newScore  = Integer.parseInt(oldscore)+inc;
        score.setText(""+newScore);
    }
    private void showScore2(int inc){
        Log.i("show","inc"+inc);
        String oldscore = (String) score2.getText();
        int newScore  = Integer.parseInt(oldscore)+inc;
        score2.setText(""+newScore);
    }

}
