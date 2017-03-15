package com.demoandroinodemcu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class NguoiDungActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        initToolbar();
        initControls();
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Tài khoản");
    }

    public void initControls() {
        TextView tvTaiKhoan, tvChieuCao, tvCanNang, tvSoLitNuoc;
        tvTaiKhoan = (TextView) findViewById(R.id.tvTaiKhoan);
        tvChieuCao = (TextView) findViewById(R.id.tvChieuCao);
        tvCanNang = (TextView) findViewById(R.id.tvCanNang);
        tvSoLitNuoc = (TextView) findViewById(R.id.tvSoLitNuoc);

        SharedPreferences userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);

        tvTaiKhoan.setText(userSharePreferences.getString("TEN_DANG_NHAP", ""));
        tvChieuCao.setText(userSharePreferences.getFloat("CHIEU_CAO", 0) + " CM");
        tvCanNang.setText(userSharePreferences.getFloat("CAN_NANG", 0) + " KG");
        tvSoLitNuoc.setText(String.format("%.2f", userSharePreferences.getFloat("SO_LIT", 0)) + " ML");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_nguoi_dung, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuSetting) {

            SharedPreferences userSharePreferences = this.getSharedPreferences("USER_INFOR", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = userSharePreferences.edit();
            editor.putBoolean("LOGIN", false);
            editor.putFloat("DA_UONG", 0);
            editor.apply();

            Intent intent = new Intent(NguoiDungActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } else if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

