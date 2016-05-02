package com.demoserviceinteractwithactivitybyusingbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        BroacastPhoneState broacastPhoneState = new BroacastPhoneState();
        IntentFilter intentFilter = new IntentFilter("detect_phone_state");
        registerReceiver(broacastPhoneState, intentFilter);
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

    class BroacastPhoneState extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String state=intent.getStringExtra("phone_state");
            if(state.equals("ringing")){
                tvStatus.setText(state);
            }
            else if(state.equals("recieved")){
                tvStatus.setText(state);
            }
            else if(state.equals("idle")){
                tvStatus.setText(state);
            }

        }
    }
}
