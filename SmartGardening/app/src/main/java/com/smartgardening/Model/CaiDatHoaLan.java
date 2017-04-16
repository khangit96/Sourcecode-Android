package com.smartgardening.Model;

/**
 * Created by Administrator on 4/11/2017.
 */

public class CaiDatHoaLan {
    public int doAmKhongKhiThapNhat;
    public String henGioPhunSuong;
    public int thoiGianCapNhatDuLieu;
    public int thoiGianPhunSuong;

    public CaiDatHoaLan() {
    }

    public CaiDatHoaLan(int doAmKhongKhiThapNhat, String henGioPhunSuong, int thoiGianCapNhatDuLieu, int thoiGianPhunSuong) {
        this.doAmKhongKhiThapNhat = doAmKhongKhiThapNhat;
        this.henGioPhunSuong = henGioPhunSuong;
        this.thoiGianCapNhatDuLieu = thoiGianCapNhatDuLieu;
        this.thoiGianPhunSuong = thoiGianPhunSuong;
    }
}
