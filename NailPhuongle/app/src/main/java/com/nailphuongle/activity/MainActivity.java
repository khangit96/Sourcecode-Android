package com.nailphuongle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.nailphuongle.R;
import com.nailphuongle.adapter.CustomGridViewAdapter;
import com.nailphuongle.model.Item;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    CustomGridViewAdapter adapter;
    ArrayList<Item> listItem;
    GridView gvItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        initToolbar();
        initDrawer();
    }

    public void addControls() {
        listItem = new ArrayList<>();
        listItem.add(new Item("Website", "Website", 1));
        listItem.add(new Item("Fanpage", "Fanpage", 1));
        listItem.add(new Item("Video Channel", "Video", 1));
        listItem.add(new Item("Album Channel", "Album", 1));

        gvItem = (GridView) findViewById(R.id.gvItem);
        adapter = new CustomGridViewAdapter(MainActivity.this, R.layout.item_gridview, listItem);
        gvItem.setAdapter(adapter);
    }

    public void addEvents() {
        gvItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(MainActivity.this, ItemActivity.class));
            }
        });
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("PLApp");
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

               /* if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                Drawable drawable = menuItem.getIcon();
                drawable.setColorFilter(getResources().getColor(R.color.bg_login), PorterDuff.Mode.SRC_ATOP);
                switch (menuItem.getItemId()) {
                    case R.id.menu_login:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    default:
                        break;
                }*/
                return true;
            }
        });

      /*  TextView tvAbout, tvLogin, tvShare;
        tvAbout = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_about));
        tvLogin = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_login));
        tvShare = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_share));

        customMenuItemDrawer(tvAbout, "Giới thiệu", 150);
        customMenuItemDrawer(tvLogin, "Đăng nhập", 150);
        customMenuItemDrawer(tvShare, "Chia sẻ", 180);*/

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
}
