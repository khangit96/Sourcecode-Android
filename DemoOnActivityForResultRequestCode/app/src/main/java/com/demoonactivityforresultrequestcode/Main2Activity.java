package com.demoonactivityforresultrequestcode;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main2Activity extends AppCompatActivity {
    Button bt;
    EditText ed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bt = (Button) findViewById(R.id.bt);
        ed = (EditText) findViewById(R.id.ed);
        Intent iReceiver = getIntent();
        String name = iReceiver.getStringExtra("name");
        if (name == null || name.equals("")) {
            bt.setText("Add");
        } else {
            ed.setText(name);
            bt.setText("Update");
        }

    }

    public void Click(View v) {
        Intent intent = new Intent();
        intent.putExtra("name",ed.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

}
