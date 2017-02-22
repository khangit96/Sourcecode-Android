package com.demoandroinodemcu;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;

import at.grabner.circleprogress.CircleProgressView;

public class SplashScreenActivity extends AppCompatActivity {
    CircleProgressView circleProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        circleProgressView = (CircleProgressView) findViewById(R.id.circleView);
        circleProgressView.spin();
        circleProgressView.setShowTextWhileSpinning(true); // Show/hide text in spinning mode
        circleProgressView.setText("Connecting ...");
        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                finish();
            }
        }.start();
    }
}
