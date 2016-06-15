package com.demoanimationlistview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 6/16/2016.
 */
public class MyAdapter extends ArrayAdapter<String> {
    Activity context;
    int resource;
    List<String> list;

    public MyAdapter(Activity context, int resource, List<String> list) {
        super(context, resource,list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource, null);
        TextView tv = (TextView) row.findViewById(R.id.tv);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.listview);
        tv.setText(this.list.get(position));
        row.startAnimation(animation);
        return row;

    }
}
