package com.demosortingarraylistobject;

/**
 * Created by Administrator on 5/22/2016.
 */
public class Person implements Comparable<Person>{
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

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

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int compareTo(Person p) {
        int compareage=((Person)p).getAge();
        /* For Ascending order*/
        return this.getAge()-compareage;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }
}
