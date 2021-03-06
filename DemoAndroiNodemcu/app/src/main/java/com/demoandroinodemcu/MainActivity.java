package com.demoandroinodemcu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.Calendar;

import static com.demoandroinodemcu.LoginActivity.userSharePreferences;

public class MainActivity extends AppCompatActivity {
    public static Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private static int FRAGMENT_INDEX = 0;
    private static boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initDrawer();
        initFragment(new TodayFragment(), null);

    }

    public void initFragment(Fragment fr, Bundle bd) {
        if (bd != null) {
            fr.setArguments(bd);
        }

        if (fr == new TodayFragment()) {
            FRAGMENT_INDEX = 0;
        } else if (fr == new PreviousDayFragment()) {
            FRAGMENT_INDEX = 1;
        } else {
            FRAGMENT_INDEX = 2;
        }

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fr, "DEMO");
        fragmentTransaction.commitAllowingStateLoss();
    }


    /*
     *
     * */
    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View navHeader;
        navHeader = navigationView.getHeaderView(0);

        SharedPreferences userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);
        TextView tvProfile = (TextView) navHeader.findViewById(R.id.tvProfile);
        tvProfile.setText(userSharePreferences.getString("TEN_DANG_NHAP", "KBOTTLE"));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.menuLichTrinh:
                        initFragment(new TodayFragment(), null);
                        setTitle("Lịch Trình");
                        break;
                    case R.id.menuTaiKhoan:
                        startActivity(new Intent(MainActivity.this, NguoiDungActivity.class));
                        break;
                    case R.id.menuSetting:
                        startActivity(new Intent(MainActivity.this, CaiDatActivity.class));
                        break;
                    case R.id.menuGioiThieu:
                        AlertDialog.Builder dialogGioiThieu = new AlertDialog.Builder(MainActivity.this);
                        dialogGioiThieu.setTitle("Giới thiệu");
                        dialogGioiThieu.setIcon(R.mipmap.ic_launcher);
                        dialogGioiThieu.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogBox, int id) {
                                // ToDo get user input here
                            }
                        });
                        dialogGioiThieu.setMessage("Đây là bình nước thông minh-sản phẩm IoT của trường Đại Học Thủ Dầu Một.");
                        dialogGioiThieu.show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

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
        setTitle("Lịch Trình");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        /*DatabaseReference mData = FirebaseDatabase.getInstance().getReference().child("status");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              *//*  if ((Boolean) dataSnapshot.getValue() == true) {
                    check = true;
                    menu.getItem(0).setIcon(R.drawable.ic_wifi_on);
                } else {
                    check = false;
                    menu.getItem(0).setIcon(R.drawable.ic_wifi_off);
                }*//*
                Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_LONG).show();
                invalidateOptionsMenu();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuCalendar) {

            CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                    .setOnDateSetListener(new CalendarDatePickerDialogFragment.OnDateSetListener() {
                        @Override
                        public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {

                            String day = String.valueOf(dayOfMonth) + " / " + String.valueOf(monthOfYear + 1) + " / " + String.valueOf(year);
                            String day1 = String.valueOf(dayOfMonth) + "-" + String.valueOf(monthOfYear + 1);
                            Bundle bd = new Bundle();
                            bd.putString("DAY", day);
                            bd.putString("DAY1", day1);

                            Calendar c = Calendar.getInstance();
                            int currentMonth = c.get(Calendar.MONTH) + 1;
                            int currentDay = c.get(Calendar.DATE);

                            if (dayOfMonth > currentDay) {

                                if (monthOfYear + 1 >= currentMonth) {

                                    initFragment(new TomorrowDaykFragment(), bd);

                                } else {
                                    initFragment(new PreviousDayFragment(), bd);
                                }

                            } else if (dayOfMonth < currentDay) {

                                if (monthOfYear + 1 <= currentMonth) {
                                    initFragment(new PreviousDayFragment(), bd);

                                } else {
                                    initFragment(new TomorrowDaykFragment(), bd);
                                }

                            } else if (dayOfMonth == currentDay && monthOfYear + 1 == currentMonth) {
                                initFragment(new TodayFragment(), null);
                            }

                        }
                    })
                    .setFirstDayOfWeek(Calendar.SUNDAY)
                    .setDoneText("Đồng ý")
                    .setCancelText("Huỷ bỏ");
            cdp.show(getSupportFragmentManager(), "lđ");

        }
        return super.onOptionsItemSelected(item);
    }

    public void putBooleanValueSharePreferences(String key, boolean value) {
        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putBoolean(key, value);

        editor.apply();
    }
}
