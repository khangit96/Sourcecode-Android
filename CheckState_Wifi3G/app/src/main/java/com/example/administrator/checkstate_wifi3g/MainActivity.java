package com.example.administrator.checkstate_wifi3g;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    WifiManager wifiManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Boolean check1=checkNetwork();
        if (check1==true){
            Toast.makeText(MainActivity.this,"Network Connected",Toast.LENGTH_LONG).show();
        } else {
            connectWifi();
            CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                  Boolean check2=checkNetwork();
                    if (check2==true) {
                        Toast.makeText(MainActivity.this, "Network Connected", Toast.LENGTH_LONG).show();
                    }
                    else{
                        disconnectWifi();
                        connect3G();
                    }
                }
            }.start();
        }

    }

    //Hàm kiểm tra tình trạng mạng (bao gồm cả wifi và 3g
    public boolean checkNetwork() {
        ConnectivityManager manager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
          //  Toast.makeText(MainActivity.this,"Network Connected",Toast.LENGTH_LONG).show();
            return true;
        } else {
          //  Toast.makeText(MainActivity.this,"Network Disconnected",Toast.LENGTH_LONG).show();
            return  false;

        }
    }


    //Hàm thực hiện kết nối 3g;
public  void connect3G(){
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
}

    //Hàm thực hiện kết nối wifi
    public void connectWifi(){
        wifiManager=(WifiManager)getSystemService(getApplicationContext().WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
    }

    //Hàm thực hiện disconnectWifi
    public void disconnectWifi(){
        wifiManager.setWifiEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
