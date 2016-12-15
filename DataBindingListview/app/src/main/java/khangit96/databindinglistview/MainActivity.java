package khangit96.databindinglistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;

import khangit96.databindinglistview.databinding.ListItemBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Person> persons = new ArrayList<>();
        persons.add(new Person(3, "fd"));
        persons.add(new Person(4, "jjj"));
        persons.add(new Person(5, "jj"));
        persons.add(new Person(6, "fjk"));
        MyAdapter adapter = new MyAdapter(MainActivity.this, 3, persons);
        ListView lv = (ListView) findViewById(R.id.listview);
        lv.setAdapter(adapter);

    }
}
