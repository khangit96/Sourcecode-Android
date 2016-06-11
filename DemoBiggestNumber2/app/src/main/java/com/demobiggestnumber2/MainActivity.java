package com.demobiggestnumber2;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btContinue;
    SharedPreferences sp;

    /*Sound*/
    private SoundPool soundPool;
    private int soundIDPressButton, soundIDBackground;
    boolean plays = false, loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_main);
        init();
    }

    public void init() {
        initView();
        initSound();
        sp = getSharedPreferences("CHECK", Context.MODE_PRIVATE);
        if (sp.getString("checkContinue", "").equals("ok")) {
            btContinue.setVisibility(View.VISIBLE);
        } else {
            btContinue.setVisibility(View.GONE);
        }
        playBackgroundSound();
    }

    /*putDataSharedPreferences*/
    public void putDataSharedPreferences(String name, String data) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, data);
        editor.commit();
    }

    private void initView() {
        btContinue = (Button) findViewById(R.id.btContinue);
        btContinue.setVisibility(View.GONE);

    }

    /*init sound*/
    public void initSound() {
        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        //Hardware buttons setting to adjust the media sound
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // the counter will help us recognize the stream id of the sound played  now
        counter = 0;

        // Load the sounds
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
       /* soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });*/
        soundIDPressButton = soundPool.load(this, R.raw.press, 1);
        soundIDBackground = soundPool.load(this, R.raw.intro, 1);

    }

    /*Play press button sound*/
    public void playPressButtonSound() {
        soundPool.play(soundIDPressButton, volume, volume, 1, 0, 1f);
    }

    /*Play background sound*/
    public void playBackgroundSound() {
        soundPool.play(soundIDBackground, volume, volume, 1, -1, 1f);
    }

    /*Event play game*/
    public void newGame(View v) {
        playPressButtonSound();
        Intent newGameIntent = new Intent(MainActivity.this, PlayGameActivity.class);
        newGameIntent.putExtra("game", "new game");
        startActivityForResult(newGameIntent, 0);
    }

    /*Continue game*/
    public void Continue(View v) {
        Intent newGameIntent = new Intent(MainActivity.this, PlayGameActivity.class);
        newGameIntent.putExtra("game", "continue");
        startActivityForResult(newGameIntent, 0);
    }
  public void leaderBoard(View v){

  }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {
                btContinue.setVisibility(View.VISIBLE);
                putDataSharedPreferences("checkContinue", "ok");
            }
        } else {
            btContinue.setVisibility(View.GONE);
            putDataSharedPreferences("checkContinue", "null");
        }
    }

    /*  *//*High score*//*
    public void About(View v) {
        Toast.makeText(getApplicationContext(), "About", Toast.LENGTH_LONG).show();
    }*/
/*
    *//*Instruction *//*
    public void HowToPlay(View v) {
        Toast.makeText(getApplicationContext(), "How to play", Toast.LENGTH_LONG).show();
    }*/

    /*Exit*/
    public void howToPlay(View v) {


    }

    @Override
    public void onBackPressed() {
       super.onBackPressed();
    }
}
