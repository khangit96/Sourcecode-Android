package khangit96.tdmuteamfhome;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import khangit96.tdmuteamfhome.lib.BottomSheetBehaviorGoogleMapsLike;
import khangit96.tdmuteamfhome.lib.MergedAppBarLayoutBehavior;
import me.relex.circleindicator.CircleIndicator;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    FloatingActionButton fabLocation, fabAdd;
    View bottomSheet;
    LatLng currentLatLng;
    FloatingSearchView floatingSearchView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    boolean checkHideBottomSheet = true;
    ViewStub stub;
    View view;
    BottomSheetBehaviorGoogleMapsLike behavior;
    Marker previousMaker = null;
    GoogleApiClient client;
    LocationRequest mLocationRequest;
    PendingResult<LocationSettingsResult> result;
    static final Integer GPS_SETTINGS = 0x7;
    static final Integer COLOR_TEXTVIEW_BOTTOMSHEET = 0x7f0e0105;
    List<House> houseList = new ArrayList<>();
    HandlerInitMaker handlerInitMaker = new HandlerInitMaker();
    ClusterManager<HouseCluster> clusterManager;
    HomeRender homeRender;
    boolean checkFirstTimeLocationChange = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initMainView();

        client = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
                .addApi(LocationServices.API)
                .build();
        getDataFromFireBase();

    }

    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.disconnect();
    }

    /*
            *
            * */
    public void initMainView() {
        fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForGPS();
            }
        });
        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        initDrawerLayout();
        initSwipeRefreshLayout();
        initStubView();
        hideBottomSheet();
    }

    /*
    *
    * */
    private void initSwipeRefreshLayout() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setProgressViewOffset(true, 0, 150);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    /*
    * */
    public void initStubView() {
        stub = (ViewStub) findViewById(R.id.layout_stub);
        stub.setLayoutResource(R.layout.bottom_sheet);
        view = stub.inflate();
        bottomSheet = view.findViewById(R.id.bottom_sheet);
    }

    /*
    *
    * */
    public void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        floatingSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
    }

    /*
    *
     */
    public void showBottonSheet(final House house) {
        //Image
        int[] mDrawables = {
                R.drawable.nhatro,
                R.drawable.nhatro2jpg,
                R.drawable.nhatro3
        };

        //Viewpager
        ViewPager viewPager;
        final CircleIndicator indicator;
        ItemPagerAdapter adapter;
        adapter = new ItemPagerAdapter(this, mDrawables);
        viewPager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);
        viewPager.setAdapter(adapter);
        indicator.setViewPager(viewPager);

        //Appbar,toolbar,bottomsheet content
        final RelativeLayout relative_bottom = (RelativeLayout) findViewById(R.id.relative_bottom);
        AppBarLayout mergedAppBarLayout;
        MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
        mergedAppBarLayout = (AppBarLayout) view.findViewById(R.id.merged_appbarlayout);
        Toolbar toolbarExpanded;
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle(house.tenChuHo);
        toolbarExpanded = (Toolbar) view.findViewById(R.id.expanded_toolbar);
        setSupportActionBar(toolbarExpanded);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });

        final FloatingActionButton fabDirection = (FloatingActionButton) view.findViewById(R.id.fabDirection);
        final FloatingActionButton fabDirection1 = (FloatingActionButton) view.findViewById(R.id.fabDirection1);
        checkHideBottomSheet = false;
        fabAdd.setVisibility(View.GONE);
        stub.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) fabLocation.getLayoutParams();
        mlp.setMargins(0, 0, 0, 150);

        //Textview bottom sheet
        final TextView textview_name, textview_address, textview_housePrice, textview_electricPrice, textview_waterPrice, textview_addressDetail, textview_nameOfOwnHouse,
                textview_phone, textview_houseStatus, textview_sizeOfHouse, textview_timeOpen, textview_timeClose;
        textview_name = (TextView) bottomSheet.findViewById(R.id.textview_name);
        textview_address = (TextView) bottomSheet.findViewById(R.id.textview_address);

        textview_name.setTextColor(COLOR_TEXTVIEW_BOTTOMSHEET);
        textview_address.setTextColor(COLOR_TEXTVIEW_BOTTOMSHEET);

        textview_name.setText(house.tenChuHo);
        textview_address.setText(house.diaChi);

        //Textview bottom sheet detail information
        textview_housePrice = (TextView) bottomSheet.findViewById(R.id.textview_housePrice);
        textview_electricPrice = (TextView) bottomSheet.findViewById(R.id.textview_electricPrice);
        textview_waterPrice = (TextView) bottomSheet.findViewById(R.id.textview_waterPrice);
        textview_addressDetail = (TextView) bottomSheet.findViewById(R.id.textview_addressDetail);
        textview_sizeOfHouse = (TextView) bottomSheet.findViewById(R.id.textview_sizeOfHouse);
        textview_nameOfOwnHouse = (TextView) bottomSheet.findViewById(R.id.textview_nameOfOwnHouse);
        textview_timeOpen = (TextView) bottomSheet.findViewById(R.id.textview_timeOpen);
        textview_timeClose = (TextView) bottomSheet.findViewById(R.id.textview_timeClose);
        textview_phone = (TextView) bottomSheet.findViewById(R.id.textview_phone);
        textview_houseStatus = (TextView) bottomSheet.findViewById(R.id.textview_houseStatus);

        checkIsNullBeforeSetText(textview_housePrice, String.format(getString(R.string.housePrice), house.giaPhong));
        checkIsNullBeforeSetText(textview_electricPrice, String.format(getString(R.string.electricPrice), house.giaDien));
        checkIsNullBeforeSetText(textview_waterPrice, String.format(getString(R.string.waterPrice), house.giaNuoc));
        checkIsNullBeforeSetText(textview_addressDetail, String.format(getString(R.string.address), house.diaChi));
        checkIsNullBeforeSetText(textview_nameOfOwnHouse, String.format(getString(R.string.nameOfOwnHouse), house.tenChuHo));
        checkIsNullBeforeSetText(textview_phone, String.format(getString(R.string.phone), house.sdt));
        checkIsNullBeforeSetText(textview_houseStatus, String.format(getString(R.string.housetStatus), house.tinhTrang));

        //not update in database
        checkIsNullBeforeSetText(textview_sizeOfHouse, String.format(getString(R.string.sizeOfHouse), getString(R.string.updatingInformation)));
        checkIsNullBeforeSetText(textview_timeOpen, String.format(getString(R.string.timeOpen), getString(R.string.updatingInformation)));
        checkIsNullBeforeSetText(textview_timeClose, String.format(getString(R.string.timeClose), getString(R.string.updatingInformation)));

        //Listen state bottomsheet
        behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        floatingSearchView.setVisibility(View.VISIBLE);
                        relative_bottom.setBackgroundColor(getResources().getColor(android.R.color.white));
                        textview_name.setTextColor(COLOR_TEXTVIEW_BOTTOMSHEET);
                        textview_address.setTextColor(COLOR_TEXTVIEW_BOTTOMSHEET);
                        fabDirection.setVisibility(View.GONE);
                        fabDirection1.setVisibility(View.VISIBLE);
                        fabLocation.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        floatingSearchView.setVisibility(View.GONE);
                        fabDirection.setVisibility(View.VISIBLE);
                        relative_bottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        textview_name.setTextColor(getResources().getColor(android.R.color.white));
                        textview_address.setTextColor(getResources().getColor(android.R.color.white));
                        fabDirection.setVisibility(View.VISIBLE);
                        fabDirection1.setVisibility(View.GONE);
                        fabLocation.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:
                        indicator.setVisibility(View.GONE);
                        fabDirection.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:
                        indicator.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });


        //fabDirection click listener
        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDataFromGoogleMapServer(String.valueOf(house.viDo) + "," + house.kinhDo)) {
                    behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                    hideBottomSheet();
                }

            }
        });
        fabDirection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDataFromGoogleMapServer(String.valueOf(house.viDo) + "," + house.kinhDo)) {
                    behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                    hideBottomSheet();
                }
            }
        });
    }

    /*
    * */
    public void hideBottomSheet() {
        checkHideBottomSheet = true;
        fabLocation.setVisibility(View.VISIBLE);
        fabAdd.setVisibility(View.VISIBLE);
        stub.setVisibility(View.GONE);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) fabLocation.getLayoutParams();
        mlp.setMargins(0, 0, 18, 100);
    }

    /*
    * */
    private void getCurrentLocation(float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom));
    }

    /*
    *
    * */
    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
      /*  switch (menuItem.getItemId()) {
            case R.id.s:
                return true;
            default:
                return true;
        }*/
        return true;
    }

    /*
    * */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            showToast("Ứng dụng cần quyền truy câp vị trí của bạn!");
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            showToast("Bạn đã cho phép ứng dụng truy cập vịt trí.");
            mMap.setMyLocationEnabled(true);
        }
    }

    /*
    *
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                } else {
                    showToast("Bạn phải cấp quyền cho ứng dụng truy cập vị trí.");
                    System.exit(1);
                    return;
                }
                break;
        }
    }

    /*
    *
    *
    */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tdmu = new LatLng(10.980568, 106.674618);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tdmu, 15f));
        clusterManager = new ClusterManager<HouseCluster>(MainActivity.this, mMap);
        homeRender = new HomeRender(getApplicationContext(), mMap, clusterManager);
        mMap.setOnCameraChangeListener(clusterManager);
        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.getMarkerCollection().setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (previousMaker != null) {
                    previousMaker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_default));
                }
                previousMaker = marker;
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_selected));
                showBottonSheet(houseList.get(Integer.parseInt(marker.getTitle())));
                return true;
            }
        });
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<HouseCluster>() {
            @Override
            public boolean onClusterClick(Cluster<HouseCluster> cluster) {
                return true;
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();
            } else {
                mMap.setMyLocationEnabled(true);
                showToast("Bạn đã cho phép ứng dụng truy cập vịt trí.");
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }


        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                if (checkFirstTimeLocationChange) {
                    getCurrentLocation(14f);
                    checkFirstTimeLocationChange = false;
                }

            }
        });

    }


    /*
    * ask For GPS if GPS is disabled
    * */
    private void askForGPS() {
        if (!checkStateGPS()) {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setInterval(30 * 1000);
            mLocationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
            builder.setAlwaysShow(true);
            result = LocationServices.SettingsApi.checkLocationSettings(client, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            getCurrentLocation(14);
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                status.startResolutionForResult(MainActivity.this, GPS_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            break;
                    }
                }
            });
        } else {
            if (currentLatLng != null) {
                getCurrentLocation(14);
            }
        }

    }

    /*
     *
     * */
    private class HomeRender extends DefaultClusterRenderer<HouseCluster> {
        IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());

        public HomeRender(Context context, GoogleMap map, ClusterManager<HouseCluster> clusterManager) {
            super(context, map, clusterManager);
            clusterManager.setRenderer(this);
        }

        @Override
        protected void onBeforeClusterItemRendered(HouseCluster item, MarkerOptions markerOptions) {
            if (item.icon != null) {
                markerOptions.icon(item.icon);
                markerOptions.title(item.title);
            }
            markerOptions.visible(true);
        }

        /*
         *
         * */
        @Override
        protected void onBeforeClusterRendered(Cluster<HouseCluster> cluster, MarkerOptions markerOptions) {
            final Drawable clusterIcon = getResources().getDrawable(R.drawable.ic_oval_fix);
            clusterIcon.setColorFilter(getResources().getColor(android.R.color.holo_orange_light), PorterDuff.Mode.SRC_ATOP);
            mClusterIconGenerator.setTextAppearance(R.style.clusterTextStyle);
            mClusterIconGenerator.setBackground(clusterIcon);

             /*  if (cluster.getSize() < 10) {
                   mClusterIconGenerator.setContentPadding(40, 20, 0, 0);
               }
               else {
                   mClusterIconGenerator.setContentPadding(30, 20, 0, 0);
               }*/
            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }


        @Override
        protected boolean shouldRenderAsCluster(Cluster<HouseCluster> cluster) {
            return cluster.getSize() > 1;
        }

        @Override
        protected void onClusterItemRendered(HouseCluster clusterItem, Marker marker) {
            super.onClusterItemRendered(clusterItem, marker);
        }
    }


    /*
     *
     * */
    @Override
    public void onBackPressed() {
        if (checkHideBottomSheet == true) {
            super.onBackPressed();
            return;
        }
        if (behavior.getState() == BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED) {
            hideBottomSheet();
        } else {
            behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
        }
    }

    /*
        Get data House from server Firebase
    * */
    public void getDataFromFireBase() {
        String SERVER_FIREBASE = "https://test-8e5bd.firebaseio.com/NhaTro.json?pretty";
        if (isNetworkAvailable()) {
            swipeRefreshLayout.setRefreshing(true);
            final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER_FIREBASE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    swipeRefreshLayout.setRefreshing(false);
                    Gson gson = new GsonBuilder().create();
                    houseList = Arrays.asList(gson.fromJson(jsonArray.toString(), House[].class));
                    requestQueue.stop();
                    new ThreadInitMaker().start();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    swipeRefreshLayout.setRefreshing(false);
                    showSnackBar("Có lỗi xảy ra.");
                    requestQueue.stop();
                }
            });
            requestQueue.add(jsonArrayRequest);
        } else {
            showSnackBar("Không có kết nối mạng.");
        }


    }

    /*
    * Parse JSon get direction from google map server
    * */
    public boolean getDataFromGoogleMapServer(String destinationLocation) {
        if (currentLatLng == null || !isNetworkAvailable()) {
            showToast("Vui lòng kiểm tra lại kết nối! ");
            return false;
        }

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang lấy thông tin đường đi...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String currentLocation = String.valueOf(currentLatLng.latitude) + "," + String.valueOf(currentLatLng.longitude);
        String SERVER_GOOGLEMAP = "https://maps.googleapis.com/maps/api/directions/json?origin=" + currentLocation + "&destination=" + destinationLocation + "&key=AIzaSyD5Xzis5_DOGW2XLYvOQ7FCVvFzLsym9aA";
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, SERVER_GOOGLEMAP, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonRoutes = jsonObject.getJSONArray("routes");
                            JSONObject jsonRoute = jsonRoutes.getJSONObject(0);
                            JSONObject overview_polylineJson = jsonRoute.getJSONObject("overview_polyline");
                            JSONArray jsonLegs = jsonRoute.getJSONArray("legs");
                            JSONObject jsonLeg = jsonLegs.getJSONObject(0);
                            JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                            //      JSONObject jsonDuration = jsonLeg.getJSONObject("duration");
                            showToast(jsonDistance.getString("value"));
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        showToast("Vui lòng kiểm tra lại kết nối!");
                    }
                });
        requestQueue.add(jsObjRequest);
        return true;
    }


    /*
        Thread procees init maker not influent main ui
    * */
    class ThreadInitMaker extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < houseList.size(); i++) {
                Message message = new Message();
                Bundle bd = new Bundle();
                bd.putInt("key", i);
                message.setData(bd);
                handlerInitMaker.sendMessage(message);
            }
        }
    }

    /*
    * */
    class HandlerInitMaker extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Bundle bd = msg.getData();
            int key = bd.getInt("key", 0);
            MarkerOptions markerOptions = new MarkerOptions()
                    .title(String.valueOf(key))
                    .position(new LatLng(houseList.get(key).viDo, houseList.get(key).kinhDo))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_default));
            HouseCluster houseCluster = new HouseCluster(markerOptions);
            clusterManager.addItem(houseCluster);
            clusterManager.cluster();


        }
    }

    /*
    * */
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
     Show snackBar when error occur,action="firebase"->get data firebase error
     action="google map"->get data google map error
    * */
    public void showSnackBar(String errorTitle) {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        Snackbar snackbar = Snackbar
                .make(drawerLayout, errorTitle, Snackbar.LENGTH_LONG)
                .setAction("THỬ LẠI", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getDataFromFireBase();
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.setDuration(Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
    }

    /*
    *
    * */
    public void checkIsNullBeforeSetText(TextView textView, String text) {
        if (text != "") {
            textView.setText(text);
        } else {
            textView.setText(text + " " + getString(R.string.updatingInformation));
        }
    }

    /*

    * */
    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /*
    *
    * */
    public boolean checkStateGPS() {
        final LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String provider = LocationManager.GPS_PROVIDER;
        return locationManager.isProviderEnabled(provider);
    }
}