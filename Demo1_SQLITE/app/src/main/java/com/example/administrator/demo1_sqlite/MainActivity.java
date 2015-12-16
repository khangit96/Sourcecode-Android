package com.example.administrator.demo1_sqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database db=new Database(this);
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        lv=(ListView)findViewById(R.id.lv);
       // db.Query_Data("CREATE TABLE IF NOT EXISTS khoahoc(_id INTEGER PRIMARY KEY,tenmonhoc VARCHAR(200) NOT NULL)");
       // db.Query_Data("INSERT INTO khoahoc VALUES(null,'Lap trinh PHP')");
        // db.Query_Data("INSERT INTO khoahoc VALUES(null,'Lap trinh Game')");
        final  ArrayList<String> arrName=new ArrayList<String>();
        final  ArrayList<Integer> arrId=new ArrayList<Integer>();
                Cursor kq=db.GetData("SELECT * FROM khoahoc");
                while(kq.moveToNext()){
                    Integer id=kq.getInt(0);
                    String s=kq.getString(1);
                    arrName.add(s);
                    arrId.add(id);
                }
        final ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrName);
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                adapter.remove(arrName.remove(position));
               adapter.notifyDataSetChanged();

                db.delete_byID(arrId.get(position), "khoahoc", "_id");
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
