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
    ArrayList<Home> homeArrayList = new ArrayList<>();
    ResultAdapter resultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        lv = (ListView) findViewById(R.id.lv);
        Intent intent = getIntent();
        homeArrayList = intent.getParcelableArrayListExtra("homeArrayList");
        resultAdapter = new ResultAdapter(this, R.layout.item, homeArrayList);
        lv.setAdapter(resultAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             /*   Intent intent1 = new Intent(ResultActivity.this, DrawMapActivity.class);
                intent1.putExtra("home", (Serializable) homeArrayList.get(i));
                startActivity(intent1);*/
                Toast.makeText(getApplicationContext(), "" + homeArrayList.get(i).routeList.size(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
