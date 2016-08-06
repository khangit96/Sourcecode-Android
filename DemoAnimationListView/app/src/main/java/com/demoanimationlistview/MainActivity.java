package com.demoanimationlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> list;
    MyAdapter myAdapter;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add("Tên thứ: " + i);
        }
         myAdapter = new MyAdapter(MainActivity.this,R.layout.item, list);
       // adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
        lv.setAdapter(myAdapter);

        // myAdapter.notifyDataSetChanged();
    }
}
