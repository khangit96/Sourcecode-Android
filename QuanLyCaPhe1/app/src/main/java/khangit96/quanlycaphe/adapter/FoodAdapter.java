package khangit96.quanlycaphe.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.activity.MainActivity;
import khangit96.quanlycaphe.databinding.ListFoodBinding;
import khangit96.quanlycaphe.model.Food;
import khangit96.quanlycaphe.model.FortmatCurrency;
import khangit96.quanlycaphe.model.Order;

/**
 * Created by Administrator on 12/12/2016.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    List<Food> foodList;
    List<Food> foodListSelected = new ArrayList<>();
    Context context;
    double totalOrderPrice = 0;

    /*
    *
    * */
    public FoodAdapter(Context context, ArrayList<Food> foodList) {
        this.foodList = foodList;
        this.context = context;
    }

    /*
    *
    * */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_food, parent, false);

        return new MyViewHolder(itemView);
    }

    /*
        *
        * */
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.bind(foodList.get(position));
        holder.binding.cbAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (holder.binding.cbAgree.isChecked()) {

                    foodListSelected.add(foodList.get(position));
                    totalOrderPrice += foodList.get(position).foodPrice;

                    if (totalOrderPrice == 0) {
                        setDataDefault();
                    } else {
                        setTextButtoOrder();
                    }
                    return;
                }

                totalOrderPrice -= foodList.get(position).foodPrice;
                if (totalOrderPrice == 0) {
                    setDataDefault();

                } else {
                    setTextButtoOrder();
                }

                for (int i = 0; i < foodListSelected.size(); i++) {
                    if (foodListSelected.get(i).foodName.equals(foodList.get(position).foodName)) {
                        foodListSelected.remove(i);
                        return;
                    }
                }
            }
        });

        MainActivity.binding.buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalOrderPrice == 0) {
                    showToast("Vui lòng chọn thức uống!");
                    return;
                }
                if (MainActivity.selectedItemSpinnerPos == 0) {
                    showToast("Vui lòng chọn bàn!");
                    return;
                }
                MainActivity.pushOrderToFirebase(new Order(foodListSelected, totalOrderPrice),
                        MainActivity.selectedItemSpinnerPos);

                showToast("Gọi thức uống thành công!");

            }
        });
    }

    /***
     *
     * */
    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ListFoodBinding binding;


        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(Food food) {
            binding.setFood(food);
        }
    }

    /*
    * set text button order
    * */
    public void setTextButtoOrder() {
        MainActivity.binding.buttonOrder.setText(String.format(context.getString(R.string.order),
                FortmatCurrency.formatVnCurrence(FortmatCurrency.formatDouble(totalOrderPrice))));
    }

    /*
    *
    * */
    public void setDataDefault() {
        MainActivity.binding.buttonOrder.setText(context.getString(R.string.notOrder));
    }

    /*
    *
    * */
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
