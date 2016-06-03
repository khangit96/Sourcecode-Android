package com.learngooglemap;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private GoogleMap mMap;
    Spinner spinnerMapType;
    ArrayList<String> dsType;
    ArrayAdapter<String> adapterType;
    ProgressDialog progressDialog;
    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if (mMap != null) {
                mMap.clear();
                Marker marker = mMap.addMarker(new
                        MarkerOptions().position(loc));

                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }
    public void Start(View v){
        startActivity(new Intent(MainActivity.this,Main2Activity.class));
    }

    private void addControls() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mMap = mapFragment.getMap();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    public void requestPermissions(@NonNull String[] permissions, int requestCode)
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(listener);
        spinnerMapType = (Spinner) findViewById(R.id.spinner);
        dsType = new ArrayList<>();
        dsType.addAll(Arrays.asList(getResources().getStringArray(R.array.arrayType)));
        adapterType = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, dsType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMapType.setAdapter(adapterType);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Alert");
        progressDialog.setMessage("Loading map...Please wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void addEvents() {
        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                xuLyDoiCheDoHienThi(position);
                Toast.makeText(getApplicationContext(), "" + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                progressDialog.dismiss();
                /*show current location*/
                // Add a marker in Sydney and move the camera
               /* LatLng currentLocation = new LatLng(10.8230989, 106.6296638);
                mMap.addMarker(
                        new MarkerOptions()
                                .position(currentLocation)
                                .title("Marker in Sydney")
                                .snippet("Current Location"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,13));*/
            }
        });
    }

    private void xuLyDoiCheDoHienThi(int position) {
        switch (position) {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 4:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
    }
}
