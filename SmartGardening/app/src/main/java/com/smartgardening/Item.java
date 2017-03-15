package com.smartgardening;

import java.io.Serializable;

/**
 * Created by ShimizuRou on 3/2/2017.
 */

public class Item implements Serializable {
    private String name;
    private String key;

    public Item() {
    }

    public Item(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() { return key; }

    public void setKey(String key) { this.key = key; }

    @Override
    public String toString() {
        return this.name;
    }
}
