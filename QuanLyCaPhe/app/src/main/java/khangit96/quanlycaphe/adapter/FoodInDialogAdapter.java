package khangit96.quanlycaphe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.model.Food;
import khangit96.quanlycaphe.model.FortmatCurrency;

/**
 * Created by Administrator on 12/25/2016.
 */

public class FoodInDialogAdapter extends RecyclerView.Adapter<FoodInDialogAdapter.MyViewHolder> {
    List<Food> foodList;
    Context context;

    public FoodInDialogAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_in_dialog, parent, false);

        return new FoodInDialogAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.tvFoodName.setText(food.foodName);
        String formatFoodPrice = FortmatCurrency.formatVnCurrence(FortmatCurrency.formatDouble(food.foodPrice));
        holder.tvFoodPrice.setText(formatFoodPrice);
        Glide.with(context).load(foodList.get(position).foodUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.imgFood);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice;
        ImageView imgFood;

        public MyViewHolder(View v) {
            super(v);
            tvFoodName = (TextView) v.findViewById(R.id.tvFoodName);
            tvFoodPrice = (TextView) v.findViewById(R.id.tvFoodPrice);
            imgFood = (ImageView) v.findViewById(R.id.imgFood);
        }
    }
}
