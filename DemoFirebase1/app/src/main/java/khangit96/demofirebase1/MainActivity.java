package khangit96.demofirebase1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextAddress;
    private TextView textViewPersons;
    private Button buttonSave;
    ArrayList<Person> personArrayList = new ArrayList<>();
    Firebase ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        initView();
        initEvent();
        ref = new Firebase(Config.FIREBASE_URL);
    }

    public void initView() {
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        textViewPersons = (TextView) findViewById(R.id.textViewPersons);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        textViewPersons = (TextView) findViewById(R.id.textViewPersons);
    }

    ValueEventListener listener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Toast.makeText(getApplicationContext(), "Data change!", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancelled(FirebaseError firebaseError) {

        }
    };

    public void initEvent() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating firebase object

                ref.addValueEventListener(listener);
            }
        });
    }

    public void Remove(View v) {
        ref.removeEventListener(listener);
    }

}
