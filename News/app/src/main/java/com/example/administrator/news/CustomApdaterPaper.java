package com.example.administrator.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import khang.Variables;

/**
 * Created by Administrator on 12/16/2015.
 */
public class CustomApdaterPaper extends ArrayAdapter<Variables> {

    public CustomApdaterPaper(Context context, int resource, List<Variables> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_paper, null);
        }

        //SinhVien3 p = getItem(position);
            // Anh xa + Gan gia tri
            ImageView img=(ImageView) v.findViewById(R.id.img);
            TextView tvName = (TextView) v.findViewById(R.id.tvName);
            tvName.setText(Variables.PAPERS[position]);
            img.setImageResource(Variables.ICONS[position]);
        return v;
    }

}