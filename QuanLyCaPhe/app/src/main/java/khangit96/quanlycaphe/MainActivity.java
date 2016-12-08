package khangit96.quanlycaphe;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button button_order;
    ArrayList<Food> foodList;
    Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();

    }

    public void addControls() {
        button_order = (Button) findViewById(R.id.button_order);
        foodList = new ArrayList<>();
        foodList.add(new Food("Nước Chanh", 20000, 1));
        foodList.add(new Food("Cà Phê Sữa", 25000, 1));
        foodList.add(new Food("Nước Chanh Muối", 24000, 1));
        order = new Order("8", foodList, 200000);
    }

    public void addEvents() {
        button_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Order").push();
                mDatabase.setValue(order);
            }
        });
    }

    public void checkLoginFirebase(final String username, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang kiểm tra đăng nhập!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Admin");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    Admin admin = (Admin) dt.getValue(Admin.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_login:
                showLoginDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

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
