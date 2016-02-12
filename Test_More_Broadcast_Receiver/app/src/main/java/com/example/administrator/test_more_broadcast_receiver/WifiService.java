package com.example.administrator.test_more_broadcast_receiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Administrator on 1/19/2016.
 */
public class WifiService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(getApplicationContext(),"Received sms",Toast.LENGTH_LONG).show();
        super.onStart(intent, startId);
        //Toast.makeText(getApplicationContext(), "Wifi changed", Toast.LENGTH_LONG).show();
    }
}
