package com.demogridview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Administrator on 6/13/2016.
 */
public class PictureAdapter extends ArrayAdapter<Integer> {
    Activity context;
    int resource;
    List<Integer> list;

    public PictureAdapter(Activity context, int resource, List<Integer> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        ImageView img= (ImageView) row.findViewById(R.id.img);
        img.setImageResource(list.get(position));
        return row;
    }
}
