package com.example.administrator.demo_service;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btPlay,btStop;
   // MediaPlayer mp3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        btPlay=(Button)findViewById(R.id.btPlay);
        btStop=(Button)findViewById(R.id.btStop);
      //  mp3=MediaPlayer.create(MainActivity.this,R.raw.chuabaogio);
      //  mp3.start();



    }
    public void Play(View v){
        Intent i=new Intent(MainActivity.this,Play.class);
        String url="http://khoapham.vn/KhoaPhamTraining/laptrinhios/EmCuaNgayHomQua.mp3";
        i.putExtra("url",url);
        stopService(new Intent(MainActivity.this, Play.class));
      //  startService(new Intent(MainActivity.this,Play.class));
        startService(i);

    }
    public  void Stop(View v){
        //mp3.release();
        stopService(new Intent(MainActivity.this,Play.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /*@Override
    protected void onPause() {
        super.onPause();
        mp3.release();
        finish();
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
