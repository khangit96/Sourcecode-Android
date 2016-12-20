package khangit96.democreatelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import khangit96.demolibrary.Person;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Person p=new Person("khang",19);
    }
}
