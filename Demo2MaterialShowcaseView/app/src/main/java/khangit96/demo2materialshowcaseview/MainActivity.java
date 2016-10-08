package khangit96.demo2materialshowcaseview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.widget.TextView;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    private static final String SHOWCASE_ID = "simple example";
    Menu main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        presentShowcaseView(1000);
        main= (Menu) findViewById(R.id.test);
    }

    private void presentShowcaseView(int withDelay) {
        new MaterialShowcaseView.Builder(this)
                .setTarget(main)
                .setDismissText("Tiếp tục")
                .setContentTextColor(getResources().getColor(R.color.colorAccent))
                .setContentText("Đây là khu vực hiển thị")
                .setDelay(withDelay) // optional but starting animations immediately in onCreate can make them choppy
                .singleUse(SHOWCASE_ID) // provide a unique ID used to ensure it is only shown once
                .show();
    }

}
