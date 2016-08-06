package tdmug3.revisioncustomlistviewbaseadapter;

import android.content.Context;

/**
 * Created by Administrator on 4/30/2016.
 */
public class Person {
    private String name;
    private int age;
    Context context;

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

    public Person(Context context, String NAME, int AGE) {
        this.setName(NAME);
        this.setAge(AGE);
        this.context=context;
    }
}
