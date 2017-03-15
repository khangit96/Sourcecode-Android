package com.demoandroinodemcu;

import java.io.Serializable;

/**
 * Created by Administrator on 3/13/2017.
 */

public class ThongTinChung implements Serializable {
    public float canNang;
    public float chieuCao;
    public String matKhau;
    public float soLit;
    public String tenDangNhap;
    public String key;

    public ThongTinChung() {
    }


    public ThongTinChung(float canNang, float chieuCao, String matKhau, float soLit, String tenDangNhap) {
        this.canNang = canNang;
        this.chieuCao = chieuCao;
        this.matKhau = matKhau;
        this.soLit = soLit;
        this.tenDangNhap = tenDangNhap;
    }

    public static float CAN_NANG;
    public static float CHIEU_CAO;
    public static String MAT_KHAU;
    public static float SO_LIT;
    public static String TEN_DANG_NHAP;
    public static String KEY;


}
