package com.demomvp;

/**
 * Created by Administrator on 4/27/2017.
 */

public class LoginModel {
    OnLoginListener loginListener;
    public LoginModel(OnLoginListener loginListener){
        this.loginListener=loginListener;
    }

   public void processLogin(String username,String password) {
       if(username.equals("khang")&&password.equals("12345")){
           loginListener.onLoginSuccess();
       }
       else{
           loginListener.onLoginFail();
       }
    }
}
