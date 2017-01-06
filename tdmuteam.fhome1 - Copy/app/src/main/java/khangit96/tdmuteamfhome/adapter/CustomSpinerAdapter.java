package khangit96.tdmuteamfhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.annotations.NonNull;

import java.util.List;

import khangit96.tdmuteamfhome.R;


/**
 * Created by Administrator on 12/31/2016.
 */

public class CustomSpinerAdapter extends ArrayAdapter<String> {
    List<String> list;
    Context context;

    public CustomSpinerAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
        this.context = context;
        this.list = list;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_house_price, parent, false);
        TextView tvShow = (TextView) convertView.findViewById(R.id.tvShow);
        tvShow.setText(list.get(position));
        return convertView;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_house_price, parent, false);
        TextView tvShow = (TextView) convertView.findViewById(R.id.tvShow);
        tvShow.setText(list.get(position));
        return convertView;
    }
}
