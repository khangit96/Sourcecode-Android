package musicapp.khangit.broadcastreceiver_intentservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Administrator on 2/13/2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING))
        {
            //Toast.makeText(context, "Phone Is Ringing", Toast.LENGTH_LONG).show();//Khi có cuộc gọi đến
            context.startService(new Intent(context,CallService.class));
        }
    }
}
