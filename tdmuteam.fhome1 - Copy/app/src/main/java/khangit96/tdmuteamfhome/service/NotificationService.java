package khangit96.tdmuteamfhome.service;

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
import com.google.firebase.database.ValueEventListener;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.activity.MainActivity;

/**
 * Created by Administrator on 12/9/2016.
 */

public class NotificationService extends Service {
    int count = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NhaTro");
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                count = (int) dataSnapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //    if (Integer.parseInt(dataSnapshot.getKey()) + 1 > count && count != 0) {
                if ((Boolean)dataSnapshot.child("verified").getValue() == true)
                    showNotification("Nhà trọ " + dataSnapshot.child("tenChuHo").getValue().toString());
                //   }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if ((Boolean)dataSnapshot.child("verified").getValue() == true)
                    showNotification("Nhà trọ " + dataSnapshot.child("tenChuHo").getValue().toString());
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
        super.onStart(intent, startId);
    }

    /*
    * Show notification to user
    * */
    public void showNotification(String houseName) {
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification mNotification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Có  nhà trọ mới!")
                .setContentText(houseName)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);
    }
}
