package khangit96.demosendarraylistobject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 6/16/2016.
 */
public class Animal implements Parcelable {
    public String name;

    public Animal(String name) {
        this.name = name;
    }

    protected Animal(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Animal> CREATOR = new Creator<Animal>() {
        @Override
        public Animal createFromParcel(Parcel in) {
            return new Animal(in);
        }

        @Override
        public Animal[] newArray(int size) {
            return new Animal[size];
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
