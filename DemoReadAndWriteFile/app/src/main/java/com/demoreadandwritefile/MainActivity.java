package com.demoreadandwritefile;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    EditText edWrite;
    Button btWrite,btLoad;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edWrite = (EditText) findViewById(R.id.edWrite);
        btWrite = (Button) findViewById(R.id.btWrite);
        btLoad = (Button) findViewById(R.id.btLoad);
        tv=(TextView)findViewById(R.id.tv);

    }

    public void Write(View v) throws IOException {
        String text = edWrite.getText().toString();
        //save at download folder
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + Environment.DIRECTORY_DOWNLOADS + "/"+"khang.txt";
        File textFle = new File(filePath);
        FileWriter fw = null;
        try {
            fw = new FileWriter(textFle);
            fw.write(text);
            fw.flush();
            Toast.makeText(getApplicationContext(),"File saved at"+filePath,Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    public void Load(View v){
        String filePath=Environment.getExternalStorageDirectory().getPath()+"/"+Environment.DIRECTORY_DOWNLOADS+"/"+"khang.txt";
        FileReader fr=null;
        try{
            fr=new FileReader(filePath);
            int byteCharacter=-1;
            String result="";
            while ((byteCharacter=fr.read())!=-1){
                result+=(char)byteCharacter+"";
            }
            edWrite.setText(result);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                fr.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
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
