package com.demogetmacbluetoothandroid;

import android.bluetooth.BluetoothAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),getBluetoothMacAddress(),Toast.LENGTH_LONG).show();
    }
    public static String getLocalBluetoothName() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // if device does not support Bluetooth
        if (mBluetoothAdapter == null) {
           // Log.d(TAG, "device does not support bluetooth");
            return null;
        }
        return mBluetoothAdapter.getName();
    }
    public static String getBluetoothMacAddress() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        // if device does not support Bluetooth
        if(mBluetoothAdapter==null){
           // Log.d(TAG,"device does not support bluetooth");
            return null;
        }

        return mBluetoothAdapter.getAddress();
    }


}
