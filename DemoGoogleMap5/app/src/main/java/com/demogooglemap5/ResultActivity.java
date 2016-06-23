package com.demogooglemap5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.demogooglemap5.adapter.ResultAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Route> routeArrayList = new ArrayList<>();
    ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        lv = (ListView) findViewById(R.id.lv);
        Intent intent = getIntent();
        routeArrayList = intent.getParcelableArrayListExtra("routes");
        resultAdapter = new ResultAdapter(this, R.layout.item,routeArrayList);
        lv.setAdapter(resultAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(ResultActivity.this, DrawMapActivity.class);
                intent1.putExtra("route", (Serializable) routeArrayList.get(i));
                startActivity(intent1);
            }
        });
    }
}
