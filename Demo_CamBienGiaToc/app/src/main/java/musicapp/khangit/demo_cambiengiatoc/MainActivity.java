package musicapp.khangit.demo_cambiengiatoc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    Sensor camBienGiaToc;
    float last_x, last_y, last_z;
    long lastTime;

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
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        camBienGiaToc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (camBienGiaToc == null) {
            Toast.makeText(this, "Device doesn't support ACCELEROMETER", Toast.LENGTH_LONG).show();
        } else {
            sensorManager.registerListener(this, camBienGiaToc, SensorManager.SENSOR_DELAY_NORMAL);
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        camBienGiaToc = event.sensor;
        if (camBienGiaToc.getType() == Sensor.TYPE_ACCELEROMETER) {
            Log.d("kiemtra", "tọa độ x:  " + event.values[0] + "\ntọa độ y: " + event.values[1] + "\ntọa độ z: " + event.values[2]);
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            long currentTime=System.currentTimeMillis();
            if((currentTime-lastTime)>100){
                long diffTime=currentTime-lastTime;
                lastTime=currentTime;
                float speed=Math.abs(x+y+z-last_x-last_y-last_z)/diffTime*10000;
                if(speed>600){
                    Toast.makeText(this, "Bạn đã lắc thiết bị", Toast.LENGTH_LONG).show();
                }
                last_x=x;
                last_y=y;
                last_z=z;
                Log.d("kiemtra",""+speed);
            }

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
