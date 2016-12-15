package khangit96.quanlycaphe.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import khangit96.quanlycaphe.R;

/**
 * Created by Administrator on 12/12/2016.
 */

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    List<String> tableList;
    Activity activity;

    public CustomSpinnerAdapter(Activity activity, int resource, List<String> tableList) {
        super(activity, resource, tableList);
        this.activity = activity;
        this.tableList = tableList;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_table, parent, false);
        TextView tvTableName = (TextView) convertView.findViewById(R.id.tvTableName);
        tvTableName.setText(tableList.get(position));
        return convertView;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_table, parent, false);
        TextView tvTableName = (TextView) convertView.findViewById(R.id.tvTableName);
        tvTableName.setText(tableList.get(position));
        return convertView;
    }
}
