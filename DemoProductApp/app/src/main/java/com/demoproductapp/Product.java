package com.demoproductapp;

/**
 * Created by Administrator on 5/17/2016.
 */
public class Product {
    public String TenSP;
    public Integer GiaSP;
    public String HinhAnh;
    public Integer id;

    public Product(String tenSP, Integer giaSP, String hinhAnh,Integer ID) {
        TenSP = tenSP;
        GiaSP = giaSP;
        HinhAnh = hinhAnh;
        id=ID;
    }
}
