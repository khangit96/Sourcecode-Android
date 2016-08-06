package com.example.administrator.flashlight_icoming_smscall;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2/2/2016.
 */
public class CallService extends Service {
    private Camera camera = null;
    private boolean isFlashlightOn;
    Camera.Parameters parameters;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        String state = intent.getStringExtra("state");
        if (TelephonyManager.EXTRA_STATE_IDLE.equals(state)) {
            Toast.makeText(getApplicationContext(),"call received",Toast.LENGTH_LONG).show();
          //  setFlashlightOff();
        }

        if (TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
            Toast.makeText(getApplicationContext(),"call received",Toast.LENGTH_LONG).show();
        }
        if (TelephonyManager.EXTRA_STATE_RINGING.equals(state)) {
            // Toast.makeText(getApplicationContext(),"phone ringing",Toast.LENGTH_LONG).show();
            String myString = "010101010101010101010101010101";
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
        }


        super.onStart(intent, startId);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

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


    public void setFlashlightOn() {
        parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        isFlashlightOn = true;
    }

    public void setFlashlightOff() {
        parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
        isFlashlightOn = false;
    }
}
