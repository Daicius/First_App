package com.swufe.first_app;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class RateManager {
    private DBHelper dbHelper;
    private String TBNAME;

    public RateManager(Context context){
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }
    public void add(RateItem rateItem){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("currate",rateItem.getCurRate());
        values.put("curname",rateItem.getCurName());
        db.insert(TBNAME,null,values);
        db.close();
    }
    public void addAll(List<RateItem> items){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (RateItem item : items){
            ContentValues values = new ContentValues();
            values.put("currate",item.getCurRate());
            values.put("curname",item.getCurName());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    public List<RateItem> listAll(){
        List<RateItem> rateItemList = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
        if (cursor != null){
            rateItemList = new ArrayList<RateItem>();
            while (cursor.moveToNext()){
                RateItem item = new RateItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                item.setCurRate(cursor.getString(cursor.getColumnIndex("CURRATE")));
                rateItemList.add(item);
            }
            cursor.close();
        }
        db.close();
        return rateItemList;
    }



}
