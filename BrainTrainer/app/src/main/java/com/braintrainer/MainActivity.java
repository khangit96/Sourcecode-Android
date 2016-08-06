package com.braintrainer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button bt0, bt1, bt2, bt3,btNewGame;
    TextView tvPoint, tvSum, tvGameOver;
    Random rand;
    int locationOfCorrectAnswer;
    int incorrectAnswers;
    int a, b;
    ArrayList<Integer> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        init();
        process();

    }

    /*Initalze*/
    public void init() {
        tvPoint = (TextView) findViewById(R.id.tvPoint);
        tvSum = (TextView) findViewById(R.id.tvSum);
        tvGameOver = (TextView) findViewById(R.id.tvGameOver);
        tvGameOver.setVisibility(View.INVISIBLE);
        bt0 = (Button) findViewById(R.id.bt0);
        bt1 = (Button) findViewById(R.id.bt1);
        bt2 = (Button) findViewById(R.id.bt2);
        bt3 = (Button) findViewById(R.id.bt3);
        btNewGame=(Button)findViewById(R.id.btNewGame);
        btNewGame.setVisibility(View.INVISIBLE);
        rand = new Random();
        answers = new ArrayList<>();

    }

    /*Process*/
    public void process() {
        answers.clear();
        a = rand.nextInt(21);
        b = rand.nextInt(21);
        locationOfCorrectAnswer = rand.nextInt(4);
        tvSum.setText(String.valueOf(a) + "+" + String.valueOf(b));
        for (int i = 0; i < 4; i++) {
            if (i == locationOfCorrectAnswer) {//save position of correct answer
                answers.add(a + b);
            } else {//save position of incorrect answer
                incorrectAnswers = rand.nextInt(41);
                while (incorrectAnswers == a + b) {
                    incorrectAnswers = rand.nextInt(41);
                }
                answers.add(incorrectAnswers);
            }
        }
        bt0.setText(Integer.toString(answers.get(0)));
        bt1.setText(Integer.toString(answers.get(1)));
        bt2.setText(Integer.toString(answers.get(2)));
        bt3.setText(Integer.toString(answers.get(3)));
    }

    /*Process for game over*/
    public void gameOver() {
        tvGameOver.setVisibility(View.VISIBLE);
        bt0.setEnabled(false);
        bt1.setEnabled(false);
        bt2.setEnabled(false);
        bt3.setEnabled(false);
        btNewGame.setVisibility(View.VISIBLE);
    }

    /*Event button new game*/
    public void newGame(View v){
        tvGameOver.setVisibility(View.INVISIBLE);
        btNewGame.setVisibility(View.INVISIBLE);
        bt0.setEnabled(true);
        bt1.setEnabled(true);
        bt2.setEnabled(true);
        bt3.setEnabled(true);
        process();
    }

    /*Event click choose answer*/
    public void chooseAnswer(View v) {

        if (v.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))) {//if user choose correct answer
            process();//generate question
            Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_LONG).show();
        } else {//incorrect answer
            gameOver();
        }

    }


}
