package com.demogooglemap5.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demogooglemap5.Home;
import com.demogooglemap5.R;
import com.demogooglemap5.Route;

import java.util.List;

/**
 * Created by Administrator on 6/16/2016.
 */
public class ResultAdapter extends ArrayAdapter<Route> {
    Activity context;
    int resource;
    List<Route> routeList;

    public ResultAdapter(Activity context, int resource, List<Route> routeList) {
        super(context, resource, routeList);
        this.context = context;
        this.resource = resource;
        this.routeList = routeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView tvName, tvDistance;
        tvName = (TextView) row.findViewById(R.id.tvName);
        tvDistance = (TextView) row.findViewById(R.id.tvDistance);
        tvName.setText(routeList.get(position).name);
        tvDistance.setText("" + routeList.get(position).distance.value + "m");
        return row;
    }
}
