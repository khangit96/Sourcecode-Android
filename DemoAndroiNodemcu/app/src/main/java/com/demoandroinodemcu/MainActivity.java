package com.demoandroinodemcu;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Random;

import static com.demoandroinodemcu.R.id.contentContainer;


public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    Fragment fr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initDrawer();
        final BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
              /*  if (tabId == R.id.tab_favorites) {
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }*/
                Random rd=new Random();
                initFragmentDay(rd.nextInt(100));
            }
        });

    }

    /*
    * */
    public void initFragmentDay(int value) {
        fr = new DayFragment();
        Bundle bd = new Bundle();
        bd.putInt("VALUE", value);
        fr.setArguments(bd);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(contentContainer, fr);
        fragmentTransaction.commit();
    }

    /*
       *
       * */
    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                Drawable drawable = menuItem.getIcon();
                //  drawable.setColorFilter(getResources().getColor(R.color.bg_login), PorterDuff.Mode.SRC_ATOP);
                switch (menuItem.getItemId()) {
                   /* case R.id.menu_login:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));

                    default:
                        break;*/
                }
                return true;
            }
        });

      /*  TextView tvAbout, tvLogin, tvShare;
        tvAbout = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_about));
        tvLogin = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_login));
        tvShare = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_share));

        customMenuItemDrawer(tvAbout, "Giới thiệu", 150);
        customMenuItemDrawer(tvLogin, "Đăng nhập", 145);
        customMenuItemDrawer(tvShare, "Chia sẻ", 170);*/

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

    /*
       *
       * */
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle("Today, 23 April");
        setSupportActionBar(toolbar);
    }

    private void setStateToFirebase(boolean state) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("state");
        myRef.setValue(state);
    }


    public void getDataFirebase() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("value");
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                // tvResult.setText("Distance: " + dataSnapshot.getValue() + " cm");
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
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }*/

}
