package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    Handler handler;
    private String TAG = "Ratelist";
    String date[] = {"wait..."};
    private String LogDate = "";
    private final String DATE_SP_KEY = "lastRateDateStr";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("myrate", Context.MODE_PRIVATE);
        LogDate = sp.getString(DATE_SP_KEY,"");

        ListAdapter listAdapter = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,date);
        setListAdapter(listAdapter);
        Thread thread = new Thread(this);
        thread.start();
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        final List<String> list1 = new ArrayList<String>();
         handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what == 7){
                   List<String> list2 = (List<String>) msg.obj;
                   ListAdapter listAdapter2 = new ArrayAdapter<String>(RateListActivity.this,android.R.layout.simple_list_item_1,list2);
                   setListAdapter(listAdapter2);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        Thread thread = new Thread();
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, "Run:Run.....");
        List<String> relist = new ArrayList<String>();
        String curDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
        if (curDateStr.equals(LogDate)){
            Log.i(TAG,"日期相等，从数据库获得数据");
            RateManager manager = new RateManager(this);
            for (RateItem item : manager.listAll()){
                relist.add(item.getCurName()+"==>"+item.getCurRate());
            }

        }else {
            Log.i(TAG,"日期不相等，从网络获得数据");
            List<RateItem> Ratelist = new ArrayList<RateItem>();
            Document doc = null;
            try {
                doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements tables = doc.getElementsByTag("table");
            Element table6 = tables.get(1);
            Elements tds = table6.getElementsByTag("td");
            for(int i = 0;i < tds.size();i+=8){
                Element td1 = tds.get(i);
                Element td5 = tds.get(i+5);
                String str1 = td1.text();
                String str2 = td5.text();
                double rate1 = Double.parseDouble(str2)/100.0;
                Log.i(TAG,"text="+str1);
                Log.i(TAG, "val="+rate1);
                relist.add(str1+"=>"+rate1);
                Ratelist.add((new RateItem(str1,str2)));
            }
            RateManager manager = new RateManager(this);
            manager.deleteAll();
            manager.addAll(Ratelist);
            SharedPreferences sp = getSharedPreferences("myrate",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(DATE_SP_KEY,curDateStr);
            editor.commit();
            Log.i(TAG,"日期保存");
        }



        Message msg = handler.obtainMessage(7);
        msg.obj = relist;
        handler.sendMessage(msg);
    }
}
