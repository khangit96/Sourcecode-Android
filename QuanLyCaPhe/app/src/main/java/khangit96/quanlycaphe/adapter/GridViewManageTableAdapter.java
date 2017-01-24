package khangit96.quanlycaphe.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Table;

/**
 * Created by Administrator on 12/15/2016.
 */

public class GridViewManageTableAdapter extends ArrayAdapter<Table> {
    Context context;
    int layoutResourceId;
    ArrayList<Table> data = new ArrayList<Table>();

    public GridViewManageTableAdapter(Context context, int layoutResourceId,
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

        listenOrderFromFirebase(table.tableNumber, holder.tvItemCount);

        return row;

    }

    /*
    *
    * */
    public void listenOrderFromFirebase(final int table, final TextView tv) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_KEY + "/Order/Table " + table);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                tv.setText(String.valueOf(dataSnapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    /*
    *
    * */
    static class RecordHolder {
        TextView tvTableName, tvItemCount;
    }
}
