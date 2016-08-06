package com.demosoundpool2;

import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;
    private int soundID1,soundID2;
    boolean plays = false, loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    int counter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the layout of the Activity
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
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
                Toast.makeText(getApplicationContext(),"Load completed",Toast.LENGTH_SHORT).show();
            }
        });
        soundID1 = soundPool.load(this, R.raw.beep, 1);
        soundID2 = soundPool.load(this, R.raw.success, 1);

    }

    public void playSound(View v) {
        // Is the sound loaded does it already play?
        if (loaded && !plays) {
            Toast.makeText(getApplicationContext(),""+plays,Toast.LENGTH_LONG).show();
            soundPool.play(soundID1, volume, volume, 1, 0, 1f);
            counter = counter++;
            Toast.makeText(this, "Played sound", Toast.LENGTH_SHORT).show();
            plays = true;
            Toast.makeText(getApplicationContext(),""+plays,Toast.LENGTH_LONG).show();
        }
    }

    public void playLoop(View v) {
        // Is the sound loaded does it already play?
        if (loaded && !plays) {

            // the sound will play forever if we put the loop parameter -1
            soundPool.play(soundID1, volume, volume, 1, -1, 1f);
            counter = counter++;
            Toast.makeText(this, "Plays loop", Toast.LENGTH_SHORT).show();
            plays = true;
        }
    }

    public void pauseSound(View v) {
        // Is the sound loaded already?
        if (plays) {
            soundPool.pause(soundID1);
            soundID1 = soundPool.load(this, R.raw.beep, counter);
            Toast.makeText(this, "Pause sound", Toast.LENGTH_SHORT).show();
            plays = false;

        }
    }

    public void stopSound(View v) {
        // Is the sound loaded already?
        if (plays) {
            soundPool.stop(soundID1);
            soundID1 = soundPool.load(this, R.raw.beep, counter);
            Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
            plays = false;
        }
    }
    public void playSoundID2(View v){
        soundPool.play(soundID2, volume, volume, 1, -1, 1f);
    }

}
