package khangit96.demohashmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HashMap<Integer, Person> hashMap = new HashMap<>();
        hashMap.put(0, new Person("Khang", 19));
        hashMap.put(1, new Person("Linh", 19));
        Toast.makeText(getApplicationContext(), "" + hashMap.get(0).name, Toast.LENGTH_LONG).show();
    }
}
