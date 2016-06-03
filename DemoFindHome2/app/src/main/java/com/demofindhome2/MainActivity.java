package com.demofindhome2;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    TextView tv;
    EditText ed;
    ArrayList<Home> homeArrayList = new ArrayList<>();
    int checkFirtTime =0;
    private Location currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEnvents();
    }

    /*Initialze*/
    private void addControls() {
        //ed= (EditText) findViewById(R.id.ed);
        currentLocation = new Location("currentLocation");
      //  tv = (TextView) findViewById(R.id.tv);
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
     //   mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationChangeListener(listener);
    }

    /*Event*/
    private void addEnvents() {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    /*Listen for location change*/
    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(),
                    location.getLongitude());
            if (mMap != null) {
                mMap.clear();
                Marker marker = mMap.addMarker(new MarkerOptions().position(loc));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
                checkFirtTime++;

                currentLocation.setLatitude(location.getLatitude());
                currentLocation.setLongitude(location.getLongitude());
                Toast.makeText(getApplicationContext(), ""+checkFirtTime, Toast.LENGTH_LONG).show();
           //     tv.setText("Latitude: " + String.valueOf(location.getLatitude()) + "\n" + "Longtitude: " + String.valueOf(location.getLongitude()));
                if (checkFirtTime ==3) {//run only once
                   //  new ShowAllHomeInfor().execute();
                    checkFirtTime =20;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Please enable GPS", Toast.LENGTH_LONG).show();
            }

        }

    };

    /*  HTTP POST*/
    /*private String POST_URL(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(3);
        nameValuePair.add(new BasicNameValuePair("name",ed.getText().toString()));

        nameValuePair.add(new BasicNameValuePair("latitude", Double.toString(currentLocation.getLatitude())));
        nameValuePair.add(new BasicNameValuePair("longtitude", Double.toString(currentLocation.getLongitude())));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String kq = "";
        try {
            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            kq = EntityUtils.toString(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return kq;
    }*/

    //Get distance
    public void Get(View v) {
        Collections.sort(homeArrayList);
        String result = "";

        for (int i = 0; i < homeArrayList.size(); i++) {
            result+=homeArrayList.get(i).homeName;
            result+=String.valueOf(homeArrayList.get(i).distance);
        }
        tv.setText(result);
    }

    /*Add */
    public void Add(View v){
      //  new AddHomeInfor().execute();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
    /* Add Home Information to webservice*/
   /* class AddHomeInfor extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return POST_URL("http://192.168.43.141/code/FindHomeLaravelWebservice/public/FindHome", null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }*/

    /* Get all home from webservice*/
    class ShowAllHomeInfor extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return GET_URL("http://192.168.43.141/code/FindHomeLaravelWebservice/public/FindHome");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Location location = new Location("home");
                    location.setLatitude(jsonObject.getDouble("latitude"));
                    location.setLongitude(jsonObject.getDouble("longtitude"));
                    double distance = currentLocation.distanceTo(location);
                    homeArrayList.add(new Home(jsonObject.getString("name"), jsonObject.getDouble("latitude"), jsonObject.getDouble("longtitude"), distance));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /*HTTP GET*/
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

}
