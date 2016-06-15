
package com.demohtmltextview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(Html.fromHtml("<h2>Title</h2><br><p>Hi, this is a fairly simple game.Your task is to find the biggest number within 4 seconds.In the game there are a total of 16 cells\n" +
                "The number will appear randomly on cells.Initially only appear in 2 cells.If you choose correctly, the number in other cells will appear.You lose when the time out or choose incorrect the biggest number\n" +
                "Stay calm pick out the biggest number to reach the high score\n</p>"));
    }
}
