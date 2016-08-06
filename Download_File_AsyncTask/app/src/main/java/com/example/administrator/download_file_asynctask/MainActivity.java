package com.example.administrator.download_file_asynctask;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Download().execute();
            }
        });

    }
    class Download extends AsyncTask<Void,Integer,Boolean>{
        //khỏi tạo các đối tượng
        private TextView tv;
        private ProgressDialog progressDialog;
        @Override
        //thực hiện đầu tiên
        protected void onPreExecute() {
            tv=(TextView)findViewById(R.id.tv);
            progressDialog=new ProgressDialog(MainActivity.this);
            super.onPreExecute();
        }
        //thực hiện chạy nền
        @Override
        protected Boolean doInBackground(Void... params) {
            for(int i=0;i<100;i++){
                try {
                    Thread.sleep(300);//cứ sau 300milisecond thì tiếp tục
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(i+1);//bắn ra các thông báo trong khi xủ lí
            }
            return true;
        }
        //phương thức này nhận các thông báo bắn ra từ publishProgress(i+1);
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setMessage("Loading "+String.valueOf(values[0])+"%");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setProgress(values[0]);
            progressDialog.show();
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            progressDialog.cancel();
            tv.setText("Download complete");
            super.onPostExecute(aBoolean);
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
