package com.smartgardening;

import java.io.Serializable;

/**
 * Created by ShimizuRou on 3/2/2017.
 */

public class Item implements Serializable {
    public float ground_humidity;
    public float humidity;
    public String key;
    public String name;
    public float temp;

    public Item() {
    }

    public Item(float ground_humidity, float humidity, String key, String name, float temp) {
        this.ground_humidity = ground_humidity;
        this.humidity = humidity;
        this.key = key;
        this.name = name;
        this.temp = temp;
    }
}
