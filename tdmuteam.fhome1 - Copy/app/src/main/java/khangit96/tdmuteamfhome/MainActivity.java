package khangit96.tdmuteamfhome;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

import java.util.ArrayList;

import khangit96.tdmuteamfhome.lib.BottomSheetBehaviorGoogleMapsLike;
import khangit96.tdmuteamfhome.lib.MergedAppBarLayoutBehavior;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    int[] mDrawables = {
            R.drawable.nhatro,
            R.drawable.nhatro2jpg,
            R.drawable.nhatro3
    };
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    TextView tvName, tvTime, tvDistance;
    FloatingActionButton fabLocation, fabSearch, fabDirection;
    View bottomSheet;
    AppBarLayout mergedAppBarLayout;
    MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
    ItemPagerAdapter adapter;
    Toolbar toolbarExpanded;
    LatLng currentLatLng;
    FloatingSearchView floatingSearchView;
    int count = 0;
    ViewPager viewPager;
    ArrayList<Location> locationArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    boolean checkDrag=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        floatingSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
        bottomSheet = (View) findViewById(R.id.bottom_sheet);
        fabDirection = (FloatingActionButton) findViewById(R.id.fabDirection);
        fabDirection.setVisibility(View.VISIBLE);
        fabSearch = (FloatingActionButton) findViewById(R.id.fabSearch);
        fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
        mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
        mergedAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
               // if(appBarLayout.){
                    Toast.makeText(getApplicationContext(),"ok",Toast.LENGTH_LONG).show();
               // }
            }
        });
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        floatingSearchView.setVisibility(View.VISIBLE);
                       // fabDirection.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        floatingSearchView.setVisibility(View.GONE);
                       // fabDirection.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_EXPANDED:

                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_ANCHOR_POINT:
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_HIDDEN:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //fabDirection.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(1).start();
                // fabDirection.animate().setDuration(2).start();
            }
        });
        mergedAppBarLayoutBehavior = MergedAppBarLayoutBehavior.from(mergedAppBarLayout);
        mergedAppBarLayoutBehavior.setToolbarTitle("Nhà trọ Ngọc Lan");
        toolbarExpanded = (Toolbar) findViewById(R.id.expanded_toolbar);
        setSupportActionBar(toolbarExpanded);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mergedAppBarLayoutBehavior.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
            }
        });
        adapter = new ItemPagerAdapter(this, mDrawables);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        tvName = (TextView) bottomSheet.findViewById(R.id.tvName);
        tvDistance = (TextView) bottomSheet.findViewById(R.id.tvDistance);
        tvTime = (TextView) bottomSheet.findViewById(R.id.tvTime);
        tvName.setText("Nhà Trọ Ngọc Lan");
        tvTime.setText("Chỉ đường");
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation(14f);
            }
        });
        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                hideBotoomSheet();

            }
        });
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fabDirection.getLayoutParams();
        params.setAnchorId(View.NO_ID);
        fabDirection.setLayoutParams(params);
        fabDirection.setVisibility(View.GONE);
        swipeRefreshLayout.setEnabled(false);
        swipeRefreshLayout.setProgressViewOffset(true, 0, 150);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }


    private void getCurrentLocation(float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
           /* case R.id.sliding_list_example:
                return true;
            case R.id.sliding_search_bar_example:
                return true;*/
            default:
                return true;
        }
    }


    public void hideBotoomSheet() {
        bottomSheet.setVisibility(View.GONE);
        fabDirection.setVisibility(View.GONE);
        mergedAppBarLayout.setVisibility(View.GONE);
        fabSearch.setVisibility(View.VISIBLE);
        fabLocation.setVisibility(View.VISIBLE);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fabDirection.getLayoutParams();
        params.setAnchorId(View.NO_ID);
        fabDirection.setLayoutParams(params);
        viewPager.setVisibility(View.GONE);
    }

    public void showBootomSheet() {
        bottomSheet.setVisibility(View.VISIBLE);
        mergedAppBarLayout.setVisibility(View.VISIBLE);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fabDirection.getLayoutParams();
        params.setAnchorId(R.id.bottom_sheet);
        fabDirection.setLayoutParams(params);
        fabDirection.setVisibility(View.VISIBLE);
        fabLocation.setVisibility(View.GONE);
        fabSearch.setVisibility(View.GONE);
        viewPager.setVisibility(View.VISIBLE);

    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Ứng dụng cần quyền truy câp vị trí của bạn!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            Toast.makeText(getApplicationContext(), "Bạn đã cho phép ứng dụng truy cập vịt trí.", Toast.LENGTH_LONG).show();
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    enableMyLocation();
                } else {
                    Toast.makeText(getApplicationContext(), "Bạn phải cấp quyền cho ứng dụng truy cập vị trí.", Toast.LENGTH_LONG).show();
                    System.exit(1);
                    return;
                }
                break;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tdmu = new LatLng(10.980568, 106.674618);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tdmu, 14f));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                enableMyLocation();

            } else {
                mMap.setMyLocationEnabled(true);
                Toast.makeText(getApplicationContext(), "Bạn đã cho phép ứng dụng truy cập vịt trí.", Toast.LENGTH_LONG).show();
            }

        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                hideBotoomSheet();
            }
        });
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                count++;
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                if (count == 3) {
                    getCurrentLocation(14f);
                }

            }
        });
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mMap.clear();
                swipeRefreshLayout.setRefreshing(true);
                Location centerLocation = setLocation(cameraPosition.target.latitude, cameraPosition.target.longitude);
                LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                Location bottomLeftLocation = setLocation(latLngBounds.southwest.latitude, latLngBounds.southwest.longitude);
                try {
                    initMarkers(centerLocation, bottomLeftLocation);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                showBootomSheet();
                return true;
            }
        });
    }

    private void initMarkers(Location centerLocation, Location bottomLeftLocation) throws InterruptedException {
        IconGenerator icon = new IconGenerator(getApplicationContext());
       // icon.setTextAppearance(R.style.iconGenText);
        icon.setStyle(IconGenerator.STYLE_BLUE);
        Bitmap bitmap = icon.makeIcon("300k");
        ClusterManager<Person> clusterManager = new ClusterManager<Person>(this, mMap);
        PersonRender personRender = new PersonRender(getApplicationContext(), mMap, clusterManager);

        ArrayList<Location> curLocationArrayList = new ArrayList<>();
        curLocationArrayList.add(setLocation(10.981716, 106.683973));
        curLocationArrayList.add(setLocation(10.982970, 106.682364));
        curLocationArrayList.add(setLocation(10.981748, 106.684928));
        curLocationArrayList.add(setLocation(10.981632, 106.683780));
        curLocationArrayList.add(setLocation(10.966088, 106.668197));
        int count = 0;
        for (int i = 0; i < curLocationArrayList.size(); i++) {

            double distaneFromCenterToTdmu = centerLocation.distanceTo(curLocationArrayList.get(i));
            double distanceFromCenterToBottomLeft = centerLocation.distanceTo(bottomLeftLocation);
            if (distaneFromCenterToTdmu < distanceFromCenterToBottomLeft) {
                locationArrayList.add(curLocationArrayList.get(i));
                MarkerOptions m1 = new MarkerOptions()
                        .position(new LatLng(curLocationArrayList.get(i).getLatitude(), curLocationArrayList.get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                Person person = new Person(m1);
                clusterManager.addItem(person);
            }


        }
        clusterManager.cluster();
        //  swipeRefreshLayout.setRefreshing(false);

    }

    private class PersonRender extends DefaultClusterRenderer<Person> {
         IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());

        public PersonRender(Context context, GoogleMap map, ClusterManager<Person> clusterManager) {
            super(context, map, clusterManager);
            clusterManager.setRenderer(this);
        }

        @Override
        protected void onBeforeClusterItemRendered(Person item, MarkerOptions markerOptions) {
            if (item.icon != null) {
                markerOptions.icon(item.icon); //Here you retrieve BitmapDescriptor from ClusterItem and set it as marker icon
            }
            markerOptions.visible(true);
        }


        @Override
        protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
            final Drawable clusterIcon = getResources().getDrawable(R.drawable.ic_oval_fix);
            clusterIcon.setColorFilter(getResources().getColor(android.R.color.holo_orange_light), PorterDuff.Mode.SRC_ATOP);
          //  mClusterIconGenerator.setStyle(R.style.iconGenText);
           mClusterIconGenerator.setTextAppearance(R.style.clusterStyle);
            mClusterIconGenerator.setBackground(clusterIcon);


/*
            if (cluster.getSize() < 10) {
                mClusterIconGenerator.setContentPadding(40, 20, 0, 0);
            }
            else {
                mClusterIconGenerator.setContentPadding(30, 20, 0, 0);
            }*/


            Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(cluster.getSize()));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }


        @Override
        protected boolean shouldRenderAsCluster(Cluster<Person> cluster) {
            return cluster.getSize() > 1;

        }
    }

    public Location setLocation(double latitude, double longttitude) {
        Location location = new Location("result");
        location.setLatitude(latitude);
        location.setLongitude(longttitude);
        return location;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
