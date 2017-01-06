package khangit96.quanlycaphe.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import khangit96.quanlycaphe.R;
import khangit96.quanlycaphe.activity.MainActivity;
import khangit96.quanlycaphe.activity.ManageActivity;
import khangit96.quanlycaphe.model.Config;

/**
 * Created by Administrator on 12/21/2016.
 */

public class NotiService extends Service {
    ArrayList<Integer> countNotiList = new ArrayList<>();

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        for (int j = 1; j <= MainActivity.tableList.size(); j++) {
            countNotiList.add(0);
        }
        for (int i = 1; i <= MainActivity.tableList.size(); i++) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child(Config.COMPANY_KEY + "/Order/Table " + i);
            final int ii = i;
            mDatabase.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    int value = countNotiList.get(ii - 1);
                    value++;

                    countNotiList.set(ii - 1, value);
                    showNotification("Bàn " + ii, ii, value);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        super.onStart(intent, startId);
    }

    public void showNotification(String content, int notiID, int countMessage) {

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), ManageActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification mNotification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Có order mới!")
                .setContentText(content)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setNumber(countMessage)
                .setSound(alarmSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(notiID, mNotification);
    }
}
