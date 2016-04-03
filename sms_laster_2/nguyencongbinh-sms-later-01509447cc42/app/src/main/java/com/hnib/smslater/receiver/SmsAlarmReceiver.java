package com.hnib.smslater.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hnib.smslater.model.SmsPojo;
import com.hnib.smslater.service.SmsService;
import com.hnib.smslater.utils.AppConstants;

/**
 * Created by caucukien on 13/12/2015.
 */
public class SmsAlarmReceiver extends BroadcastReceiver {
    private SmsPojo currentSms;
    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        long idSms = intent.getLongExtra(AppConstants.KEY_CURRENT_SMS, 0L);
        Log.d("binh", "receive sms id: " + idSms);

        Intent myIntent = new Intent(context, SmsService.class);
        myIntent.putExtra(AppConstants.KEY_CURRENT_SMS, idSms);
        context.startService(myIntent);

    }

}
