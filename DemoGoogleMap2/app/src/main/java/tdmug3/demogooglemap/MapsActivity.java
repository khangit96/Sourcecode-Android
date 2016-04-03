package tdmug3.demogooglemap;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.os.Handler;

public class MapsActivity extends AppCompatActivity {

    private GoogleMap mMap;
    public Button btGetCurrentLocation;
    ProgressDialog progressDialog;
    Handler handler = new Handler();
    Activity activity = MapsActivity.this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        btGetCurrentLocation = (Button) findViewById(R.id.btGetCurrentLocation);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Please ait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //Thiết lập sự kiện tải map thành công
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                progressDialog.dismiss();

            }
        });

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //Hiển thị zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);
        //set Location
        mMap.setMyLocationEnabled(true);

        //Khi location thay đỏi
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                TuiDangODau();//lấy vị trí hiện tại
            }

        });

        //Add maker
        LatLng latLng = new LatLng(10.8325849,106.3508966);
        AddMaker(latLng);
        }


    //Hàm xác định vị trí hiện tại
    private void TuiDangODau() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        Location lastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (lastLocation != null) {
            LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))
                    .zoom(15)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            Toast.makeText(getApplicationContext(),"Latitude: "+lastLocation.getLatitude()+"\n"+"Longtitude: "+lastLocation.getLongitude(),Toast.LENGTH_LONG).show();
        }
    }

    //Khi nhấn button để lấy vị trí hiện tại
    public void GetCurrent(View v) {
        TuiDangODau();
    }

    //Hàm lấy tên vị trí hiện tại
    public void getCurrentLocationName(Location lastLocation) {
      /*  Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        try {
            List<Address> listAddresses = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1);
            if (null != listAddresses && listAddresses.size() > 0) {
                String nameLocation = listAddresses.get(0).getAddressLine(0);
                Toast.makeText(getApplicationContext(), nameLocation, Toast.LENGTH_LONG).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        //
            /*    Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(activity, Locale.getDefault());

                // try {
                try {
                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    //get current Street name
                    String address = addresses.get(0).getAddressLine(0);

                    //get current province/City
                    String province = addresses.get(0).getAdminArea();

                    //get country
                    String country = addresses.get(0).getCountryName();

                    //get postal code
                    String postalCode = addresses.get(0).getPostalCode();

                    //get place Name
                    String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
                    Toast.makeText(getApplicationContext(),address,Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

    }


    //Hàm thêm maker
    public void AddMaker(LatLng latLng) {
        MarkerOptions option = new MarkerOptions();
        option.title("Chổ tui đứng");
        option.snippet("Latitude: "+10.8325849+"\n"+"Longtitude"+106.3508966);
        option.position(latLng);
        Marker currentMarker = mMap.addMarker(option);
        currentMarker.showInfoWindow();
    }


    public void getAddressFromLocation(
            final Location location, final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;
                try {
                    List<Address> list = geocoder.getFromLocation(
                            location.getLatitude(), location.getLongitude(), 1);
                    if (list != null && list.size() > 0) {
                        Address address = list.get(0);
                        // sending back first address line and locality
                        result = address.getAddressLine(0) + ", " + address.getLocality();
                    }
                } catch (IOException e) {
                    //Log.e(TAG, "Impossible to connect to Geocoder", e);
                } finally {
                    Message msg = Message.obtain();
                    msg.setTarget(handler);
                    if (result != null) {
                        msg.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putString("address", result);
                        msg.setData(bundle);
                    } else
                        msg.what = 0;
                    msg.sendToTarget();
                }

            }
        };
        thread.start();
    }

    public Address getAddressForLocation(Context context, Location location) throws IOException {

        if (location == null) {
            return null;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        int maxResults = 1;

        Geocoder gc = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = gc.getFromLocation(latitude, longitude, maxResults);

        if (addresses.size() == 1) {
            return addresses.get(0);
        } else {
            return null;
        }
    }

}
