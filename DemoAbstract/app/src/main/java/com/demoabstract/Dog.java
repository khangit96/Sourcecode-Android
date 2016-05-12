package com.demoabstract;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 5/11/2016.
 */
public class Dog extends Animal {
    public Context context;
    public Dog(Context context,String name,String color){
        this.context=context;
        this.setName(name);
        this.setColor(color);
    }
    @Override
    public void run() {
        Toast.makeText(context.getApplicationContext(),this.getName()+" is running",Toast.LENGTH_LONG).show();
    }

    @Override
    public void eat() {

    }
}
