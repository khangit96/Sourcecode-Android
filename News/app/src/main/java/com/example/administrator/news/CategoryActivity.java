package com.example.administrator.news;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import khang.Variables;

public class CategoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
*/      //   getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Bundle bd=getIntent().getExtras();
        if(bd!=null){
            int category=bd.getInt("pos");
            Integer icon=bd.getInt("icon");
            ListView lvCategory=(ListView)findViewById(R.id.lvCategory);
            setTitle(Variables.PAPERS[category]);
            ArrayList<String> arrCategory=new ArrayList<String>();
            for(int i=0;i<Variables.CATEGORIES[category].length;i++){
                arrCategory.add(Variables.CATEGORIES[category][i]);
            }
            CustomAdapterCategory adapter=new CustomAdapterCategory(this,R.layout.custom_category,icon,arrCategory);
           lvCategory.setAdapter(adapter);
        }

    }

}
