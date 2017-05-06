package com.demomvp;

/**
 * Created by Administrator on 4/27/2017.
 */

public class LoginPresenter implements OnLoginListener{
    LoginModel loginModel;
    LoginView loginView;

    public LoginPresenter(LoginView loginView){
        loginModel=new LoginModel(this);
        this.loginView=loginView;
    }

   public void login(String username,String password){
        loginModel.processLogin(username,password);
    }

    @Override
    public void onLoginSuccess() {
        loginView.loginSuccess();
    }

    @Override
    public void onLoginFail() {
        loginView.loginFail();
    }
}
