package khangit96.quanlycaphe.activity.admin;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.adapter.AdminTableAdapter;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Table;

public class AdminTableActivity extends AppCompatActivity {
    RecyclerView recylerViewTable;
    public static List<Table> tableList;
    public static AdminTableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_table);

        initToolbar();
        intRecylerViewTable();
        addEvents();
        loadDataTableFromFirebase();
    }

    private void addEvents() {
        findViewById(R.id.fabAddTable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogAddTable();
            }
        });
    }

    public void showDialogAddTable() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_add_table_admin, null);
        final EditText edTable = (EditText) mView.findViewById(R.id.edTableName);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(mView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addTableToFirebase(edTable.getText().toString());
            }
        });
        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }


    private void addTableToFirebase(String tableName) {
        if (tableName.equals("")) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập tên bàn", Toast.LENGTH_LONG).show();
            return;
        }

        int tableNumber = tableList.size() + 1;
        Table table = new Table(tableNumber, tableName);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Config.COMPANY_KEY + "/Table/" + tableNumber).setValue(table);

        tableList.add(table);
        adapter.notifyDataSetChanged();

    }

    public void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Bàn");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void intRecylerViewTable() {
        recylerViewTable = (RecyclerView) findViewById(R.id.recylerViewTable);
        recylerViewTable.setLayoutManager(new LinearLayoutManager(this));
        tableList = new ArrayList<>();

    }

    public void loadDataTableFromFirebase() {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Config.COMPANY_KEY + "/Table").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Table table = dt.getValue(Table.class);
                    tableList.add(table);
                }
                adapter = new AdminTableAdapter(tableList, AdminTableActivity.this);
                recylerViewTable.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_admin, menu);
        return true;
    }
}
