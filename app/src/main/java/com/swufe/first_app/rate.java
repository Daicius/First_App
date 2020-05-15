package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class rate extends AppCompatActivity implements Runnable{
    TextView forignMoney;
    EditText myMoney;
    double JPY_rate = 0.06422;
    double USD_rate = 7.095;
    double THB_rate = 0.2167;
    double GBP_rate = 8.434;
    String updateDate = "";
    String TAG = "rate";
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        forignMoney = findViewById(R.id.tv_forign);
        myMoney = findViewById(R.id.et_imput);
        SharedPreferences sharedPreferences = getSharedPreferences("myrate", Activity.MODE_PRIVATE);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        USD_rate = Double.parseDouble(sharedPreferences.getString("USD_rate", "0"));
        JPY_rate = Double.parseDouble(sharedPreferences.getString("JPY_rate", "0"));
        GBP_rate = Double.parseDouble(sharedPreferences.getString("GBP_rate", "0"));
        THB_rate = Double.parseDouble(sharedPreferences.getString("THB_rate", "0"));
        updateDate = sharedPreferences.getString("update_date","");
        Log.i(TAG, "onCreat_USD_rate" + USD_rate);
        Log.i(TAG, "onCreat_GBP_rate" + GBP_rate);
        Log.i(TAG, "onCreat_JPY_rate" + JPY_rate);
        Log.i(TAG, "onCreat_THB_rate" + THB_rate);
        //获取当前时间
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String todayStr = simpleDateFormat.format(today);
        Log.i(TAG,"当前时间"+todayStr);
        //判断日期是否相同
        if (!todayStr.equals(updateDate)) {
            Log.i(TAG, "onCreate: need updates");
            //开启子线程
            Thread t = new Thread(this);
            t.start();
        } else {
            Log.i(TAG, "onCreate:don't need updates");
        }
            handler = new Handler() {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    if (msg.what == 5) {
                        Bundle bd1 = (Bundle) msg.obj;
                        USD_rate = bd1.getDouble("USD_rate");
                        JPY_rate = bd1.getDouble("JPY_rate");
                        THB_rate = bd1.getDouble("THB_rate");
                        GBP_rate = bd1.getDouble("GBP_rate");
                        updateDate = bd1.getString("update_date");

                        Log.i(TAG, "handlermessage" + USD_rate);
                        Log.i(TAG, "handlermessage" + JPY_rate);
                        Log.i(TAG, "handlermessage" + GBP_rate);
                        Log.i(TAG, "handlermessage" + THB_rate);
                        Log.i(TAG, "handlermessage" + updateDate);
                        Toast.makeText(rate.this, "汇率更新", Toast.LENGTH_SHORT).show();
                    }
                    super.handleMessage(msg);

                }
            };
        //保存更新的日期
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("update_date", todayStr);
        editor.commit();
    }

//判断输入合法性
    public static boolean isNumber(String str) {
        String reg = "^[0-9]+(.[0-9]+)?$";
        return str.matches(reg);
    }

    public double Exchange(String str) {
        if (myMoney.getText().length() == 0) {
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
                Toast.makeText(this,"请输入数字",Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
    }

    public void btnUSD(View btn) {
        Log.i(TAG,"onclick:USD");
        double usd=Exchange("USD");
        forignMoney.setText(""+usd);

    }

    public void btnJPY(View btn) {
        Log.i(TAG,"onclick:JPY");
       double jap = Exchange("JPY");
        forignMoney.setText(""+jap);
    }

    public void btnGBP(View btn) {
        Log.i(TAG,"onclick:GBP");
        double gbp = Exchange("GBP");
        forignMoney.setText(""+gbp);
    }

    public void btnTHB(View btn) {
        Log.i(TAG,"onclick:THB");
        double thb = Exchange("THB");
        forignMoney.setText(""+thb);
    }
    public void btnSwitchConfig(View btn){
      OpenConfig();
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
        if(item.getItemId() == R.id.setting){
            OpenConfig();
        }else if(item.getItemId() == R.id.Open_list){
            Intent list = new Intent(this,Mylist2Activity.class);
            startActivity(list);
            Log.i(TAG,"open_new");
        }else  if (item.getItemId() == R.id.test){
            Intent list = new Intent(this,List2Activity.class);
            startActivity(list);
        }
         return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.rate,menu);
            return true;
        }
        public  void OpenConfig(){
            Log.i("open","open1");
            Intent config = new Intent(this,config.class);
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
    private String inputStream2String(InputStream inputStream) throws IOException {
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        Reader in = new InputStreamReader(inputStream, "utf-8");
        int charsRead;
        while((charsRead = in.read(buffer, 0, buffer.length)) > 0) {
            out.append(buffer, 0, charsRead);
        }
        return out.toString();
    }


    @Override
    public void run() {
        Log.i(TAG, "Run:Run.....");
//        for (int i = 1; i < 6; i++) {
//            Log.i(TAG, "i:" + i);
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
        Bundle bundle = new Bundle();
//        URL url = null;
//        try {
//            url = new URL("https://www.boc.cn/sourcedb/whpj/");
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            InputStream inputStream = httpURLConnection.getInputStream();
//            String html = inputStream2String(inputStream);
//            Log.i(TAG,"html:"+html);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.i(TAG, doc.title());
        Elements tables = doc.getElementsByTag("table");

//        for (Element table : tables) {
//            Log.i(TAG,"run:tables"+table);
//        }
        Element table6 = tables.get(1);
//        Log.i(TAG,"table6 = "+table6);
        Elements tds = table6.getElementsByTag("td");
        for(int i = 0;i < tds.size();i+=8){
            Element td1 = tds.get(i);
            Element td5 = tds.get(i+5);
            String str1 = td1.text();
            String str2 = td5.text();
            Log.i(TAG,"text="+str1);
            Log.i(TAG, "val="+str2);
            if(str1.equals("英镑")){
                bundle.putDouble("GBP_rate",Double.parseDouble(str2)/100.0);
            }else if(str1.equals("泰国铢")){
                bundle.putDouble("THB_rate",Double.parseDouble(str2)/100.0);
            }else if(str1.equals("美元")){
                bundle.putDouble("USD_rate",Double.parseDouble(str2)/100.0);
            }else if(str1.equals("日元")){
                bundle.putDouble("JPY_rate",Double.parseDouble(str2)/100.0);
            }
        }
        Message msg = handler.obtainMessage(5);
        msg.obj = bundle;
        handler.sendMessage(msg);
//        for(Element td : tds) {
//            Log.i(TAG, "tds = " + td);
//            Log.i(TAG,"text="+td.text());
//            Log.i(TAG,"html="+td.html());
//        }
    }
}


