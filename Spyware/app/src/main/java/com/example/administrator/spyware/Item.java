package com.example.administrator.spyware;

/**
 * Created by Administrator on 12/21/2015.
 */
public class Item {
    private String SmsContent;
    private  String SmsFrom;

    public Item(String smsContent, String smsFrom) {
        SmsContent = smsContent;
        SmsFrom = smsFrom;
    }

    public String getSmsContent() {
        return SmsContent;
    }

    public void setSmsContent(String smsContent) {
        SmsContent = smsContent;
    }

    public String getSmsFrom() {
        return SmsFrom;
    }

    public void setSmsFrom(String smsFrom) {
        SmsFrom = smsFrom;
    }
}
