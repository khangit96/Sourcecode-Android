package com.demogooglemap5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView tv;
    ArrayList<Home> homeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        tv = (TextView) findViewById(R.id.tv);
        Intent intent = getIntent();
        homeArrayList = intent.getParcelableArrayListExtra("homeArrayList");
        String result = "";
        for (int i = 0; i < homeArrayList.size(); i++) {
            result += homeArrayList.get(i).name + "-" + homeArrayList.get(i).distance + "\n";
        }
        tv.setText(result);
    }
}
