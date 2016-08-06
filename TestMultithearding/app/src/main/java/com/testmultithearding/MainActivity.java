package com.testmultithearding;

import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tvTimer,tvAsyntask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvTimer= (TextView) findViewById(R.id.tvTimer);
        tvAsyntask= (TextView) findViewById(R.id.tvAsynTask);
        CountDownTimer countDownTimer=new CountDownTimer(100000,1000) {
            @Override
            public void onTick(long l) {
                tvTimer.setText("Timer:"+l/1000);
            }

            @Override
            public void onFinish() {
            tvTimer.setText("Finish");
            }
        }.start();
        new Test().execute();
    }
    class Test extends AsyncTask<Void,Void,Void>{
        int count=0;
        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<100;i++){
                count++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            tvAsyntask.setText("Finished: "+Integer.toString(count));
        }
    }
}
