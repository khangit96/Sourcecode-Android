package khangit96.demolocationtrack;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final LocationManager locationManagers = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isNetworkEnabled;
        Location location = new Location("");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManagers.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                1000, 1, this);
        // Log.d("Network", "Network");
        if (locationManagers != null) {
            location = locationManagers.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                //latitude = location.getLatitude();
                //longitude = location.getLongitude();

                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());
                //  addresses = geocoder.getFromLocation(latitude, longitude, 1);

                //   address = addresses.get(0).getAddressLine(0);
                //                              String city = addresses.get(0).getAddressLine(1);
                //                              String country = addresses.get(0).getAddressLine(2);
            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(getApplicationContext(), "oo", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}