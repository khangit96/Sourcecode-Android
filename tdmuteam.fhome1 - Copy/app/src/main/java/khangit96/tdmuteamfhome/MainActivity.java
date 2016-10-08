package khangit96.tdmuteamfhome;

import android.content.pm.PackageManager;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import khangit96.tdmuteamfhome.lib.BottomSheetBehaviorGoogleMapsLike;
import khangit96.tdmuteamfhome.lib.MergedAppBarLayoutBehavior;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    int[] mDrawables = {
            R.drawable.nhatro,
            R.drawable.nhatro2jpg,
            R.drawable.nhatro3
    };
    private final String TAG = "MainActivity";
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    TextView tvName, tvTime, tvDistance;
    ViewPager viewPager;
    FloatingActionButton fabLocation, fabSearch, fabDirection;
    View bottomSheet;
    AppBarLayout mergedAppBarLayout;
    MergedAppBarLayoutBehavior mergedAppBarLayoutBehavior;
    ItemPagerAdapter adapter;
    Toolbar toolbarExpanded;
    LatLng currentLatLng;
    FloatingSearchView floatingSearchView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        floatingSearchView = (FloatingSearchView) findViewById(R.id.floating_search_view);
        bottomSheet = (View) findViewById(R.id.bottom_sheet);
        floatingSearchView.setOnLeftMenuClickListener(new FloatingSearchView.OnLeftMenuClickListener() {
            @Override
            public void onMenuOpened() {
                //floatingSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
                Toast.makeText(getApplicationContext(), "open", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onMenuClosed() {
                //     floatingSearchView.detachNavigationDrawerFromMenuButton(mDrawerLayout);
                Toast.makeText(getApplicationContext(), "closed", Toast.LENGTH_LONG).show();

            }
        });
        floatingSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {

            }
        });
        final BottomSheetBehaviorGoogleMapsLike behavior = BottomSheetBehaviorGoogleMapsLike.from(bottomSheet);
        behavior.addBottomSheetCallback(new BottomSheetBehaviorGoogleMapsLike.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
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

        tvName = (TextView) bottomSheet.findViewById(R.id.tvName);
        tvDistance = (TextView) bottomSheet.findViewById(R.id.tvDistance);
        tvTime = (TextView) bottomSheet.findViewById(R.id.tvTime);
        tvName.setText("Nhà Trọ Ngọc Lan");
        //tvDistance.setText("Bình luận(50)");
        tvTime.setText("Chỉ đường");
        adapter = new ItemPagerAdapter(this, mDrawables);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCurrentLocation(14f);
            }
        });
        fabDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   hideBotoomSheet();
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
    }

    public void showBootomSheet() {
        bottomSheet.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
        mergedAppBarLayout.setVisibility(View.VISIBLE);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) fabDirection.getLayoutParams();
        params.setAnchorId(R.id.bottom_sheet);
        fabDirection.setLayoutParams(params);
        fabDirection.setVisibility(View.VISIBLE);
        fabLocation.setVisibility(View.GONE);
        fabSearch.setVisibility(View.GONE);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng tdmu = new LatLng(10.980568, 106.674618);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tdmu, 14f));
        mMap.setMapType(googleMap.MAP_TYPE_NORMAL);
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
                //  if (count == 3)
                //  getCurrentLocation(14f);

            }
        });
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                Toast.makeText(getApplicationContext(),"lat: "+cameraPosition.target.latitude+"\n"+"long: "+cameraPosition.target.longitude,Toast.LENGTH_LONG).show();
            }
        });
    }
}
