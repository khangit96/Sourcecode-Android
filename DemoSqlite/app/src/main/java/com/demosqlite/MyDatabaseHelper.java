package com.demosqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 4/19/2017.
 */

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "books";

    private static final String TABLE_AUTHOR = "tblAuthors";

    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FULLNAME = "fullname";
    private static final String COLUMN_TEL = "tel";
    private static final String COLUMN_ADDRESS = "address";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo các bảng.
    @Override
    public void onCreate(SQLiteDatabase db) {

        String script = "CREATE TABLE " + TABLE_AUTHOR + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_FULLNAME + " TEXT,"
                + COLUMN_TEL + " TEXT," + COLUMN_ADDRESS + " TEXT" + ")";
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);

        onCreate(db);
    }


    public void addNote(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME, author.fullname);
        values.put(COLUMN_TEL, author.tel);
        values.put(COLUMN_ADDRESS, author.address);


        // Trèn một dòng dữ liệu vào bảng.
        db.insert(TABLE_AUTHOR, null, values);


        db.close();
    }


   /* public Author author(int id) {
       *//* SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTE, new String[]{COLUMN_NOTE_ID,
                        COLUMN_NOTE_TITLE, COLUMN_NOTE_CONTENT}, COLUMN_NOTE_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Note note = new Note(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return note
        return note;*//*
    }
*/

    public List<Author> getAllAuthor() {

        List<Author> authors = new ArrayList<Author>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_AUTHOR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // Duyệt trên con trỏ, và thêm vào danh sách.
        if (cursor.moveToFirst()) {
            do {
                Author author = new Author();
                author.ID = Integer.parseInt(cursor.getString(0));
                author.fullname = cursor.getString(1);
                author.address = cursor.getString(2);

                // Thêm vào danh sách.
                authors.add(author);
            } while (cursor.moveToNext());
        }

        // return note list
        return authors;
    }

  /*  public int getNotesCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + TABLE_NOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
*/

    public int updateNote(Author author) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FULLNAME, author.fullname);
        values.put(COLUMN_TEL, author.tel);
        values.put(COLUMN_ADDRESS, author.address);

        // updating row
        return db.update(TABLE_AUTHOR, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(author.ID)});
    }

    public void deleteAutor(Author author) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_AUTHOR, COLUMN_ID + " = ?",
                new String[]{String.valueOf(author.ID)});
        db.close();
    }
}
