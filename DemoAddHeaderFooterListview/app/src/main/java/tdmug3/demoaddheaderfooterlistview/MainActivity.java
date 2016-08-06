package tdmug3.demoaddheaderfooterlistview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> arrayList = new ArrayList<>();
    ListView lv;
    View listHeader, listFooter;

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
        Init();
        SetupHeaderAndFooter();
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        arrayList.add("DUy Khang");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
        lv.setAdapter(adapter);
    }

    public void Init() {
        lv = (ListView) findViewById(R.id.lv);
    }

    private void SetupHeaderAndFooter() {
        listHeader = View.inflate(this, R.layout.list_header, null);
        //listFooter = View.inflate(this, R.layout.list_footer, null);
        lv.addHeaderView(listHeader);
        //lv.addFooterView(listFooter);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
