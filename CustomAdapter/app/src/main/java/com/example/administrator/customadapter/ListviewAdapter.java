package com.example.administrator.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Administrator on 11/23/2015.
 */
public class ListviewAdapter extends ArrayAdapter<sanpham> {

    public ListviewAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListviewAdapter(Context context, int resource, List<sanpham> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.content_custom__listview, null);
        }

        sanpham p = getItem(position);

        if (p != null) {
            // Anh xa + Gan gia tri
            TextView tvTen = (TextView) v.findViewById(R.id.tvTen);
            TextView tvGia=(TextView) v.findViewById(R.id.tvGia);
            ImageView img=(ImageView)v.findViewById(R.id.img);
            tvTen.setText(p.ten);
            tvGia.setText(p.gia.toString());
            Picasso.with(getContext()).load(p.hinh).into(img);
        }

        return v;
    }

}