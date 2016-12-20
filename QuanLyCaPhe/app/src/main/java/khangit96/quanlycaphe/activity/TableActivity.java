package khangit96.quanlycaphe.activity;

import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Food;
import khangit96.quanlycaphe.model.Order;

public class TableActivity extends AppCompatActivity {
    ListView lvTable;
    ArrayAdapter<String> adapter;
    ArrayList<String> tableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        int tableNumber = getIntent().getExtras().getInt("TABLE_NUMBER");

        setTitle(String.format(getString(R.string.tableActivity), String.valueOf(tableNumber)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addControls();
        getOrderTableFromFirebase(tableNumber);

    }

    public void addControls() {
        lvTable = (ListView) findViewById(R.id.lvTable);
        tableList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tableList);
        lvTable.setAdapter(adapter);
    }

    public void getOrderTableFromFirebase(int tableNumber) {

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Config.COMPANY_NAME + "/Order/Table " + tableNumber).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                List<Food> foodList = new ArrayList<Food>();
                DataSnapshot dtFoodList = dataSnapshot.child("foodList");

                for (DataSnapshot dt : dtFoodList.getChildren()) {
                    String foodName = dt.child("foodName").getValue().toString();
                    int foodImage = Integer.parseInt(dt.child("foodImage").getValue().toString());
                    double foodPrice = Double.parseDouble(dt.child("foodPrice").getValue().toString());
                    foodList.add(new Food(foodName, foodPrice, foodImage));
                }

                double totalPrice = Double.parseDouble(dataSnapshot.child("totalPrice").getValue().toString());

                Order order = new Order(foodList, totalPrice);

                tableList.add("" + order.totalPrice);
                adapter.notifyDataSetChanged();

                lvTable.post(new Runnable() {
                    @Override
                    public void run() {
                        // Select the last row so it will scroll into view...
                        lvTable.setSelection(lvTable.getCount() - 1);
                    }
                });

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
