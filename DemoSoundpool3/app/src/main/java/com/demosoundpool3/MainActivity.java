package com.demosoundpool3;

import android.media.AudioManager;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.media.SoundPool.OnLoadCompleteListener;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private SoundPool soundPool;
    private int soundID1,soundID2;
    boolean plays = false, loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });
        soundID1 = soundPool.load(this, R.raw.gover, 1);

    }
    public void Play1(View v){
        // Is the sound loaded does it already play?
        /*if (loaded && !plays) {
            soundPool.play(soundID1, volume, volume, 1, 0, 1f);
            counter = counter++;
            Toast.makeText(this, "Played sound", Toast.LENGTH_SHORT).show();
            plays = true;
        }*/
        soundPool.play(soundID1, volume, volume, 1, 0, 1f);
    } public void Play2(View v){
        // Is the sound loaded does it already play?
        /*if (loaded && !plays) {
            soundPool.play(soundID1, volume, volume, 1, 0, 1f);
            counter = counter++;
            Toast.makeText(this, "Played sound", Toast.LENGTH_SHORT).show();
            plays = true;
        }*/
    soundPool.play(soundID1, volume, volume, 1, 0, 1f);
    }

    public void Stop(View v){
        if (plays) {
            soundPool.stop(soundID1);
            soundID1 = soundPool.load(this, R.raw.gover, counter);
            Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
    }
}
