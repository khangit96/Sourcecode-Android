package com.minhuyenwallpaper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<Wallpaper> wallpapers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initRecyclerViewWallpaper();
        getDataFromFirebase();


    }


    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initRecyclerViewWallpaper() {
        wallpapers = new ArrayList<>();
       /* wallpapers.add(new Wallpaper("Ellsat", "http://s3.zerochan.net/Ellsat.240.2071450.jpg"));
        wallpapers.add(new Wallpaper("Fate Grand", "http://s3.zerochan.net/Fate.Grand.Order.240.2071447.jpg"));
        wallpapers.add(new Wallpaper("Berserker", "http://s3.zerochan.net/Berserker.%28Florence.Nightingale%29.240.2071444.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.2071453.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.2032535.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.2026360.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.1955693.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.1900609.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.1897900.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.1841032.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.1827875.jpg"));
        wallpapers.add(new Wallpaper("Yuuri", "http://s3.zerochan.net/Yuuri.%28Asterisk%29.240.1809742.jpg"));*/

        // Calling the RecyclerView
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        // The number of Columns
        mLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new GridAdapter(MainActivity.this, wallpapers);
        mRecyclerView.setAdapter(mAdapter);
    }


    public void getDataFromFirebase() {
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference().child("Category/Sport");
        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Wallpaper wallpaper = dataSnapshot.getValue(Wallpaper.class);
                wallpapers.add(0, wallpaper);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        /*mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dtCategory : dataSnapshot.getChildren()) {
                    for (DataSnapshot dtDetail : dtCategory.getChildren()) {
                        Wallpaper wallpaper = dtDetail.getValue(Wallpaper.class);
                        wallpapers.add(wallpaper);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
    }
}
