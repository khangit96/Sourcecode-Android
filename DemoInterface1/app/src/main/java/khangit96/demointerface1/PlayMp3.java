package khangit96.demointerface1;

import android.widget.Toast;

import  android.util.Log;

/**
 * Created by Administrator on 6/23/2016.
 */
public class PlayMp3 implements PlayHandle {
    @Override
    public void play() {
        Log.d("interface","Play mp3");
    }
}
