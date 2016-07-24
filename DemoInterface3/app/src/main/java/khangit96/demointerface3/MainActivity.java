package khangit96.demointerface3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btPLayMp3, btPlayMp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btPLayMp3 = (Button) findViewById(R.id.btPlayMp3);
        btPlayMp4 = (Button) findViewById(R.id.btPlayMp4);

    }

    public void Play(View v) {
        if (v.getId() == R.id.btPlayMp3) {
            Play play = new Play(getApplicationContext());
            play.playMp3();

        } else {
            Play play = new Play(getApplicationContext());
            play.playMp4();

        }
    }
}
