package khangit96.quanlycaphe.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.FoodInDialogAdapter;
import khangit96.quanlycaphe.adapter.FoodInTableAdapter;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Food;
import khangit96.quanlycaphe.model.Order;
import khangit96.quanlycaphe.model.RecyclerItemClickListener;

public class TableActivity extends AppCompatActivity {

    ArrayList<Order> orderList;
    FoodInTableAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        int tableNumber = getIntent().getExtras().getInt("TABLE_NUMBER");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(String.format(getString(R.string.tableActivity), String.valueOf(tableNumber)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        addEvents();

        getOrderTableFromFirebase(tableNumber);
    }

    /*
    *
    * */
    public void addControls() {

        orderList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new FoodInTableAdapter(getApplicationContext(), orderList);
    }

    /*
    *
    * */
    private void addEvents() {

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                adapter.setSelected(position);
                showDialogOrderDetail((ArrayList<Food>) orderList.get(position).foodList);
            }

        }));
    }

    /*
    *
    * */
    public void getOrderTableFromFirebase(int tableNumber) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_KEY + "/Order/Table " + tableNumber);
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                List<Food> foodList = new ArrayList<Food>();
                DataSnapshot dtFoodList = dataSnapshot.child("foodList");

                for (DataSnapshot dt : dtFoodList.getChildren()) {
                    Food food = dt.getValue(Food.class);
                    foodList.add(food);
                }

                double totalPrice = Double.parseDouble(dataSnapshot.child("totalPrice").getValue().toString());
                Order order = new Order(foodList, totalPrice);
                orderList.add(order);

                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);

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

    /*
    *
    * */
    private void showDialogOrderDetail(ArrayList<Food> foodList) {

        AlertDialog.Builder builder = new AlertDialog.Builder(TableActivity.this);

        final View inflate = LayoutInflater.from
                (getApplicationContext()).inflate(R.layout.show_listview_dialog, null, false);

        RecyclerView re = (RecyclerView) inflate.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager mLayout = new LinearLayoutManager(getApplicationContext());
        re.setLayoutManager(mLayout);

        FoodInDialogAdapter ad = new FoodInDialogAdapter(getApplicationContext(), foodList);
        re.setAdapter(ad);

        builder.setView(inflate);

        builder.setNegativeButton("Thanh toán", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
