package khangit96.demodatabinding3;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import khangit96.demodatabinding3.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        mainBinding.textview.setText("Duy Khang");
    }
}
