package com.demoserviceinteractwithactivitybyusingbroadcastreceiver.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Administrator on 5/2/2016.
 */
public class PhoneStatus extends BroadcastReceiver {
    String phoneState = "";
    int test;

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            phoneState = "ringing";
            test = 10;

            Toast.makeText(context.getApplicationContext(), phoneState, Toast.LENGTH_LONG).show();
        }
        if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
            phoneState = "recieved";
            test = 11;
            Toast.makeText(context, "Call Recieved", Toast.LENGTH_LONG).show();
        }

        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
            test = 12;
            phoneState = "idle";
            Toast.makeText(context, "Phone Is Idle", Toast.LENGTH_LONG).show();
        }
        Intent iPhoneState = new Intent();
        iPhoneState.putExtra("phone_state",phoneState);
        iPhoneState.setAction("detect_phone_state");
        context.sendBroadcast(iPhoneState);

    }
}
