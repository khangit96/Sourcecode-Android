package com.demoeyetraining;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button btClick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btClick= (Button) findViewById(R.id.btnRate1);
    }
    public void Click(View v){
        btClick.setTextColor(getResources().getColor(R.color.white));
    }

}
