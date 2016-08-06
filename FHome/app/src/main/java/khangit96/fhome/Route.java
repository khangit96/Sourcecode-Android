package khangit96.fhome;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Administrator on 7/2/2016.
 */
public class Route implements Parcelable, Serializable, Comparable<Route> {
    public String name;
    public Distance distance;
    public Duration duration;
    public String points;
    public String address;
    public StartLocation startLocation;
    public EndLocation endLocation;
    public String price;


    public Route(String name, String price, Distance distance, Duration duration, String points, String address, StartLocation startLocation, EndLocation endLocation) {
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.points = points;
        this.address = address;
        this.price = price;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    protected Route(Parcel in) {
        name = in.readString();
        price = in.readString();
        distance = in.readParcelable(getClass().getClassLoader());
        duration = in.readParcelable(getClass().getClassLoader());
        startLocation = in.readParcelable(getClass().getClassLoader());
        endLocation = in.readParcelable(getClass().getClassLoader());
        points = in.readString();
        address = in.readString();

    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(price);
        parcel.writeParcelable((Parcelable) distance, i);
        parcel.writeParcelable((Parcelable) duration, i);
        parcel.writeParcelable((Parcelable) startLocation, i);
        parcel.writeParcelable(endLocation, i);
        parcel.writeString(points);
        parcel.writeString(address);

    }

    @Override
    public int compareTo(Route route) {
        int compareage = ((Route) route).distance.value;
        /* For Ascending order*/
        return this.distance.value - compareage;
    }
}
