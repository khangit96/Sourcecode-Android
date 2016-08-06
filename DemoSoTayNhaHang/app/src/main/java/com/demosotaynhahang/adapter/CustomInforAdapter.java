package com.demosotaynhahang.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.demosotaynhahang.R;
import com.demosotaynhahang.model.NhaHang;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Created by Administrator on 6/1/2016.
 */
public class CustomInforAdapter implements GoogleMap.InfoWindowAdapter {
    Activity context;
    NhaHang nhaHang;

    public CustomInforAdapter(Activity context, NhaHang nhaHang) {
        this.context = context;
        this.nhaHang = nhaHang;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        LayoutInflater inflater=this.context.getLayoutInflater();
        View row=inflater.inflate(R.layout.item, null);
        ImageView imgHinh= (ImageView) row.findViewById(R.id.imgHinh);
        TextView tvTen= (TextView) row.findViewById(R.id.tvTen);
        imgHinh.setImageResource(nhaHang.getHinh());
        tvTen.setText(nhaHang.getTen());
        return row;
    }
}
