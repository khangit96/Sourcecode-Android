package com.demoandroinodemcu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class LoginActivity extends AppCompatActivity {

    public static SharedPreferences userSharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);
        if (userSharePreferences.getBoolean("LOGIN", false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        setContentView(R.layout.activity_login);
    }

    public void Login(View v) {
        final ProgressDialog pg = new ProgressDialog(LoginActivity.this);
        pg.setMessage("Đang kiểm tra thông tin...");
        pg.setCanceledOnTouchOutside(false);
        pg.show();

        final EditText edUsername = (EditText) findViewById(R.id.edUsername);
        final EditText edPassword = (EditText) findViewById(R.id.edPassword);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NguoiDung");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dt : dataSnapshot.getChildren()) {
                    ThongTinChung thongTinChung = dt.child("ThongTinChung").getValue(ThongTinChung.class);
                    if (thongTinChung.tenDangNhap.equals(edUsername.getText().toString()) && thongTinChung.matKhau.equals(edPassword.getText().toString())) {
                        setConfigUser(thongTinChung.tenDangNhap, thongTinChung.matKhau, dt.getKey().toString());
                        pg.dismiss();
                        startActivity(new Intent(LoginActivity.this, ThongTinActivity.class));
                        finish();
                        return;
                    }
                    pg.dismiss();
                    Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void setConfigUser(String tenDangNhap, String matKhau, String key) {

        ThongTinChung.TEN_DANG_NHAP = tenDangNhap;
        ThongTinChung.MAT_KHAU = matKhau;
        ThongTinChung.KEY = key;

        SharedPreferences.Editor editor = userSharePreferences.edit();
        editor.putString("TEN_DANG_NHAP", tenDangNhap);
        editor.putString("MAT_KHAU", matKhau);
        editor.putBoolean("LOGIN", true);
        editor.putString("KEY", key);
        editor.apply();

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }
}
