package com.example.administrator.demo2_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Administrator on 11/28/2015.
 */
public class Database  extends SQLiteOpenHelper{
    Context context;
    public Database(Context context){

        super(context,"db_account.sqlite",null,1);
        this.context=context;
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
    public void add(user u,String table){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues v=new ContentValues();
        v.put("username", u.Username);
        v.put("password", u.Password);
       if( db.insert(table, null, v)!=-1){
           Toast.makeText(context, "Insert success", Toast.LENGTH_SHORT).show();
       }
        else{
           Toast.makeText(context, "Insert failed", Toast.LENGTH_SHORT).show();
       }

    }
    public int update(Integer id,user u) {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put("username", u.Username.toString());
        val.put("password", u.Password.toString());
        String findId = "_id=" + id;
        return db.update("user", val, findId, null);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
