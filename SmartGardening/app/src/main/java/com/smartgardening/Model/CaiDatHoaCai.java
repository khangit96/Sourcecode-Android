package com.smartgardening.Model;

/**
 * Created by Administrator on 4/11/2017.
 */

public class CaiDatHoaCai {
    public int doAmDatThapNhat;
    public String henGioTuoi;
    public int thoiGianCapNhatDuLieu;
    public int thoiGianTuoi;

    public CaiDatHoaCai() {
    }

    public CaiDatHoaCai(int doAmDatThapNhat, String henGioTuoi, int thoiGianCapNhatDuLieu, int thoiGianTuoi) {
        this.doAmDatThapNhat = doAmDatThapNhat;
        this.henGioTuoi = henGioTuoi;
        this.thoiGianCapNhatDuLieu = thoiGianCapNhatDuLieu;
        this.thoiGianTuoi = thoiGianTuoi;
    }
}
