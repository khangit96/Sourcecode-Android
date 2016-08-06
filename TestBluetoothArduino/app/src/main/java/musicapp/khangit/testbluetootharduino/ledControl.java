package musicapp.khangit.testbluetootharduino;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import android.os.Handler;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.UUID;
import java.util.logging.LogRecord;

/**
 * Created by Administrator on 3/7/2016.
 */
public class ledControl extends AppCompatActivity {
    Button btnOn, btnOff, btnDis;
    SeekBar brightness;
    String address = null;
    private ProgressDialog progress;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    volatile boolean stopWorker;
    Handler handler=new Handler();

    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led_control);

        //call the widgets
        btnOn = (Button)findViewById(R.id.button2);
        btnOff = (Button)findViewById(R.id.button3);
        btnDis = (Button)findViewById(R.id.button4);
        brightness = (SeekBar)findViewById(R.id.seekBar);

        //receive the address of the bluetooth device
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS);

        //connect bluetooth
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ConnectBT().execute();
            }
        });

        //Reading Incoming Data
     /* try {
            readIncomingData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        */


        btnOn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 turnOnLed();
             }
         });

        //Turn off led
        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffLed();
            }
        });
    }

    //Class connect Bluetooth
    private class ConnectBT extends AsyncTask<Void, Void, Void> // UI thread
    {
        private boolean ConnectSuccess = true;
        @Override
        protected void onPreExecute()
        {
            progress = ProgressDialog.show(ledControl.this, "Connecting...", "Please wait!!!");
        }
        @TargetApi(Build.VERSION_CODES.GINGERBREAD_MR1)
        @Override
        protected Void doInBackground(Void... devices)
        {
            try
            {
                if (btSocket == null || !isBtConnected)
                {  myBluetooth = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();
                    //


                }
            }
            catch (IOException e)
            {
                ConnectSuccess = false;
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            if (!ConnectSuccess)
            {
                msg("Connection Failed. Is it a SPP Bluetooth? Try again.");
                finish();
            }
            else
            {
                msg("Connected.");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    //TEst connect Bluetooth

    /*public  void connectBluetooth(){
        Set bondedDevices = myBluetooth.getBondedDevices();

        if(bondedDevices.isEmpty()) {

            Toast.makeText(getApplicationContext(),"Please Pair the Device first",Toast.LENGTH_SHORT).show();

        } else {

            for (BluetoothDevice iterator : bondedDevices) {

                if(iterator.getAddress().equals(DEVICE_ADDRESS)) //Replace with iterator.getName() if comparing Device names.

                {

                    device=iterator; //device is an object of type BluetoothDevice

                    found=true;

                    break;

                } } }
    }
    */
    //Reading Incoming Data
    public void readIncomingData() throws IOException {
        inputStream=btSocket.getInputStream();
        int byteCount = inputStream.available();
        if (byteCount > 0) {
            byte[] rawBytes = new byte[byteCount];
            inputStream.read(rawBytes);
            final String string = new String(rawBytes, "UTF-8");
            handler.post(new Runnable() {
                public void run() {
                    //textView.append(string);
                    Toast.makeText(getApplicationContext(),string,Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //Class disconnect Bluetooth
    private void Disconnect()
    {
        if (btSocket!=null) //If the btSocket is busy
        {
            try {
                btSocket.close(); //close connection
            }
            catch (IOException e)
            { msg("Error");}
        }
        finish(); //return to the first layout
    }

    //Turn off led
    private void turnOffLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TURN OFF".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    //Turn on led

    private void turnOnLed()
    {
        if (btSocket!=null)
        {
            try
            {
                btSocket.getOutputStream().write("TURN ON".toString().getBytes());
            }
            catch (IOException e)
            {
                msg("Error");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    //
    //method msg
    private void msg(String s)
    {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    }
}
