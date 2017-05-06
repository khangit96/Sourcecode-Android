package com.demomvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements LoginView{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void Login(View v) {
        LoginPresenter presenter=new LoginPresenter(this);
        presenter.login("khang","df");
    }

    @Override
    public void loginFail() {
        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_LONG).show();
    }

    @Override
    public void loginSuccess() {
        Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
    }
}
