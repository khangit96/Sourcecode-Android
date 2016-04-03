package com.hnib.smslater.application;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.crashlytics.android.Crashlytics;
import com.hnib.smslater.utils.CommonUtils;
import com.hnib.smslater.utils.SoundPoolManager;
import io.fabric.sdk.android.Fabric;

/**
 * Created by caucukien on 18/12/2015.
 */
public class AppApplication extends Application {



    private static AppApplication instance;
    @Override
    public void onCreate() {
        Log.d("AppApplication", "onCreate");
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        instance = this;
        ActiveAndroid.initialize(this);
        SoundPoolManager.getInstance(this).loadSound();
        showDeviceInfo();


    }

    private void showDeviceInfo() {
        String density = CommonUtils.getDensityName(this);
        Log.d("binh", "density: "+density);
        int[] screen = CommonUtils.getScreenSize(this);
        Log.d("binh", "width: "+screen[0]+ " height: "+screen[1]);
        String screenCategory = CommonUtils.getCatetoryScreenSize(this);
        Log.d("binh", "screen category is: "+screenCategory);
        String deviceID = CommonUtils.getDeviceId(this);
        Log.d("binh", "device id: "+deviceID);


    }


    public static AppApplication getInstance() {
        return  instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

}
