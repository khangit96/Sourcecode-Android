package khangit96.demosendarraylistobject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 6/16/2016.
 */
public class Person implements Parcelable{
    public String name;

    public Person(String name) {
        this.name = name;
    }

    protected Person(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
    }
}
