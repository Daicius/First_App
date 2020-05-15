package com.swufe.first_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Mylist2Activity extends ListActivity implements Runnable, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {
    Handler handler;
    String TAG = "Mylist2Activity";
    private List<HashMap<String,String>> listItem;
    private SimpleAdapter ListItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initListView();
//        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, listItem);
//        this.setListAdapter(myAdapter);
        this.setListAdapter(ListItemAdapter);
        Thread thread = new Thread(this);
        thread.start();
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                            listItem = (List<HashMap<String, String>>) msg.obj;
                            ListItemAdapter = new SimpleAdapter(Mylist2Activity.this,listItem,R.layout.list_item,
                                    new String[]{"ItemTitle","ItemDetail"},
                                    new int[]{R.id.itemTitle,R.id.itemDetail});
                            setListAdapter(ListItemAdapter);
                }
                super.handleMessage(msg);

            }
        };
getListView().setOnItemClickListener(this);
getListView().setOnItemLongClickListener(this);
    }
    private void initListView(){
        listItem = new ArrayList<HashMap<String, String>>();
        for(int i = 0;i <10 ;i++){
            HashMap<String,String> map = new HashMap<String, String>();
            map.put("ItemTitle","Rate:"+i);
            map.put("ItemDetail","detail"+i);
            listItem.add(map);
        }
        ListItemAdapter = new SimpleAdapter(this,listItem,
                R.layout.list_item,
                new String[]{"ItemTitle","ItemDetail"},
                new int[]{R.id.itemTitle,R.id.itemDetail}
        );
    }

    @Override
    public void run() {
        Log.i(TAG, "Run:Run.....");
        Document doc = null;
        List<HashMap<String,String>> realist = new ArrayList<HashMap<String, String>>();
        try {
            doc = Jsoup.connect("https://www.boc.cn/sourcedb/whpj/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements tables = doc.getElementsByTag("table");
        Element table6 = tables.get(1);
        Elements tds = table6.getElementsByTag("td");
        for (int i = 0; i < tds.size(); i += 8) {
            Element td1 = tds.get(i);
            Element td5 = tds.get(i + 5);
            String str1 = td1.text();
            String str2 = td5.text();
//            Log.i(TAG, "text=" + str1);
//            Log.i(TAG, "val=" + str2);
            HashMap<String,String> hashMap = new HashMap<String, String>();
            hashMap.put("ItemTitle",str1);
            hashMap.put("ItemDetail",str2);
            realist.add(hashMap);
            }
        Log.i(TAG,"GET_MSG");
        Message msg = handler.obtainMessage(5);
        msg.obj = realist;
        handler.sendMessage(msg);
        }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> hashMap = (HashMap<String, String>) getListView().getItemAtPosition(position);
        String titleStr = hashMap.get("ItemTitle");
        String detailStr = hashMap.get("ItemDetail");
        TextView title = view.findViewById(R.id.itemTitle);
        TextView detail = view.findViewById(R.id.itemDetail);
        String title2 = String.valueOf(title.getText());
        String detail2 = String.valueOf(detail.getText());
        //打开界面
        Intent intent = new Intent(this,RateCalcActivity.class);
        intent.putExtra("Title",titleStr);
        intent.putExtra("Detail",Double.parseDouble(detailStr)/100.0);

        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view,final int position, long id) {
        Log.i(TAG,"on_long_click");
        //删除操作
//        listItem.remove(position);
//        ListItemAdapter.notifyDataSetChanged();
        //构造对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("是否删除").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                     listItem.remove(position);
        ListItemAdapter.notifyDataSetChanged();
            }
        }).
                setNegativeButton("否",null);
        builder.create().show();
        Log.i(TAG,"size"+listItem.size());
        return true;
    }
}