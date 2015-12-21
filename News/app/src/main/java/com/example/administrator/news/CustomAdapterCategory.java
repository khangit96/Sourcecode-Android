package com.example.administrator.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import khang.Variables;

/**
 * Created by Administrator on 12/16/2015.
 */
public class CustomAdapterCategory extends ArrayAdapter<String> {
    ArrayList<String> item;
    private  int icon;
    public CustomAdapterCategory(Context context, int resource,int icon, ArrayList<String> items) {
        super(context, resource,icon, items);
        this.item=items;
        this.icon=icon;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_category, null);
        }

        //SinhVien3 p = getItem(position);
        // Anh xa + Gan gia tri
      //  ImageView img=(ImageView) v.findViewById(R.id.img);
        TextView tvCategory = (TextView) v.findViewById(R.id.tvCategory);
        ImageView img=(ImageView)v.findViewById(R.id.img);
        tvCategory.setText(item.get(position).toString());
        img.setImageResource(icon);
        return v;
    }
}
