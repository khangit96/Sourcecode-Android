package com.demomvp;

/**
 * Created by Administrator on 4/22/2017.
 */

public class ModelLogin {
    LoginListener listener;
    public ModelLogin(LoginListener listener){
        this.listener=listener;
    }
    public void processLogin(String username,String password){
        if(username.equals("khang")&&password.equals("12345")){
            listener.onLoginSuccess();
        }
        else{
            listener.onLoginFail();
        }
    }
}
