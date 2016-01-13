package com.example.administrator.context_menu_dialog;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String>arrName;
    ArrayAdapter adapter;
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
        arrName=new ArrayList<String>();
        arrName.add("Duy Khang");
        arrName.add("Văn Tiến");
        arrName.add("Thiện Huy");
   adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrName);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
     /*   menu.setHeaderTitle("Detail");
        menu.add(0, v.getId(), 0, "Call");
        menu.add(0, v.getId(), 0, "Send SMS");*/
        AdapterView.AdapterContextMenuInfo info=(AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(arrName.get(info.position));
        //menu.add(menu.NONE,0,0,"From: "+arrName[info.position]);
      //  menu.add(menu.NONE,1,1,"To: "+arrName[info.position]);
        menu.add(menu.NONE,1,1,"Delete");

       /* for(int i=0;i<arrName.length;i++){
            menu.add(i,v.getId(),0,arrName[i]);
        }*/
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getItemId()==0) {
           // String name=arrName[menuInfo.position];
            //Toast.makeText(MainActivity.this,name, Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId()==1){
            adapter.remove(arrName.remove(menuInfo.position));
            adapter.notifyDataSetChanged();
          //  Toast.makeText(MainActivity.this, "Send sms", Toast.LENGTH_LONG).show();
        }
       return true;

    }
}
