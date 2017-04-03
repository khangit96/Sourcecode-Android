package com.smartgardening;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ThongTinHeThong> thongTinHeThongList = new ArrayList<>();
    private Toolbar toolbar;
    boolean isFabLedClick = false, isFabWatering = false, isFabPush = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initGridView();
        initDrawer();
        initEvents();
    }

    /*
    * Init Event
    * */
    private void initEvents() {

        //Event led
        findViewById(R.id.fabLed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(MainActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (isFabLedClick) {

                    pg.setMessage("Đang tắt đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/batDen");
                    mDatabase.setValue(false, listener);

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabLed);
                    fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    isFabLedClick = false;

                } else {
                    pg.setMessage("Đang tắt đèn...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/batDen");
                    mDatabase.setValue(true, listener);
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabLed);
                    fab.setColorFilter(getResources().getColor(android.R.color.white));
                    isFabLedClick = true;
                }

            }
        });

        //Event watering
        findViewById(R.id.fabWatering).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(MainActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (isFabWatering) {

                    pg.setMessage("Đang tắt máy bơm...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/batMayBom");
                    mDatabase.setValue(false, listener);

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabWatering);
                    fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    isFabWatering = false;

                } else {
                    pg.setMessage("Đang bật máy bơm...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/batMayBom");
                    mDatabase.setValue(true, listener);

                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabWatering);
                    fab.setColorFilter(getResources().getColor(android.R.color.white));
                    isFabWatering = true;
                }
            }
        });

        //Event push
        findViewById(R.id.fabPush).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(MainActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (isFabPush) {
                    pg.setMessage("Đang tắt kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/keoMang");
                    mDatabase.setValue(false, listener);
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabPush);
                    fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                    isFabPush = false;

                } else {
                    pg.setMessage("Đang bật kéo màng che...");
                    pg.show();

                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/tinhTrang");
                            mDatabase.setValue(true);
                            pg.dismiss();
                        }
                    };
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("DieuKhienChung/keoMang");
                    mDatabase.setValue(true, listener);
                    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabPush);
                    fab.setColorFilter(getResources().getColor(android.R.color.white));
                    isFabPush = true;
                }
            }
        });
    }

    /*
    * Init toolbar
    * */
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
    }

    public void initGridView() {
        final ProgressDialog pg = new ProgressDialog(MainActivity.this);
        pg.setMessage("Đang tải dữ liệu...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final ThongTinHeThongAdapter thongTinHeThongAdapter = new ThongTinHeThongAdapter(this, thongTinHeThongList);
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(thongTinHeThongAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity = new Intent(MainActivity.this, DetailActivity.class);
                activity.putExtra("SYSTEM", (Serializable) thongTinHeThongList.get(position));
                startActivity(activity);
            }
        });

        mData.child("/").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    if (!dt.getKey().equals("DieuKhienChung")) {
                        DataSnapshot dt1 = dt.child("ThongTinHeThong");
                        ThongTinHeThong thongTinHeThong = dt1.getValue(ThongTinHeThong.class);
                        thongTinHeThongList.add(thongTinHeThong);
                        thongTinHeThongAdapter.notifyDataSetChanged();
                        pg.dismiss();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void initDrawer() {
        //DrawerLayout
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        //NavigationView
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
