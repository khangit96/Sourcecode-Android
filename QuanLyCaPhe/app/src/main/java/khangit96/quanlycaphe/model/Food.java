package khangit96.quanlycaphe.model;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

/**
 * Created by Administrator on 12/6/2016.
 */

public class Food {
    public String foodName;
    public double foodPrice;
    public int foodImage;

    public Food(String foodName, double foodPrice, int foodImage) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodImage = foodImage;
    }

    public String getFoodPrice() {
        return FortmatCurrency.formatVnCurrence(FortmatCurrency.formatDouble(foodPrice));
    }

    @BindingAdapter("setImage")
    public static void setImage(ImageView img, int resource) {
        img.setImageResource(resource);
    }


}
