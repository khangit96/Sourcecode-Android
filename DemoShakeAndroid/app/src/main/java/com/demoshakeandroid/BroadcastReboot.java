package com.demoshakeandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 5/4/2016.
 */
public class BroadcastReboot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context.getApplicationContext(), "Boot completed", Toast.LENGTH_LONG).show();

        context.startService(new Intent(context, ShakerService.class));
    }
}
