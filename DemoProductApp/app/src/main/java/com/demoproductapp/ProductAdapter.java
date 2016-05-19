package com.demoproductapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.util.Base64;

import java.util.List;

/**
 * Created by Administrator on 5/17/2016.
 */
public class ProductAdapter extends BaseAdapter {
    Context myContext;
    List<Product> productList;

    public ProductAdapter(Context myContext, List<Product> productList) {
        this.productList = productList;
        this.myContext = myContext;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.product_row, null);
        TextView tvProductName, tvProductPrice;
        ImageView productImg;
        tvProductName = (TextView) convertView.findViewById(R.id.tvProductName);
        tvProductPrice = (TextView) convertView.findViewById(R.id.tvProductPrice);
        productImg = (ImageView) convertView.findViewById(R.id.productImg);
        tvProductName.setText(productList.get(position).TenSP);
        tvProductPrice.setText(""+productList.get(position).GiaSP);
        String_To_ImageView(productList.get(position).HinhAnh,productImg);
        return convertView;
    }
    public void String_To_ImageView(String strBase64, ImageView iv){
        byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        iv.setImageBitmap(decodedByte);
    }
}
