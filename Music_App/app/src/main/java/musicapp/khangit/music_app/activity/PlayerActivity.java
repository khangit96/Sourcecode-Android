package musicapp.khangit.music_app.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import musicapp.khangit.music_app.R;

public class PlayerActivity extends AppCompatActivity {

    TextView timeStartTv, timeEndTv;
    SeekBar seekBar;
    Button statusButton;
    boolean isPlay = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        timeStartTv = (TextView) findViewById(R.id.time_start);
        timeEndTv = (TextView) findViewById(R.id.time_end);
        seekBar = (SeekBar) findViewById(R.id.progress_player);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendCurrentPositionToService(seekBar.getProgress());
            }
        });
        statusButton = (Button) findViewById(R.id.status_button);

        statusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeStatusMedia();
            }
        });

        IntentFilter filter = new IntentFilter("SendDuration");
        filter.addAction("SendProgress");

        LocalBroadcastManager.getInstance(this).registerReceiver(createMessageReceiver(), filter);

        //Intent i = getIntent();
        // startService(createIntentService(i.getStringExtra("songName"), i.getStringExtra("songURL")));

    }

    private void sendCurrentPositionToService(int currentPosition) {
        Intent i = new Intent("SeekChange");

        i.putExtra("currentPosition", currentPosition);
        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private void changeStatusMedia() {
        Intent i = new Intent("ChangeStatusMedia");
        isPlay = !isPlay;

        statusButton.setBackgroundResource(isPlay ? R.drawable.ic_pause : R.drawable.ic_play);

        LocalBroadcastManager.getInstance(this).sendBroadcast(i);
    }

    private BroadcastReceiver createMessageReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (intent.getAction()) {
                    case "SendDuration":
                        int duration = intent.getIntExtra("duration", 0);
                        String endTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),
                                TimeUnit.MILLISECONDS.toSeconds(duration) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
                        timeEndTv.setText(endTime);
                        break;
                    case "SendProgress":
                        int progress = intent.getIntExtra("progress", 0);
                        int currentPosition = intent.getIntExtra("currentPosition", 0);

                        seekBar.setProgress(progress);
                        String startTime = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(currentPosition),
                                TimeUnit.MILLISECONDS.toSeconds(currentPosition) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(currentPosition)));
                        timeStartTv.setText(startTime);
                        break;
                }
            }
        };
    }

    /*private Intent createIntentService(String name, String url){
        Intent i = new Intent(this, PlayerService.class);
        i.putExtra("songName", name);
        i.putExtra("songURL", url);
        return i;
    }
    */
}
