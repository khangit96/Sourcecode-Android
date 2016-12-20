package khangit96.quanlycaphe.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.activity.ManageActivity;
import khangit96.quanlycaphe.model.Table;

/**
 * Created by Administrator on 12/15/2016.
 */

public class CustomGridViewAdapter extends ArrayAdapter<Table> {
    Context context;
    int layoutResourceId;
    ArrayList<Table> data = new ArrayList<Table>();

    public CustomGridViewAdapter(Context context, int layoutResourceId,
                                 ArrayList<Table> data) {
        super(context, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        row = inflater.inflate(layoutResourceId, parent, false);

        holder = new RecordHolder();
        holder.tvTableName = (TextView) row.findViewById(R.id.tvTableName);
        holder.tvItemCount = (TextView) row.findViewById(R.id.tvItemCount);
        row.setTag(holder);

        Table table = data.get(position);
        holder.tvTableName.setText(table.tableName);

        ManageActivity.listenOrderFromFirebase(table.tableNumber, holder.tvItemCount);
        return row;

    }

    static class RecordHolder {
        TextView tvTableName, tvItemCount;
    }
}
