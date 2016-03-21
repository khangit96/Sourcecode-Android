package musicapp.khangit.smartwaterbottle;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.os.Handler;

import static android.content.Context.MODE_PRIVATE;
import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Administrator on 3/16/2016.
 */
public class FollowerActivity extends android.support.v4.app.Fragment {
    Button btLitre;
    Boolean clickCheck = true;
    TextView tvStatus, tvDrank, tvRemaining;
    ImageView imgRemaining, imgDrank;
    LinearLayout ln;
    private boolean doubleBackToExitPressedOnce = false;
    static Activity thisActivity = null;


    //Bluetooth
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

    //Xử lý thông tin nước
    ArrayList<String> arrayList = new ArrayList<>();
    public static int count = 0;
    public static int count1 = 0;

    //Lưu dữ liệu bằng SharePreferences
    SharedPreferences spLitre;//thông tin lít nước
    String strLitre;
    int intLitre;

    //nước còn lại phải uống của người dùng
    String strRemainingLitreOfUser;

    //Tổng lượng nước người dùng đã uống
    String strDrankWaterSumOfUser;

    //Tổng nước người dùng đã uống
    public static int drankWaterSum;

    //Nước còn lại trong bình
    int remainingWaterInBottle = 100;

    //Đếm để tính tổng nước đã uống
    public static int countWater = 0;

    //Biến dùng để tính lượng nước đã uống
    public static int fakeWater;

    //
    public static int saveDrankWater;

    //Biến dùng để kiểm tra lượng nước(tức là nếu người dùng đổ nước thêm vào bình)
    public static int waterCheck = 5000;

    //
    public static boolean check = false;

