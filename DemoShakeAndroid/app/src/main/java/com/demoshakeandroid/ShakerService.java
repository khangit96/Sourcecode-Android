package com.demoshakeandroid;

import android.app.KeyguardManager;
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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.text.format.Time;

/**
 * Created by Administrator on 5/4/2016.
 */
public class ShakerService extends Service implements SensorEventListener {
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
    SensorManager sensorManager;
    Sensor camBienTruongGan;
    Boolean checkScreenOff = false;
    Context context;
    public static int count = 0;
    public static String second1 = "";
    public static String second2 = "";


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

        /*Broadcast ScreenOff*/
        BroadCastScreenOff broadCastScreenOff = new BroadCastScreenOff();
        IntentFilter intentFilterScreenOff = new IntentFilter();
        intentFilterScreenOff.addAction("android.intent.action.SCREEN_OFF");
        registerReceiver(broadCastScreenOff, intentFilterScreenOff);

        /*Broadcast ScreenOn*/
        BroadCastScreenOn broadCastScreenOn = new BroadCastScreenOn();
        IntentFilter intentFilterScreenOn = new IntentFilter();
        intentFilterScreenOn.addAction("android.intent.action.SCREEN_ON");
        registerReceiver(broadCastScreenOn, intentFilterScreenOn);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        camBienTruongGan = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, camBienTruongGan, SensorManager.SENSOR_DELAY_NORMAL);
        context = getApplicationContext();

    }

    //proximity sensor
    @Override
    public void onSensorChanged(SensorEvent event) {
        float giatri = event.values[0];
        if (giatri == 0) {//when user wave
            if (checkScreenOff == true) {
                vibrator.vibrate(400);
                UnlockScreen();
            /*    count++;
                if (count == 1) {
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    second1 = today.format("%S");
                    Log.d("test", "" + second1);
                    //lấy móc thời gian khi count==1
                }
                if (count == 2) {
                    //lấy móc thời gian khi count==2
                    Time today = new Time(Time.getCurrentTimezone());
                    today.setToNow();
                    second2 = today.format("%S");
                    int subSencond = Integer.parseInt(second1) - Integer.parseInt(second2);
                    if (subSencond <= 5) {
                        count=0;
                        Log.d("test", "" + subSencond);
                        vibrator.vibrate(400);
                        UnlockScreen();
                        58 3-> 59 0 1 2 3
                    }
                    else if(subSencond<=0){

                    }
                    else{

                    }

                }*/

            }

        }
    }

    public void UnlockScreen() {
        KeyguardManager km = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        final KeyguardManager.KeyguardLock kl = km.newKeyguardLock("MyKeyguardLock");
        kl.disableKeyguard();//mở trực tiếp lên  không hiện mở khóa mặc định của hệ thống

        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK
                | PowerManager.ACQUIRE_CAUSES_WAKEUP
                | PowerManager.ON_AFTER_RELEASE, "MyWakeLock");
        wakeLock.acquire();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    //Broadcast ScreenOff
    class BroadCastScreenOff extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkScreenOff = true;

        }
    }

    //Broadcast ScreenOn
    class BroadCastScreenOn extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            checkScreenOff = false;
        }
    }

    //Unclock Screen
    private void unlockScreen(Context context) {


    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
