package com.demopsimplenotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by Administrator on 5/16/2016.
 */
public class DeleteBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("test","Deleted");
    }
}
