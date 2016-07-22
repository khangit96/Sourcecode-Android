package khangit96.demointentserviceinteractactivity;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Administrator on 7/21/2016.
 */
public class DemoIntentService extends IntentService {
    public DemoIntentService() {
        super("De mo");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent();
        intent.setAction("receiver");
        sendBroadcast(intent);
    }
}
