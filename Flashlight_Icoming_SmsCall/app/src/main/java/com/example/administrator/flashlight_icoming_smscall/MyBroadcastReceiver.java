package com.example.administrator.flashlight_icoming_smscall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Administrator on 1/27/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    public  static final  String action_Sms="android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(action_Sms)){
            Intent intent_sms=new Intent();
            intent_sms.setAction("SmsService");
            context.startService(intent_sms);
        }
    }
}
