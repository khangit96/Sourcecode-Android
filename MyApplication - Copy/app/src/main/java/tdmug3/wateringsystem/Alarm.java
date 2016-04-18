package tdmug3.wateringsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 4/14/2016.
 */
public class Alarm extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alarm);
    }
    public void Back(View v){
        Intent iHome=new Intent(Alarm.this,MainActivity.class);
       iHome.putExtra("time","time");
    }

}
