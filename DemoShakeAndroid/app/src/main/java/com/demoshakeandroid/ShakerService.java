package com.demoshakeandroid;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 5/4/2016.
 */
public class ShakerService extends Service {
    /* private static final int FORCE_THRESHOLD = 350;
     private static final int TIME_THRESHOLD = 100;
     private static final int SHAKE_TIMEOUT = 500;
     private static final int SHAKE_DURATION = 1000;
     private static final int SHAKE_COUNT = 3;

     //private SensorManager mSensorMgr;
     private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
     private long mLastTime;
     //private OnShakeListener mShakeListener;
     private Context mContext;
     private int mShakeCount = 0;
     private long mLastShake;
     private long mLastForce;
     Vibrator vibrator;
     //----------------------------------------bruno's tutorial stuff
     SensorManager mSensorEventManager;

     Sensor mSensor;

     // BroadcastReceiver for handling ACTION_SCREEN_OFF.
     BroadcastReceiver mReceiver = new BroadcastReceiver() {
         @Override
         public void onReceive(Context context, Intent intent) {
             // Check action just to be on the safe side.
             if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
                 Log.v("onShake", "trying re-registration");
                 // Unregisters the listener and registers it again.
                 mSensorEventManager.unregisterListener(ShakerService.this);
                 mSensorEventManager.registerListener(ShakerService.this, mSensor,
                         SensorManager.SENSOR_DELAY_GAME);
             }
         }
     };

     @Override
     public void onSensorChanged(SensorEvent event) {
         if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

         Log.v("sensor","sensor change is verifying");
         long now = System.currentTimeMillis();

         if ((now - mLastForce) > SHAKE_TIMEOUT) {
             mShakeCount = 0;
         }

         if ((now - mLastTime) > TIME_THRESHOLD) {
             long diff = now - mLastTime;
             float speed = Math.abs(event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y] + event.values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ) / diff * 10000;
             if (speed > FORCE_THRESHOLD) {
                 if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                     mLastShake = now;
                     mShakeCount = 0;

                     //call the reaction you want to have happen
                     onShake();
                 }
                 mLastForce = now;
             }
             mLastTime = now;
             mLastX = event.values[SensorManager.DATA_X];
             mLastY = event.values[SensorManager.DATA_Y];
             mLastZ = event.values[SensorManager.DATA_Z];
         }
     }

     @Override
     public void onAccuracyChanged(Sensor sensor, int accuracy) {

     }

     @Override
     public void onCreate() {
         super.onCreate();
         Log.v("shake service startup", "registering for shake");
         vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
         mContext = getApplicationContext();
         // Obtain a reference to system-wide sensor event manager.
         mSensorEventManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);

         // Get the default sensor for accel
         mSensor = mSensorEventManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

         // Register for events.
         mSensorEventManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_GAME);
         //TODO I'll only register at screen off. I don't have a use for shake while not in sleep (yet)

         // Register our receiver for the ACTION_SCREEN_OFF action. This will make our receiver
         // code be called whenever the phone enters standby mode.
         IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
         registerReceiver(mReceiver, filter);

     }

     @Override
     public void onDestroy() {
         super.onDestroy();
         // Unregister our receiver.
         unregisterReceiver(mReceiver);
         // Unregister from SensorManager.
         mSensorEventManager.unregisterListener(this);
     }

     public void onShake() {
         //Poke a user activity to cause wake?
         Log.v("onShake", "doing wakeup");
         vibrator.vibrate(400);
         //send in a broadcast for exit request to the main mediator

     }

     @Nullable
     @Override
     public IBinder onBind(Intent intent) {
         return null;
     }*/
    private Shaker shaker;
    Vibrator vibrator;
    private static final int ADMIN_INTENT = 15;
    private static final String description = "Some Description About Your Admin";
    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;
    private PowerManager.WakeLock mWakeLock;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        shaker.setOnShakeListener(new Shaker.OnShakeListener() {
            @Override
            public void onShake() {
                // Toast.makeText(getApplicationContext(), "Shaked", Toast.LENGTH_LONG).show();
                vibrator.vibrate(400);
                boolean isAdmin = mDevicePolicyManager.isAdminActive(mComponentName);
                if (isAdmin) {
                    mDevicePolicyManager.lockNow();
                } else {
                    Toast.makeText(getApplicationContext(), "Not Registered as admin", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        shaker = new Shaker(this);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, MyAdminReceiver.class);
        BroadCastScreenOff broadCastScreenOff = new BroadCastScreenOff();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(broadCastScreenOff, intentFilter);
      /*  Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,description);*/
    }

    class BroadCastScreenOff extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            shaker = new Shaker(context);
            shaker.setOnShakeListener(new Shaker.OnShakeListener() {
                @Override
                public void onShake() {
                    vibrator.vibrate(400);
                }
            });
            Log.d("test", "screenoff");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
