package khangit96.demosendarraylistobject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        ArrayList<Earth> earthArrayList = new ArrayList<>();
        earthArrayList = intent.getParcelableArrayListExtra("earth");
        Toast.makeText(getApplicationContext(), "" + earthArrayList.size(), Toast.LENGTH_LONG).show();
    }
}
