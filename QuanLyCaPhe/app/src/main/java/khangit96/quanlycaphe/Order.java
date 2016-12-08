package khangit96.quanlycaphe;

import java.util.List;

/**
 * Created by Administrator on 12/4/2016.
 */

public class Order {
    String table;
    List<Food> foodList;
    double totalPrice;

    public Order(String table, List<Food> foodList, double totalPrice) {
        this.table = table;
        this.foodList = foodList;
        this.totalPrice = totalPrice;
    }
}
