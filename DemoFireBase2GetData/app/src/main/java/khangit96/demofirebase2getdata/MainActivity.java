package khangit96.demofirebase2getdata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        firebase = new Firebase("https://demokhang.firebaseio.com/");
        createData();
        getData();
    }

    public void createData() {
        Firebase personRef = firebase.child("Person");
        Map<String, Person> personMap = new HashMap<>();
        personMap.put("Person1", new Person("Duy Khang", 19));
        personMap.put("Person2", new Person("Thùy Linh", 19));
        personRef.setValue(personMap);
        Firebase animalRef = firebase.child("Animal");
        HashMap<String, Person> animalMap = new HashMap<>();
        animalMap.put("Animal1", new Person("Lion", 19));
        animalMap.put("Animal2", new Person("Cat", 19));
        animalRef.setValue(animalMap);
    }

    public void getData() {
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Animal> animalList = new ArrayList<Animal>();

                for (DataSnapshot child : dataSnapshot.child("Animal").getChildren()) {
                    Animal animal = child.getValue(Animal.class);
                    animalList.add(animal);
                }
                Toast.makeText(getApplicationContext(), "" + animalList.get(0).name, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }
}
