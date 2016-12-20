package khangit96.quanlycaphe.activity;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.CustomSpinnerAdapter;
import khangit96.quanlycaphe.adapter.FoodAdapter;
import khangit96.quanlycaphe.databinding.ActivityMainBinding;
import khangit96.quanlycaphe.model.Admin;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Food;
import khangit96.quanlycaphe.model.Order;


public class MainActivity extends AppCompatActivity {
    ArrayList<Food> foodList;
    ArrayList<String> tableList;
    private FoodAdapter mAdapter;
    Toolbar toolbar;
    Spinner spn;
    public static ActivityMainBinding binding;
    public static int selectedItemSpinnerPos = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        addControls();
        addEvents();
    }

    private void addEvents() {
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                selectedItemSpinnerPos = pos;
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
        setTitle("Menu");
    }

    /*
    *
    * */
    public void initSpinner() {
        spn = (Spinner) findViewById(R.id.spn);
        tableList = new ArrayList<>();
        tableList.add("Chọn bàn ");
        tableList.add("Bàn 1");
        tableList.add("Bàn 2");
        tableList.add("Bàn 3");
        tableList.add("Bàn 4");
        tableList.add("Bàn 5");
        CustomSpinnerAdapter spinnerAdapter = new CustomSpinnerAdapter(MainActivity.this, R.layout.list_table, tableList);
        spn.setAdapter(spinnerAdapter);
    }

    /*
    *
    * */
    private void initDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = navigationView.getHeaderView(0);
       /* ImageView image = (ImageView) headerLayout.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.logo);*/
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);
                binding.drawerLayout.closeDrawers();
                switch (menuItem.getItemId()) {
                    case R.id.menu_login:
                        showLoginDialog();
                    default:
                        break;
                }

                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    /*
    * */
    public void addControls() {
        foodList = new ArrayList<>();
        foodList.add(new Food("Trà Lipton", 30000, R.drawable.tralipton));
        foodList.add(new Food("Cà Phê Sữa", 35000, R.drawable.caphesua));
        foodList.add(new Food("Nước Cam ", 45000, R.drawable.nuoccam));
        foodList.add(new Food("Trà Đào", 45000, R.drawable.tradaojpg));
        foodList.add(new Food("Cà Phê Đá", 45000, R.drawable.capheden));
        foodList.add(new Food("Trà Sữa", 45000, R.drawable.trasua));
        foodList.add(new Food("Nước Chanh Dây", 45000, R.drawable.nuocchangday));

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
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        binding.recyclerView.setLayoutManager(mLayoutManager);
        binding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerView.setAdapter(mAdapter);
    }


    /*
    *
    * */
    public static void pushOrderToFirebase(Order order, int table) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_NAME + "/Order/Table " + table).push();
        mDatabase.setValue(order);

    }

    /*
    *
    * */
    public void checkLoginFirebase(final String username, final String password) {

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang kiểm tra đăng nhập!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child(Config.COMPANY_NAME + "/Admin");
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
