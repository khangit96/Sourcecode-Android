package com.nailphuongle.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nailphuongle.R;
import com.nailphuongle.model.Item;

import java.util.ArrayList;

/**
 * Created by Administrator on 12/15/2016.
 */

public class CustomGridViewAdapter extends ArrayAdapter<Item> {
    Context context;
    int layoutResourceId;
    ArrayList<Item> listItem = new ArrayList<Item>();

    public CustomGridViewAdapter(Context context, int layoutResourceId, ArrayList<Item> listItem) {
        super(context, layoutResourceId, listItem);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.listItem = listItem;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       /* LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        convertView = inflater.inflate(layoutResourceId, parent, false);

        RecordHolder holder = null;
        holder = new RecordHolder();
        holder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
        holder.tvItemName.setText(listItem.get(position).name);
        return convertView;*/
        View row = convertView;
        RecordHolder holder = null;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new RecordHolder();
        holder.tvItemName = (TextView) row.findViewById(R.id.tvItemName);
        row.setTag(holder);
        holder.tvItemName.setText(listItem.get(position).name);
        return row;
    }

    /*
    *
    * */
    static class RecordHolder {
        TextView tvItemName;

    }
}
