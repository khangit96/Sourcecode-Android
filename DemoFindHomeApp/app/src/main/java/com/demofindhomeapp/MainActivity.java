package com.demofindhomeapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String name;
    double latitude;
    double longtitude;
    TextView tv;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv=(TextView)findViewById(R.id.tv);
        //init location
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = locationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(provider,1000, 1, new
                LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        latitude =  location.getLatitude();
                        longtitude =location.getLongitude();
                       /* count++;
                        //if (count == 10) {
                          *//*  tv.setText(""+latitude);
                            new ShowAllHomeInfor().execute();*//*
                        // }*/
                       // if(count==0){
                            //if(String.valueOf(latitude)!=null&&String.valueOf(location)!=null){
                               // count=1;
                              //  new ShowAllHomeInfor().execute();

                           // }
                       //
                  //      new AddHomeInfor().execute();
                      /*  new ShowAllHomeInfor().execute();
                        tv.setText("la:"+latitude+"long: "+longtitude);*/
                        Location currentLocation = new Location("currentLocation");
                        currentLocation.setLatitude(10.832483);
                        currentLocation.setLongitude(106.34962);
                        Location b = new Location("location");
                        b.setLatitude(latitude);
                        b.setLongitude(longtitude);
                        double distance = currentLocation.distanceTo(b);
                        tv.setText(""+distance);

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
    }
    private String POST_URL(String url, String type) {
        HttpClient httpClient = new DefaultHttpClient();

        // URL của trang web nhận request
        HttpPost httpPost = new HttpPost(url);

        // Các tham số truyền
        List nameValuePair = new ArrayList(3);
        nameValuePair.add(new BasicNameValuePair("name","Home"));

        nameValuePair.add(new BasicNameValuePair("latitude",String.valueOf(latitude)));
        nameValuePair.add(new BasicNameValuePair("longtitude",String.valueOf(longtitude)));

        //Encoding POST data
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair,"utf-8"));
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
    }

   public void Add(View v){
   }
    class AddHomeInfor extends AsyncTask<String,Integer,String>{

        @Override
        protected String doInBackground(String... params) {
            return POST_URL("http://192.168.1.5/code/FindHomeLaravelWebservice/public/FindHome",null);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }
    class ShowAllHomeInfor extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return GET_URL("http://192.168.1.5/code/FindHomeLaravelWebservice/public/FindHome");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
              /*  for (int i = 0; i < jsonArray.length(); i++) {
                    count++;
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                }*/
                JSONObject jsonObject = jsonArray.getJSONObject(4);
                double la=jsonObject.getDouble("latitude");
                double lon=jsonObject.getDouble("longtitude");
                Location currentLocation = new Location("currentLocation");
                currentLocation.setLatitude(latitude);
                currentLocation.setLongitude(longtitude);
                Location location = new Location("location");
                location.setLatitude(la);
                location.setLongitude(lon);
                double distance = currentLocation.distanceTo(location);
                tv.setText(""+distance);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
