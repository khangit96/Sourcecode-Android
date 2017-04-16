package com.smartgardening.Service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smartgardening.Activity.MainActivity;
import com.smartgardening.Model.Config;
import com.smartgardening.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 3/24/2017.
 */

public class CheckErrorService extends Service {
    Timer timer1, timer2, timer3;
    int countD1, countD2, countD3;
    int coun1 = 0, count2 = 0, count3 = 0;
    boolean check1 = true, check2 = true, check3 = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        DatabaseReference mData1 = FirebaseDatabase.getInstance().getReference().child("System/1/count");

        mData1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coun1++;
                if (coun1 > 1) {
                    Config.STATUS1 = true;
                    stopTimer1();
                    startTimer1(1000);
                    if (!check1) {
                        showNotification("Hệ thống 1 đang hoạt động", 1);
                        check1 = true;
                    }
                } else {
                    startTimer1(1000);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        DatabaseReference mData2 = FirebaseDatabase.getInstance().getReference().child("System/2/count");

        mData2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count2++;

                if (count2 > 1) {
                    Config.STATUS2 = true;
                    stopTimer2();
                    if (!check2) {
                        showNotification("Hệ thống 2 đang hoạt động", 1);
                        check2 = true;
                    }
                    startTimer2(1000);
                } else {
                    startTimer2(1000);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference mData3 = FirebaseDatabase.getInstance().getReference().child("System/3/count");

        mData3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count3++;
                if (count3 > 1) {
                    Config.STATUS3 = true;
                    stopTimer3();
                    if (!check3) {
                        showNotification("Hệ thống 3 đang hoạt động", 1);
                        check3 = true;
                    }
                    startTimer3(1000);
                } else {
                    startTimer3(1000);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    /*Handler1*/
    public Handler mHandler1 = new Handler() {
        public void handleMessage(Message msg) {
            showNotification("Hệ thống 1 đã dừng hoạt động!", 1);
            Config.STATUS1 = false;
            check1 = false;
            Toast.makeText(getApplicationContext(), "Hệ thống 1 đã dừng hoạt động!", Toast.LENGTH_LONG).show();
        }
    };
    /*Handler1*/
    public Handler mHandler2 = new Handler() {
        public void handleMessage(Message msg) {
            showNotification("Hệ thống 2 đã dừng hoạt động!", 2);
            Config.STATUS2 = false;
            check2 = false;
            Toast.makeText(getApplicationContext(), "Hệ thống 2 đã dừng hoạt động!", Toast.LENGTH_LONG).show();
        }
    };

    /*Handler1*/
    public Handler mHandler3 = new Handler() {
        public void handleMessage(Message msg) {
            Config.STATUS3 = false;
            check3 = false;
            showNotification("Hệ thống 3 đã dừng hoạt động!", 3);
            Toast.makeText(getApplicationContext(), "Hệ thống 3 đã dừng hoạt động", Toast.LENGTH_LONG).show();
        }
    };

    /*Start timer*/
    public void startTimer1(int Speed) {
        countD1 = 0;
        timer1 = new Timer();
        timer1.schedule(new TimerTask() {
            @Override
            public void run() {
                countD1++;
                if (countD1 == 45) {
                    mHandler1.obtainMessage(1).sendToTarget();
                }


            }
        }, 1, Speed);//speed coundown time

    }

    /*Stop timer*/
    public void stopTimer1() {
        timer1.cancel();
    }

    /*Start timer*/
    public void startTimer2(int Speed) {
        countD2 = 0;
        timer2 = new Timer();
        timer2.schedule(new TimerTask() {
            @Override
            public void run() {
                countD2++;
                if (countD2 == 45) {
                    mHandler2.obtainMessage(1).sendToTarget();
                }


            }
        }, 1, Speed);//speed coundown time

    }

    /*Stop timer*/
    public void stopTimer2() {
        timer2.cancel();
    }

    /*Start timer*/
    public void startTimer3(int Speed) {
        countD3 = 0;
        timer3 = new Timer();
        timer3.schedule(new TimerTask() {
            @Override
            public void run() {
                countD3++;
                if (countD3 == 45) {
                    mHandler3.obtainMessage(1).sendToTarget();
                }


            }
        }, 1, Speed);//speed coundown time

    }

    /*Stop timer*/
    public void stopTimer3() {
        timer3.cancel();
    }

    /*
   * Show notification to user
   * */
    public void showNotification(String content, int id) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification mNotification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Thông báo")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(id, mNotification);
    }
}
