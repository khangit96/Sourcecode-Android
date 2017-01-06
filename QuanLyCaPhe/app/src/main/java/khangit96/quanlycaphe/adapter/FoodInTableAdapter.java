package khangit96.quanlycaphe.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.model.FortmatCurrency;
import khangit96.quanlycaphe.model.Order;

/**
 * Created by Administrator on 12/24/2016.
 */

public class FoodInTableAdapter extends RecyclerView.Adapter<FoodInTableAdapter.MyViewHolder> {
    Context context;
    List<Order> orderList;
    int selectedPos = 0;

    public FoodInTableAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_food_in_table, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvIndexInvoice.setText(String.valueOf(position + 1));
        String totalPrice = "Tổng tiền: " + FortmatCurrency.formatVnCurrence(
                FortmatCurrency.formatDouble(orderList.get(position).totalPrice));
        holder.tvTotalPrice.setText(totalPrice);

        if (orderList.get(position).isSelected()) {
            holder.backgroundItem.setBackgroundColor(Color.parseColor("#d5d5d5"));
            selectedPos = position;
        } else {
            holder.backgroundItem.setBackgroundColor(Color.TRANSPARENT);
        }

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvIndexInvoice, tvTotalPrice;
        RelativeLayout backgroundItem;

        public MyViewHolder(View v) {
            super(v);
            tvIndexInvoice = (TextView) v.findViewById(R.id.tvIndexInvoice);
            tvTotalPrice = (TextView) v.findViewById(R.id.tvTotalprice);
            backgroundItem = (RelativeLayout) v.findViewById(R.id.backgroundItem);

        }
    }

    public void setSelected(int pos) {
        try {
            orderList.get(selectedPos).setSelected(false);
            orderList.get(pos).setSelected(true);
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
