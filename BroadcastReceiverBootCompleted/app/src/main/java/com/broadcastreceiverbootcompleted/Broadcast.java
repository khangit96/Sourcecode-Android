package com.broadcastreceiverbootcompleted;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 5/4/2016.
 */
public class Broadcast extends BroadcastReceiver {
    Vibrator vibrator;
    private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
// Vibrate for 400 milliseconds
        v.vibrate(400);
        String action = intent.getAction();
        //f (action.equalsIgnoreCase(BOOT_ACTION)) {
            //check for boot complete event & start your service
            Toast.makeText(context.getApplicationContext(),"boot completed",Toast.LENGTH_LONG).show();
            Log.d("test","boot completed");
        //}

    }
}
