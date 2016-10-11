package khangit96.tdmuteamfhome;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
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
        bottomSheet = (View) findViewById(R.id.bottom_sheet);
        floatingSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                floatingSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
            }

            @Override
            public void onMenuClosed() {
                floatingSearchView.detachNavigationDrawerFromMenuButton(mDrawerLayout);


            }
        });
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        floatingSearchView.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        floatingSearchView.setVisibility(View.GONE);
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
                fabDirection.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(1).start();
            }
        });
        fabDirection = (FloatingActionButton) findViewById(R.id.fabDirection);
        fabSearch = (FloatingActionButton) findViewById(R.id.fabSearch);
        fabLocation = (FloatingActionButton) findViewById(R.id.fabLocation);
        mergedAppBarLayout = (AppBarLayout) findViewById(R.id.merged_appbarlayout);
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

        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation(14f);
            }
        });
        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                hideBotoomSheet();

            }
        });
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fabDirection.getLayoutParams();
        params.setAnchorId(View.NO_ID);
        fabDirection.setLayoutParams(params);
        fabDirection.setVisibility(View.GONE);
    }


    private void getCurrentLocation(float zoom) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, zoom));
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (menuItem.getItemId()) {
            case R.id.sliding_list_example:
                // showFragment(new SlidingSearchResultsExampleFragment());
                return true;
            case R.id.sliding_search_bar_example:
                // showFragment(new SlidingSearchViewExampleFragment());
                return true;
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


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tdmu = new LatLng(10.980568, 106.674618);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tdmu, 14f));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                showBootomSheet();
            }
        });
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

                    initMarkers();
                    getCurrentLocation(14f);
                }

            }
        });
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                mMap.clear();
               /* IconGenerator tc = new IconGenerator(MainActivity.this);
                tc.setStyle(IconGenerator.STYLE_BLUE);
                Bitmap bmp = tc.makeIcon("350k");

                Location centerLocation = setLocation(cameraPosition.target.latitude, cameraPosition.target.longitude);
                Location tdmuLocation = setLocation(10.980604, 106.675832);
                Location bdLocation = setLocation(10.990218, 106.664481);
                Location bxLocation = setLocation(10.966098, 106.669034);
                Location laLocation = setLocation(10.539056, 106.409336);
                LatLngBounds latLngBounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                Location bottomLeftLocation = setLocation(latLngBounds.southwest.latitude, latLngBounds.southwest.longitude);

                ArrayList<Location> locationArrayList = new ArrayList<Location>();
                locationArrayList.add(tdmuLocation);
                locationArrayList.add(bdLocation);
                locationArrayList.add(bxLocation);
                locationArrayList.add(laLocation);
                int count = 0;
                for (int i = 0; i < locationArrayList.size(); i++) {
                    double distaneFromCenterToTdmu = centerLocation.distanceTo(locationArrayList.get(i));
                    double distanceFromCenterToBottomLeft = centerLocation.distanceTo(bottomLeftLocation);
                    if (distaneFromCenterToTdmu < distanceFromCenterToBottomLeft) {
                        count++;
                        mMap.addMarker(new MarkerOptions()
                                .position(new LatLng(locationArrayList.get(i).getLatitude(), locationArrayList.get(i).getLongitude()))
                                .icon(BitmapDescriptorFactory.fromBitmap(bmp))
                        );
                    }
                }*/


            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "clicked marker", Toast.LENGTH_LONG).show();
                return true;
            }
        });
    }

    private void initMarkers() {
        IconGenerator icon = new IconGenerator(getApplicationContext());
        icon.setStyle(IconGenerator.STYLE_BLUE);
        Bitmap bitmap = icon.makeIcon("1Triệu");

//        ClusterManager clusterManager = new ClusterManager<>(getApplicationContext(), mMap);
        ClusterManager<Person> clusterManager = new ClusterManager<Person>( this, mMap );

        mMap.setOnCameraChangeListener(clusterManager);
        PersonRender personRender = new PersonRender(getApplicationContext(), mMap, clusterManager);

        ArrayList<Location> locationArrayList = new ArrayList<>();
        locationArrayList.add(setLocation(10.981716, 106.683973));
        locationArrayList.add(setLocation(10.982970, 106.682364));
        locationArrayList.add(setLocation(10.981748, 106.684928));
        locationArrayList.add(setLocation(10.981632, 106.683780));

        for (int i = 0; i < locationArrayList.size(); i++) {
            MarkerOptions m1 = new MarkerOptions()
                    .position(new LatLng(locationArrayList.get(i).getLatitude(), locationArrayList.get(i).getLongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

            Person person = new Person(m1);
            clusterManager.addItem(person);
        }
        clusterManager.cluster();

    }

    private class PersonRender extends DefaultClusterRenderer<Person> {

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

}
