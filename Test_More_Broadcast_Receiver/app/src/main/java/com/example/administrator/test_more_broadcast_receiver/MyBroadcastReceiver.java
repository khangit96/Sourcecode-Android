package com.example.administrator.test_more_broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Administrator on 1/18/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    public static final String Action_Connected = "android.intent.action.ACTION_POWER_CONNECTED";
    public static final String Action_Wifi = "android.net.wifi.STATE_CHANGE";
    public static final String Action_Sms = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
       /* if (intent.getAction().equals(Action_Connected)){
            Intent serviceConnect=new Intent();
            serviceConnect.setAction("ConnectService");
            context.startService(serviceConnect);

        }
        */
    /*  if(intent.getAction().equals(Action_Wifi)) {
          Intent serviceWifi=new Intent();
          serviceWifi.setAction("WifiService");
          context.startService(serviceWifi);
        }
        */
        if (intent.getAction().equals(Action_Sms)) {
            Intent serviceWifi = new Intent();
            serviceWifi.setAction("WifiService");
            context.startService(serviceWifi);
        } else {
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                Intent serviceConnect = new Intent();
                serviceConnect.setAction("ConnectService");
                context.startService(serviceConnect);
            }
        }


    }
}
