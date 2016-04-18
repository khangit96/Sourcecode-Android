package tdmug3.wateringsystem;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.os.Handler;

public class MainActivity extends AppCompatActivity {
    ToggleButton toogleLed, togglePush, toggleWatering;
    private final String DEVICE_ADDRESS = "98:D3:31:30:77:4F";
    private final UUID PORT_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");//Serial Port Service ID
    private BluetoothDevice device;
    private BluetoothSocket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    Button startButton, sendButton, clearButton, stopButton;
    TextView textView;
    EditText editText;
    boolean deviceConnected = false;
    Thread thread;
    byte buffer[];
    int bufferPosition;
    boolean stopThread;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Hệ thống tưới");
        toolbar.setNavigationIcon(R.drawable.dout);
        ConnectBluetooth();
        CountDownTimer countDownTimer=new CountDownTimer(4000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                progressDialog.setTitle("Đang kết nối với hệ thống tưới");
                progressDialog.setMessage("Vui lòng chờ...");
                progressDialog.show();
                progressDialog.setCancelable(false);
            }

            @Override
            public void onFinish() {
                progressDialog.dismiss();
                ConnectBluetooth();
                Bundle bd=getIntent().getExtras();
                if(bd!=null){

                }
                else {
                    SendData("O");//gửi tín hiệu kết nối Bluetooth với arduino
                    Toast.makeText(MainActivity.this,"Kết nối thành công!",Toast.LENGTH_LONG).show();
                }
            }
        }.start();
        init();
    }

    //Hàm khởi tạo
    public void init() {
        toogleLed = (ToggleButton) findViewById(R.id.toggleLed);
        togglePush = (ToggleButton) findViewById(R.id.togglePush);
        toggleWatering = (ToggleButton) findViewById(R.id.toggleWatering);
        progressDialog=new ProgressDialog(this);
    }

    //Hàm bật đèn
    public void Led(View v) {
        boolean checked = ((ToggleButton) v).isChecked();
        if (checked) {
            Toast.makeText(MainActivity.this, "Tắt đèn", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Bật đèn", Toast.LENGTH_LONG).show();

        }
    }

    //Hàm thay đổi độ ẩm đát
    public void ChangeHumidity(View v) {
        SendData("99");
        Toast.makeText(MainActivity.this, "Thay đổi độ ẩm", Toast.LENGTH_LONG).show();
    }

    //Hàm kéo màn
    public void Push(View v) {
        boolean checked = ((ToggleButton) v).isChecked();
        if (checked) {
            SendData("4");
            Toast.makeText(MainActivity.this, "Tắt kéo màn", Toast.LENGTH_LONG).show();
        } else {
            SendData("3");
            Toast.makeText(MainActivity.this, "Bật kéo màn ", Toast.LENGTH_LONG).show();
        }
    }

    //Hàm tưới nước
    public void Watering(View v) {
        ConnectBluetooth();
        boolean checked = ((ToggleButton) v).isChecked();
        if (checked) {
            SendData("2");
            Toast.makeText(MainActivity.this, "Tắt máy bơm", Toast.LENGTH_LONG).show();
        } else {
            SendData("1");
            Toast.makeText(MainActivity.this, "Bật máy bơm ", Toast.LENGTH_LONG).show();

        }
    }

    //Hàm tìm Bluetooth
    public boolean BTinit() {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableAdapter = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableAdapter, 0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Set<BluetoothDevice> bondedDevices = bluetoothAdapter.getBondedDevices();
        if (bondedDevices.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
        } else {
            for (BluetoothDevice iterator : bondedDevices) {
                if (iterator.getAddress().equals(DEVICE_ADDRESS)) {
                    device = iterator;
                    found = true;
                    break;
                }
            }
        }
        return found;
    }

    //
    public boolean BTconnect() {
        boolean connected = true;
        try {
            socket = device.createRfcommSocketToServiceRecord(PORT_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
        if (connected) {
            try {
                outputStream = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream = socket.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return connected;
    }

    //Hàm connect Bluetooth
    public void ConnectBluetooth() {
        if (BTinit()) {
            if (BTconnect()) {
                deviceConnected = true;

            }

        }
    }
  public void SendData(String data){
      data.concat("\n");
      if (data != "") {
          try {
              outputStream.write(data.getBytes());
          } catch (IOException e) {
              e.printStackTrace();
          }

      }
  }


    //Hàm lắng nghe dữ liệu từ arduino
    void beginListenForData() {
        final Handler handler = new Handler();
        stopThread = false;
        buffer = new byte[1024];
        Thread thread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopThread) {
                    try {
                        final int byteCount = inputStream.available();
                        if (byteCount > 0) {

                            byte[] rawBytes = new byte[byteCount];

                            inputStream.read(rawBytes);
                            final String string = new String(rawBytes, "UTF-8");
                            handler.post(new Runnable() {
                                public void run() {

                                }
                            });

                        }

                    } catch (IOException ex) {
                        stopThread = true;
                    }
                }
            }
        });

        thread.start();
    }


   //Hàm dừng kết nói Bluetooth
  public void Stop()throws IOException {
      SendData("F");
      stopThread = true;
      outputStream.close();
      inputStream.close();
      socket.close();
      deviceConnected = false;
      CountDownTimer countDownTimer=new CountDownTimer(4000,1000) {
          @Override
          public void onTick(long millisUntilFinished) {
              progressDialog.setTitle("Đang ngắt kết nối với hệ thống tưới.");
              progressDialog.setMessage("Vui lòng chờ...");
              progressDialog.show();
              progressDialog.setCancelable(false);
          }

          @Override
          public void onFinish() {
              Toast.makeText(MainActivity.this,"Ngắt kết nối thành công!",Toast.LENGTH_LONG).show();
              System.exit(0);
          }
      }.start();
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
        if (id == R.id.alarm) {
            Toast.makeText(MainActivity.this, "Alarm", Toast.LENGTH_LONG).show();
            stopThread = true;
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            deviceConnected = false;
            startActivity(new Intent(MainActivity.this, Alarm.class));
        }
        else if(id==R.id.exit){
            try {
                Stop();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        try {
            Stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
}
