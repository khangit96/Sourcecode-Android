package khangit96.quanlycaphe.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.GridViewAdminAdapter;
import khangit96.quanlycaphe.model.ItemManage;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    GridView gvAdmin;
    GridViewAdminAdapter mAdapter;
    ArrayList<ItemManage> itemManageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initToolbar();
        initDrawer();
        initGridViewAdmin();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbarAdmin);
        setSupportActionBar(toolbar);
        setTitle("Admin");
    }

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_admin);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_admin);
        navigationView.setItemIconTintList(null);
        //  View headerLayout = navigationView.getHeaderView(0);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                Drawable drawable = menuItem.getIcon();
                drawable.setColorFilter(getResources().getColor(R.color.bg_login), PorterDuff.Mode.SRC_ATOP);
                switch (menuItem.getItemId()) {
                    case R.id.menu_login:
                        startActivity(new Intent(AdminActivity.this, LoginActivity.class));
                    default:
                        break;
                }
                return true;
            }
        });

        TextView tvAbout, tvLogin, tvShare;
        tvAbout = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_about));
        tvLogin = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_login));
        tvShare = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menu_share));

        customMenuItemDrawer(tvAbout, "Giới thiệu", 150);
        customMenuItemDrawer(tvLogin, "Đăng nhập", 145);
        customMenuItemDrawer(tvShare, "Chia sẻ", 170);

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
         */
    public void customMenuItemDrawer(TextView tv, String text, int paddingRight) {
        tv.setText(text);
        tv.setTextColor(getResources().getColor(R.color.colorMenuItem));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(17);
        tv.setPadding(15, 20, paddingRight, 0);
    }

    public void initGridViewAdmin() {
        gvAdmin = (GridView) findViewById(R.id.gvAdmin);

        itemManageList = new ArrayList<>();
        itemManageList.add(new ItemManage("Đặt Hàng", "Order", R.drawable.ic_order));
        itemManageList.add(new ItemManage("Thức Uống", "Food", R.drawable.ic_food));
        itemManageList.add(new ItemManage("Bàn", "Table", R.drawable.ic_table));
        itemManageList.add(new ItemManage("Thống Kê", "Statistic", R.drawable.ic_pie_chart));
        itemManageList.add(new ItemManage("Tài Khoản", "Account", R.drawable.ic_account));
        itemManageList.add(new ItemManage("Thoát", "Exit", R.drawable.ic_exit));

        mAdapter = new GridViewAdminAdapter(this, R.layout.row_grid_item_admin, itemManageList);
        gvAdmin.setAdapter(mAdapter);
    }
}
