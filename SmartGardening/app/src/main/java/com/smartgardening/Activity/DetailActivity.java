package com.smartgardening.Activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartgardening.R;
import com.smartgardening.ThongTinHeThong;


public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView temp, loaiCay, ground_humidity;
    ThongTinHeThong item;
    String key;
    public int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        initEvents();
        initToolbar();
    }

    /*
    * Init toolbar
    * */
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.leaf);
        setTitle(item.tenHeThong);
    }


    private void init() {
        item = (ThongTinHeThong) getIntent().getSerializableExtra("SYSTEM");
        key = item.key;
        temp = (TextView) findViewById(R.id.tvTemperature);
        loaiCay = (TextView) findViewById(R.id.tvLoaiCay);
        loaiCay.setText("Loại cây: " + item.loaiCay);
        ground_humidity = (TextView) findViewById(R.id.tvDoAm);
        if (key.equals("2")) {
            findViewById(R.id.lnKeoMang).setVisibility(View.GONE);
        }


        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child(key + "/DuLieu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String nhietDo = dataSnapshot.child("NhietDo").getValue().toString();
                String doAm1 = dataSnapshot.child("DoAm1").getValue().toString();
                //String doAm2 = dataSnapshot.child("DoAm2").getValue().toString();
                // String doAm3 = dataSnapshot.child("DoAm3").getValue().toString();

                String nhietDoNumber1 = nhietDo.substring(1, 2);
                String nhietDoNumber2 = nhietDo.substring(2, 3);
                String nhietDoNumber3 = nhietDo.substring(3, 4);

                String doAm1Number1 = doAm1.substring(1, 2);
                String doAm1Number2 = doAm1.substring(2, 3);
                String doAm1Number3 = doAm1.substring(3, 4);

                temp.setText("Nhiệt độ: " + nhietDoNumber1 + nhietDoNumber2 + "." + nhietDoNumber3 + "°C");
                ground_humidity.setText("Độ ẩm đất trung bình: " + doAm1Number1 + doAm1Number2 + doAm1Number3 + "%");

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    * Init Event
    * */
    public void initEvents() {

        //Event Watering
        final SwitchCompat switchWatering = (SwitchCompat) findViewById(R.id.switchWatering);
        switchWatering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ProgressDialog pg = new ProgressDialog(DetailActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (switchWatering.isChecked()) {
                    pg.setMessage("Đang bật máy bơm...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/batMayBom");
                    mDatabase.setValue(true, listener);

                } else {
                    pg.setMessage("Đang tắt máy bơm" +
                            "...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/batMayBom");
                    mDatabase.setValue(false, listener);
                }
            }
        });

        //Event push
        final SwitchCompat switchPush = (SwitchCompat) findViewById(R.id.switchPush);
        switchPush.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ProgressDialog pg = new ProgressDialog(DetailActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (switchPush.isChecked()) {
                    pg.setMessage("Đang bật kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/keoMang");
                    mDatabase.setValue(true, listener);

                } else {
                    pg.setMessage("Đang tắt kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/keoMang");
                    mDatabase.setValue(false, listener);
                }
            }
        });

        //Event enable led
        final SwitchCompat switchEnableLed = (SwitchCompat) findViewById(R.id.switchEnableLed);
        switchEnableLed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final ProgressDialog pg = new ProgressDialog(DetailActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (switchEnableLed.isChecked()) {
                    pg.setMessage("Đang bật đèn ...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/batDen");
                    mDatabase.setValue(true, listener);

                } else {
                    pg.setMessage("Đang tắt đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(item.key + "/DieuKhien/batDen");
                    mDatabase.setValue(false, listener);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*if (item.getItemId() == R.id.menCauHinh) {
            //showDialogSettingWifi();
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

  /*  private void showDialogSettingWifi() {
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
*/
}
