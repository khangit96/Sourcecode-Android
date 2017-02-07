package com.demonoteapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.demonoteapp.Database.DatabaseAdapter;
import com.demonoteapp.Model.Note;
import com.demonoteapp.R;

public class AddNoteActivity extends AppCompatActivity {

    //Widget
    EditText edTitle, edContent;

    //
    DatabaseAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        db = new DatabaseAdapter(this);
        initToolbar();
        initControls();
    }

    /*Init
    * toolbar
    * */
    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /*
    * init
    * some controls
    * */
    public void initControls() {
        edTitle = (EditText) findViewById(R.id.edTitle);
        edContent = (EditText) findViewById(R.id.edContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {

            String noteTitle = edTitle.getText().toString();
            String noteContent = edContent.getText().toString();

            if (noteTitle.equals("") || noteContent.equals("")) {
                Toast.makeText(getApplicationContext(), "Please enter some word", Toast.LENGTH_LONG).show();
                return false;
            }

            Note note = new Note(edTitle.getText().toString(), edContent.getText().toString());
            db.addNote(note);

            Intent mainIntent = new Intent();
            mainIntent.putExtra("NOTE", note);
            setResult(RESULT_OK, mainIntent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
