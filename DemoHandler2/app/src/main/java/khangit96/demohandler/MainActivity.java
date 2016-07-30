package khangit96.demohandler;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyHandler myHandler;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        myHandler = new MyHandler();
        new MyThread().start();
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();
            tv.setText("" + bd.getInt("key", 0));
        }
    }

    class MyThread extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                if (i == 1) {
                    Message message = new Message();
                    Bundle bd = new Bundle();
                    bd.putInt("key", i);
                    message.setData(bd);
                    myHandler.sendMessage(message);
                }
            }
        }
    }
}
