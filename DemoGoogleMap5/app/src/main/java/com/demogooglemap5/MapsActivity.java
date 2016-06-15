package com.demogooglemap5;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DirectionFinderListener {
    double latitude, longtitude;
    private GoogleMap mMap;
    EditText ed;
    private ProgressDialog progressDialog;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    int count = 0;
    ArrayList<Home> homeArrayList = new ArrayList<>();
    List<Route> routes = new ArrayList<Route>();
    int check = -1;
    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            count++;

            if (count == 3) {
                LatLng hcmus = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(hcmus).title("Chỗ tui đứng"));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 16f));
                progressDialog.dismiss();
                latitude = location.getLatitude();
                longtitude = location.getLongitude();
                ed.setText(Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude()));
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ed = (EditText) findViewById(R.id.ed);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        homeArrayList.add(new Home(10.8325733, 106.3512633, "Nhà cô hai ", 0, routes));
        homeArrayList.add(new Home(10.8326316, 106.3513716, "Nhà bà bảy ", 0, routes));
        homeArrayList.add(new Home(10.8325966, 106.3508533, "Ghế đá nhà Khang", 0, routes));
        homeArrayList.add(new Home(10.8325009, 106.3493161, "Nhà nội", 0, routes));
        homeArrayList.add(new Home(10.8325433, 106.35098, "Nhà tấm Khang", 0, routes));
        homeArrayList.add(new Home(10.8325216, 106.3508766, "Bàn máy tính nhà Khang", 0, routes));
        homeArrayList.add(new Home(10.80954, 106.3669016, "Chợ xã Bình Hòa Nam", 0, routes));
        homeArrayList.add(new Home(10.828845, 106.3513416, "Nhà Cô Ba", 0, routes));
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xác định vị trí hiện tại của bạn....");
        progressDialog.show();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the userS has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        //set Location
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
     /*   LatLng hcmus = new LatLng(10.762984,106.682329);
        mMap.addMarker(new MarkerOptions().position(hcmus).title("KHTN"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 16f));
        LatLng dhsg=new LatLng(10.759677,106.682387);
        mMap.addPolyline(new PolylineOptions().add(
                        hcmus,
                        new LatLng(10.761184, 106.683331),
                        new LatLng(10.760710, 106.682204),
                        dhsg)
                        .width(10)
                        .color(Color.RED)

        );*/
    }

    public void Find(View v) {
        // sendRequest();
        progressDialog.setMessage("Đang tìm nhà trọ quang đây");
        progressDialog.show();
        for (int i = 0; i < homeArrayList.size(); i++) {
            String location = String.valueOf(homeArrayList.get(i).latitude) + "," + String.valueOf(homeArrayList.get(i).longtitude);
            new ReadJsonGoogleMap().execute("https://maps.googleapis.com/maps/api/directions/json?origin=" + ed.getText().toString() + "&destination=" + location + "&key=AIzaSyAzxaiKRJ88fKFkSapcaoJG1SDjtn5cPt0");

        }
    }

    private static String GET_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    class ReadJsonGoogleMap extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            return GET_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            JSONObject jsonData = null;
            try {
                check++;
                jsonData = new JSONObject(s);
                JSONArray jsonRoutes = jsonData.getJSONArray("routes");
                for (int i = 0; i < jsonRoutes.length(); i++) {
                    Route route = new Route();
                    JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                    JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                    JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                    JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                    JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                    JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                    JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                    JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
                    int distance = jsonDistance.getInt("value");
                    route.distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
                    route.duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
                    route.endAddress = jsonLeg.getString("end_address");
                    route.startAddress = jsonLeg.getString("start_address");
                    route.startLocation = new LatLng(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
                    route.endLocation = new LatLng(jsonEndLocation.getDouble("lat"), jsonEndLocation.getDouble("lng"));
                    route.points = decodePolyLine(overview_polylineJson.getString("points"));
                    routes.add(route);
                    Home home = new Home(homeArrayList.get(check).latitude, homeArrayList.get(check).longtitude, homeArrayList.get(check).name, distance, routes);
                    homeArrayList.set(check, home);
                }
                if (check == homeArrayList.size() - 1) {
                    progressDialog.dismiss();
                 /*   Collections.sort(homeArrayList);
                    Intent intent = new Intent(MapsActivity.this, ResultActivity.class);
                    intent.putParcelableArrayListExtra("homeArrayList", (ArrayList<? extends Parcelable>) homeArrayList);
                    startActivity(intent);*/
                  /*  Toast.makeText(getApplicationContext(), "" + routes.size(), Toast.LENGTH_LONG).show();
                    drawPolyline(routes);*/
                    //Toast.makeText(getApplicationContext(),""+homeArrayList.get(0).routeList.size(),Toast.LENGTH_LONG).show();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**/
    private List<LatLng> decodePolyLine(final String poly) {
        int len = poly.length();
        int index = 0;
        List<LatLng> decoded = new ArrayList<LatLng>();
        int lat = 0;
        int lng = 0;

        while (index < len) {
            int b;
            int shift = 0;
            int result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = poly.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            decoded.add(new LatLng(
                    lat / 100000d, lng / 100000d
            ));
        }

        return decoded;
    }

    public void drawPolyline(List<Route> routes) {

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
        /**/
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    private void sendRequest() {
        String origin = "10.8325966,106.3508533";
        String destination = "10.8325433,106.35098";
        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            ed.setText("Duration:" + route.duration.text + "Distance: " + route.distance.text);

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }
}
