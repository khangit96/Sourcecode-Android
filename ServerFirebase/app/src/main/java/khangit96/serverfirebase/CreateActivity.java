package khangit96.serverfirebase;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class CreateActivity extends AppCompatActivity {
    Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Firebase.setAndroidContext(this);
        firebase = new Firebase(Config.serverUrl);
        // createData();
        Demo demo = new Demo();
        demo.show();
    }

    public void createData() {
        Firebase homeRef = firebase.child("Home").child("1");
       /* Home home = new Home("Nhà trọ Ngọc LLan", "450000vnsđ/tháng", "Còn phòng", "Bình Hòa Nam", "106s054656,104445");
        homeRef.setValue(home);*/
     /*   Map<String, Home> personMap = new HashMap<>();
        personMap.put("1", new Home("Nhà trọ Ngọc sdLan", "450000vnsđ/tháng", "Còn phònsg", "Bình Hòa Nsam", "106s054656,104445"));*/
        // home.setValue(personMap);
        /*Firebase animalRef = firebase.child("ShopAndRestaurant");
        HashMap<String, Person> animalMap = new HashMap<>();
        animalMap.put("Animal1", new Person("Lion", 19));
        animalMap.put("Animal2", new Person("Cat", 19));
        animalRef.setValue(animalMap);*/
    }
}
