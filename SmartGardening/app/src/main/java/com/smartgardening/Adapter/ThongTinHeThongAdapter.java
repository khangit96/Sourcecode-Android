package com.smartgardening.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartgardening.R;
import com.smartgardening.Model.ThongTinHeThong;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ShimizuRou on 3/2/2017.
 */

public class ThongTinHeThongAdapter extends BaseAdapter {
    private Context context;
    private List<ThongTinHeThong> thongTinHeThongList;

    public ThongTinHeThongAdapter(Context context, ArrayList<ThongTinHeThong> thongTinHeThongList) {
        this.context = context;
        this.thongTinHeThongList = thongTinHeThongList;
    }

    @Override
    public int getCount() {
        return thongTinHeThongList.size();
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

            if (position == 1) {
                imageViewAndroid.setImageResource(R.drawable.rsz_lach1);
            } else {
                imageViewAndroid.setImageResource(R.drawable.hoa_lan);
            }
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.txt_item);
            textViewAndroid.setText(thongTinHeThongList.get(position).tenHeThong);
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}
