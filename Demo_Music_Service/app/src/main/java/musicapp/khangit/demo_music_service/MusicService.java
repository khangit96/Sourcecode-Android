package musicapp.khangit.demo_music_service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by Administrator on 2/12/2016.
 */
public class MusicService extends Service {
    MediaPlayer mediaPlayer;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer=MediaPlayer.create(this,R.raw.muadongkoem);
        mediaPlayer.start();
        Toast.makeText(getApplicationContext(),"Service is running",Toast.LENGTH_LONG).show();

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mediaPlayer.stop();
        Toast.makeText(getApplicationContext(),"Service is stopped!",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}
