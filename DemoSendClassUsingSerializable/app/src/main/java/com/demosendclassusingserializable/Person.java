package com.demosendclassusingserializable;

import java.io.Serializable;

/**
 * Created by Administrator on 5/31/2016.
 */
public class Person implements Serializable {
    public String name;
    public int age;

    public Person(int age, String name) {
        this.age = age;
        this.name = name;
    }
}
