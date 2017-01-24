package khangit96.tdmuteamfhome.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
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

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.adapter.CustomSpinerAdapter;
import khangit96.tdmuteamfhome.databinding.ActivityMainBinding;
import khangit96.tdmuteamfhome.lib.BottomSheetBehaviorGoogleMapsLike;
import khangit96.tdmuteamfhome.lib.MergedAppBarLayoutBehavior;
import khangit96.tdmuteamfhome.model.House;
import khangit96.tdmuteamfhome.model.HouseCluster;
import khangit96.tdmuteamfhome.service.NotificationService;
import me.relex.circleindicator.CircleIndicator;

import static khangit96.tdmuteamfhome.R.string.phone;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private GoogleMap mMap;
    private DrawerLayout mDrawerLayout;
    View bottomSheet;
    LatLng currentLatLng;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final int CALLPHONE_PERMISSION_REQUEST_CODE = 2;
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
    public static List<House> houseList = new ArrayList<>();
    HandlerInitMaker handlerInitMaker = new HandlerInitMaker();
    ClusterManager<HouseCluster> clusterManager;
    HomeRender homeRender;
    boolean checkFirstTimeLocationChange = true;
    List<Marker> originMarkers = new ArrayList<>();
    List<Marker> destinationMarkers = new ArrayList<>();
    List<Polyline> polylinePaths = new ArrayList<>();
    boolean checkIfGetDataHouseFireBaseSuccess = false;
    String phoneNumber = null;
    ActivityMainBinding binding;
    static Integer HOUSE_AREA_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        initMainView();

        client = new GoogleApiClient.Builder(this)
                .addApi(AppIndex.API)
                .addApi(LocationServices.API)
                .build();
        getDataFromFireBase();

        startService();

        addEvents();

        addControls();
    }

    /*
    *
    * */
    private void addControls() {
        binding.floatingSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {
                if (item.getItemId() == R.id.filter) {
                    showLoginDialogFilter();
                }
            }
        });
    }

    /*
    *
    * */
    private void showLoginDialogFilter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        final View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_filter, null, false);

        Spinner spnHousePrice = (Spinner) view.findViewById(R.id.spnHousePrice);
        ArrayList<String> housePriceList = new ArrayList<>();
        housePriceList.add("Tất cả");
        housePriceList.add("1500.000");
        housePriceList.add("300.000");
        housePriceList.add("950.000");
        housePriceList.add("750.000");
        CustomSpinerAdapter housePriceAdapter = new CustomSpinerAdapter(getApplicationContext(), R.layout.list_house_price, housePriceList);
        spnHousePrice.setAdapter(housePriceAdapter);

        Spinner spnPlace = (Spinner) view.findViewById(R.id.spnHousePlace);
        ArrayList<String> housePlace = new ArrayList<>();
        housePlace.add("Tất cả");
        housePlace.add("Phú Lợi");
        housePlace.add("Phú Hoà");
        housePlace.add("Chánh Nghĩa");
        housePlace.add("Thuận An");
        CustomSpinerAdapter housePlaceAdapter = new CustomSpinerAdapter(getApplicationContext(), R.layout.list_house_price, housePlace);
        spnPlace.setAdapter(housePlaceAdapter);

        Spinner spnRadius = (Spinner) view.findViewById(R.id.spnHouseRadius);
        ArrayList<String> houseRadius = new ArrayList<>();
        houseRadius.add("5KM");
        houseRadius.add("10KM");
        houseRadius.add("20KM");
        CustomSpinerAdapter houseRadiusAdapter = new CustomSpinerAdapter(getApplicationContext(), R.layout.list_house_price, houseRadius);
        spnRadius.setAdapter(houseRadiusAdapter);

        builder.setView(view);
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("Huỷ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.show();
    }

    /*
    *
    * */
    private void addEvents() {
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddHouseActivity.class));
            }
        });
    }

    /*start service to listener event data change and noti to user*/
    public void startService() {
        startService(new Intent(MainActivity.this, NotificationService.class));
    }

    /*
    *
    * */
    @Override
    protected void onStart() {
        super.onStart();
        client.connect();
        if (houseList != null) {
            new ThreadInitMaker().start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        client.disconnect();
        clusterManager.clearItems();
    }

    /*
        *
          * */
    public void initMainView() {
        binding.fabLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                askForGPS();
            }
        });
        initDrawerLayout();
        initSwipeRefreshLayout();
        initStubView();
        hideBottomSheet();
    }

    /*
    *
    * */
    private void initSwipeRefreshLayout() {
        binding.swipeRefreshLayout.setEnabled(false);
        binding.swipeRefreshLayout.setProgressViewOffset(true, 0, 150);
        binding.swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
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

        TextView tvAbout, tvFeedback, tvShare, tvHouseArea;
        tvAbout = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menuAbout));
        tvFeedback = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menuFeedback));
        tvShare = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menuShare));
        tvHouseArea = (TextView) MenuItemCompat.getActionView(navigationView.getMenu().findItem(R.id.menuHouseArea));

        customMenuItemDrawer(tvAbout, "Giới thiệu", 145);
        customMenuItemDrawer(tvFeedback, "Phản hồi", 150);
        customMenuItemDrawer(tvShare, "Chia sẻ", 160);
        customMenuItemDrawer(tvHouseArea, "Tìm quanh đây", 90);

        binding.floatingSearchView.attachNavigationDrawerToMenuButton(mDrawerLayout);
        binding.floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                binding.floatingSearchView.clearSuggestions();
                List<SearchSuggestion> listSuggestion = new ArrayList<SearchSuggestion>();

                if (!newQuery.equals("")) {
                    for (int i = 0; i < houseList.size(); i++) {
                        final House h = houseList.get(i);
                        final int pos = i;

                        if (h.tenChuHo.contains(oldQuery + newQuery)) {
                            SearchSuggestion searchSuggestion = new SearchSuggestion() {
                                @Override
                                public String getBody() {
                                    return h.tenChuHo;
                                }

                                @Override
                                public int describeContents() {
                                    return pos;
                                }

                                @Override
                                public void writeToParcel(Parcel parcel, int i) {

                                }
                            };
                            listSuggestion.add(searchSuggestion);
                        }
                    }

                    binding.floatingSearchView.swapSuggestions(listSuggestion);
                }

            }
        });

        binding.floatingSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                int pos = searchSuggestion.describeContents();
                LatLng lng = new LatLng(houseList.get(pos).viDo, houseList.get(pos).kinhDo);

                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lng, 15f));
                showBottonSheet(pos);
            }

            @Override
            public void onSearchAction(String currentQuery) {

            }
        });

    }

    public void customMenuItemDrawer(TextView tv, String text, int paddingRight) {
        tv.setText(text);
        tv.setTextColor(getResources().getColor(R.color.genre));
        tv.setGravity(Gravity.CENTER_HORIZONTAL);
        tv.setTextSize(17);
        tv.setPadding(0, 20, paddingRight, 0);
    }


    /*
    *
     */
    public void showBottonSheet(final int id) {
        final House house = houseList.get(id);
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
        binding.fabAdd.setVisibility(View.GONE);
        stub.setVisibility(View.VISIBLE);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) binding.fabLocation.getLayoutParams();
        mlp.setMargins(0, 0, 0, 150);
        //Textview bottom sheet
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
        checkIsNullBeforeSetText(textview_phone, String.format(getString(phone), house.sdt));
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
                        binding.floatingSearchView.setVisibility(View.VISIBLE);
                        relative_bottom.setBackgroundColor(getResources().getColor(android.R.color.white));
                        textview_name.setTextColor(COLOR_TEXTVIEW_BOTTOMSHEET);
                        textview_address.setTextColor(COLOR_TEXTVIEW_BOTTOMSHEET);
                        fabDirection.setVisibility(View.GONE);
                        fabDirection1.setVisibility(View.VISIBLE);
                        binding.fabLocation.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehaviorGoogleMapsLike.STATE_DRAGGING:
                        binding.floatingSearchView.setVisibility(View.GONE);
                        fabDirection.setVisibility(View.VISIBLE);
                        relative_bottom.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                        textview_name.setTextColor(getResources().getColor(android.R.color.white));
                        textview_address.setTextColor(getResources().getColor(android.R.color.white));
                        fabDirection.setVisibility(View.VISIBLE);
                        fabDirection1.setVisibility(View.GONE);
                        binding.fabLocation.setVisibility(View.GONE);
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
                if (getDataFromGoogleMapServer(house)) {
                    behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                    hideBottomSheet();
                }

            }
        });
        fabDirection1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getDataFromGoogleMapServer(house)) {
                    behavior.setState(BottomSheetBehaviorGoogleMapsLike.STATE_COLLAPSED);
                    hideBottomSheet();
                }
            }
        });

        //button contact click
        ImageButton button_contact = (ImageButton) findViewById(R.id.button_contact);
        button_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = "tel:" + house.sdt;
                callPhone();
            }
        });

        //button share click
        ImageButton button_share = (ImageButton) findViewById(R.id.button_share);
        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareHouse(house);
            }
        });

        //button review click

        ImageButton button_review = (ImageButton) findViewById(R.id.button_review);

        button_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(MainActivity.this, ReviewActivity.class);
                reviewIntent.putExtra("NHATRO_POS", id);
                startActivity(new Intent(reviewIntent));
            }
        });
    }

    /*
    * share content house:name,direction,water price,house price.
    * */

    public void shareHouse(House house) {

        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        String direction = "http://maps.google.com/maps?saddr=Current+Location&daddr="
                + String.valueOf(house.viDo) + "," + String.valueOf(house.kinhDo);
        intent.putExtra(Intent.EXTRA_SUBJECT, house.tenChuHo);
        intent.putExtra(Intent.EXTRA_TEXT, "Nhà trọ: " + house.tenChuHo + "\n"
                + "Chỉ đường: " + direction + "\n");
        startActivity(Intent.createChooser(intent, "Chia sẻ"));
    }

    /**
     * event call phone
     */
    public void callPhone() {
        if (phoneNumber == null) {
            showToast("Hiện tại chưa có thông tin liên hệ!");
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                        Manifest.permission.CALL_PHONE
                }, CALLPHONE_PERMISSION_REQUEST_CODE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(phoneNumber));
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(phoneNumber));
            startActivity(intent);
        }

    }

    /*
    * */
    public void hideBottomSheet() {
        checkHideBottomSheet = true;
        binding.fabLocation.setVisibility(View.VISIBLE);
        binding.fabAdd.setVisibility(View.VISIBLE);
        stub.setVisibility(View.GONE);
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) binding.fabLocation.getLayoutParams();
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
        switch (menuItem.getItemId()) {
            case R.id.menuHouseArea:
                Intent houseAreaIntent = new Intent(MainActivity.this, HouseAreaActivity.class);
                startActivityForResult(houseAreaIntent, HOUSE_AREA_REQUEST_CODE);
                break;
            case R.id.menuAbout:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
            case R.id.menuFeedback:
                final Intent _Intent = new Intent(android.content.Intent.ACTION_SEND);
                _Intent.setType("text/html");
                _Intent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{getString(R.string.about_email)});
                _Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "[F Home] Phản hồi");
                _Intent.putExtra(android.content.Intent.EXTRA_TEXT, "Xin chào,\n");
                startActivity(Intent.createChooser(_Intent, getString(R.string.app_name)));
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == HOUSE_AREA_REQUEST_CODE)
                showBottonSheet(data.getIntExtra("HOUSE_SELECTED", -1));
        }
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
            case CALLPHONE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callPhone();
                } else {
                    showToast("Bạn phải cấp quyền gọi điện cho ứng dụng!");
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
                previousMaker = marker;
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_selected));

                showBottonSheet(Integer.parseInt(marker.getTitle()));
                return true;
            }
        });
        clusterManager.setOnClusterClickListener(new ClusterManager.OnClusterClickListener<HouseCluster>() {
            @Override
            public boolean onClusterClick(Cluster<HouseCluster> cluster) {
                return true;
            }
        });

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                clusterManager.onCameraChange(cameraPosition);
                Location centerLocation = new Location("centerLocation");
                centerLocation.setLatitude(cameraPosition.target.latitude);
                centerLocation.setLongitude(cameraPosition.target.longitude);

                LatLngBounds curScreen = mMap.getProjection().getVisibleRegion().latLngBounds;
                Location leftBottomLocation = new Location("leftBottomLocation");
                leftBottomLocation.setLatitude(curScreen.southwest.latitude);
                leftBottomLocation.setLongitude(curScreen.southwest.longitude);

                if (checkIfGetDataHouseFireBaseSuccess)
                    checkIfAreaHasHouse(centerLocation, leftBottomLocation);
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
                    getCurrentLocation(15f);
                    checkFirstTimeLocationChange = false;
                }

            }
        });

    }


    /*
    *
    * */
    public void checkIfAreaHasHouse(Location centerLocation, Location leftBottomLocation) {
        binding.swipeRefreshLayout.setRefreshing(true);
        double distanceFromCenterToLeftTop = centerLocation.distanceTo(leftBottomLocation);

        for (int i = 0; i < houseList.size(); i++) {
            Location houseLocation = new Location("houseLocation");
            houseLocation.setLatitude(houseList.get(i).viDo);
            houseLocation.setLongitude(houseList.get(i).kinhDo);

            double distanceFromCenterToHouse = centerLocation.distanceTo(houseLocation);
            if (distanceFromCenterToHouse < distanceFromCenterToLeftTop) {
                binding.swipeRefreshLayout.setRefreshing(false);
                return;
            }
        }
        binding.swipeRefreshLayout.setRefreshing(false);
        showToast("Khu vực này hiện tại chưa có nhà trọ!");


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
                            getCurrentLocation(15f);
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
                getCurrentLocation(15f);
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
            binding.swipeRefreshLayout.setRefreshing(true);
            final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(SERVER_FIREBASE, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray jsonArray) {
                    binding.swipeRefreshLayout.setRefreshing(false);
                    Gson gson = new GsonBuilder().create();
                    houseList = Arrays.asList(gson.fromJson(jsonArray.toString(), House[].class));
                    requestQueue.stop();
                    checkIfGetDataHouseFireBaseSuccess = true;
                    new ThreadInitMaker().start();

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    binding.swipeRefreshLayout.setRefreshing(false);
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
    public boolean getDataFromGoogleMapServer(House house) {
        if (currentLatLng == null || !isNetworkAvailable()) {
            showToast("Vui lòng kiểm tra lại kết nối! ");
            return false;
        }
        String strDestinationLocation = String.valueOf(house.viDo) + "," + String.valueOf(house.kinhDo);
        final Location destinationLocation = new Location("destinationLocation");
        destinationLocation.setLatitude(house.viDo);
        destinationLocation.setLongitude(house.kinhDo);

        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang lấy thông tin đường đi...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        String currentLocation = String.valueOf(currentLatLng.latitude) + "," + String.valueOf(currentLatLng.longitude);
        String SERVER_GOOGLEMAP = "https://maps.googleapis.com/maps/api/directions/json?origin=" + currentLocation + "&destination=" + strDestinationLocation + "&key=AIzaSyD5Xzis5_DOGW2XLYvOQ7FCVvFzLsym9aA";

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
                            //     JSONObject jsonDistance = jsonLeg.getJSONObject("distance");
                            String points = overview_polylineJson.getString("points");
                            drawPolyline(destinationLocation, decodePolyLine(points));
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

    /*
    *
    * */

    public void drawPolyline(Location location, List<LatLng> points) {
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

        polylinePaths = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        LatLng endLocation = new LatLng(location.getLatitude(), location.getLongitude());
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(endLocation)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_selected)));
        destinationMarkers.add(marker);
        PolylineOptions polylineOptions = new PolylineOptions()
                .geodesic(true)
                .color(getResources().getColor(R.color.colorPrimary))
                .width(15);

        for (int i = 0; i < points.size(); i++)
            polylineOptions.add(points.get(i));
        polylinePaths.add(mMap.addPolyline(polylineOptions));
        getCurrentLocation(15f);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker.getTitle() != null) {
                    showBottonSheet(Integer.parseInt(marker.getTitle()));
                }
                previousMaker = marker;
                marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_house_selected));

                return true;
            }
        });
    }

    /*
    *
    * */
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
                b = poly.charAt(index++

                ) - 63;
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

}