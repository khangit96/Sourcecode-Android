package com.demosqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit");
        Author author = (Author) getIntent().getSerializableExtra("AUTHOR");
        author.fullname="Uyên";
        author.address="Bình Phước";
        Toast.makeText(getApplicationContext(), author.fullname, Toast.LENGTH_LONG).show();
        MyDatabaseHelper db=new MyDatabaseHelper(this);
        db.updateNote(author);

        Intent intent=new Intent(EditActivity.this,MainActivity.class);
        intent.putExtra("AUTHOR",author);
        setResult(RESULT_OK,intent);
        finish();
    }
}
