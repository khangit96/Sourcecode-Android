package khangit96.demointerface2;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button playMp3, playMp4, playVideo;
    PlayMusic playMusic = new PlayMusic();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        playMusic.setPlayListener(new PlayListener() {
            @Override
            public void play(String type) {
                Toast.makeText(getApplicationContext(), type, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void initView() {
        playMp3 = (Button) findViewById(R.id.playMp3);
        playMp4 = (Button) findViewById(R.id.playMp4);
        playVideo = (Button) findViewById(R.id.playVideo);
        playMp3.setOnClickListener(clickListener);
        playMp4.setOnClickListener(clickListener);
        playVideo.setOnClickListener(clickListener);
    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        public void onClick(View v) {
            if (v.getId() == R.id.playMp3) {
                playMusic.playMp3();
            } else if (v.getId() == R.id.playMp4) {
                playMusic.playMp4();

            } else {
                playMusic.playVideo();

            }
        }
    };
}
