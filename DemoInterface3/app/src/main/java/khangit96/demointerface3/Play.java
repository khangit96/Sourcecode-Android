package khangit96.demointerface3;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 7/23/2016.
 */
public class Play implements PlayListener {
    Context context;

    public Play(Context context) {
        this.context = context;
    }

    @Override
    public void play(String type) {
        Toast.makeText(context, type, Toast.LENGTH_LONG).show();
    }

    public void playMp3() {
        PlayMp3 playMp3 = new PlayMp3(new Play(context));
        playMp3.play();
    }

    public void playMp4() {
        PlayMp4 playMp4 = new PlayMp4(new Play(context));
        playMp4.play();
    }
}