    //BIến dùng để kiểm tra xem đã thêm vào bao nhiêu lít nước
    public static int addWater;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.activity_follower, container, false);

        //Khởi tạo
        init(rootview);

        //Bắt đầu kết nối Bluetooth
        Connect();

        //Init Sharepreferences
        spLitre = this.getActivity().getSharedPreferences("LITRE", Context.MODE_PRIVATE);

        //Sharepreferences số lít nước
        strLitre = spLitre.getString("litre", "");
        if (strLitre.equals(null)) {
            SharedPreferences.Editor editor = spLitre.edit();
            editor.putString("litre", "3");
            editor.commit();
        }
        if (strLitre.equals("")) {
            btLitre.setText("NO DATA");
        } else {
            btLitre.setText("NO DATA");

        }


        //Sharepreferences tổng số nước người dùng đã uống
        strDrankWaterSumOfUser = spLitre.getString("drankWaterSum", "");
        if (strDrankWaterSumOfUser.equals("")) {
            tvDrank.setText("NO DATA");
            saveDrankWater = 0;

        } else {
            tvDrank.setText("Drank: " + strDrankWaterSumOfUser + "ml");
            saveDrankWater = Integer.parseInt(strDrankWaterSumOfUser.toString());
        }

        //Sharepreferences số nước còn lại của người dung
        strRemainingLitreOfUser = spLitre.getString("remainingLitre", "");
        if (strRemainingLitreOfUser.equals("")) {
            tvRemaining.setText("NO Data ");
        } else {
            tvRemaining.setText("Remaining: " + strRemainingLitreOfUser + "ml");
        }


        //Onclick
        btLitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickCheck();

            }
        });
        /*
        //Sự kiện touch screen
        ln.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ShowInfor();
                 return false;
            }
        });
        */
        //Ẩn thông tin
        //  HideInfor();

        return rootview;
    }

    //Hàm kiểm tra xem người dùng nhấn theo dõi hay stop
    public void ClickCheck() {
        if (clickCheck == true) {//nếu người dunfng click lân đầu để theo dõi
            Toast.makeText(getContext(), "Following !", LENGTH_LONG).show();
            btLitre.setText("Following...");
            btLitre.setTextSize(15);
            clickCheck = false;

            //Connect again bluetooth
            Connect();

            try {
                //Lắng nghe dữ liệu từ arduino
                beginListenForData();
            } catch (Exception e) {

            }
            //Gửi dữ liệu qua arduino
            String string = "TURN ON";
            string.concat("\n");
            try {
                outputStream.write(string.getBytes());
            } catch (Exception e) {

            }


        } else {
            Toast.makeText(getContext(), "Stopped !", LENGTH_LONG).show();
            btLitre.setText("2.0 Lit");
            btLitre.setTextSize(30);
            clickCheck = true;
        }
    }

    //Hàm khởi tạo
    public void init(View rooView) {
        btLitre = (Button) rooView.findViewById(R.id.btLitre);
        tvStatus = (TextView) rooView.findViewById(R.id.tvStatus);
        tvRemaining = (TextView) rooView.findViewById(R.id.tvRemaining);
        tvDrank = (TextView) rooView.findViewById(R.id.tvDrank);
        imgDrank = (ImageView) rooView.findViewById(R.id.imgDrank);
        imgRemaining = (ImageView) rooView.findViewById(R.id.imgRemaining);
        ln = (LinearLayout) rooView.findViewById(R.id.ln);
    }

    //Hàm ẩn hiện thông tin theo dõi nếu như dữ liệu người dùng chưa có(mới đăng nhập lần đầu)
    public void HideInfor() {
        btLitre.setVisibility(View.INVISIBLE);
        tvStatus.setText("NO DATA FOUND");
        tvDrank.setText("");
        tvRemaining.setText("");
        imgDrank.setVisibility(View.INVISIBLE);
        imgRemaining.setVisibility(View.INVISIBLE);
    }

    //Hàm hiện thông tin theo dõi

    public void ShowInfor() {
        btLitre.setVisibility(View.VISIBLE);
        tvStatus.setText("Status: Good");
        tvDrank.setText("Drank: 300ml");
        tvRemaining.setText("Remaining: 350ml");
        imgDrank.setVisibility(View.VISIBLE);
        imgRemaining.setVisibility(View.VISIBLE);
    }

    //Hàm gán dữ liệu về cho textview
    public void getValues(ArrayList<String> ARRAYLIST, TextView TV) {
        String t = "";
        for (int i = 0; i < ARRAYLIST.size(); i++) {
            t += ARRAYLIST.get(i).toString();
        }
        TV.setText("Remaining: " + t + "ml");
        ARRAYLIST.clear();
    }

    //Find Bluetooth
    public boolean BTinit() {
        boolean found = false;
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(getContext(), "Device doesnt Support Bluetooth", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getContext(), "Please Pair the Device first", Toast.LENGTH_SHORT).show();
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

    //Hàm connect Bluetooth
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
    public void Connect() {
        if (BTinit()) {
            if (BTconnect()) {
                deviceConnected = true;
            }
        }
    }

    //Hàm listen dữ liệu từ arduino send đến android
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
                                    if (string.equals("a")) {
                                        remainingWaterInBottle = RemainingLitre(10);
                                    } else if (string.equals("b")) {
                                        remainingWaterInBottle = RemainingLitre(11);
                                    } else if (string.equals("c")) {
                                        remainingWaterInBottle = RemainingLitre(12);

                                    } else if (string.equals("d")) {
                                        remainingWaterInBottle = RemainingLitre(13);

                                    } else if (string.equals("e")) {
                                        remainingWaterInBottle = RemainingLitre(14);
                                    } else if (string.equals("f")) {
                                        remainingWaterInBottle = RemainingLitre(15);
                                    } else if (string.equals("g")) {
                                        remainingWaterInBottle = RemainingLitre(16);
                                    } else if (string.equals("h")) {
                                        remainingWaterInBottle = RemainingLitre(17);
                                    } else if (string.equals("1")) {
                                        remainingWaterInBottle = RemainingLitre(1);
                                    } else if (string.equals("2")) {
                                        remainingWaterInBottle = RemainingLitre(2);

                                    } else if (string.equals("3")) {
                                        remainingWaterInBottle = RemainingLitre(3);
                                    } else if (string.equals("4")) {
                                        remainingWaterInBottle = RemainingLitre(4);
                                    } else if (string.equals("5")) {
                                        remainingWaterInBottle = RemainingLitre(5);
                                    } else if (string.equals("6")) {
                                        remainingWaterInBottle = RemainingLitre(6);
                                    } else if (string.equals("7")) {
                                        remainingWaterInBottle = RemainingLitre(7);
                                    } else if (string.equals("8")) {
                                        remainingWaterInBottle = RemainingLitre(8);
                                    } else if (string.equals("9")) {
                                        remainingWaterInBottle = RemainingLitre(9);
                                    }


                                    //Lưu số nước còn lại của người dùng vào SharePreferences
                                    PutDataSharepreferences("remainingLitre", "" + remainingWaterInBottle);

                                    //nếu như hết nước trong bình thì thông báo
                                    if (remainingWaterInBottle == 0 || remainingWaterInBottle <= 20) {
                                        try {
                                            Toast.makeText(getContext(), "Out Of Water", LENGTH_LONG).show();

                                        } catch (Exception e) {

                                            Toast.makeText(getContext(), "Out Of Water", LENGTH_LONG).show();
                                        }

                                        //Lần đầu vào app
                                        if (countWater == 0) {
                                            fakeWater = remainingWaterInBottle;
                                            countWater = 1;

                                        } else {
                                            if (fakeWater > remainingWaterInBottle) {
                                                int res = fakeWater - remainingWaterInBottle;
                                                saveDrankWater += res;
                                                PutDataSharepreferences("drankWaterSum", "" + saveDrankWater);
                                                tvDrank.setText("Drank: " + saveDrankWater + "ml");
                                                saveDrankWater -= res;

                                            }
                                        }
                                        //Ngược lại
                                    } else {
                                        if (remainingWaterInBottle != 100) {

                                            //Lần đầu vào app
                                            if (countWater == 0) {
                                                fakeWater = remainingWaterInBottle;
                                                countWater = 1;

                                            } else {

                                                //Kiểm tra nếu người dùng có đỏ nước
                                                // thêm vào
                                                /*if (remainingWaterInBottle > waterCheck) {
                                                      //lượng nước đã thêm vào
                                                        addWater=remainingWaterInBottle-waterCheck;
                                                    fakeWater+=addWater;
                                                }*/
                                                if (fakeWater >remainingWaterInBottle) {
                                                    int res = fakeWater - remainingWaterInBottle;
                                                    saveDrankWater += res;

                                                    PutDataSharepreferences("drankWaterSum", "" + saveDrankWater);
                                                    tvDrank.setText("Drank: " + saveDrankWater + "ml");
                                                    saveDrankWater -= res;
                                                }
                                            }

                                            //
                                            // waterCheck=remainingWaterInBottle;
                                        }

                                    }
                                    tvRemaining.setText("Remaining: " + remainingWaterInBottle + "ml");

                                    //




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
    //Hàm tính lượng ml nước còn lại

    public int RemainingLitre(int height) {
        if (height == 16 || height == 17) {
            return 0;
        } else if (height == 2) {
            return 535;
        } else if (height == 1) {
            return 610;
        } else {
            int heightFix = 17 - height;
            int ml = (heightFix * 500) / 14;
            return ml;

        }
    }

    //Hàm put data Sharepreferences spLitre

    public void PutDataSharepreferences(String name, String data) {
        SharedPreferences.Editor editor = spLitre.edit();
        editor.putString(name, data);
        editor.commit();
    }

    //


    //OnBackpress
    public static void onBackpressed() {
        Log.d("test", "Press");
    }

}




