package tdmug3.revisioncustomlistviewbaseadapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<Person> listPerson = new ArrayList<>();
    SharedPreferences sp;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        sp = getSharedPreferences("TEST", Context.MODE_PRIVATE);
        lv = (ListView) findViewById(R.id.lv);
        Person p = new Person(getBaseContext(), "Duy Khang", 19);
        Person p1 = new Person(getBaseContext(), "Thiện Huy", 20);
        Person p2 = new Person(getBaseContext(), "Văn Tiến", 30);
        listPerson.add(p);
        listPerson.add(p1);
        listPerson.add(p2);
        adapter = new CustomAdapter(getApplicationContext(), listPerson);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fullMode) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("test", "full");
            editor.commit();
            adapter.notifyDataSetChanged();
        } else if (id == R.id.shortMode) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("test", "short");
            editor.commit();
            adapter.notifyDataSetChanged();

        }

        return super.onOptionsItemSelected(item);
    }
}
