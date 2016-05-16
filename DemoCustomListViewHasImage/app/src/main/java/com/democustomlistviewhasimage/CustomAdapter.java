package com.democustomlistviewhasimage;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 5/13/2016.
 */
public class CustomAdapter extends BaseAdapter {
    List<Person> personList;
    Context context;

    public CustomAdapter(List<Person> personList, Context context) {
        this.personList = personList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, R.layout.list_item, null);
        ImageView img=(ImageView)convertView.findViewById(R.id.img);
        img.setImageResource(personList.get(position).getImage());
        return convertView;
    }
}
