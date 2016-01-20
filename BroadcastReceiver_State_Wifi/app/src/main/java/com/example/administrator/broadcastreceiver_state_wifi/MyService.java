package com.example.administrator.broadcastreceiver_state_wifi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Administrator on 1/19/2016.
 */
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(getApplicationContext(), "Wifi changed", Toast.LENGTH_LONG).show();
    }
}
