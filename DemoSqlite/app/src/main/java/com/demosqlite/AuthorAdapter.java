package com.demosqlite;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 4/19/2017.
 */

public class AuthorAdapter extends ArrayAdapter<Author> {
    List<Author> authors;
    Context context;

    public AuthorAdapter(Context context, int resource, List<Author> authors) {
        super(context, resource, authors);
        this.context = context;
        this.authors = authors;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView =  LayoutInflater.from(getContext()).inflate(R.layout.item_author, parent, false);
        TextView tvFullname = (TextView) convertView.findViewById(R.id.tvFullname);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);

        tvFullname.setText(authors.get(position).fullname);
        tvPhone.setText(authors.get(position).tel);
        tvAddress.setText(authors.get(position).address);
        return convertView;
    }

    //spinner
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView =  LayoutInflater.from(getContext()).inflate(R.layout.item_author, parent, false);
        TextView tvFullname = (TextView) convertView.findViewById(R.id.tvFullname);
        TextView tvPhone = (TextView) convertView.findViewById(R.id.tvPhone);
        TextView tvAddress = (TextView) convertView.findViewById(R.id.tvAddress);

        tvFullname.setText(authors.get(position).fullname);
        tvPhone.setText(authors.get(position).tel);
        tvAddress.setText(authors.get(position).address);
        return convertView;
    }
}
