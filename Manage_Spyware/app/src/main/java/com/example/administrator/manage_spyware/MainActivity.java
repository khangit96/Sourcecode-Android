package com.example.administrator.manage_spyware;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvInbox, lvSentbox;
    ArrayList<String> arrFrom_Sentbox;
    ArrayList<String> arrTo_Sentbox;
    ArrayList<String>arrFrom_Inbox;
    ArrayList<String>arrTo_Inbox;
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
        //Tabhost
        TabHost tabHost = (TabHost) findViewById(R.id.tabManage);
        tabHost.setup();
        //tab inbox
        TabHost.TabSpec tabInbox = tabHost.newTabSpec("Inbox");
        tabInbox.setIndicator("Inbox");
        tabInbox.setContent(R.id.tabInbox);

        //tab sentbox
        TabHost.TabSpec tabSentbox = tabHost.newTabSpec("Sentbox");
        tabSentbox.setIndicator("Sentbox");
        tabSentbox.setContent(R.id.tabSentbox);

        //add tab
        tabHost.addTab(tabInbox);
        tabHost.addTab(tabSentbox);

        lvInbox = (ListView) findViewById(R.id.lvInbox);
        lvSentbox = (ListView) findViewById(R.id.lvSentbox);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocJson_Inbox().execute("http://khangserver-khangit.rhcloud.com/getInbox.php");
            }
        });
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new DocJson_Sentbox().execute("http://khangserver-khangit.rhcloud.com/getSentbox.php");
            }
        });
    }

    //Đọc json sentbox từ Server
    class DocJson_Sentbox extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        // thực hiện ở hàm này
        protected void onPostExecute(String s) {
            progressDialog.cancel();
            Integer count = 0;
            ArrayList<String> arrContent = new ArrayList<String>();
            arrFrom_Sentbox = new ArrayList<String>();
            arrTo_Sentbox = new ArrayList<String>();
            try {
                JSONArray mang = new JSONArray(s);
                for (int i = 0; i < mang.length(); i++) {
                    JSONObject ob = mang.getJSONObject(i);
                    arrContent.add(ob.getString("content"));
                    arrFrom_Sentbox.add(ob.getString("from"));
                    arrTo_Sentbox.add(ob.getString("to"));
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrContent);
                lvSentbox.setAdapter(adapter);
                registerForContextMenu(lvSentbox);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle("Detail");
        if(v.getId()==R.id.lvSentbox){
            menu.add(menu.NONE, 0, 0, "From: " + arrFrom_Sentbox.get(info.position));
            menu.add(menu.NONE, 1, 1, "To: " + arrTo_Sentbox.get(info.position));
        }
        else if(v.getId()==R.id.lvInbox) {
            menu.add(menu.NONE, 0, 0, "From: " + arrFrom_Inbox.get(info.position));
            menu.add(menu.NONE, 1, 1, "To: " + arrTo_Inbox.get(info.position));
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 0) {
            String na = "fskf";
        }
        return true;
    }

    //Đọc json inbox từ server
    class DocJson_Inbox extends AsyncTask<String, Integer, String> {
        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Loading");
            progressDialog.setCancelable(false);
            progressDialog.show();
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        // thực hiện ở hàm này
        protected void onPostExecute(String s) {
            progressDialog.cancel();
            ArrayList<String> arrContent = new ArrayList<String>();
            arrFrom_Inbox = new ArrayList<String>();
            arrTo_Inbox = new ArrayList<String>();
            try {
                JSONArray mang = new JSONArray(s);
                for (int i = 0; i < mang.length(); i++) {
                    JSONObject ob = mang.getJSONObject(i);
                    arrContent.add(ob.getString("content"));
                    arrFrom_Inbox.add(ob.getString("from"));
                    arrTo_Inbox.add(ob.getString("to"));
                }
                ArrayAdapter adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, arrContent);
                lvInbox.setAdapter(adapter);
                registerForContextMenu(lvInbox);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private static String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();

        // many of these calls can throw exceptions, so i've just
        // wrapped them all in one try/catch statement.
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
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
