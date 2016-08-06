package com.quiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.demobiggestnumber2.R;

public class HowToPlayActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set fullscreen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Set No Title
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_how_to_play);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(Html.fromHtml("<p>Hi, this is a fairly simple game.</p></br><p>Your task is to find the biggest number within 4 seconds.</p></br>" +
                "<p>In the game there are a total of 16 cells.</p>The number will appear randomly on cells.<p>Initially only appear in 2 cells.</p><p>If you choose correctly, the number in other cells will appear." +
                "</p><p>You lose when the time out or choose incorrect the biggest number.</p><h6>Stay calm pick out the biggest number to reach the high score!</h6>"));
    }
}
