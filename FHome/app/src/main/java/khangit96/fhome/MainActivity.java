package khangit96.fhome;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
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

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    private GoogleMap mMap;

    ProgressDialog progressDialog;

    ArrayList<NhaTro> nhaTroArrayLis;

    TextView tvTenNhaTro;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = mapFragment.getMap();
        mMap.setMyLocationEnabled(true);

        KhoiTaoControl();

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings();
                mMap.setTrafficEnabled(true);
            }
        });
        mMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                count++;
                if (count == 3) {
                    progressDialog.dismiss();
                    LatLng viTriHienTai = new LatLng(location.getLatitude(), location.getLongitude());

                    for (int i = 0; i < nhaTroArrayLis.size(); i++) {
                        LatLng viTrinNhaTro = new LatLng(nhaTroArrayLis.get(i).viDo, nhaTroArrayLis.get(i).kinhDo);
                        mMap.addMarker(new MarkerOptions()
                                .position(viTrinNhaTro)
                                .title(nhaTroArrayLis.get(i).tenNhaTro));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(viTriHienTai, 16f));
                }

            }
        });

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
        nhaTroArrayLis = new ArrayList<>();
        nhaTroArrayLis.add(new NhaTro("Nhà trọ Ngọc Lan", "Bình Hòa Nam,Đức Huệ", 10.8325009, 106.3493161));
        nhaTroArrayLis.add(new NhaTro("Nhà trọ Phương Thảo", "Phú Hòa,Phú Lợi", 10.828845, 106.3513416));
        nhaTroArrayLis.add(new NhaTro("Nhà trọ Việt Đức", "Phường Chánh Nghĩa,Tp.Thủ Dầu Một,Bình Dương", 10.80954, 106.3669016));

        LatLng hcmus = new LatLng(10.980808, 106.682577);
        mMap.addMarker(new MarkerOptions()
                .position(hcmus)
                .title("Đại Học Thủ Dầu Một"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hcmus, 16f));
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Đang tìm nhà trọ quanh đây...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //   getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public void OpenBottomSheet(String tenNhaTro, String diaChi) {
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        TextView tvTenNhaTro = (TextView) view.findViewById(R.id.tvTenNhaTro);
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

}
