package com.swufe.first_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

public class RateListActivity extends ListActivity {
    String date[] = {"one","two","three"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_rate_list);
        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,date);
        setListAdapter(listAdapter);
    }
}
