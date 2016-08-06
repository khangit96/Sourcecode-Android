package com.demojson2;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv = (TextView) findViewById(R.id.tv);
        progressDialog = new ProgressDialog(this);
        new DoGetData().execute("http://khangserver-khangit.rhcloud.com/json.php");
    }

     /*Way 1*/
   /* class DoGetData extends AsyncTask<Void, Void, Void> {
         String urlLink;
        String result = "";

        public DoGetData(String URL) {
            this.urlLink = URL;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL(urlLink);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                int byteCharater;
                while ((byteCharater = is.read()) != -1) {
                    result += (char) byteCharater;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tv.setText(result);
        }
    }*/

    /*Way 2*/
    class DoGetData extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Loading...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try {
              /*  URL url = new URL(params[0]);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                int byteCharater;
                int loadByte = 0;
                int totaLe = conn.getContentLength();
                while ((byteCharater = is.read()) != -1) {
                    result += (char) byteCharater;
                    //loadByte++;
                    //int progress = loadByte * 100 / totaLe;
                   // publishProgress(progress);
                }*/
                for(int i=1;i<100;i++){
                    publishProgress(i);
                    Thread.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            super.onPostExecute(s);
            tv.setText(s);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int p=values[0];
            progressDialog.setProgress(p);
        }
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
