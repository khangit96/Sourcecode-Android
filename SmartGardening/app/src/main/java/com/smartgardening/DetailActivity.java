package com.smartgardening;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.smartgardening.R.id.edMatKhau;
import static com.smartgardening.R.id.edTenWifi;


public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView temp, humidity, ground_humidity;
    Item item;
    String key;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        initEvents();
        initToolbar();
        getDataFireBase();
        SetData();
    }

    public void initEvents() {
        final ToggleButton toggleLed = (ToggleButton) findViewById(R.id.toggleLed);
        toggleLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                final ProgressDialog pg = new ProgressDialog(DetailActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (isChecked) {
                    pg.setMessage("Đang tắt đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("System/" + item.key + "/ledEnable");
                    mDatabase.setValue(false, listener);

                } else {
                    pg.setMessage("Đang bật đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("System/" + item.key + "/ledEnable");
                    mDatabase.setValue(true, listener);

                }
            }
        });
    }

    private void getDataFireBase() {
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child("System").child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                item = dataSnapshot.getValue(Item.class);
                SetData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void initToolbar() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(item.name);
        Config.NAME = item.name;
    }

    private void init() {
        item = (Item) getIntent().getSerializableExtra("SYSTEM");
        key = item.key;
        temp = (TextView) findViewById(R.id.tvTemperature);
        humidity = (TextView) findViewById(R.id.tvNang);
        ground_humidity = (TextView) findViewById(R.id.tvDoAm);
    }

    private void SetData() {
        temp.setText("Nhiệt độ: " + item.temp + "°C");
        humidity.setText("Độ ẩm không khí: " + item.humidity + "%");
        ground_humidity.setText("Độ ẩm đất: " + item.ground_humidity + "%");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menCauHinh) {
            showDialogSettingWifi();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void showDialogSettingWifi() {
        count = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);

        final View inflate = LayoutInflater.from
                (getApplicationContext()).inflate(R.layout.dialog_setting_wifi, null, false);

        TextView tvTenHeThong = (TextView) inflate.findViewById(R.id.tvTenHeThong);
        tvTenHeThong.setText(Config.NAME);

        TextView tvTinhTrang = (TextView) inflate.findViewById(R.id.tvTinhTrang);
        final EditText edTenWifi = (EditText) inflate.findViewById(R.id.edTenWifi);
        final EditText edMatKhau = (EditText) inflate.findViewById(R.id.edMatKhau);

        if (item.key.equals("1")) {
            if (Config.STATUS1) {
                tvTinhTrang.setText("Đang hoạt động");
            } else {
                tvTinhTrang.setText("Đã ngừng hoạt động");
            }
        } else if (item.key.equals("2")) {
            if (Config.STATUS2) {
                tvTinhTrang.setText("Đang hoạt động");
            } else {
                tvTinhTrang.setText("Đã ngừng hoạt động");
            }
        } else {
            if (Config.STATUS3) {
                tvTinhTrang.setText("Đang hoạt động");
            } else {
                tvTinhTrang.setText("Đã ngừng hoạt động");
            }
        }
        builder.setView(inflate);

        builder.setNegativeButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (item.key.equals("3")) {
                    final ProgressDialog pg = new ProgressDialog(DetailActivity.this);
                    pg.setMessage("Đang cấu hình wifi...");
                    pg.setCanceledOnTouchOutside(false);
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            DatabaseReference mDatabaseWifiChange = FirebaseDatabase.getInstance().getReference().child("System/3/wifiChange");
                            mDatabaseWifiChange.setValue(true);


                            DatabaseReference mDatabaseWifiSuccess = FirebaseDatabase.getInstance().getReference().child("System/3/wifiSuccess");
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

                                                    pg.dismiss();
                                                    Toast.makeText(getApplicationContext(), "Cấu hình thành công", Toast.LENGTH_LONG).show();
                                                } else {
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

                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("System/3/Wifi");
                    mDatabase.setValue(new Wifi(edMatKhau.getText().toString(), edTenWifi.getText().toString()), listener);
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

}
