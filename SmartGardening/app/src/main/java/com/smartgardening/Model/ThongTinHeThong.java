package com.smartgardening.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 3/31/2017.
 */

public class ThongTinHeThong implements Serializable{
    public String key;
    public String loaiCay;
    public String tenHeThong;
    public ThongTinHeThong() {
    }

    public ThongTinHeThong(String key, String loaiCay, String tenHeThong) {
        this.key = key;
        this.loaiCay = loaiCay;
        this.tenHeThong = tenHeThong;
    }
}
