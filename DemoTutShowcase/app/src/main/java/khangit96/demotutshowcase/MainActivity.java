package khangit96.demotutshowcase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.github.florent37.tutoshowcase.TutoShowcase;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCase();
    }

    public void showCase() {
        TutoShowcase.from(this)
                .setContentView(R.layout.tut_showcase)

                .on(R.id.about) //a view in actionbar
                .addCircle()
                .withBorder()
                .onClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //custom action
                    }
                })

               /* .on(R.id.swipable)
                .displaySwipableRight()*/

                .show();
    }

}
