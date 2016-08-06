package com.example.administrator.read_sms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
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
        init();
        process();

    }
    public ArrayList<String> fetchInbox(){
        ArrayList<String>sms=new ArrayList<String>();
       // Uri uri=Uri.parse("content://sms/inbox");
        Uri uri=Uri.parse("content://sms/sent");
        Cursor cursor=getContentResolver().query(uri, new String[]{"_id", "address", "date", "body"}, null, null, null);
          int count=cursor.getCount();//đếm số tin nhắn
        if(count!=0) {
            cursor.moveToFirst();
            String bodySms = null;
            String address = null;
            String date = null;
            date = cursor.getString(2);
            address = cursor.getString(1);
            bodySms = cursor.getString(3);
            sms.add(bodySms.toString() + "" + address + "" + date);
            while (cursor.moveToNext()) {
                date = cursor.getString(2);
                address = cursor.getString(1);
                bodySms = cursor.getString(3);
                sms.add(bodySms.toString() + "" + address + "" + date);
            }
        }

        return  sms;
    }

    public void init(){
        lv=(ListView)findViewById(R.id.lv);
    }
    public void process(){
        if (fetchInbox()!=null){
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,fetchInbox());
            lv.setAdapter(adapter);

        }
        if(fetchInbox().isEmpty()){
            Toast.makeText(MainActivity.this," not have sms",Toast.LENGTH_LONG).show();
        }

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
