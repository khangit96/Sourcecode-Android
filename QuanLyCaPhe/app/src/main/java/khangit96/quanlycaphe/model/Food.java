package khangit96.quanlycaphe.model;

/**
 * Created by Administrator on 12/6/2016.
 */

public class Food {
    public String foodName;
    public double foodPrice;
    public String foodUrl;
    public String formatPrice;

    public Food(String foodName, double foodPrice, String foodUrl) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.foodUrl = foodUrl;
    }

    public Food() {

    }

    public String getFormatPrice() {
        formatPrice = FortmatCurrency.formatVnCurrence(FortmatCurrency.formatDouble(foodPrice));
        return formatPrice;
    }

}
