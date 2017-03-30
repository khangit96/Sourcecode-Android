package com.example.mypc.watereffect;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;

/**
 * Created by MyPC on 16/06/2016.
 */
public class MyLiveWallpaper extends AndroidLiveWallpaperService {

    ApplicationListener listener;

    @Override
    public void onCreateApplication() {
        super.onCreateApplication();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = false;
        config.useCompass = false;
        config.useWakelock = false;
        config.useAccelerometer = false;
        config.getTouchEventsForLiveWallpaper = true;

        listener = new WaterRipples(this);
        initialize(listener, config);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        listener.dispose();
    }
}
