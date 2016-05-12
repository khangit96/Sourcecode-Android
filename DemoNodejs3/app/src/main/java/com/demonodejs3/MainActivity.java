package com.demonodejs3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edChat;
    ListView lv;
    ArrayAdapter adapter;
    ArrayList<String>arrUsername=new ArrayList<>();
    private Socket msocket;

    {
        try {
            msocket = IO.socket("http://demo-khangit.rhcloud.com/");
        } catch (URISyntaxException e) {
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edChat=(EditText)findViewById(R.id.edChat);
        lv=(ListView)findViewById(R.id.lv);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrUsername);
        lv.setAdapter(adapter);
        msocket.connect();
        msocket.on("serverSendStatusConnectDatabase",onMessage);
    }
    public void Chat(View v){
        msocket.emit("serverSendChat",edChat.getText().toString());
    }
    /*Hàm lắng nghe đầu tiên để thông báo nếu kết nối với database thành công hay chưa*/
    private Emitter.Listener onMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray noiDung;
                    try {
                        arrUsername.clear();
                        noiDung =data.getJSONArray("noiDung");
                        int count=0;
                        for(int i=0;i<noiDung.length();i++){
                            arrUsername.add(noiDung.get(i).toString());
                            //count++;
                        }
                       adapter.notifyDataSetChanged();
                      //  arrUsername.clear();
                       // Toast.makeText(getApplicationContext(),""+count, Toast.LENGTH_LONG).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
    };

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
