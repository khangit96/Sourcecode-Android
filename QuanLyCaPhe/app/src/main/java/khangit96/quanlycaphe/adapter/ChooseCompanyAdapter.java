package khangit96.quanlycaphe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.model.Config;

/**
 * Created by Administrator on 12/26/2016.
 */

public class ChooseCompanyAdapter extends RecyclerView.Adapter<ChooseCompanyAdapter.MyViewHolder> {

    List<Config> configList;
    Context context;

    public ChooseCompanyAdapter(Context context, List<Config> configList) {
        this.context = context;
        this.configList = configList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_comany, parent, false);

        return new ChooseCompanyAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvCompanyName.setText(configList.get(position).companyName);
    }

    @Override
    public int getItemCount() {
        return configList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCompanyName;

        public MyViewHolder(View v) {
            super(v);
            tvCompanyName = (TextView) v.findViewById(R.id.tvCompanyName);
        }
    }
}
