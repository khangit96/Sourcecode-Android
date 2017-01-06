package khangit96.quanlycaphe.activity;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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
import khangit96.quanlycaphe.model.Admin;
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
    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(Config.COMPANY_KEY);
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
        View headerLayout = navigationView.getHeaderView(0);

        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.menu_login:
                        showLoginDialog();
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
        initToolbar();

        //Drawer
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


    /*
    /*
    *
    * */
    public void checkLoginFirebase(final String username, final String password) {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang kiểm tra đăng nhập!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(Config.COMPANY_KEY + "/Admin");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dt : dataSnapshot.getChildren()) {

                    Admin admin = dt.getValue(Admin.class);
                    if (admin.username.equals(username) && admin.password.equals(password)) {
                        progressDialog.dismiss();
                        startActivity(new Intent(MainActivity.this, ManageActivity.class));
                        return;
                    }

                }

                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    *
    * */
    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Đăng nhập");

        final View viewInflated = LayoutInflater.from(getApplicationContext()).inflate(R.layout.input_login, null, false);

        builder.setView(viewInflated);
        builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText_username = (EditText) viewInflated.findViewById(R.id.editText_username);
                EditText editText_password = (EditText) viewInflated.findViewById(R.id.editText_password);
                checkLoginFirebase(editText_username.getText().toString(), editText_password.getText().toString());

            }
        });

        builder.show();
    }

}
