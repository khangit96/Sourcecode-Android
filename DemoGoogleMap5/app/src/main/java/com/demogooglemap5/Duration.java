package com.demogooglemap5;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 6/8/2016.
 */
public class Duration implements Parcelable{
    public String text;
    public int value;

    public Duration(String text, int value) {
        this.text = text;
        this.value = value;
    }

    protected Duration(Parcel in) {
        text = in.readString();
        value = in.readInt();
    }

    public static final Creator<Duration> CREATOR = new Creator<Duration>() {
        @Override
        public Duration createFromParcel(Parcel in) {
            return new Duration(in);
        }

        @Override
        public Duration[] newArray(int size) {
            return new Duration[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeInt(value);
    }
}