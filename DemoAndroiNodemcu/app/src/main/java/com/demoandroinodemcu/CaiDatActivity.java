package com.demoandroinodemcu;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

import static com.demoandroinodemcu.LoginActivity.userSharePreferences;

public class CaiDatActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    Toolbar toolbar;
    TextView tvThongBao, tvHenGio, tvTuDongKetNoi;
    CheckBox cbThongBao, cbTuDongKetNoi;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        initToolbar();
        initControls();
        initvents();
    }

    /*
   *
   * */
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Cài đặt");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void initControls() {

        tvThongBao = (TextView) findViewById(R.id.tvThongBao);
        tvTuDongKetNoi = (TextView) findViewById(R.id.tvTuDongKetNoi);
        tvHenGio = (TextView) findViewById(R.id.tvHenGio);
        cbThongBao = (CheckBox) findViewById(R.id.cbThongBao);
        cbTuDongKetNoi = (CheckBox) findViewById(R.id.cbTuDongKetNoi);

        if (userSharePreferences.getBoolean("THONG_BAO", false)) {
            cbThongBao.setChecked(true);
            tvThongBao.setText("Bật");
        } else {
            cbThongBao.setChecked(false);
            tvThongBao.setText("Tắt");
        }

        if (userSharePreferences.getBoolean("TU_DONG", false)) {
            cbTuDongKetNoi.setChecked(true);
            tvTuDongKetNoi.setText("Bật");
        } else {
            cbTuDongKetNoi.setChecked(false);
            tvTuDongKetNoi.setText("Tắt");
        }
        tvHenGio.setText(String.valueOf(userSharePreferences.getInt("HOUR", 0)) + ":" + String.valueOf(userSharePreferences.getInt("MINUTE", 0)));

    }

    public void initvents() {

        findViewById(R.id.imgSetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogSettingWifi();
            }
        });

        findViewById(R.id.imgAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        cbThongBao = (CheckBox) findViewById(R.id.cbThongBao);
        cbThongBao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbThongBao.isChecked()) {

                    tvThongBao.setText("Bật");
                    putBooleanValueSharePreferences("THONG_BAO", true);
                } else {

                    tvThongBao.setText("Tắt");
                    putBooleanValueSharePreferences("THONG_BAO", false);
                }
            }
        });

        cbTuDongKetNoi = (CheckBox) findViewById(R.id.cbTuDongKetNoi);
        cbTuDongKetNoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbTuDongKetNoi.isChecked()) {

                    tvTuDongKetNoi.setText("Bật");
                    putBooleanValueSharePreferences("TU_DONG", true);
                } else {

                    tvTuDongKetNoi.setText("Tắt");
                    putBooleanValueSharePreferences("TU_DONG", false);
                }
            }
        });
    }

    public void putBooleanValueSharePreferences(String key, boolean value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putBoolean(key, value);

        editor.apply();
    }

    public void putIntValueSharePreferences(String key, int value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putInt(key, value);

        editor.apply();
    }

    /*
   *
   * */
    private void showDialogSettingWifi() {
        count = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(CaiDatActivity.this);

        final View inflate = LayoutInflater.from
                (getApplicationContext()).inflate(R.layout.dialog_setting_wifi, null, false);

        TextView tvTinhTrang = (TextView) inflate.findViewById(R.id.tvTinhTrang);
        final EditText edTenWifi = (EditText) inflate.findViewById(R.id.edTenWifi);
        final EditText edMatKhau = (EditText) inflate.findViewById(R.id.edMatKhau);

        edTenWifi.setText(userSharePreferences.getString("TEN_WIFI", "khangtdmu"));
        edMatKhau.setText(userSharePreferences.getString("MAT_KHAU", "12345678"));

        if (userSharePreferences.getBoolean("STATUS", false)) {
            tvTinhTrang.setText("Đã kết nối");
        } else {
            tvTinhTrang.setText("Chưa kết nối");
        }
        builder.setView(inflate);

        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (userSharePreferences.getBoolean("STATUS", false)) {

                    final ProgressDialog pg = new ProgressDialog(CaiDatActivity.this);
                    pg.setMessage("Đang cấu hình wifi...");
                    pg.setCanceledOnTouchOutside(false);
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            DatabaseReference mDatabaseWifiChange = FirebaseDatabase.getInstance().getReference().child("WifiChange");
                            mDatabaseWifiChange.setValue(true);


                            DatabaseReference mDatabaseWifiSuccess = FirebaseDatabase.getInstance().getReference().child("wifiSuccess");
                            mDatabaseWifiSuccess.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    count++;
                                    if (count > 1) {
                                        int count1 = 0;
                                        for (DataSnapshot dt : dataSnapshot.getChildren()) {
                                            count1++;
                                            if (count1 == dataSnapshot.getChildrenCount()) {
                                                if ((Boolean) dt.getValue() == true) {

                                                    putStringValueSharePreferences("TEN_WIFI", edTenWifi.getText().toString());
                                                    putStringValueSharePreferences("MAT_KHAU", edMatKhau.getText().toString());
                                                    pg.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Cấu hình thành công", Toast.LENGTH_LONG).show();
                                                }
                                                else{
                                                    pg.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Cấu hình thất bại", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        }
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    };

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Wifi");
                    mDatabase.setValue(new Wifi(edMatKhau.getText().toString(), edTenWifi.getText().toString()), listener);

                } else {
                    Toast.makeText(getApplicationContext(), "Vui lòng kết nối thiết bị để thay đổi cấu hình wifi", Toast.LENGTH_LONG).show();
                }


            }
        });
        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    public void putStringValueSharePreferences(String key, String value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putString(key, value);

        editor.apply();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        putIntValueSharePreferences("HOUR", hourOfDay);
        putIntValueSharePreferences("MINUTE", minute);
        putIntValueSharePreferences("SECOND", second);
        tvHenGio.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND,0);
        Intent myIntent = new Intent(CaiDatActivity.this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(CaiDatActivity.this, 0, myIntent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

    }

    public void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(
                (TimePickerDialog.OnTimeSetListener) CaiDatActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        timePickerDialog.setAccentColor(Color.parseColor("#30B3C5"));
        timePickerDialog.show(getFragmentManager(), "Timepicker");
    }
}
