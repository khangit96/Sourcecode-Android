package musicapp.khangit.demo_startactivityforresult;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by Administrator on 2/23/2016.
 */
public class SecondActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sencond_layout);
    }
    public  void SendBack(View v){
        finish();
    }

    @Override
    public void finish() {
        Intent data=new Intent();
        data.putExtra("value","Hi,i have received from you");
        setResult(RESULT_OK,data);
        super.finish();
    }
}
