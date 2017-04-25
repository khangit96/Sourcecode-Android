package com.demomvp.View;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.demomvp.LoginListener;
import com.demomvp.ModelLogin;
import com.demomvp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void Login(View v) {
        ModelLogin login = new ModelLogin(new LoginListener() {
            @Override
            public void onLoginSuccess() {
                Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLoginFail() {
                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();

            }
        });

        login.processLogin("khang", "12345");
    }

}
