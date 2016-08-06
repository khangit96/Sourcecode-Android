package com.demomagicprogressbarwidget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.liulishuo.magicprogresswidget.MagicProgressBar;
import com.liulishuo.magicprogresswidget.MagicProgressCircle;

import java.util.Random;

import cn.dreamtobe.percentsmoothhandler.ISmoothTarget;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignViews();
        anim();
    }
    private boolean isAnimActive;
    private final Random random = new Random();
    private void anim() {
        final int ceil = 200;
        final int progress = random.nextInt(ceil);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(

                ObjectAnimator.ofFloat(demo2Mpb, "percent", 0, random.nextInt(ceil) / 100f)

        );
        set.setDuration(600);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                isAnimActive = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                isAnimActive = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.setInterpolator(new AccelerateInterpolator());
        set.start();
    }
    public void onReRandomPercent(final View view) {
        if (isAnimActive) {
            return;
        }
        anim();
    }
    public void onClickIncreaseSmoothly(final View view) {
        if (isAnimActive) {
            return;
        }

       /* float mpcPercent = getIncreasedPercent(demoMpc);
        demoMpc.setSmoothPercent(mpcPercent);
        demoTv.setSmoothPercent(mpcPercent);*/
        // Just for demo smoothly process to the target percent in 3000ms duration.

        demo2Mpb.setSmoothPercent(getIncreasedPercent(demo2Mpb));
    }

    private float getIncreasedPercent(ISmoothTarget target) {
        float increasedPercent = target.getPercent() + 0.8f;

        return Math.min(1, increasedPercent);
    }

    private MagicProgressBar demo2Mpb;
    private void assignViews() {

        demo2Mpb = (MagicProgressBar) findViewById(R.id.demo_2_mpb);

    }

}
