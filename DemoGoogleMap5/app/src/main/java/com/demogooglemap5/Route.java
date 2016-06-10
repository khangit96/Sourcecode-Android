package com.demogooglemap5;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by Administrator on 6/8/2016.
 */
public class Route {
    public Distance distance;
    public Duration duration;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
