package khangit96.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;
import com.startapp.android.publish.splash.SplashConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class PlayGameActivity extends AppCompatActivity {
    float actVolume;
    public AudioManager audioManager;
    private int biggestNumber = 0;
    Button[] bt = new Button[16];
    ImageButton btContinue;
    ImageButton btPause;
    ImageButton btRestart;
    private boolean checkGameOver = true;
    private int countQuestion = 0;
    float maxVolume;
    private int posBiggestNumber;
    ArrayList<Integer> posRandomButtonArray = new ArrayList<>();
    ProgressBar progressBar;
    Random random = new Random();
    private int score = 0;
    private int soundIDGameOver;
    private int soundIDPause;
    private int soundIDPressButton;
    private int soundIDSuccess;
    private SoundPool soundPool;
    SharedPreferences sp;
    TextView tvGameOver;
    TextView tvPause;
    TextView tvScore;
    TextView tvHightScore;
    float volume;
    private Timer timer;
    private int count = 0;
    int counter;
    boolean plays = false, loaded = false;
    private AdView mAdView;
    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play_game);

     /*   MobileAds.initialize(getApplicationContext(), "ca-app-pub-3317370381024566~7562405938");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        StartAppSDK.init(this, "205723865", true);
        /*StartAppAd.showAd(this);*/
      //  StartAppAd.showSplash(this, savedInstanceState, new SplashConfig().setTheme(SplashConfig.Theme.GLOOMY));
        init();
    }

    public void init() {
        initView();
        initSound();
        sp = getSharedPreferences("CHECK", MODE_PRIVATE);
        if (!sp.getString("score", "").equals("null")) {
            String receivedData = getIntent().getStringExtra("game");
            if (receivedData.equals("continue")) {
                score = Integer.parseInt(sp.getString("score", ""));
                countQuestion = Integer.parseInt(sp.getString("question", ""));
                processRandomQuestion(countQuestion);
                tvScore.setText("" + score);
            } else if (receivedData.equals("new game")) {
                score = 0;
                tvScore.setText("" + score);
                processRandomButton(2, 100);
            }
        } else {
            processRandomButton(2, 100);
        }
        if (!sp.getString("highScore", "").equals("")) {
            tvHightScore.setText(sp.getString("highScore", ""));
        } else {
            tvHightScore.setText(Integer.toString(0));
        }

        startTimer(1000);
    }

    public void initView() {
        btRestart = (ImageButton) findViewById(R.id.btRestart);
        btPause = (ImageButton) findViewById(R.id.btPause);
        btContinue = (ImageButton) findViewById(R.id.btContinue);
        tvScore = (TextView) findViewById(R.id.tvScore);
        //   tvPause = (TextView) findViewById(R.id.tvPause);
        tvHightScore = (TextView) findViewById(R.id.tvHighScore);
        tvGameOver = (TextView) findViewById(R.id.tvGameOver);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvGameOver.setVisibility(View.GONE);
        btRestart.setVisibility(View.GONE);
        btContinue.setVisibility(View.GONE);
        //   tvPause.setVisibility(View.INVISIBLE);
        bt[0] = (Button) findViewById(R.id.bt0);
        bt[1] = (Button) findViewById(R.id.bt1);
        bt[2] = (Button) findViewById(R.id.bt2);
        bt[3] = (Button) findViewById(R.id.bt3);
        bt[4] = (Button) findViewById(R.id.bt4);
        bt[5] = (Button) findViewById(R.id.bt5);
        bt[6] = (Button) findViewById(R.id.bt6);
        bt[7] = (Button) findViewById(R.id.bt7);
        bt[8] = (Button) findViewById(R.id.bt8);
        bt[9] = (Button) findViewById(R.id.bt9);
        bt[10] = (Button) findViewById(R.id.bt10);
        bt[11] = (Button) findViewById(R.id.bt11);
        bt[12] = (Button) findViewById(R.id.bt12);
        bt[13] = (Button) findViewById(R.id.bt13);
        bt[14] = (Button) findViewById(R.id.bt14);
        bt[15] = (Button) findViewById(R.id.bt15);
    }

    public void initSound() {
        // AudioManager audio settings for adjusting the volume
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        actVolume = (float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        maxVolume = (float) audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        volume = actVolume / maxVolume;

        //Hardware buttons setting to adjust the media sound
        this.setVolumeControlStream(AudioManager.STREAM_MUSIC);
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundIDSuccess = soundPool.load(this, R.raw.success, 1);
        soundIDGameOver = soundPool.load(this, R.raw.gover, 1);
        soundIDPause = soundPool.load(this, R.raw.pause, 1);
        soundIDPressButton = soundPool.load(this, R.raw.press, 1);
    }

    public void putDataSharedPreferences(String name, String data) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(name, data);
        editor.commit();
    }

    public void Choose(View v) {
        if (Integer.parseInt(bt[Integer.parseInt((String) v.getTag())].getText().toString()) == biggestNumber) {
            clearText();
            playSuccessSound();
            countQuestion++;
            score += 5;
            processRandomQuestion(countQuestion);
            tvScore.setText(Integer.toString(score));
            stopTimer();
            startTimer(1000);
        } else {
            checkGameOver = false;
            gameOver();
        }

    }

    public void processRandomQuestion(int question) {
        if (question <= 3) {
            processRandomButton(3, 100);
        } else if (question >= 4 && question <= 7) {
            processRandomButton(4, 100);
        } else if (question >= 8 && question <= 11) {
            processRandomButton(5, 100);
        } else if (question >= 12 && question <= 14) {
            processRandomButton(6, 100);
        } else if (question >= 15 && question <= 18) {
            processRandomButton(7, 100);
        } else if (question >= 19 && question <= 21) {
            processRandomButton(8, 100);
        } else if (question >= 22 && question <= 23) {
            processRandomButton(9, 100);
        } else if (question >= 24 && question <= 26) {
            processRandomButton(10, 100);
        } else if (question >= 27 && question <= 28) {
            processRandomButton(11, 100);
        } else if (question >= 29 && question <= 30) {
            processRandomButton(12, 100);
        } else if (question == 31) {
            processRandomButton(13, 100);
        } else if (question == 32) {
            processRandomButton(14, 100);
        } else if (question == 33) {
            processRandomButton(15, 100);
        } else {
            processRandomButton(16, 100);
        }
    }

    public void playSuccessSound() {
        soundPool.play(soundIDSuccess, volume, volume, 1, 0, 1.0f);
    }

    public void playGameOverSound() {
        soundPool.play(soundIDGameOver, volume, volume, 1, 0, 1f);
    }

    public void playPauseSound() {
        soundPool.play(soundIDPause, volume, volume, 1, 0, 1.0f);
    }

    public void playPressButtonSound() {
        soundPool.play(soundIDPressButton, volume, volume, 1, 0, 1.0f);
    }

    public void processRandomButton(int pos, int range) {
        goNextQuestion();
        for (int i = 0; i < pos; i++) {
            int r = random.nextInt(16);
            while (posRandomButtonArray.contains(r)) {
                r = random.nextInt(16);
            }
            posRandomButtonArray.add(r);
        }
        for (int i = 0; i < posRandomButtonArray.size(); i++) {
            int ranNumber = random.nextInt(1000);
            if (ranNumber > biggestNumber) {
                biggestNumber = ranNumber;
            }
            bt[posRandomButtonArray.get(i)].setText(Integer.toString(ranNumber));
            bt[posRandomButtonArray.get(i)].setEnabled(true);
            bt[posRandomButtonArray.get(i)].setBackgroundResource(R.drawable.button_random);
        }
        for (int i = 0; i < bt.length; i++) {
            if (bt[i].getText().toString() == Integer.toString(biggestNumber)) {
                posBiggestNumber = i;
                return;
            }
        }
    }

    public void goNextQuestion() {
        biggestNumber = 0;
        posRandomButtonArray.clear();
        defaultData();
    }

    public void defaultData() {
        clearText();
        disableButton();
    }

    public void clearText() {
        bt[0].setText("");
        bt[1].setText("");
        bt[2].setText("");
        bt[3].setText("");
        bt[4].setText("");
        bt[5].setText("");
        bt[6].setText("");
        bt[7].setText("");
        bt[8].setText("");
        bt[9].setText("");
        bt[10].setText("");
        bt[11].setText("");
        bt[12].setText("");
        bt[13].setText("");
        bt[14].setText("");
        bt[15].setText("");

        bt[0].setBackgroundResource(R.drawable.button_selector);
        bt[1].setBackgroundResource(R.drawable.button_selector);
        bt[2].setBackgroundResource(R.drawable.button_selector);
        bt[3].setBackgroundResource(R.drawable.button_selector);
        bt[4].setBackgroundResource(R.drawable.button_selector);
        bt[5].setBackgroundResource(R.drawable.button_selector);
        bt[6].setBackgroundResource(R.drawable.button_selector);
        bt[7].setBackgroundResource(R.drawable.button_selector);
        bt[8].setBackgroundResource(R.drawable.button_selector);
        ;
        bt[9].setBackgroundResource(R.drawable.button_selector);
        bt[10].setBackgroundResource(R.drawable.button_selector);
        bt[11].setBackgroundResource(R.drawable.button_selector);
        bt[12].setBackgroundResource(R.drawable.button_selector);
        bt[13].setBackgroundResource(R.drawable.button_selector);
        bt[14].setBackgroundResource(R.drawable.button_selector);
        bt[15].setBackgroundResource(R.drawable.button_selector);
    }

    public void disableButton() {
        bt[0].setEnabled(false);
        bt[1].setEnabled(false);
        bt[2].setEnabled(false);
        bt[3].setEnabled(false);
        bt[4].setEnabled(false);
        bt[5].setEnabled(false);
        bt[6].setEnabled(false);
        bt[7].setEnabled(false);
        bt[8].setEnabled(false);
        bt[9].setEnabled(false);
        bt[10].setEnabled(false);
        bt[11].setEnabled(false);
        bt[12].setEnabled(false);
        bt[13].setEnabled(false);
        bt[14].setEnabled(false);
        bt[15].setEnabled(false);
    }

    public void gameOver() {
        if (!sp.getString("highScore", "").equals("")) {
            int highScore = Integer.parseInt(sp.getString("highScore", ""));
            if (score > highScore) {
                putDataSharedPreferences("highScore", Integer.toString(score));
                tvHightScore.setText(Integer.toString(score));
            }
        } else {
            putDataSharedPreferences("highScore", Integer.toString(score));
            tvHightScore.setText(Integer.toString(score));
        }

        stopTimer();
        score = 0;
        bt[posBiggestNumber].setBackgroundResource(R.drawable.button_game_over);
        bt[posBiggestNumber].setText("" + biggestNumber);
        //  bt[posBiggestNumber].setTextSize(30);
        posRandomButtonArray.clear();
        disableButton();
        tvGameOver.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        tvGameOver.setVisibility(View.VISIBLE);
        countQuestion = 0;
        biggestNumber = 0;
        btRestart.startAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        btRestart.setVisibility(View.VISIBLE);
        btPause.setEnabled(false);
        btContinue.setVisibility(View.GONE);
        playGameOverSound();
    }

    public void Restart(View v) {
        // bt[posBiggestNumber].setTextSize(20);
        tvGameOver.setText("Game Over");
        this.checkGameOver = true;
        this.btRestart.setVisibility(View.GONE);
        this.btPause.setVisibility(View.VISIBLE);
        btPause.setEnabled(true);
        this.bt[this.posBiggestNumber].setBackgroundResource(R.drawable.button_selector);
        this.tvScore.setText("0");
        this.posBiggestNumber = 0;
        clearText();
        disableButton();
        processRandomButton(2, 100);
        this.tvGameOver.setVisibility(View.INVISIBLE);
        startTimer(1000);
        playPressButtonSound();
    }

    public void Pause(View v) {
        clearText();
        disableButton();
        //   this.tvPause.setVisibility(View.VISIBLE);
        this.btContinue.setVisibility(View.VISIBLE);
        this.btPause.setVisibility(View.GONE);
        stopTimer();
        playPauseSound();
    }

    public void Continue(View v) {
        this.btPause.setVisibility(View.VISIBLE);
        this.btContinue.setVisibility(View.GONE);
        //   this.tvPause.setVisibility(View.INVISIBLE);
        continueTimer(1000);
        processRandomQuestion(this.countQuestion);
        playPressButtonSound();
    }

    private void continueTimer(int speed) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count = count + 10;
                progressBar.setProgress(100 - (count * 2));
                if (count == 50) {
                    checkGameOver = false;
                    progressBar.setProgress(0);
                    mHandler.obtainMessage(1).sendToTarget();

                }

            }
        }, 1, speed);//speed coundown time
    }

    /*Start timer*/
    public void startTimer(int Speed) {
        count = 0;
        progressBar.setProgress(100);
        timer = new Timer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (count != 0) {
                    progressBar.setProgress(100 - (count * 3));
                    if (count == 30) {
                        checkGameOver = false;
                        progressBar.setProgress(0);
                        mHandler.obtainMessage(1).sendToTarget();

                    }

                }
                count += 10;

            }
        }, 1, Speed);//speed coundown time

    }

    /*Handler*/
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            tvGameOver.setText("Time Out!");
            gameOver();
        }
    };

    /*Stop timer*/
    public void stopTimer() {
        timer.cancel();
    }


    public void onBackPressed() {
        StartAppAd.onBackPressed(this);
        stopTimer();
        if (this.checkGameOver) {
            putDataSharedPreferences("score", Integer.toString(this.score));
            putDataSharedPreferences("question", Integer.toString(countQuestion));
            setResult(RESULT_OK, new Intent());
        } else {
            putDataSharedPreferences("score", "null");
        }
        finish();
        super.onBackPressed();
    }

    private void takeScreenshot() {

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
            // openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }


}

