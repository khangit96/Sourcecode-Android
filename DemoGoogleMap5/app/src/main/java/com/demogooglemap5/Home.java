package com.demogooglemap5;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 6/14/2016.
 */
public class Home implements Parcelable, Comparable<Home>, Serializable {
    public double latitude;
    public double longtitude;
    public String name;
    public int distance;
    public Route route;

    public Home(double latitude, double longtitude, String name, int distance, Route route) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.name = name;
        this.distance = distance;
        this.route = route;
    }

    protected Home(Parcel in) {
        latitude = in.readDouble();
        longtitude = in.readDouble();
        name = in.readString();
        distance = in.readInt();
        route = in.readParcelable(getClass().getClassLoader());
    }

    public static final Creator<Home> CREATOR = new Creator<Home>() {
        @Override
        public Home createFromParcel(Parcel in) {
            return new Home(in);
        }

        @Override
        public Home[] newArray(int size) {
            return new Home[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(latitude);
        parcel.writeDouble(longtitude);
        parcel.writeString(name);
        parcel.writeInt(distance);
        parcel.writeParcelable(route, i);
    }

    @Override
    public int compareTo(Home home) {
        int compareage = ((Home) home).distance;
        /* For Ascending order*/
        return this.distance - compareage;
    }
}
