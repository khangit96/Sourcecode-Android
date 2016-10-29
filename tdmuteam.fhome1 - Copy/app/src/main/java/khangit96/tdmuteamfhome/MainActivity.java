package khangit96.tdmuteamfhome;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
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
import android.view.ViewStub;
import android.widget.RelativeLayout;
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
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    FloatingActionButton fabLocation, fabAdd;
    View bottomSheet;
    LatLng currentLatLng;
    FloatingSearchView floatingSearchView;
    int count = 0;
    ArrayList<Location> locationArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    boolean checkHideBottomSheet = true;
    ViewStub stub;
    View view;
    BottomSheetBehaviorGoogleMapsLike behavior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initMainView();
    }

    /*
    *
    * */
    public void initMainView() {
        fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation(14f);
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
    public void showBottonSheet() {
        //Drawable
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
        mergedAppBarLayoutBehavior.setToolbarTitle("Nhà trọ Ngọc Lan");
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
        fabLocation.setVisibility(View.GONE);
        fabAdd.setVisibility(View.GONE);
        stub.setVisibility(View.VISIBLE);

        //SetText textview
        final TextView tvName, tvTime, tvAddress;
        tvName = (TextView) bottomSheet.findViewById(R.id.tvName);
        tvAddress = (TextView) bottomSheet.findViewById(R.id.tvAddress);
        tvTime = (TextView) bottomSheet.findViewById(R.id.tvTime);
        tvName.setText("Nhà Trọ Ngọc Lan");
        //tvTime.setText("Chỉ đường");

        //Listen state bottomsheet
        behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        floatingSearchView.setVisibility(View.VISIBLE);
                        relative_bottom.setBackgroundColor(getResources().getColor(android.R.color.white));
                        tvName.setTextColor(getResources().getColor(R.color.colorPrimary));
                        tvAddress.setTextColor(getResources().getColor(R.color.colorPrimary));
                        fabDirection.setVisibility(View.GONE);
                        fabDirection1.setVisibility(View.VISIBLE);

                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        floatingSearchView.setVisibility(View.GONE);
                        fabDirection.setVisibility(View.VISIBLE);
                        relative_bottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        tvName.setTextColor(getResources().getColor(android.R.color.white));
                        tvAddress.setTextColor(getResources().getColor(android.R.color.white));
                        fabDirection.setVisibility(View.VISIBLE);
                        fabDirection1.setVisibility(View.GONE);
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
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                hideBottomSheet();
            }
        });
        fabDirection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                hideBottomSheet();
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
    }

    /*
    * */
    private void getCurrentLocation(float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.menu_favorite:
                return true;
            default:
                return true;
        }
    }

    /*
    * */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "Ứng dụng cần quyền truy câp vị trí của bạn!", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, LOCATION_PERMISSION_REQUEST_CODE);

        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            Toast.makeText(getApplicationContext(), "Bạn đã cho phép ứng dụng truy cập vịt trí.", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Bạn phải cấp quyền cho ứng dụng truy cập vị trí.", Toast.LENGTH_LONG).show();
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

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                hideBottomSheet();
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
                hideBottomSheet();
                //   swipeRefreshLayout.setRefreshing(true);
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
                if (marker.getTitle() == null) {
                    return true;
                }
               /* IconGenerator icon = new IconGenerator(getApplicationContext());
                icon.setStyle(IconGenerator.STYLE_DEFAULT);
                Bitmap bitmap = icon.makeIcon("300k");
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap));*/
                showBottonSheet();
                return true;
            }
        });
    }

    /*
     *
     * */
    private void initMarkers(Location centerLocation, Location bottomLeftLocation) throws InterruptedException {
        IconGenerator icon = new IconGenerator(getApplicationContext());
        final Drawable clusterIcon = getResources().getDrawable(R.drawable.ic_oval_fix);
        clusterIcon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        icon.setTextAppearance(R.style.iconTextStyle);
        icon.setBackground(clusterIcon);
        Bitmap bitmap = icon.makeIcon("300k");
        ClusterManager<Person> clusterManager = new ClusterManager<Person>(this, mMap);
        PersonRender personRender = new PersonRender(getApplicationContext(), mMap, clusterManager);

        ArrayList<Location> curLocationArrayList = new ArrayList<>();
        curLocationArrayList.add(setLocation(10.981716, 106.683973));
        curLocationArrayList.add(setLocation(10.982970, 106.682364));
        curLocationArrayList.add(setLocation(10.981748, 106.684928));
        curLocationArrayList.add(setLocation(10.981632, 106.683780));
        curLocationArrayList.add(setLocation(10.966088, 106.668197));
        curLocationArrayList.add(setLocation(10.980717, 106.674394));
        curLocationArrayList.add(setLocation(10.975790, 106.677854));
        int count = 0;
        for (int i = 0; i < curLocationArrayList.size(); i++) {

            double distaneFromCenterToTdmu = centerLocation.distanceTo(curLocationArrayList.get(i));
            double distanceFromCenterToBottomLeft = centerLocation.distanceTo(bottomLeftLocation);
            if (distaneFromCenterToTdmu < distanceFromCenterToBottomLeft) {
                locationArrayList.add(curLocationArrayList.get(i));
                MarkerOptions m1 = new MarkerOptions()
                        .title("300k")
                        .position(new LatLng(curLocationArrayList.get(i).getLatitude(), curLocationArrayList.get(i).getLongitude()))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_home2));
                Person person = new Person(m1);
                clusterManager.addItem(person);
            }
        }
        clusterManager.cluster();
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<Person>() {
            @Override
            public boolean onClusterClick(Cluster<Person> cluster) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
                return true;
            }
        });
        clusterManager.setOnClusterInfoWindowClickListener(new ClusterManager.OnClusterInfoWindowClickListener<Person>() {
            @Override
            public void onClusterInfoWindowClick(Cluster<Person> cluster) {
                Toast.makeText(getApplicationContext(), "clicked", Toast.LENGTH_LONG).show();
            }
        });
        //  swipeRefreshLayout.setRefreshing(false);
    }

    /*
     *
     * */
    private class PersonRender extends DefaultClusterRenderer<Person> {
        IconGenerator mClusterIconGenerator = new IconGenerator(getApplicationContext());

        public PersonRender(Context context, GoogleMap map, ClusterManager<Person> clusterManager) {
            super(context, map, clusterManager);
            clusterManager.setRenderer(this);
        }

        @Override
        protected void onBeforeClusterItemRendered(Person item, MarkerOptions markerOptions) {
            if (item.icon != null) {
                markerOptions.icon(item.icon);//Here you retrieve BitmapDescriptor from ClusterItem and set it as marker icon
                markerOptions.title(item.title);
            }
            markerOptions.visible(true);
        }

        /*
         *
         * */
        @Override
        protected void onBeforeClusterRendered(Cluster<Person> cluster, MarkerOptions markerOptions) {
            final Drawable clusterIcon = getResources().getDrawable(R.drawable.ic_oval_fix);
            clusterIcon.setColorFilter(getResources().getColor(android.R.color.holo_orange_light), PorterDuff.Mode.SRC_ATOP);
            mClusterIconGenerator.setTextAppearance(R.style.clusterTextStyle);
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

    /*
     *
     *
     */
    public Location setLocation(double latitude, double longttitude) {
        Location location = new Location("result");
        location.setLatitude(latitude);
        location.setLongitude(longttitude);
        return location;
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
}