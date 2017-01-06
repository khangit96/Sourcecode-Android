package khangit96.quanlycaphe.model;

import android.text.TextUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Administrator on 12/14/2016.
 */

public class FortmatCurrency {

    /*
   *
   * */
    public static String formatVnCurrence(String price) {

        NumberFormat format =
                new DecimalFormat("#,##0.00");// #,##0.00 ¤ (¤:// Currency symbol)
        format.setCurrency(Currency.getInstance(Locale.US));//Or default locale

        price = (!TextUtils.isEmpty(price)) ? price : "0";
        price = price.trim();
        price = format.format(Double.parseDouble(price));
        price = price.replaceAll(",", "\\.");

        if (price.endsWith(".00")) {
            int centsIndex = price.lastIndexOf(".00");
            if (centsIndex != -1) {
                price = price.substring(0, centsIndex);
            }
        }
        price = String.format("%s VNĐ", price);
        return price;
    }

    /*
    *
    * */
    public static String formatDouble(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }
}
