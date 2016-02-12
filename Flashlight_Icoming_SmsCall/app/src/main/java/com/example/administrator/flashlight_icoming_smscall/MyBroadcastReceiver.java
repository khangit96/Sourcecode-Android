package com.example.administrator.flashlight_icoming_smscall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 1/27/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    public  static final  String action_Sms="android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if(intent.getAction().equals(action_Sms)){
            Intent intent_sms=new Intent();
            intent_sms.setAction("SmsService");
            context.startService(intent_sms);
        }
        else {
            if (TelephonyManager.EXTRA_STATE_RINGING.equals(state) ||
                    TelephonyManager.EXTRA_STATE_IDLE.equals(state) ||
                    TelephonyManager.EXTRA_STATE_OFFHOOK.equals(state)) {
              //  intent_Call.setAction("CallService");
             //   intent_Call.putExtra("state",state);
                context.startService(new Intent(context,CallService.class).putExtra("state",state));
               // Log.e("error","error");
            }
        }

    }
}
