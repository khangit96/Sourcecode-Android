package com.demogamebubble;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    ObjectAnimator objectAnimator;
    ViewGroup.LayoutParams layoutParams;
    LinearLayout layoutBubble;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = (ImageView) findViewById(R.id.img);
        img.setVisibility(View.GONE);
    }

    public void Create(View v) {
        img.setVisibility(View.VISIBLE);
        for(int i=0;i<5;i++) {
            objectAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.bubbleanimator);
            objectAnimator.setDuration(3000);
            objectAnimator.setTarget(img);
            objectAnimator.start();
        }
    }

    public ImageView getImageView() {
        ImageView img = new ImageView(MainActivity.this);
        img.setX(300);
        img.setImageResource(R.drawable.bubble);
        return img;
    }

}
