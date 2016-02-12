package musicapp.khangit.broadcastreceiver_screenoff;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2/9/2016.
 */
public class ScreenOffService extends Service {
    BroadcastReceiver mReceiver=null;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        // Toast.makeText(getBaseContext(), "Service on create", Toast.LENGTH_SHORT).show();

        // Register receiver that handles screen on and screen off logic
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyBroadcastReceiver();
        registerReceiver(mReceiver, filter);
        Log.d("check","onCreate");
        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        boolean screenOn = false;
        Log.d("check","onStart");

        try{
            // Get ON/OFF values sent from receiver ( AEScreenOnOffReceiver.java )
            screenOn = intent.getBooleanExtra("screen_state", false);

        }catch(Exception e){}

        //  Toast.makeText(getBaseContext(), "Service on start :"+screenOn,
        //Toast.LENGTH_SHORT).show();

        if (!screenOn) {

            // your code here
            // Some time required to start any service
            Toast.makeText(getBaseContext(), "Screen on, ", Toast.LENGTH_SHORT).show();
            Log.d("check", "Screen on");


        } else {

            // your code here
            // Some time required to stop any service to save battery consumption
            Toast.makeText(getBaseContext(), "Screen off,", Toast.LENGTH_SHORT).show();
            Log.d("check","Screen off");
        }
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        Log.d("check","onDestroy");
        Log.i("ScreenOnOff", "Service  distroy");
        if(mReceiver!=null)
            unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
