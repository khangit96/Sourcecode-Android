package khangit96.quiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


public class MainActivity extends AppCompatActivity {
    Button btContinue;
    Button btShare;
    Button btHpwToPlay;
    SharedPreferences sp;
    TextView tvQuiz;
    /*Sound*/
    private SoundPool soundPool;
    private int soundIDPressButton, soundIDBackground;
    boolean plays = false, loaded = false;
    float actVolume, maxVolume, volume;
    AudioManager audioManager;
    int counter;
    LinearLayout ln;
    ImageView imgSound;
    ShareDialog shareDialog;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        init();
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result result) {
                Toast.makeText(getApplicationContext(), "Share successed!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "Share failed!", Toast.LENGTH_LONG).show();
            }
        });


    }

    public void init() {
        initView();
        initSound();
        sp = getSharedPreferences("CHECK", Context.MODE_PRIVATE);
        if (sp.getString("checkContinue", "").equals("ok")) {
            btContinue.setVisibility(View.VISIBLE);
        } else {
            btContinue.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) ln.getLayoutParams();
            marginParams.setMargins(0, 70, 0, 0);
           /* ViewGroup.LayoutParams layoutParams = ln.getLayoutParams(); // Step 1.
            LinearLayout.LayoutParams castLayoutParams = (LinearLayout.LayoutParams) layoutParams; // Step 2.
            castLayoutParams.gravity = Gravity.CENTER_VERTICAL; // Step 3.*/

        }
        playBackgroundSound();
    }

    /*putDataSharedPreferences*/
    public void putDataSharedPreferences(String name, String data) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, data);
        editor.commit();
    }

    private void initView() {
        //  imgSound = (ImageView) findViewById(R.id.imgSound);
        btContinue = (Button) findViewById(R.id.btContinue);
        btShare = (Button) findViewById(R.id.btShare);
        btHpwToPlay = (Button) findViewById(R.id.btHpwToPlay);
        btContinue.setVisibility(View.GONE);
        tvQuiz = (TextView) findViewById(R.id.tvQuiz);
        ln = (LinearLayout) findViewById(R.id.ln);
    }

    /*init sound*/
    public void initSound() {
        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        //Hardware buttons setting to adjust the media sound
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);

        // the counter will help us recognize the stream id of the sound played  now
        counter = 0;

        // Load the sounds
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
       /* soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                loaded = true;
            }
        });*/
        soundIDPressButton = soundPool.load(this, R.raw.press, 1);
        soundIDBackground = soundPool.load(this, R.raw.intro, 1);

    }

    public void sound(View v) {
        Toast.makeText(getApplicationContext(), "Ok", Toast.LENGTH_LONG).show();
    }

    /*Play press button sound*/
    public void playPressButtonSound() {
        soundPool.play(soundIDPressButton, volume, volume, 1, 0, 1f);
    }

    /*Play background sound*/
    public void playBackgroundSound() {
        soundPool.play(soundIDBackground, volume, volume, 1, -1, 1f);
    }

    /*Event play game*/
    public void newGame(View v) {
        // playPressButtonSound();
        Intent newGameIntent = new Intent(MainActivity.this, PlayGameActivity.class);
        newGameIntent.putExtra("game", "new game");
        startActivityForResult(newGameIntent, 0);
    }

    /*Continue game*/
    public void Continue(View v) {
        //    playPressButtonSound();
        Intent newGameIntent = new Intent(MainActivity.this, PlayGameActivity.class);
        newGameIntent.putExtra("game", "continue");
        startActivityForResult(newGameIntent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 0) {

                ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) ln.getLayoutParams();
                marginParams.setMargins(0, 0, 0, 100);
                btContinue.setVisibility(View.VISIBLE);
                putDataSharedPreferences("checkContinue", "ok");
            }
        } else {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) ln.getLayoutParams();
            marginParams.setMargins(0, 95, 0, 0);
            btContinue.setVisibility(View.GONE);
            putDataSharedPreferences("checkContinue", "null");
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void howToPlay(View v) {
        //    playPressButtonSound();
        startActivity(new Intent(MainActivity.this, HowToPlayActivity.class));

    }

    public void Share(View v) {

        if (ShareDialog.canShow(ShareLinkContent.class)) {
            // Ví dụ với link. Video và ảnh tương tự
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=khangit96.quiz"))
                    .build();

            shareDialog.show(linkContent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
