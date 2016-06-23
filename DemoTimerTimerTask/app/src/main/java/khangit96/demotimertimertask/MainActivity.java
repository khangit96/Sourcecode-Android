package khangit96.demotimertimertask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;
public class MainActivity extends AppCompatActivity {
    Timer timer = null;
    TimerTask timerTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       Log.d("timer","OK");
                    }
                });
            }
        };
        timer=new Timer();
        timer.schedule(timerTask,0,3000);
    }
}
