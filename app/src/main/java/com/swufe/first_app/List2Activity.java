package com.swufe.first_app;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class List2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    List<String> data = new ArrayList<String>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);
        ListView listView = (ListView) findViewById(R.id.list1);
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            data.add("item" + i);
        }
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(arrayAdapter);
        listView.setEmptyView(findViewById(R.id.no_data));
        listView.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> listv, View view, int position, long id) {
        arrayAdapter.remove(listv.getItemAtPosition(position));
//        arrayAdapter.notifyDataSetChanged();
    }

}

