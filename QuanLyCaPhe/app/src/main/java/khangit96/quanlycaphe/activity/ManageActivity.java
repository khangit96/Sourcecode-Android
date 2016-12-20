package khangit96.quanlycaphe.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.CustomGridViewAdapter;
import khangit96.quanlycaphe.databinding.ActivityManageBinding;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Table;

public class ManageActivity extends AppCompatActivity {
    ActivityManageBinding binding;
    GridView gridView;
    ArrayList<Table> gridArray = new ArrayList<Table>();
    CustomGridViewAdapter customGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set grid view item

        Bitmap table = BitmapFactory.decodeResource(this.getResources(), R.drawable.table);

        gridArray.add(new Table(1, "Bàn 1"));
        gridArray.add(new Table(2, "Bàn 2"));
        gridArray.add(new Table(3, "Bàn 3"));
        gridArray.add(new Table(4, "Bàn 4"));
        gridArray.add(new Table(5, "Bàn 5"));
        gridArray.add(new Table(6, "Bàn 6"));
        gridArray.add(new Table(7, "Bàn 7"));
        gridArray.add(new Table(8, "Bàn 8"));
        gridArray.add(new Table(9, "Bàn 9"));


        gridView = (GridView) findViewById(R.id.gridView1);
        customGridAdapter = new CustomGridViewAdapter(this, R.layout.row_grid, gridArray);
        gridView.setAdapter(customGridAdapter);

        addEvents();
    }

    private void addEvents() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent tableIntent = new Intent(ManageActivity.this, TableActivity.class);
                tableIntent.putExtra("TABLE_NUMBER", gridArray.get(i).tableNumber);
                startActivity(tableIntent);
            }
        });
    }

    public static void listenOrderFromFirebase(final int table, final TextView tv) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_NAME+"/Order/Table " + table);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                tv.setText("" + dataSnapshot.getChildrenCount());
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
