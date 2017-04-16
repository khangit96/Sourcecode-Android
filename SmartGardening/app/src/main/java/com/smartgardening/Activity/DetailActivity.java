package com.smartgardening.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartgardening.Fragment.CaiDatHoaCaiFragment;
import com.smartgardening.Fragment.CaiDatHoaLanFragment;
import com.smartgardening.Fragment.DieuKhienFragment;
import com.smartgardening.Fragment.DuLieuFragment;
import com.smartgardening.Fragment.ThongTinHoaCaiFragment;
import com.smartgardening.Fragment.ThongTinHoaLanFragment;
import com.smartgardening.Model.ThongTinHeThong;
import com.smartgardening.R;


public class DetailActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView temp, loaiCay, ground_humidity;
    public static ThongTinHeThong item;
    public static String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();
        initToolbar();
        loaFragment(new DieuKhienFragment());
    }

    public void loaFragment(Fragment fr) {
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment, fr);
        ft.commit();
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

        DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        mData.child(key + "/DuLieu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (key.equals("1")) {

                    String doAm1 = dataSnapshot.child("DoAm1").getValue().toString();
                    String doAm1Number2 = doAm1.substring(2, 3);
                    String doAm1Number3 = doAm1.substring(3, 4);

                    ground_humidity.setText("Độ ẩm đất trung bình: " + doAm1Number2 + doAm1Number3 + "%");
                } else {
                    String doAmKK = dataSnapshot.child("DoAmKhongKhi").getValue().toString();
                    ground_humidity.setText("Độ ẩm không khí: " + doAmKK + "%");
                }


                String nhietDo = dataSnapshot.child("NhietDo").getValue().toString();
                /*String nhietDoNumber1 = nhietDo.substring(1, 2);
                String nhietDoNumber2 = nhietDo.substring(2, 3);
                String nhietDoNumber3 = nhietDo.substring(3, 4);*/

                temp.setText("Nhiệt độ: " + nhietDo + "°C");


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuDuLieu) {
            loaFragment(new DuLieuFragment());

        } else if (item.getItemId() == R.id.menuThongTin) {
            if (key.equals("1")) {
                loaFragment(new ThongTinHoaCaiFragment());
            } else {
                loaFragment(new ThongTinHoaLanFragment());
            }


        } else if (item.getItemId() == R.id.menuDieuKhien) {
            loaFragment(new DieuKhienFragment());
        } else {
            if (key.equals("1")) {
                loaFragment(new CaiDatHoaCaiFragment());
            } else {
                loaFragment(new CaiDatHoaLanFragment());
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
