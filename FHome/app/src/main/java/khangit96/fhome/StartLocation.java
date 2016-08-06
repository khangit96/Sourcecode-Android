package khangit96.fhome;

/**
 * Created by Administrator on 7/2/2016.
 */

import android.os.Parcel;
import android.os.Parcelable;

public class StartLocation implements Parcelable {
    public double latitude;
    public double longtitude;

    public StartLocation(double latitude, double longtitude) {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

    protected StartLocation(Parcel in) {
        latitude = in.readDouble();
        longtitude = in.readDouble();
    }

    public static final Creator<StartLocation> CREATOR = new Creator<StartLocation>() {
        @Override
        public StartLocation createFromParcel(Parcel in) {
            return new StartLocation(in);
        }

        @Override
        public StartLocation[] newArray(int size) {
            return new StartLocation[size];
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
