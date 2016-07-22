package khangit96.demointerface;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        OnclickListener onclickListener = new OnclickListener();
        onclickListener.getListener(new OnclickListener.MyOnclickListener() {
            @Override
            public void onClick(String text) {
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG).show();
            }
        });
        onclickListener.showData();
    }
}
