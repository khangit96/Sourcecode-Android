package com.example.administrator.demo1_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 11/27/2015.
 */
public class Database extends SQLiteOpenHelper {
    public Database(Context context){
        super(context,"demo.sqlite",null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }
    public Cursor GetData(String sql) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public void Query_Data(String sql){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(sql);
    }
    public void delete_byID(int id,String table,String key_id){
        SQLiteDatabase db=getWritableDatabase();
        db.delete(table, key_id + "=" + id, null);
    }
  /*  public boolean updateContact(long rowId, String name, String email) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_EMAIL, email);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }*/



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
