package khangit96.quanlycaphe.adapter;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.activity.admin.AdminTableActivity;
import khangit96.quanlycaphe.model.Config;
import khangit96.quanlycaphe.model.Table;

/**
 * Created by Administrator on 1/26/2017.
 */

public class AdminTableAdapter extends RecyclerView.Adapter<AdminTableAdapter.MyViewHolder> {
    List<Table> tableList;
    AdminTableActivity activity;

    public AdminTableAdapter(List<Table> tableList, AdminTableActivity activity) {
        this.tableList = tableList;
        this.activity = activity;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_table_admin, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.tvTableAdminName.setText(tableList.get(position).tableName);
        holder.btEditTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditTable(position + 1, tableList.get(position).tableName);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTableAdminName;
        Button btEditTable;

        public MyViewHolder(View v) {
            super(v);
            tvTableAdminName = (TextView) v.findViewById(R.id.tvTableAdminName);
            btEditTable = (Button) v.findViewById(R.id.btEditTable);
        }
    }

    public void showDialogEditTable(final int tableNumber, final String tableName) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(activity);
        View mView = layoutInflaterAndroid.inflate(R.layout.dialog_add_table_admin, null);
        TextView tvTitle = (TextView) mView.findViewById(R.id.tvTitle);
        tvTitle.setText("Chỉnh sửa bàn");

        final EditText edTable = (EditText) mView.findViewById(R.id.edTableName);
        edTable.setText(tableName);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setView(mView);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                editTable(tableNumber, edTable.getText().toString());


            }
        });
        builder.setNegativeButton("Huỷ", null);
        builder.show();
    }

    public void editTable(int tableNumber, String tableName) {
        if (tableName.equals("")) {
            Toast.makeText(activity, "Vui lòng nhập tên bàn", Toast.LENGTH_LONG).show();
            return;
        }
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(Config.COMPANY_KEY + "/Table/" + tableNumber).setValue(new Table(tableNumber, tableName));
        
        AdminTableActivity.tableList.set(tableNumber - 1, new Table(tableNumber, tableName));
        AdminTableActivity.adapter.notifyDataSetChanged();
    }
}
