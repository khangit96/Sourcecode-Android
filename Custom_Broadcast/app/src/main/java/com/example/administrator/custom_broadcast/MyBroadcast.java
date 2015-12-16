package com.example.administrator.custom_broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 12/15/2015.
 */
public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String Infor=intent.getStringExtra("Infor");
        Toast.makeText(context,Infor,Toast.LENGTH_LONG).show();
    }
}
