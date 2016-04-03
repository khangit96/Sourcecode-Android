package com.hnib.smslater.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.hnib.smslater.utils.CommonUtils;

import java.util.Comparator;
import java.util.List;

/**
 * Created by caucukien on 14/12/2015.
 */

@Table(name = "SMS")
public class SmsPojo extends Model implements Comparator<SmsPojo> {
    @Column(name = "content")
    public String content;

    @Column(name = "status")
    public int status;

    @Column(name = "date")
    public String date;

    @Column(name = "time")
    public String time;

    public SmsPojo() {
        super();
    }


    public List<ContactPojo> getContactPojos() {
        return new Select()
                .from(ContactPojo.class)
                .where("SmsPojo = ?", this.getId())
                .orderBy("Name ASC")
                .execute();

        //return getMany(ContactPojo.class, "SmsPojo");
    }

    public boolean isSent() {
        if (status == 1) {
            return true;
        }
        return false;
    }

    @Override
    public int compare(SmsPojo sms1, SmsPojo sms2) {
        String date1 = sms1.date.split(" ")[1] + " " + sms1.time;
        String date2 = sms2.date.split(" ")[1] + " " + sms2.time;

        int compare = CommonUtils.compareTwoDate(date1, date2);

        return compare ;
    }
}
