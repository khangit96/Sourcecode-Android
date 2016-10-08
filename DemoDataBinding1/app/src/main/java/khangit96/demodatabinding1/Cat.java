package khangit96.demodatabinding1;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by Administrator on 9/28/2016.
 */

public class Cat extends BaseObservable {
    private String name;
    private int age;
    private String owner;

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(khangit96.demodatabinding1.BR.name);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(khangit96.demodatabinding1.BR.age);
    }

    @Bindable
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
