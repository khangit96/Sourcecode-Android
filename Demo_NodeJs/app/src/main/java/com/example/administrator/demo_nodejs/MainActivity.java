package com.example.administrator.demo_nodejs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    EditText ed;
    Button bt,btChat;
    ListView lvUser,lvChat;
    ArrayList<String>mangUsernames;
    ArrayList<String>mangChat;
    private Socket mSocket;
    {
        try {
        // mSocket = IO.socket("http://192.168.56.1:5000");
         // mSocket = IO.socket("http://demo-nodejs-android.herokuapp.com/");
            mSocket = IO.socket("http://demo-khangit.rhcloud.com/");
        } catch (URISyntaxException e) {}
    }
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
        mSocket.connect();
        mSocket.on("ketquaDangKyUn", onNewMessage_DangKyUsername);
        mSocket.on("server-gui-usernames", onNewMessage_DanhSachUsername);
        mSocket.on("server-gui-tinchat", onNewMessage_DanhSachChat);
        mSocket.emit("client-gui-username", "open");
        mangChat=new ArrayList<String>();
        bt=(Button)findViewById(R.id.bt);
        btChat=(Button)findViewById(R.id.btChat);
        ed=(EditText)findViewById(R.id.ed);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("client-gui-username", ed.getText().toString());

            }
        });
        btChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSocket.emit("client-gui-chat", ed.getText().toString());
            }
        });
        lvUser=(ListView)findViewById(R.id.lvUser);
        lvChat=(ListView)findViewById(R.id.lvChat);

    }
    private Emitter.Listener onNewMessage_DangKyUsername = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung = data.getString("noidung");
                        if(noidung=="true"){
                            Toast.makeText(getApplicationContext(),"Đăng kí thành công",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Đăng kí thất bại",Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
    //
    private Emitter.Listener onNewMessage_DanhSachUsername = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    JSONArray noidung;
                    try {
                        noidung = data.getJSONArray("danhsach");
                        mangUsernames=new ArrayList<String>();
                        for(int i=0;i<noidung.length();i++){

                            mangUsernames.add(noidung.get(i).toString());
                        }
                        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,mangUsernames);
                        lvUser.setAdapter(adapter);
                            //Toast.makeText(getApplicationContext(),""+noidung.length(),Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
    //
    private Emitter.Listener onNewMessage_DanhSachChat = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String noidung;
                    try {
                        noidung =data.getString("tinchat");
                        mangChat.add(noidung);
                        ArrayAdapter adapter=new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,mangChat);
                        lvChat.setAdapter(adapter);
                        //Toast.makeText(getApplicationContext(),""+noidung.length(),Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        return;
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
