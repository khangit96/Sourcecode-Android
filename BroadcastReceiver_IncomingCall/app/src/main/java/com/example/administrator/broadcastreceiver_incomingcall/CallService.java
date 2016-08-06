package com.example.administrator.broadcastreceiver_incomingcall;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Administrator on 2/6/2016.
 */
public class CallService extends Service implements SensorEventListener {
   SensorManager sensorManager;
    Sensor camBienTruongGan;
    private static Integer count=0;
    Context context=this;
  /*SensorManager sensorManager;
    Sensor camBienGiaToc;
    float last_x, last_y, last_z;
    long lastTime;

    Integer count=0;
    */

    @Override
    public void onStart(Intent intent, int startId) {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        camBienTruongGan = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorManager.registerListener(this, camBienTruongGan, SensorManager.SENSOR_DELAY_NORMAL);
        /*sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        camBienGiaToc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (camBienGiaToc == null) {
            Toast.makeText(this, "Device doesn't support ACCELEROMETER", Toast.LENGTH_LONG).show();
        } else {
            sensorManager.registerListener(this, camBienGiaToc, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Toast.makeText(getApplicationContext(), "Phone ringing", Toast.LENGTH_LONG).show();
        count=0;
        */
        super.onStart(intent, startId);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       try {
            float giatri = event.values[0];
            if (giatri == 0) {
                count++;
                Toast.makeText(getApplicationContext(), "Gần ", Toast.LENGTH_LONG).show();
                answerPhoneHeadsethook();
            }
            else {
                if (count >= 1) {
                    disconnectCall();
                }
            }

        }
        catch (Exception e){

        }

        //
       /*camBienGiaToc = event.sensor;

        if (camBienGiaToc.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long currentTime=System.currentTimeMillis();
            if((currentTime-lastTime)>100){
                long diffTime=currentTime-lastTime;
                lastTime=currentTime;
                float speed=Math.abs(x+y+z-last_x-last_y-last_z)/diffTime*10000;
                if(speed>600){
                  //  disconnectCall();
                    count++;
                    if(count==1){
                        answerPhoneHeadsethook();
                    }
                    else if(count==2){
                        disconnectCall();
                    }

                    Toast.makeText(context,"Bạn đã lăc thiết bị : "+count,Toast.LENGTH_LONG).show();
                }
                last_x=x;
                last_y=y;
                last_z=z;
            }


        }
        */

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    //Hàm tự trả lời cuộc gọi không cần nhấn nút
    public void answerPhoneHeadsethook() {
      //  String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
      //  if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
        //    String number = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            Intent buttonUp = new Intent(Intent.ACTION_MEDIA_BUTTON);
            buttonUp.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_HEADSETHOOK));
            try {
                context.sendOrderedBroadcast(buttonUp, "android.permission.CALL_PRIVILEGED");
            }
            catch (Exception e) {
            }

            Intent headSetUnPluggedintent = new Intent(Intent.ACTION_HEADSET_PLUG);
            headSetUnPluggedintent.addFlags(Intent.FLAG_RECEIVER_REGISTERED_ONLY);
            headSetUnPluggedintent.putExtra("state", 1); // 0 = unplugged  1 = Headset with microphone 2 = Headset without microphone
            headSetUnPluggedintent.putExtra("name", "Headset");
            try {
                context.sendOrderedBroadcast(headSetUnPluggedintent, null);
            }
            catch (Exception e) {
            }
      //  }
    }
    public void disconnectCall(){
        try {

            String serviceManagerName = "android.os.ServiceManager";
            String serviceManagerNativeName = "android.os.ServiceManagerNative";
            String telephonyName = "com.android.internal.telephony.ITelephony";
            Class<?> telephonyClass;
            Class<?> telephonyStubClass;
            Class<?> serviceManagerClass;
            Class<?> serviceManagerNativeClass;
            Method telephonyEndCall;
            Object telephonyObject;
            Object serviceManagerObject;
            telephonyClass = Class.forName(telephonyName);
            telephonyStubClass = telephonyClass.getClasses()[0];
            serviceManagerClass = Class.forName(serviceManagerName);
            serviceManagerNativeClass = Class.forName(serviceManagerNativeName);
            Method getService = // getDefaults[29];
                    serviceManagerClass.getMethod("getService", String.class);
            Method tempInterfaceMethod = serviceManagerNativeClass.getMethod("asInterface", IBinder.class);
            Binder tmpBinder = new Binder();
            tmpBinder.attachInterface(null, "fake");
            serviceManagerObject = tempInterfaceMethod.invoke(null, tmpBinder);
            IBinder retbinder = (IBinder) getService.invoke(serviceManagerObject, "phone");
            Method serviceMethod = telephonyStubClass.getMethod("asInterface", IBinder.class);
            telephonyObject = serviceMethod.invoke(null, retbinder);
            telephonyEndCall = telephonyClass.getMethod("endCall");
            telephonyEndCall.invoke(telephonyObject);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
