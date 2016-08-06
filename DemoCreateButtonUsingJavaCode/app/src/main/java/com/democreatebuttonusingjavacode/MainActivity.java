package com.democreatebuttonusingjavacode;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TableLayout tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tb = (TableLayout) findViewById(R.id.tb);
        TableRow tableRow = new TableRow(this);
        Button button = new Button(this);
        button.setText("1");
        button.setTextColor(getResources().getColor(R.color.white));
        button.setPadding(30, 30, 30, 30);
        button.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        tableRow.addView(button);
        Button button1 = new Button(this);
        button1.setText("1");
        button1.setTextColor(getResources().getColor(R.color.white));
        button1.setPadding(30, 30, 30, 30);
        button1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        tableRow.addView(button1);
        /*Button button2 = new Button(this);
        button2.setText("1");
        button2.setTextColor(getResources().getColor(R.color.white));
        button2.setPadding(30, 30, 30, 30);
        button2.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        tableRow.addView(button2);*/

        tb.addView(tableRow);

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
