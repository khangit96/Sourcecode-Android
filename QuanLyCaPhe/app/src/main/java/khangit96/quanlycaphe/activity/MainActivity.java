package khangit96.quanlycaphe.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.CustomSpinnerAdapter;
import khangit96.quanlycaphe.adapter.FoodAdapter;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Food;
import khangit96.quanlycaphe.model.Table;


public class MainActivity extends AppCompatActivity {

    ArrayList<Food> foodList;
    private FoodAdapter mAdapter;
    Toolbar toolbar;
    Spinner spn;
    DrawerLayout mDrawerLayout;
    RecyclerView recyclerView;

    public static ArrayList<Table> tableList;
    public static int tableNumberSelected = 0;
    public static Context context;

    public static Button buttonOrder;
    public Integer LOGIN_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();
        loadFoodFromFirebase();
    }

    private void addEvents() {
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                tableNumberSelected = tableList.get(pos).tableNumber;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /*
    *
    * */
    public void initToolbar(String title, int id) {
        toolbar = (Toolbar) findViewById(id);
        setSupportActionBar(toolbar);
        setTitle(title);
    }

    /*
    *
    * */
    public void initSpinner() {
        spn = (Spinner) findViewById(R.id.spn);
        tableList = (ArrayList<Table>) loadTableFromFirebase();
    }

    /*
    *
    * */
    public List<Table> loadTableFromFirebase() {

        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Đang tải dữ liệu...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        List<Table> list = new ArrayList<>();
        list.add(new Table(0, "Chọn bàn"));
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_KEY + "/Table");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Table table = dt.getValue(Table.class);
                    tableList.add(table);

                }
                CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(MainActivity.this, R.layout.list_table, tableList);
                spn.setAdapter(spinnerAdapter);
                pd.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return list;
    }

    /*
    *
    * */
    public void loadFoodFromFirebase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Config.COMPANY_KEY + "/Food").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Food food = dt.getValue(Food.class);
                    foodList.add(food);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    *
    * */
    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
                        Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(loginIntent, LOGIN_REQUEST_CODE);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == LOGIN_REQUEST_CODE) {
            setContentView(R.layout.activity_admin);
            initAdminUI();
        }
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

    /*
    * */
    public void addControls() {
        buttonOrder = (Button) findViewById(R.id.button_order);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        foodList = new ArrayList<>();
        context = getApplicationContext();

        //Spinner table
        initSpinner();

        //RecylerView
        initRecylerview();

        //Toolbar
        initToolbar(Config.COMPANY_KEY, R.id.toolbarMain);

        //Drawer
        initDrawer();
    }

    public void initAdminUI() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        initToolbar("Admin", R.id.toolbarAdmin);
        initDrawer();
    }

    /*
    * */
    public void initRecylerview() {
        mAdapter = new FoodAdapter(getApplicationContext(), foodList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
