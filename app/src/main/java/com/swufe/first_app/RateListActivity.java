package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RateListActivity extends ListActivity implements Runnable{
    Handler handler;
    private String TAG = "Ratelist";
    String date[] = {"wait..."};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        Log.i(TAG, "Run:Run.....");
        Thread thread = new Thread();
        try {
            thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //获取网络数据
        List<String> relist = new ArrayList<String>();
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
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = relist;
        handler.sendMessage(msg);
    }
}
