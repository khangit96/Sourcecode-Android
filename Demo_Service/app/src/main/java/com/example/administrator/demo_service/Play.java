package com.example.administrator.demo_service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.security.Provider;

/**
 * Created by Administrator on 11/30/2015.
 */
public class Play extends Service{
    MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        mediaPlayer = new MediaPlayer();
    }
    public void PlayNhacMp3(String url){
        //url = "http://khoapham.vn/KhoaPhamTraining/laptrinhios/EmCuaNgayHomQua.mp3";
        /*try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }*/
        mediaPlayer=MediaPlayer.create(this,R.raw.chuabaogio);
        mediaPlayer.start();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        Bundle bd=intent.getExtras();
        if(bd!=null){
            String url=bd.getString("url");
            PlayNhacMp3(url);
        }
    }
}
