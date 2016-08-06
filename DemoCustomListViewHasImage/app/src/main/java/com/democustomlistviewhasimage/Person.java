package com.democustomlistviewhasimage;

/**
 * Created by Administrator on 5/13/2016.
 */
public class Person  {
    private String name;
    private int age;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
    public Person(String Name,int Age,int Image){
        this.setName(Name);
        this.setAge(Age);
        this.setImage(Image);
    }
}
