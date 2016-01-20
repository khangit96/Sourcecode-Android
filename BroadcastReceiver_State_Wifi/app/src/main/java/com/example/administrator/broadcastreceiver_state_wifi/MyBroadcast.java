package com.example.administrator.broadcastreceiver_state_wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 12/22/2015.
 */
public class MyBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent =new Intent();
        serviceIntent.setAction("MyService");//khởi tạo một dịch vụ đk định nghĩa trong AndroidMaintifest
        context.startService(serviceIntent);//bắt đầu service
    }
}
