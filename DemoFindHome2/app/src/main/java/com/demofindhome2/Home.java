package com.demofindhome2;

/**
 * Created by Administrator on 5/31/2016.
 */
public class Home implements Comparable<Home>{
    public String homeName;
    public double homeLatitude;
    public double homeLongtitude;
    public double distance;

    public Home(String homeName, double homeLongtitude, double homeLatitude, double distance) {
        this.homeName = homeName;
        this.homeLongtitude = homeLongtitude;
        this.homeLatitude = homeLatitude;
        this.distance = distance;
    }

    @Override
    public int compareTo(Home home) {
        int compareage= (int) ((Home)home).distance;
        /* For Ascending order*/
        return (int) (this.distance-compareage);
    }
}
