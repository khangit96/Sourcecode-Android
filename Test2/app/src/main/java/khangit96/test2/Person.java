package khangit96.test2;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by Administrator on 7/6/2016.
 */
public class Person {
    public String name;
    public int age;
    public static Activity context;
    public static Person person;

    public Person(String name, int age, Activity context) {
        this.name = name;
        this.age = age;
        this.context = context;
    }

    public static synchronized Person getPerson(Activity context) {
        if (person == null) {
            person = new Person("Duy Khang", 19, context);
        }
        return person;
    }

    public void show() {
        Toast.makeText(context, person.name, Toast.LENGTH_LONG).show();
    }
}
