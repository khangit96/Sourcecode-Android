package khangit96.quanlycaphe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.activity.admin.AdminActivity;
import khangit96.quanlycaphe.model.Admin;
import khangit96.quanlycaphe.model.Config;

public class LoginActivity extends AppCompatActivity {
    EditText edUsername, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addControls();
    }

    public void addControls() {
        edUsername = (EditText) findViewById(R.id.edUsername);
        edPassword = (EditText) findViewById(R.id.edPassword);
    }

    public void Login(View v) {
        Admin admin = new Admin(edPassword.getText().toString(), edUsername.getText().toString());
        if (admin.password.equals("") || admin.username.equals("")) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin đăng nhập!", Toast.LENGTH_LONG).show();
        } else
            checkLoginFirebase(edUsername.getText().toString(), edPassword.getText().toString());
    }

    public void checkLoginFirebase(final String username, final String password) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
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
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                        finish();
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
}
