package com.demogooglemap5;

import android.graphics.Point;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 6/8/2016.
 */
public class Route implements Parcelable, Serializable, Comparable<Route> {
    public String name;
    public Distance distance;
    public Duration duration;
    public String points;
    public String endAddress;
    //public LatLng endLocation;
    public String startAddress;
    // public LatLng startLocation;
    public StartLocation startLocation;
    public EndLocation endLocation;

    //   public List<LatLng> points;


    public Route(String name, Distance distance, Duration duration, String points, String startAddress, String endAddress, StartLocation startLocation, EndLocation endLocation) {
        this.name = name;
        this.distance = distance;
        this.duration = duration;
        this.points = points;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
    }

    protected Route(Parcel in) {
        name = in.readString();
        distance = in.readParcelable(getClass().getClassLoader());
        duration = in.readParcelable(getClass().getClassLoader());
        startLocation = in.readParcelable(getClass().getClassLoader());
        endLocation = in.readParcelable(getClass().getClassLoader());
        points = in.readString();
        startAddress = in.readString();
        endAddress = in.readString();

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
        parcel.writeParcelable((Parcelable) distance, i);
        parcel.writeParcelable((Parcelable) duration, i);
        parcel.writeParcelable((Parcelable) startLocation, i);
        parcel.writeParcelable(endLocation, i);
        parcel.writeString(points);
        parcel.writeString(startAddress);
        parcel.writeString(endAddress);
    }

    @Override
    public int compareTo(Route route) {
        int compareage = ((Route) route).distance.value;
        /* For Ascending order*/
        return this.distance.value - compareage;
    }
}
