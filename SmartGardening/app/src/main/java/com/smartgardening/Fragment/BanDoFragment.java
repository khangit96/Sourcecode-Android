package com.smartgardening.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.smartgardening.R;

public class BanDoFragment extends Fragment implements OnMapReadyCallback {
    MapView mapView;
    GoogleMap map;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_ban_do, container, false);
        // Gets the MapView from the XML layout and creates it

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView = (MapView) v.findViewById(R.id.map);
        mapView.onCreate(null);
        mapView.onResume();

        // Gets to GoogleMap from the MapView and does initialization stuff
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(this.getActivity());
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng posHoaLan = new LatLng(10.980609, 106.674446);
        LatLng posHoaCai=new LatLng(10.981612, 106.674019);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(posHoaLan, 18f));
        googleMap.addMarker(new MarkerOptions().position(posHoaLan)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.hoa_lan))
                .title("Vườn hoa lan"));
        googleMap.addMarker(new MarkerOptions().position(posHoaCai)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.rsz_lach1))
                .title("Vườn hoa cải"));

    }
}
