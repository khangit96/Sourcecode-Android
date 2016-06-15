package com.demoanimation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    Button btXoayControll, btXoayManHinh,btXoay3sTatManHinh;
    Animation animation = null;
    LinearLayout ln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    private void addControls() {
        animation = AnimationUtils.loadAnimation(this, R.anim.xoay_control);
        btXoayControll = (Button) findViewById(R.id.btXoayControl);
        btXoayManHinh = (Button) findViewById(R.id.btXoayManHinh);
        btXoay3sTatManHinh = (Button) findViewById(R.id.btXoay3sTat);

        ln = (LinearLayout) findViewById(R.id.ln);
    }

    private void addEvents() {
        btXoayControll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btXoayControll.startAnimation(animation);
            }
        });
        btXoayManHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ln.startAnimation(animation);
            }
        });
        btXoay3sTatManHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btXoay3sTatManHinh.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

    }
}
