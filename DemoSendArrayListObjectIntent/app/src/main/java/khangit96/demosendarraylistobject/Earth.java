package khangit96.demosendarraylistobject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 6/16/2016.
 */
public class Earth implements Parcelable {
    public Animal animal;
    public Person person;

    public Earth(Animal animal, Person person) {
        this.animal = animal;
        this.person = person;
    }
    protected Earth(Parcel in) {
        animal=in.readParcelable(getClass().getClassLoader());
        person=in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Earth> CREATOR = new Creator<Earth>() {
        @Override
        public Earth createFromParcel(Parcel in) {
            return new Earth(in);
        }

        @Override
        public Earth[] newArray(int size) {
            return new Earth[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable((Parcelable) animal, i);
        parcel.writeParcelable((Parcelable) person, i);
    }
}
