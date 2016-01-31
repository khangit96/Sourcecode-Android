package com.example.administrator.flashlight_icoming_smscall;

import android.app.Service;
import android.content.Intent;
import android.hardware.Camera;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import   android.hardware.Camera.Parameters;
import android.widget.Toast;

/**
 * Created by Administrator on 1/27/2016.
 */
public class SmsService extends Service {
    private  Camera camera=null;
    private  boolean isFlashlightOn;
    Parameters parameters;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        String myString = "0101010101010101";
        long blinkDelay=50;
        camera=Camera.open();
        for (int i = 0; i < myString.length(); i++) {
            if (myString.charAt(i) == '0') {
              setFlashlightOn();
            } else {
                setFlashlightOff();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //setFlashlightOn();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (camera != null) {
            camera.release();
            camera = null;
        }
        super.onDestroy();
    }


    public  void setFlashlightOn(){
        parameters=camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        isFlashlightOn=true;
    }
    public  void setFlashlightOff(){
        parameters=camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
        isFlashlightOn=false;
    }
}
