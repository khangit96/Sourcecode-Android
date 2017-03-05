package com.minhuyenwallpaper.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.minhuyenwallpaper.R;

import java.util.List;

/**
 * Created by Administrator on 12/12/2016.
 */

public class SpinnerToolbarAdapter extends ArrayAdapter<String> {
    List<String> categoryList;
    Activity activity;

    public SpinnerToolbarAdapter(Activity activity, int resource, List<String> categoryList) {
        super(activity, resource, categoryList);
        this.activity = activity;
        this.categoryList = categoryList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.spinner_category, parent, false);
        TextView tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
        tvCategoryName.setText(categoryList.get(position));
        return convertView;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.spinner_category, parent, false);
        TextView tvCategoryName = (TextView) convertView.findViewById(R.id.tvCategoryName);
        tvCategoryName.setText(categoryList.get(position));
        return convertView;
    }
}
