package com.demobiggestnumber2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 6/5/2016.
 */
public class PlayGameActivity extends AppCompatActivity {
    float actVolume;
    public AudioManager audioManager;
    private int biggestNumber = 0;
    Button[] bt = new Button[16];
    Button btContinue;
    Button btPause;
    Button btRestart;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_play_game);
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
        btRestart = (Button) findViewById(R.id.btRestart);
        btPause = (Button) findViewById(R.id.btPause);
        btContinue = (Button) findViewById(R.id.btContinue);
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvPause = (TextView) findViewById(R.id.tvPause);
        tvHightScore = (TextView) findViewById(R.id.tvHighScore);
        tvGameOver = (TextView) findViewById(R.id.tvGameOver);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvGameOver.setVisibility(View.INVISIBLE);
        btRestart.setVisibility(View.GONE);
        btContinue.setVisibility(View.GONE);
        tvPause.setVisibility(View.INVISIBLE);
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
        Editor editor = sp.edit();
        editor.putString(name, data);
        editor.commit();
    }

    public void Choose(View v) {
        if (Integer.parseInt(bt[Integer.parseInt((String) v.getTag())].getText().toString()) == biggestNumber) {
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
        } else if (question < 24 || question > 26) {
            processRandomButton(16, 100);
        } else {
            processRandomButton(10, 100);
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
            int ranNumber = random.nextInt(50);
            if (ranNumber > biggestNumber) {
                biggestNumber = ranNumber;
            }
            bt[posRandomButtonArray.get(i)].setText(Integer.toString(ranNumber));
            bt[posRandomButtonArray.get(i)].setEnabled(true);
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
        bt[posBiggestNumber].setBackgroundResource(R.drawable.button_biggest_number);
        posRandomButtonArray.clear();
        disableButton();
        tvGameOver.setVisibility(View.VISIBLE);
        countQuestion = 0;
        biggestNumber = 0;
        btRestart.setVisibility(View.VISIBLE);
        btPause.setVisibility(View.GONE);
        btContinue.setVisibility(View.GONE);
        playGameOverSound();
    }

    public void Restart(View v) {
        this.checkGameOver = true;
        this.btRestart.setVisibility(View.GONE);
        this.btPause.setVisibility(View.VISIBLE);
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
        this.tvPause.setVisibility(View.VISIBLE);
        this.btContinue.setVisibility(View.VISIBLE);
        this.btPause.setVisibility(View.GONE);
        stopTimer();
        playPauseSound();
    }

    public void Continue(View v) {
        this.btPause.setVisibility(View.VISIBLE);
        this.btContinue.setVisibility(View.GONE);
        this.tvPause.setVisibility(View.INVISIBLE);
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
                    progressBar.setProgress(0);
                    mHandler.obtainMessage(1).sendToTarget();

                }

            }
        }, 1, speed);//speed coundown time
    }

    /*Start timer*/
    public void startTimer(int Speed) {
        count = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count = count + 10;
                progressBar.setProgress(100 - (count * 2));
                if (count == 50) {
                    progressBar.setProgress(0);
                    mHandler.obtainMessage(1).sendToTarget();

                }

            }
        }, 1, Speed);//speed coundown time

    }

    /*Handler*/
    public Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            gameOver();
        }
    };

    /*Stop timer*/
    public void stopTimer() {
        timer.cancel();
    }


    public void onBackPressed() {
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


}
