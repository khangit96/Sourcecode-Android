package com.demosotaynhahang.model;

import com.demosotaynhahang.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/1/2016.
 */
public class FakeNhaHang {
    public static ArrayList<NhaHang> layDanhSach(){
        ArrayList<NhaHang>ds=new ArrayList<>();
        ds.add(new NhaHang("Nhà hàng Bạch Kim", R.drawable.bachkim,10.7756175,106.6464489));
      /*  ds.add(new NhaHang("Nhà hàng Hương Phố", R.drawable.huongpho,10.8288601,106.6812516));*/
        ds.add(new NhaHang("Nhà hàng Hương Phố", R.drawable.huongpho,10.8325009,106.3493161));
        return ds;
    }
}
