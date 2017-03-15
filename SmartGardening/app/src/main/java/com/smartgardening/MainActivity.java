package com.smartgardening;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
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

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Item> itemList = new ArrayList<>();
    private ItemAdapter itemAdapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initGridView();
        initDrawer();

    }

    public void initToolbar() {
        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
    }


    public void initGridView() {
        ItemAdapter itemAdapter = new ItemAdapter(this, itemList);
        itemList.add(new Item("Hệ Thống 1"));
        itemList.add(new Item("Hệ Thống 2"));
        itemList.add(new Item("Hệ Thống 3"));
        itemList.add(new Item("Hệ Thống 4"));
        itemList.add(new Item("Hệ Thống 5"));
        itemList.add(new Item("Hệ Thống 6"));
        itemList.add(new Item("Hệ Thống 7"));
        itemList.add(new Item("Hệ Thống 8"));
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
