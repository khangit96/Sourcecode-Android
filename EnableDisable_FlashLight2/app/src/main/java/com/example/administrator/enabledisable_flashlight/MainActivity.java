package com.example.administrator.enabledisable_flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import   android.hardware.Camera.Parameters;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {
    private  Camera camera;
    private  boolean isFlashlightOn;
    Parameters parameters;
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
        /*boolean isCameraFlash=getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if(!isCameraFlash){
            showToast("Device hasn't camera flash");
        }
        else{*/
           // camera=Camera.open();
            //parameters=camera.getParameters();
            CountDownTimer countDownTimer=new CountDownTimer(5000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setFlashlightOn();
                    setFlashlightOff();
                }

                @Override
                public void onFinish() {
                    setFlashlightOff();
                }
            }.start();
     //   }
    }

    @Override
    protected void onStart() {

        super.onStart();
        Log.d("test","On Start");
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (camera != null) {
            camera.release();
        }
}
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "On Destroy");
    }

    @Override
    protected void onResume() {
         camera=Camera.open();
        //  parameters=camera.getParameters();
        super.onResume();
        Log.d("test", "On Resum");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "On Restart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "On Pause");
    }

    public void Turnon(View v) {
            setFlashlightOn();
    }
    public  void Turnoff(View v){
        setFlashlightOff();
    }

    public  void showToast(String alert){
        Toast.makeText(MainActivity.this,alert,Toast.LENGTH_LONG).show();
    }
   public  void setFlashlightOn(){
      // camera=Camera.open();
      parameters=camera.getParameters();
       parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);
       camera.setParameters(parameters);
       camera.startPreview();
       isFlashlightOn=true;
   }
    public  void setFlashlightOff(){
        parameters=camera.getParameters();
        parameters.setFlashMode(Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.stopPreview();
        isFlashlightOn=false;
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
