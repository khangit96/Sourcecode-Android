package khangit96.demodatabinding1;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import khangit96.demodatabinding1.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    Cat cat = new Cat("khang", 19);
    ActivityMainBinding mainBinding;
   private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainBinding.setCat(cat);
        mainBinding.setHandler(new OnClickEvent());
    }

    public class OnClickEvent {
        public void onUpdate(View v) {
            count++;
            if (count == 1) {
                cat.setName("Cat");
                cat.setAge(19);
            } else if (count == 2) {
                cat.setName("Dog");
                cat.setAge(20);
            }
        }

    }

}
