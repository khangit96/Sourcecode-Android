package com.demobiggestnumber2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.os.Handler;

import android.util.Log;

public class PlayGameActivity extends AppCompatActivity {
    Button[] bt = new Button[16];
    TextView tvScore, tvGameOver;
    ImageView imgGameOver;
    ArrayList<Integer> posRandomButtonArray = new ArrayList<>();
    Random random = new Random();
    private int biggestNumber = 0;
    private int countQuestion = 0;
    private int score = 0;
    private int posBiggestNumber = 0;
    private Timer timer;
    private int count = 0;
    ProgressBar progressBar;
    private boolean checkGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_play_game);
        addControls();
        processRandomButton(2, 100);
        startTimer(1000);
    }

    public void addControls() {
        tvScore = (TextView) findViewById(R.id.tvScore);
    //    imgGameOver = (ImageView) findViewById(R.id.imgGameOver);
        tvGameOver=(TextView)findViewById(R.id.tvGameOver);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        tvGameOver.setVisibility(View.INVISIBLE);
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

    /*Button CLick event*/
    public void Choose(View v) {
        String tag = (String) v.getTag();
        if (Integer.parseInt(bt[Integer.parseInt(tag)].getText().toString()) == biggestNumber) {
            countQuestion++;
            score += 5;
            progressBar.setProgress(100);
            tvScore.setText(Integer.toString(score));
            if (countQuestion <= 3) {
                processRandomButton(3, 100);
            } else if (countQuestion >= 4 && countQuestion <= 7) {
                processRandomButton(4, 100);
            } else if (countQuestion >= 8 && countQuestion <= 11) {
                processRandomButton(5, 100);
            } else if (countQuestion >= 12 && countQuestion <= 14) {
                processRandomButton(6, 100);
            } else if (countQuestion >= 15 && countQuestion <= 18) {
                processRandomButton(7, 100);
            } else if (countQuestion >= 19 && countQuestion <= 21) {
                processRandomButton(8, 100);
            } else if (countQuestion >= 22 && countQuestion <= 23) {
                processRandomButton(9, 100);
            } else if (countQuestion >= 24 && countQuestion <= 26) {
                processRandomButton(10, 100);
            } else {
                processRandomButton(16, 100);
            }
            stopTimer();
            startTimer(1000);

        } else {
            gameOver();
        }
    }

    /*Procces random button*/
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

    /*Reset data game*/
    public void goNextQuestion() {
        biggestNumber = 0;
        posRandomButtonArray.clear();
        defaultData();
    }

    /*reset data*/
    public void defaultData() {
        clearText();
        disableButton();

    }

    /*Clear text*/
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

    /*Disable button*/
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


    /*game over*/
    public void gameOver() {
        stopTimer();
        score = 0;
        bt[posBiggestNumber].setBackgroundResource(R.drawable.button_biggest_number);
        posRandomButtonArray.clear();
        disableButton();
        tvGameOver.setVisibility(View.VISIBLE);
        countQuestion = 0;
        biggestNumber = 0;
    }

    /*Restart game*/
    public void Restart(View v) {
        bt[posBiggestNumber].setBackgroundResource(R.drawable.button_selector);
        posBiggestNumber = 0;
        tvScore.setText("");
        clearText();
        disableButton();
        processRandomButton(2, 100);
        tvGameOver.setVisibility(View.INVISIBLE);
        startTimer(1000);
    }


    /*Start timer*/
    public void startTimer(int Speed) {
        count = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count = count + 10;
                progressBar.setProgress(100-(count*2));;
                Log.d("test", "" + count);
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

}
