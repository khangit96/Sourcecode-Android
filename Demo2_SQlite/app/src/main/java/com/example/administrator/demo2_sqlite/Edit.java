package com.example.administrator.demo2_sqlite;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Edit extends AppCompatActivity {
    EditText edUsername,edPassword;
    Database db=new Database(this);
    Button btUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
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
        init();

        Bundle  bd=getIntent().getExtras();
        if(bd!=null){
            edUsername.setText(bd.getString("username"));
            edPassword.setText(bd.getString("password"));
            final Integer id=bd.getInt("id");

            btUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    db.update(id, new user(edUsername.getText().toString(),edPassword.getText().toString()));
                   Intent i=new Intent(Edit.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });
        }
    }

    public void init(){
        edPassword=(EditText)findViewById(R.id.edPassword);
        edUsername=(EditText)findViewById(R.id.edUsername);
        btUpdate=(Button)findViewById(R.id.btUpdate);
    }

}
