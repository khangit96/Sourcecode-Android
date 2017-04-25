package com.demosqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Author> authors;
    AuthorAdapter adapter;
    ListView lv;
    int posSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main");

        lv = (ListView) findViewById(R.id.lv);
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.addNote(new Author("NGuyễn Tường Minh Uyên", "0976671809", "Long AN"));
        authors = (ArrayList<Author>) db.getAllAuthor();
        adapter = new AuthorAdapter(getApplicationContext(), 1, authors);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                posSelected = i;
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode==0){
                authors.set(posSelected, (Author) data.getSerializableExtra("AUTHOR"));
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            Intent intent = new Intent(MainActivity.this, EditActivity.class);
            intent.putExtra("AUTHOR", authors.get(posSelected));
            startActivityForResult(intent,0);
        } else {
            new AlertDialog.Builder(this)
                    .setMessage(" Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MyDatabaseHelper db = new MyDatabaseHelper(getApplicationContext());
                            db.deleteAutor(authors.get(posSelected));
                            authors.remove(posSelected);
                            adapter.notifyDataSetChanged();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();


        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title
        menu.add(0, 0, 0, "Edit");
        menu.add(0, 1, 1, "Delete");
    }
}
