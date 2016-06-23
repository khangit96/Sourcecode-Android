package com.aspsine.multithreaddownload.demo;


import com.aspsine.multithreaddownload.demo.entity.AppInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/7/8.
 */
public class DataSource {

    private static DataSource sDataSource = new DataSource();

    private static final String[] NAMES = {
            "Đếm ngày xa em",
            "Tri kỷ",
            "Em muốn anh sống sao"
    };

    private static final String[] IMAGES = {
            "http://img.wdjimg.com/mms/icon/v1/d/f1/1c8ebc9ca51390cf67d1c3c3d3298f1d_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/3/2d/dc14dd1e40b8e561eae91584432262d3_512_512.png",
            "http://img.wdjimg.com/mms/icon/v1/8/10/1b26d9f0a258255b0431c03a21c0d108_512_512.png",

    };

    private static final String[] URLS = {
            "http://stream7.r13s120.vcdn.vn/fsfsdfdsfdserwrwq3/03d5071bd64d451c4f422f3599f62569/57689c5b/2016/05/04/5/9/591d76600e7b5627724a3a0eeb3b36d3.mp3",
            "http://mp3.zing.vn/xml/load-song/MjAxNiUyRjA0JTJGMTIlMkZiJTJGNyUyRmI3OTkzZjJmYjVjYTY5OTMxZmM5NDM0YTQ3YTFhMWJjLm1wMyU3QzEz",
           " http://stream7.r13s120.vcdn.vn/fsfsdfdsfdserwrwq3/f67b7a8d7e1dd6a4a6f513c43e98f4a7/5768abfa/2015/04/25/8/6/8682d606395475d1a2e891f787b3ade6.mp3"
    };

    public static DataSource getInstance() {
        return sDataSource;
    }

    public List<AppInfo> getData() {
        List<AppInfo> appInfos = new ArrayList<AppInfo>();
        for (int i = 0; i < NAMES.length; i++) {
            AppInfo appInfo = new AppInfo(String.valueOf(i), NAMES[i], IMAGES[i], URLS[i]);
            appInfos.add(appInfo);
        }
        return appInfos;
    }
}
