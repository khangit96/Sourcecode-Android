package com.demoautocompletetextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView autoComplete;
    String[]provinceList;
    ArrayAdapter<String>provinceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        autoComplete = (AutoCompleteTextView) findViewById(R.id.autoComplete);
        provinceList=getResources().getStringArray(R.array.provinceList);
        provinceAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,provinceList);
        autoComplete.setAdapter(provinceAdapter);
    }
}
