package com.example.administrator.demo2_sqlite;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database db=new Database(this);
    EditText edUsername,edPassword;
    Button btAdd;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        init();
        Create_Table();
        ListUsername();


    }
    public void init(){
        btAdd=(Button)findViewById(R.id.btAdd);
        edUsername=(EditText)findViewById(R.id.edUsername);
        edPassword=(EditText)findViewById(R.id.edPassword);
        lv=(ListView)findViewById(R.id.lv);
    }
    public void Create_Table(){
        db.Query_Data("CREATE TABLE IF NOT EXISTS user(_id INTEGER PRIMARY KEY,username VARCHAR(200)NOT NULL,password VARCHAR(200)NOT NULL)");
    }
    public void Insert(){
        String Username=edUsername.getText().toString();
        String Password=edPassword.getText().toString();
        user u=new user(Username,Password);
        db.add(u, "user");
    }
    public void Add(View v){
        Insert();
        ListUsername();
    }
    public void ListUsername(){
        ArrayList<String> arrUsername=new ArrayList<String>();
        ArrayList<String> arrPassword=new ArrayList<String>();
        Cursor kq=db.GetData("SELECT * FROM user");
        while(kq.moveToNext()){
            String username=kq.getString(1);
            String password=kq.getString(2);
            arrUsername.add(username);
            arrPassword.add(password);
        }
        ArrayList<user> arrUser=new ArrayList<user>();
        for(int i=0;i<arrUsername.size();i++){
            user u=new user(arrUsername.get(i),arrPassword.get(i));
            arrUser.add(u);
        }
        ListAdapter adapter=new ListAdapter(MainActivity.this,R.layout.content_custom__listview,arrUser);
        lv.setAdapter(adapter);

    }
    @Override
    public void onBackPressed() {
        System.exit(0);
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
