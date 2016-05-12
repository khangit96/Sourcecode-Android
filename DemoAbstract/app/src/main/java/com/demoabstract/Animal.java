package com.demoabstract;

/**
 * Created by Administrator on 5/11/2016.
 */
public abstract class Animal {
    private String name;
    private String color;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
    public abstract void run();
    public abstract void eat();


}
