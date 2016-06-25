package khangit96.demointerface1;

import android.util.Log;

/**
 * Created by Administrator on 6/23/2016.
 */
public class PlayMp4 implements PlayHandle {
    @Override
    public void play() {
        Log.d("interface", "Play mp4");
    }
}
