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
    private ArrayList<Item> itemList = new ArrayList<>();
    private Toolbar toolbar;
    boolean isLedEnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initGridView();
        initDrawer();
        startService(new Intent(MainActivity.this, CheckErrorService.class));
    }

    public void initToolbar() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
    }

    public void initGridView() {
        final ProgressDialog pg = new ProgressDialog(MainActivity.this);
        pg.setMessage("Đang tải dữ liệu");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final ItemAdapter itemAdapter = new ItemAdapter(this, itemList);
        final DatabaseReference mData = FirebaseDatabase.getInstance().getReference();
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setAdapter(itemAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity = new Intent(MainActivity.this, DetailActivity.class);
                activity.putExtra("SYSTEM", (Serializable) itemList.get(position));
                startActivity(activity);
            }
        });
        mData.child("System").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Item item = dt.getValue(Item.class);
                    itemList.add(item);
                    itemAdapter.notifyDataSetChanged();
                    pg.dismiss();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog pg = new ProgressDialog(MainActivity.this);
                pg.setCanceledOnTouchOutside(false);

                if (isLedEnable) {
                    pg.setMessage("Đang bật đèn");
                    pg.show();
                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            pg.dismiss();
                            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                            fab.setColorFilter(getResources().getColor(android.R.color.white));
                        }
                    };
                    DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("ledEnable");
                    m.setValue(true, listener);
                    isLedEnable = false;
                } else {
                    DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            pg.dismiss();
                            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
                            fab.setColorFilter(getResources().getColor(android.R.color.darker_gray));
                        }
                    };
                    pg.setMessage("Đang tắt đèn");
                    pg.show();
                    DatabaseReference m = FirebaseDatabase.getInstance().getReference().child("ledEnable");
                    m.setValue(false, listener);
                    isLedEnable = true;
                }

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
