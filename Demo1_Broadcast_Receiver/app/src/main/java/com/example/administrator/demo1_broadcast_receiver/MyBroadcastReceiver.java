package com.example.administrator.demo1_broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by Administrator on 12/15/2015.
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
    public static final String ACTION = "android.provider.Telephony.SMS_RECEIVED";
    @Override
    public void onReceive(Context context, Intent intent) {

        //processSMS(context,intent);
        if (intent.getAction().equals(ACTION)) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                Object[] pdus = (Object[]) bundle.get("pdus");
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }
                for (SmsMessage message : messages) {

                    String strMessageFrom = message.getDisplayOriginatingAddress();
                    String strMessageBody = message.getDisplayMessageBody();

                    Toast.makeText(context, "SMS Message received from:" + strMessageFrom, Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "SMS Message content" + strMessageBody, Toast.LENGTH_LONG).show();

                }
            }
        }
    }
    /*public void processSMS(Context context, Intent intent) {
        // lấy đối tượng bundle mình đã nói ở loạt bài về intent
        Bundle bundle = intent.getExtras();
        // Do hệ thống trả về một loạt các tin nhắn đến cùng lúc nên phải dùng mảng
        Object[] array = (Object[]) bundle.get("pdus"); // hành động "pdus" để lấy gói tin nhắn

        for (int i = 0; i < array.length; i++) {
            // lệnh này dùng được chuyển đổi Object về tin nhắn createFromPdu
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) array[i]);
            // lấy nội dung tin nhắn
            String msg = smsMessage.getMessageBody();
            // lấy số điện thoại tin nhắn
            String number = smsMessage.getDisplayOriginatingAddress();

            // Dùng Toast để hiển thị tin nhắn
//          Toast.makeText(context, number + "\n" + msg, Toast.LENGTH_LONG).show();

            // Hiển thị Tin nhắn lên một Dialog
             showDialog(context, intent, number, msg);
        }

        // Hiển thị tin nhắn cuối cùng trong số loạt tin nhắn nhận được lên Activity

    }
        public void showDialog(Context context, Intent intent, String number, String msg){

        // * vì MyBroadcastReceiver không kế thừa context (Activity) nên khi tạo intent mới không truyền
        // * this vào được, mà phải truyền cái context đã được gửi kèm

            // Gửi dữ liệu lên Activity mới
            Intent i = new Intent(context, MainActivity.class);
            Bundle bundle2 = new Bundle();
            bundle2.putString("PHONE", number);
            bundle2.putString("SMS", msg);
            i.putExtra("GOITIN", bundle2);
       //  Do đang làm việc trong BroadcastReceiver và một số vấn đề liên quan tới task
        // * trong Android nên phải thêm cờ FLAG_ACTIVITY_NEW_TASK

            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i); // cũng vì lý do trên nên phải dùng context để khởi động Activity

        }*/
}
