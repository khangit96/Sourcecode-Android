package khangit96.demointentserviceinteractactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MyTestReceiver.Receiver {
    TextView tv;
    MyTestReceiver myTestReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        myTestReceiver = new MyTestReceiver(new Handler());
        myTestReceiver.setReceiver(MainActivity.this);
        MyBroadcast myBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter("receiver");
        registerReceiver(myBroadcast, intentFilter);
    }

    public void Start(View v) {
        startService(new Intent(MainActivity.this, DemoIntentService.class));
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {

    }

    class MyBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            tv.setText("Finished");
        }
    }

}
