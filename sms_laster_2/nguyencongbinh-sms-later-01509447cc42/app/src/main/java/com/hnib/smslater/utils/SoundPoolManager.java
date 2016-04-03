package com.hnib.smslater.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.util.SparseIntArray;

import com.hnib.smslater.R;

public class SoundPoolManager {

    private SoundPool mSoundPool;
    private SparseIntArray mSoundPoolMap;
    private AudioManager mAudioManager;
    private Context mContext;
    private static SoundPoolManager instance;
    private SoundPoolManager(Context context){
        mContext = context;
        mSoundPool = buildSoundPool();
        mSoundPoolMap = new SparseIntArray();
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    public static SoundPoolManager getInstance(Context context){
        if(instance == null){
            instance = new SoundPoolManager(context);
        }
        return instance;
    }

    
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private SoundPool buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                                                .setUsage(AudioAttributes.USAGE_GAME)
                                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                                .build();
            mSoundPool = new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        } else {
            mSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
        return mSoundPool;
    }

    // to add new sound
    public void addSound(int Index, int SoundID) {
        mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
    }

    // to load available sound
    public void loadSound() {
        addSound(1, R.raw.sound_error);

    }

    public void playSound(int index) {
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, 1f);
    }

    public void playLoopedSound(int index) {
        int streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, -1, 1f);
    }
    
    public void stopSound(int index) {
        mSoundPool.stop(index);
    }
}
