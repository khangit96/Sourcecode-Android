package khangit96.fhome;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private GoogleMap mMap;

    ProgressDialog progressDialog;

    TextView tvThongBao;

    public static int count = 0;
    public int check = -1;

    LatLng viTriHienTai;
    Location loHienTai;

    ArrayList<Route> routes = new ArrayList<Route>();
    GoogleMap.OnMyLocationChangeListener listener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            count++;
            viTriHienTai = new LatLng(location.getLatitude(), location.getLongitude());
            loHienTai = location;
            if (count == 3) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viTriHienTai, 16f));
             /*   CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                        .zoom(13)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
                tvThongBao.setText("Chạm vào icon tìm kiếm để tìm kiếm nhà trọ.");
            } else if (count < 3) {
                tvThongBao.setText("Đang xác định vị trí hiện tại của bạn.");
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        KhoiTaoControl();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setCompassEnabled(false);
                mMap.setTrafficEnabled(true);
                mMap.setOnMyLocationChangeListener(listener);
            }
        });

    /*    mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                count++;
                viTriHienTai = new LatLng(location.getLatitude(), location.getLongitude());
                if (count == 3) {
                    loHienTai = location;
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viTriHienTai, 13f));
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(13)                   // Sets the zoom
                            .bearing(90)                // Sets the orientation of the camera to east
                            .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                            .build();                   // Creates a CameraPosition from the builder
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    tvThongBao.setText("Chạm vào icon tìm kiếm để tìm kiếm nhà trọ.");
                } else if (count < 3) {
                    tvThongBao.setText("Đang xác định vị trí hiện tại của bạn.");
                }
            }
        });*/

      /*Maker listen*/
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                OpenBottomSheet(marker.getTitle().toString(), "Ok");
                return true;
            }
        });
        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                drawerLayout.closeDrawers();

                switch (menuItem.getItemId()) {
                   /* case R.id.find:
                        return true;*/

                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }


    /*Hàm hởi tạo các control*/
    public void KhoiTaoControl() {
        tvThongBao = (TextView) findViewById(R.id.tvThongBao);
        LatLng hcmus = new LatLng(10.980808, 106.682577);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 16f));
        if (KiemTraTinhTrangGPS() == false) {
            tvThongBao.setText("Vui lòng bật GPS và thử lại.");
            return;
        }
        tvThongBao.setText("Đang xác định vị trí hiện tại của bạn.");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.gps) {
            if (KiemTraTinhTrangGPS() == false) {
                Toast.makeText(getApplicationContext(), "Vui lòng bật GPS và thử lại!", Toast.LENGTH_LONG).show();
                tvThongBao.setText("Vui lòng bật GPS và thử lại!");
                return false;
            }
            if (count < 3) {
                Toast.makeText(getApplicationContext(), "Đang xác định vị trí hiện tại của bạn.Vui lòng chờ!", Toast.LENGTH_LONG).show();
                tvThongBao.setText("Đang xác định vị trí hiện tại của bạn.");
                return false;
            }
            TuiDangODau();
        } else if (id == R.id.search) {
            if (KiemTraTinhTrangGPS() == false) {
                Toast.makeText(getApplicationContext(), "Vui lòng bật GPS và thử lại!", Toast.LENGTH_LONG).show();
                tvThongBao.setText("Vui lòng bật GPS và thử lại!");
                return false;
            }
            if (count < 3) {
                Toast.makeText(getApplicationContext(), "Đang xác định vị trí hiện tại của bạn.Vui lòng chờ!", Toast.LENGTH_LONG).show();
                tvThongBao.setText("Đang xác định vị trí hiện tại của bạn.");
                return false;
            }
            VeNhaTro();
        }
        return super.onOptionsItemSelected(item);
    }


    public void OpenBottomSheet(String tenNhaTro, String diaChi) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);

        TextView tvTenNhaTro = (TextView) view.findViewById(R.id.tvTenNhaTro);
        LinearLayout lnChiDuong = (LinearLayout) view.findViewById(R.id.lnChiDuong);
        LinearLayout lnChiaSe = (LinearLayout) view.findViewById(R.id.lnChiaSe);
        LinearLayout lnLuu = (LinearLayout) view.findViewById(R.id.lnLuu);

        lnChiDuong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        lnChiaSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Chia sẻ", Toast.LENGTH_LONG).show();
            }
        });
        lnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Lưu", Toast.LENGTH_LONG).show();
            }
        });

        tvTenNhaTro.setText(tenNhaTro);
        final Dialog mBottomSheetDialog = new Dialog(MainActivity.this,
                R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
    }

    /*Hàm xác định vị trí hiện tại*/
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
            //  mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
            // mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viTriHienTai, 16f));
          /*  CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()))
                    .zoom(13)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16f));
        }
    }

    /*Hàm vẽ vị trí các nhà trọ*/
    public void VeNhaTro() {
        mMap.clear();
        if (count >= 3) {

            check = -1;
            DanhSachNhaTro();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Đang tìm nhà trọ quanh đây...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            for (int i = 0; i < routes.size(); i++) {
                String viTriHienTai = String.valueOf(loHienTai.getLatitude()) + "," + String.valueOf(loHienTai.getLongitude());
                String viTriNhaTro = String.valueOf(routes.get(i).endLocation.latitude) + "," + String.valueOf(routes.get(i).endLocation.longtitude);
                new ReadJsonGoogleMap().execute("https://maps.googleapis.com/maps/api/directions/json?origin=" + viTriHienTai + "&destination=" + viTriNhaTro + "&key=AIzaSyBQi4eaAMVe-u4BjL3ntmjAtoRnup-BdJk");
            }
            for (int i = 0; i < routes.size(); i++) {
                LatLng viTriNhaTro = new LatLng(routes.get(i).endLocation.latitude, routes.get(i).endLocation.longtitude);
                Bitmap.Config conf = Bitmap.Config.ARGB_8888;
                Bitmap bmp = Bitmap.createBitmap(300, 100, conf);
                Canvas canvas1 = new Canvas(bmp);

                Paint color = new Paint();
                color.setFakeBoldText(true);
                color.setTextSize(15);
                color.setColor(Color.GRAY);
                /*Typeface plain = Typeface.createFromAsset(assetManager, pathToFont);
                Typeface bold = Typeface.create(plain, Typeface.DEFAULT_BOLD)
                Paint paint = new Paint();
                paint.setTypeface(bold);
                canvas.drawText("Sample text in bold", 0, 0, paint);*/

                canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
                        R.drawable.home_maker2), 0, 0, color);
                canvas1.drawText(routes.get(i).name, 3, 63, color);

                mMap.addMarker(new MarkerOptions().position(viTriNhaTro)
                        .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        .title(routes.get(i).name)
                        .anchor(0.5f, 1));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viTriHienTai, 16f));
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(), "Tìm được " + routes.size() + " nhà trọ.", Toast.LENGTH_LONG).show();
            tvThongBao.setText("Chạm vào vị trí nhà trọ để xem chi tiết.");

        }
    }

    /*Kiểm tra tình trạng gps*/
    public boolean KiemTraTinhTrangGPS() {
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        String provider = locationManager.GPS_PROVIDER;
        if (!locationManager.isProviderEnabled(provider)) {
            //check if gps disable
            return false;
        } else {
            //check if gps enabled
            return true;
        }
    }

    /*Hàm thực hiện lấy dữ liệu từ server google map*/
    public void PhanTichDuLieu(String serverUrl) {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, serverUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PhanTichJson(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    /*Lấy json*/
    public void PhanTichJson(String response) {
        JSONObject jsonData = null;
        try {
            check++;
            jsonData = new JSONObject(response);
            JSONArray jsonRoutes = jsonData.getJSONArray("routes");
            for (int i = 0; i < jsonRoutes.length(); i++) {

                JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
                Distance distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
                Duration duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
                StartLocation startLocation = new StartLocation(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
                Route route = new Route(routes.get(check).name, distance, duration, overview_polylineJson.getString("points"), jsonLeg.getString("start_address"), jsonLeg.getString("end_address"), startLocation, routes.get(check).endLocation);
                routes.set(check, route);
            }
            if (check == 0) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "" + routes.get(0).distance.value, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        count = 0;
        super.onBackPressed();
    }

    /*Hàm khởi tạo danh sách nhà trọ fake*/
    public void DanhSachNhaTro() {
        routes.clear();
        routes.add(new Route("Nhà cô hai ", null, null, null, null, null, null, new EndLocation(10.8325733, 106.3512633)));
       // routes.add(new Route("Nhà bà bảy ", null, null, null, null, null, null, new EndLocation(10.8326316, 106.3513716)));
      //  routes.add(new Route("Ghế đá nhà Khang", null, null, null, null, null, null, new EndLocation(10.8325966, 106.3508533)));
        routes.add(new Route("Nhà nội", null, null, null, null, null, null, new EndLocation(10.8325009, 106.3493161)));
      //  routes.add(new Route("Nhà tấm Khang", null, null, null, null, null, null, new EndLocation(10.8325433, 106.35098)));
        //routes.add(new Route("Bàn máy tính nhà Khang", null, null, null, null, null, null, new EndLocation(10.8325216, 106.3508766)));
        routes.add(new Route("Chợ xã Bình Hòa Nam", null, null, null, null, null, null, new EndLocation(10.80954, 106.3669016)));
        routes.add(new Route("Nhà Cô Ba", null, null, null, null, null, null, new EndLocation(10.828845, 106.3513416)));
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

    /**/
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

                    JSONObject jsonRoute = jsonRoutes.getJSONObject(i);
                    JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                    JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                    JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                    JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                    JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                    JSONObject jsonEndLocation = jsonLeg.getJSONObject("end_location");
                    JSONObject jsonStartLocation = jsonLeg.getJSONObject("start_location");
                    Distance distance = new Distance(jsonDistance.getString("text"), jsonDistance.getInt("value"));
                    Duration duration = new Duration(jsonDuration.getString("text"), jsonDuration.getInt("value"));
                    StartLocation startLocation = new StartLocation(jsonStartLocation.getDouble("lat"), jsonStartLocation.getDouble("lng"));
                    Route route = new Route(routes.get(check).name, distance, duration, overview_polylineJson.getString("points"), jsonLeg.getString("start_address"), jsonLeg.getString("end_address"), startLocation, routes.get(check).endLocation);
                    routes.set(check, route);
                }
                if (check == routes.size() - 1) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + routes.get(0).distance.value, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


}
