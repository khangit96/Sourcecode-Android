package com.demosqlite;

import java.io.Serializable;

/**
 * Created by Administrator on 4/19/2017.
 */

public class Author implements Serializable{
    public int ID;
    public String fullname;
    public String tel;
    public String address;

    public Author() {
    }

    public Author(String fullname, String tel, String address) {
        this.fullname = fullname;
        this.tel = tel;
        this.address = address;
    }
}
