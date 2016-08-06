package tdmug3.locationbasedservice;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 5/1/2016.
 */
public class LocationService extends Service {
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //init location
       final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = locationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(provider, 1000, 1, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longtitude = location.getLongitude();
                Toast.makeText(getApplicationContext(),"la: "+latitude+"long: "+longtitude,Toast.LENGTH_LONG).show();
                Location home = new Location("home");
                home.setLatitude(10.832513);
                home.setLongitude(106.35087);
                double distance = home.distanceTo(location);
                Toast.makeText(getBaseContext(), "distance: " + distance, Toast.LENGTH_LONG).show();
                 //Log.d("location", "lat: " + latitude + "long: " + longtitude);
                if(distance<=10){
                    Intent intent1=new Intent();
                    intent1.putExtra("location_status",true);
                    intent1.setAction("detect_location_status");
                    sendBroadcast(intent1);
                }
                else {
                    Intent intent1=new Intent();
                    intent1.putExtra("location_status",false);
                    intent1.setAction("detect_location_status");
                    sendBroadcast(intent1);
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
