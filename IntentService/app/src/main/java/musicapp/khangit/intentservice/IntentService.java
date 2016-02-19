package musicapp.khangit.intentservice;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2/12/2016.
 */
public class IntentService extends android.app.IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public IntentService() {
        super("My Intent Service");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                Log.d("Test", "" + i);
                try {
                    wait(10000);
                } catch (Exception e) {

                }

            }
        }
    }

    @Override
    public void onDestroy() {
        Toast.makeText(getApplicationContext(),"Service is Stopped",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
