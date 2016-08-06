package com.demolistviewselector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<String> arrName = new ArrayList<>();
    Boolean checkClick = false;
    int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.lv);
        arrName.add("Khang");
        arrName.add("Linh");
        arrName.add("HUy");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrName);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View viewCurrent, int posCurrent, long l) {
                if (position != -1) {
                    View v = adapterView.getChildAt(position);//get view at position
                    v.setBackgroundColor(getResources().getColor(R.color.white));
                }

                viewCurrent.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                position = posCurrent;

            }
        });
    }
}
