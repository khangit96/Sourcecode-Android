package com.example.administrator.external_storage;

import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
private  boolean check;
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
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
      //  check=isExternalStorageWritable();
       // Toast.makeText(MainActivity.this,""+check,Toast.LENGTH_LONG).show();
      //  writeData();
      /*  if(check==true){
            Toast.makeText(MainActivity.this,"Ok",Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
        }*/
        saveFileExternal();
        String separator = "/";
        String sdcardPath = Environment.getExternalStorageDirectory().getPath() + separator;
        Toast.makeText(MainActivity.this,sdcardPath,Toast.LENGTH_LONG).show();
    }
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
    public void writeData()
    {
       String sdcard=Environment
                .getExternalStorageDirectory().getAbsolutePath()+"/data.txt";

        try {
            OutputStreamWriter writer=
                    new OutputStreamWriter(
                            new FileOutputStream(sdcard));
            writer.write("Nguyễn Hồ Duy Khang" + "");
            writer.close();
            Toast.makeText(getBaseContext(), sdcard, Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

     /*  try {
            File myFile = new File("mysdfile.txt");
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter =
                    new OutputStreamWriter(fOut);
            myOutWriter.append("Ok");
            myOutWriter.close();
            fOut.close();
            Toast.makeText(getBaseContext(),
                    "Done writing SD 'mysdfile.txt'",
                    Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
        */
    }

    private void saveFileExternal() {
        // TODO Auto-generated method stub
        OutputStreamWriter writer = null;
        // Get sdCard path
        File sdCard = Environment.getExternalStorageDirectory();
        if (sdCard.exists()) {// Check Sdcard exists
            File directory = new File(sdCard.getAbsolutePath() + "/MyFiles");
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(directory, "test_file.txt");
            try {
                writer = new OutputStreamWriter(new FileOutputStream(file));
                writer.write("Ok");
                writer.flush();
               // this.editText.setText("");
                Toast.makeText(this, "Save file sucess", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        // TODO: handle exception
                    }
                }
            }

        } else {
            Toast.makeText(MainActivity.this,"SdCard not exists",Toast.LENGTH_LONG).show();
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
