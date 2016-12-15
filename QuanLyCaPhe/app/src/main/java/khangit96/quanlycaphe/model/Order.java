package khangit96.quanlycaphe.model;

import java.util.List;

/**
 * Created by Administrator on 12/4/2016.
 */

public class Order {
    List<Food> foodList;
    double totalPrice;

    public Order(List<Food> foodList, double totalPrice) {
        this.foodList = foodList;
        this.totalPrice = totalPrice;
    }
}
