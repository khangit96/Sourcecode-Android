package com.demogooglemap5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 6/16/2016.
 */
public class EndLocation implements Parcelable {
    public double latitude;
    public double longtitude;

    public EndLocation(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    protected EndLocation(Parcel in) {
        latitude = in.readDouble();
        longtitude = in.readDouble();
    }

    public static final Creator<EndLocation> CREATOR = new Creator<EndLocation>() {
        @Override
        public EndLocation createFromParcel(Parcel in) {
            return new EndLocation(in);
        }

        @Override
        public EndLocation[] newArray(int size) {
            return new EndLocation[size];
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
    }
}
