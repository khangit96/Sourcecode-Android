package com.demojson1;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    TextView tvJjsonObject, tvJsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvJjsonObject = (TextView) findViewById(R.id.tvJjsonObject);
        tvJsonArray = (TextView) findViewById(R.id.tvJjsonArray);
        /*Parse Json Object*/
        String jObject = "{ \"id\":1,\"name\":\"coca\"}";
        try {
            JSONObject jsonObject = new JSONObject(jObject);
            int id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            tvJjsonObject.setText("id: " + String.valueOf(id) + "\n" + "name: " + name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
          /*Parse Json Array*/
        String jArray = "[{\"id\":1,\"name\":\"coca\"},{\"id\":2,\"name\":\"pepsi\"}]";
        String result = "";
        try {
            JSONArray jsonArray = new JSONArray(jArray);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                result += "id:" + String.valueOf(item.getString("id"));
                result += "name: " + item.getString("name");
                result += "|";
            }
            tvJsonArray.setText(result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
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
