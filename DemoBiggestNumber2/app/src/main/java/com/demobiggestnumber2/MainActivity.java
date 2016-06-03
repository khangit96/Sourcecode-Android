package com.demobiggestnumber2;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
    }

    /*Event play game*/
    public void Play(View v) {
        startActivity(new Intent(MainActivity.this, PlayGameActivity.class));
    }

    /*High score*/
    public void About(View v) {
        Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_LONG).show();
    }

    /*Instruction */
    public void HowToPlay(View v) {
        Toast.makeText(getApplicationContext(), "How to play", Toast.LENGTH_LONG).show();
    }

    /*Exit*/
    public void Exit(View v) {
        System.exit(0);
    }


}
