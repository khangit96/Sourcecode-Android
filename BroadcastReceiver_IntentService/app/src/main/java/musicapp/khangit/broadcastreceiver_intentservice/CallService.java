package musicapp.khangit.broadcastreceiver_intentservice;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2/13/2016.
 */
public class CallService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public CallService() {
        super("My");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this){
            try {
               Log.d("Test","Service is running");
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"Service is Stopped",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
