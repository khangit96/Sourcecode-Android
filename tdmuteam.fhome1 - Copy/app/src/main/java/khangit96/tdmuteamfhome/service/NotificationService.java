package khangit96.tdmuteamfhome.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import khangit96.tdmuteamfhome.R;
import khangit96.tdmuteamfhome.activity.MainActivity;

/**
 * Created by Administrator on 12/9/2016.
 */

public class NotificationService extends Service {
    private static int count = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("NhaTro");
        mDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (!MainActivity.activityState) {
                    showNotification("Nhà trọ " + dataSnapshot.child("tenChuHo").getValue().toString());
                }

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
        super.onStart(intent, startId);
    }

    /*
    * Show notification to user
    * */
    public void showNotification(String houseName) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification mNotification = new Notification.Builder(getApplicationContext())
                .setContentTitle("Có  nhà trọ mới!")
                .setContentText(houseName)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(0, mNotification);
    }
}
