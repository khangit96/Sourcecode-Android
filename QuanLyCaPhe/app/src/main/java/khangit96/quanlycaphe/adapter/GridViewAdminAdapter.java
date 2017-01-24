package khangit96.quanlycaphe.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.model.ItemManage;

/**
 * Created by Administrator on 1/22/2017.
 */

public class GridViewAdminAdapter extends ArrayAdapter<ItemManage> {
    Context context;
    int layoutResourceId;
    ArrayList<ItemManage> data;

    public GridViewAdminAdapter(Context context, int layoutResourceId, ArrayList<ItemManage> data) {
        super(context, layoutResourceId, data);
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        RecordHolder holder = null;

        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new RecordHolder();

        holder.tvItemName = (TextView) row.findViewById(R.id.tvItemName);
        holder.itemImg = (ImageView) row.findViewById(R.id.itemImg);
        holder.tvItemName.setText(data.get(position).itemName);
        holder.itemImg.setImageResource(data.get(position).itemImg);

        row.setTag(holder);

        return row;

    }


    /*
    *
    * */
    static class RecordHolder {
        TextView tvItemName;
        ImageView itemImg;
    }
}
