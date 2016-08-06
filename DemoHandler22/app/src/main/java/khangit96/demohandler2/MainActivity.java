package khangit96.demohandler2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Handler myHandler = new Handler();
    Thread myThread;
    TextView tv;
    MyRunnable myRunnable = new MyRunnable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        myThread = new Thread(myRunnable);
        myThread.start();
    }


    class MyRunnable implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                try {
                    Thread.sleep(1000);
                    if (i == 3) {
                        myHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tv.setText("Completed!");
                            }
                        });
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
