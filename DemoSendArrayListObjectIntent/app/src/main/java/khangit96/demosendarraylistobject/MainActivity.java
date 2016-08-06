package khangit96.demosendarraylistobject;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Earth> earthArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Animal animal = new Animal("Chó");
        Person person = new Person("khang");
        Animal animal1 = new Animal("Meo");
        Person person1 = new Person("Linh");
        earthArrayList.add(new Earth(animal, person));
        earthArrayList.add(new Earth(animal1, person1));
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putParcelableArrayListExtra("earth", (ArrayList<? extends Parcelable>) earthArrayList);
        startActivity(intent);
    }
}
