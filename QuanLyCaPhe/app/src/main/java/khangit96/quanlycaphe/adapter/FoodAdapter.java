package khangit96.quanlycaphe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.txusballesteros.bubbles.BubbleLayout;
import com.txusballesteros.bubbles.BubblesManager;

import java.util.ArrayList;
import java.util.List;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.activity.CartActivity;
import khangit96.quanlycaphe.activity.MainActivity;
import khangit96.quanlycaphe.model.Config;
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
    private BubblesManager bubblesManager;
    TextView tvBubbleCounter;

    /*
    *
    * */
    public FoodAdapter(Context context, ArrayList<Food> foodList) {
        this.foodList = foodList;
        this.context = context;
        initializeBubbleManager();
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
        holder.tvFoodName.setText(foodList.get(position).foodName);
        holder.tvFoodPrice.setText(foodList.get(position).getFormatPrice());

        Glide.with(context).load(foodList.get(position).foodUrl)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(holder.foodImg);

        holder.cbAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.cbAgree.isChecked()) {
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

        MainActivity.buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalOrderPrice == 0) {
                    showToast("Vui lòng chọn thức uống!");
                    return;
                }
                if (MainActivity.tableNumberSelected == 0) {
                    showToast("Vui lòng chọn bàn!");
                    return;
                }

                pushOrderToFirebase(new Order(foodListSelected, totalOrderPrice), MainActivity.tableNumberSelected);
                showToast("Gọi thức uống thành công!");
                addNewNotification();
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
        CheckBox cbAgree;
        TextView tvFoodName, tvFoodPrice;
        ImageView foodImg;

        public MyViewHolder(View v) {
            super(v);
            cbAgree = (CheckBox) v.findViewById(R.id.cbAgree);
            tvFoodName = (TextView) v.findViewById(R.id.tvFoodName);
            tvFoodPrice = (TextView) v.findViewById(R.id.tvFoodPrice);
            foodImg = (ImageView) v.findViewById(R.id.foodImg);
        }
    }

    /*
    * set text button order
    * */
    public void setTextButtoOrder() {
        MainActivity.buttonOrder.setText(String.format(context.getString(R.string.order),
                FortmatCurrency.formatVnCurrence(FortmatCurrency.formatDouble(totalOrderPrice))));
    }

    /*
    *
    * */
    public void setDataDefault() {
        MainActivity.buttonOrder.setText(context.getString(R.string.notOrder));
    }

    /*
    *
    * */
    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /*
    *
    * */
    public void pushOrderToFirebase(Order order, int table) {
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_KEY + "/Order/Table " + table).push();
        mDatabase.setValue(order);

    }

    /**
     * Configure the trash layout with your BubblesManager builder.
     */
    private void initializeBubbleManager() {
        bubblesManager = new BubblesManager.Builder(context)
                // .setTrashLayout(R.layout.notification_trash_layout)
                .build();
        bubblesManager.initialize();
    }

    private void addNewNotification() {
        BubbleLayout bubbleView = (BubbleLayout) LayoutInflater.from(context).inflate(R.layout.notification_layout, null);
        tvBubbleCounter = (TextView) bubbleView.findViewById(R.id.tvBubbleCounter);
        tvBubbleCounter.setText("1");

        // this method call when user remove notification layout
        bubbleView.setOnBubbleRemoveListener(new BubbleLayout.OnBubbleRemoveListener() {
            @Override
            public void onBubbleRemoved(BubbleLayout bubble) {

            }
        });
        // this methoid call when cuser click on the notification layout( bubble layout)
        bubbleView.setOnBubbleClickListener(new BubbleLayout.OnBubbleClickListener() {

            @Override
            public void onBubbleClick(BubbleLayout bubble) {
                Intent intentCart = new Intent(context, CartActivity.class);
                intentCart.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentCart);
            }
        });

        // add bubble view into bubble manager
        bubblesManager.addBubble(bubbleView, 300, 550);
    }


}
