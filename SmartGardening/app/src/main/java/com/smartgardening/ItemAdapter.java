package com.smartgardening;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShimizuRou on 3/2/2017.
 */

public class ItemAdapter extends BaseAdapter {
    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(context);
            gridViewAndroid = inflater.inflate(R.layout.item_layout, null);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.image_item);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.txt_item);
            textViewAndroid.setText(itemList.get(position).getName());
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
