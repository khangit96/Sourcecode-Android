package musicapp.khangit.sendreceiverdata_between_arduino_and_android_bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_ENABLE_BT = 1;
    private Button onBtn; // nút bật bluetooth
    private Button offBtn; // nút tắt bluetooth
    private Button listBtn; // liệt kê danh sách thiết bị đã kết nối
    private Button findBtn; // tìm thiết bị
    private Button connectBtn; // kết nối
    private Button sendBtn; // gửi chuỗi
    private TextView text; // text thông báo
    private int flag = 0, flag1 = 0;
    private EditText editText;
    private BluetoothAdapter myBluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;
    private ArrayList<BluetoothDevice> newDevices = null;
    private ListView myListView;
    private ArrayAdapter<String> BTArrayAdapter;
    // ////
    BluetoothSocket mSocket;
    BluetoothDevice HC06 = null;
    OutputStream mOutputStream;
    InputStream mInputStream;
    boolean connected = false;
    Button bd;
    Button tienBtn;
    Button luiBtn;
    Button traiBtn;
    Button phaiBtn;
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
        //
        // kiểm tra thiết bị có hỗ trợ Bluetooth hay ko.
        myBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (myBluetoothAdapter == null) { // nếu không hỗ trợ
            onBtn.setEnabled(false);
            offBtn.setEnabled(false);
            listBtn.setEnabled(false);
            findBtn.setEnabled(false);
            text.setText("Status: not supported");

            Toast.makeText(getApplicationContext(),
                    "Your device does not support Bluetooth", Toast.LENGTH_LONG)
                    .show();

        } else { // nếu có hỗ trợ

            // Text hiện thông báo trạng thái ---------------------------------
            text = (TextView) findViewById(R.id.text);

            // Nút bật bluetooth ----------------------------------------------
            onBtn = (Button) findViewById(R.id.turnOn);
            onBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    on(v);
                }
            });

            // Nút tắt bluetooth -----------------------------------------------
            offBtn = (Button) findViewById(R.id.turnOff);
            offBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    off(v);
                }
            });

            // Nút tìm thiết bị -----------------------------------------------
            findBtn = (Button) findViewById(R.id.scan);
            findBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    find(v); // tìm thiết bị
                }
            });

            // Nút kết nối ----------------------------------------------------
            connectBtn = (Button) findViewById(R.id.connect);
            connectBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    connect("HC-05");
                }
            });

            // Nút tiến
            tienBtn = (Button) findViewById(R.id.tien);

            tienBtn.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        flag = 1;
                        Send("F");
                        setTien();

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        flag = 0;
                        Send("S");
                        setTien();
                    }
                    return false;
                }
            });
            // Nút lùi
            luiBtn = (Button) findViewById(R.id.lui);
            luiBtn.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        flag1 = 1;
                        Send("B");
                        setLui();

                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        flag1 = 0;
                        Send("S");
                        setLui();
                    }
                    return false;
                }
            });
            // Nút trái
            traiBtn = (Button) findViewById(R.id.trai);
            traiBtn.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (flag == 0) {
                            Send("L");
                        } else {
                            Send("G");
                        }
                        if (flag1 == 0) {
                            Send("L");
                        } else {
                            Send("H");
                        }
                        setTrai();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        Send("S");
                        if (flag == 1) {
                            Send("F");
                        }
                        ;
                        if (flag1 == 1)
                            Send("B");
                        setTrai();
                    }
                    return false;
                }
            });
            // Nút phải
            phaiBtn = (Button) findViewById(R.id.phai);
            phaiBtn.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        if (flag == 0) {
                            Send("R");
                        } else {
                            Send("I");
                        }
                        if (flag1 == 0) {
                            Send("R");
                        } else {
                            Send("J");
                        }
                        setPhai();
                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
                        Send("S");
                        if (flag == 1)
                            Send("F");
                        if (flag1 == 1) {
                            Send("B");
                        }
                        setPhai();
                    }
                    return false;
                }
            });

            // bật đèn
            bd = (Button) findViewById(R.id.batden);
            bd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    setLight();

                }
            });

            // bật đèn

            // Khởi tạo listview
            myListView = (ListView) findViewById(R.id.listView1);
            // create the arrayAdapter that contains the BTDevices, and set it
            // to the ListView
            BTArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1);
            myListView.setAdapter(BTArrayAdapter);
            // myListView.setOnItemClickListener()
        }
    }

    boolean isLight = false;




    public void setLight() {
        if (!isLight) {
          //  bd.setBackgroundResource(R.drawable.btn_front_on);
            Send("D");
            isLight = true;
        } else {
           // bd.setBackgroundResource(R.drawable.btn_front_off);
            Send("T");
            isLight = false;
        }
    }

    boolean istien = false;

    public void setTien() {
        if (!istien) {
           // tienBtn.setBackgroundResource(R.drawable.lendown);
            istien = true;
        } else {
          //  tienBtn.setBackgroundResource(R.drawable.len);
            istien = false;
        }
    }

    boolean islui = false;

    public void setLui() {
        if (!islui) {
         //   luiBtn.setBackgroundResource(R.drawable.xuongdown);
            islui = true;
        } else {
           // luiBtn.setBackgroundResource(R.drawable.xuong);
            islui = false;
        }
    }

    boolean istrai = false;

    public void setTrai() {
        if (!istrai) {
           // traiBtn.setBackgroundResource(R.drawable.traidown);
            istrai = true;
        } else {
           // traiBtn.setBackgroundResource(R.drawable.trai);
            istrai = false;
        }
    }

    boolean isphai = false;

    public void setPhai() {
        if (!isphai) {
          //  phaiBtn.setBackgroundResource(R.drawable.phaidown);
            isphai = true;
        } else {
         //   phaiBtn.setBackgroundResource(R.drawable.phai);
            isphai = false;
        }
    }

    // Hàm bật bluetooth
    public void on(View view) {
        if (!myBluetoothAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);

            Toast.makeText(getApplicationContext(), "Bluetooth turned on",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Bluetooth is already on",
                    Toast.LENGTH_LONG).show();
        }
    }

    // kết quả trả về sau khi gọi hàm bật bluetooth
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        if (requestCode == REQUEST_ENABLE_BT) {
            if (myBluetoothAdapter.isEnabled()) {
                text.setText("Status: Enabled");
            } else {
                text.setText("Status: Disabled");
            }
        }
    }

    public void list(View view) {
        // lấy những thiết bị đã kết nối
        pairedDevices = myBluetoothAdapter.getBondedDevices();

        // Đưa vào listview
        for (BluetoothDevice device : pairedDevices)
            BTArrayAdapter.add(device.getName() + "\n" + device.getAddress());

        Toast.makeText(getApplicationContext(), "Show Paired Devices",
                Toast.LENGTH_SHORT).show();

    }

    // Lấy thông tin thiết bị sau khi tìm được đưa vào list view, bao gồm tên
    // thiết bị và địa chỉ MAC
    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent
                        .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                BTArrayAdapter.add(device.getName() + "\n"
                        + device.getAddress());
                if (device.getName().equals("HC-05")) {
                    Toast.makeText(getApplicationContext(),
                            "Đã tìm thấy HC-06", Toast.LENGTH_LONG).show();
                    HC06 = device;
                }
                BTArrayAdapter.notifyDataSetChanged();
            }
        }
    };

    // Hàm dò tìm thiết bị
    public void find(View view) {
        if (myBluetoothAdapter.isDiscovering()) {
            // the button is pressed when it discovers, so cancel the discovery
            myBluetoothAdapter.cancelDiscovery();
        } else {
            BTArrayAdapter.clear();
            myBluetoothAdapter.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(
                    BluetoothDevice.ACTION_FOUND));
        }
    }

    // Tắt bluetooth
    public void off(View view) {
        myBluetoothAdapter.disable();
        text.setText("Status: Disconnected");

        Toast.makeText(getApplicationContext(), "Bluetooth turned off",
                Toast.LENGTH_LONG).show();
    }

    // Hàm kết nối
    public void connect(String name) {
        if (HC06 != null) {
            try {
                UUID uuid = UUID
                        .fromString("00001101-0000-1000-8000-00805f9b34fb");
                mSocket = HC06.createRfcommSocketToServiceRecord(uuid);
                mSocket.connect();
                mOutputStream = mSocket.getOutputStream();
                mInputStream = mSocket.getInputStream();
                connected = true;
                text.setText("Status: Connected");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "HC06 is not found",
                    Toast.LENGTH_LONG).show();
        }
    }

    // Gửi chuỗi qua bluetooth
    public void Send(String msg) {
        if (connected == true) {
            try {
                mOutputStream.write(msg.getBytes());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    // Gửi số qua bluetooth
    public void Send(int number) {
        if (connected == true) {
            try {
                mOutputStream.write(number);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
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
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bReceiver);
    }
}
