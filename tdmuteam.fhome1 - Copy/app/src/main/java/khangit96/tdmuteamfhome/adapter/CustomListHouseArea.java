package khangit96.tdmuteamfhome.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import khangit96.tdmuteamfhome.R;

/**
 * Created by Administrator on 1/1/2017.
 */

public class CustomListHouseArea extends ArrayAdapter<String> {
    Context context;
    List<String> houseAreaList;

    public CustomListHouseArea(Context context, int resource, List<String> houseAreaList) {
        super(context, resource, houseAreaList);
        this.context = context;
        this.houseAreaList = houseAreaList;
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return houseAreaList.get(position);
    }

    @Override
    public int getCount() {
        return houseAreaList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_house_area, parent, false);

        return convertView;
    }
}
