package tdmug3.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    Button btnShow, btnClear;
    NotificationManager manager;
    Notification myNotication;
    private NotificationCompat.Builder notBuilder;

    private static final int MY_NOTIFICATION_ID = 12345;

    private static final int MY_REQUEST_CODE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*Intent intent = new Intent(this, NotificationView.class);
        intent.putExtra("NotiClick",true);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            Notification Noti;
            Noti = new Notification.Builder(this)
                    .setContentTitle("YourTitle")
                    .setContentText("YourDescription")
                    .setSmallIcon(R.drawable.bottle)
                    .setContentIntent(pIntent)
                    .setAutoCancel(true).build();

            final NotificationManager notificationManager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            notificationManager.notify(0, Noti);*/
        initialise();
        btnShow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
        btnClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            cancelNotification(0);
            }
        });


    }

    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        //Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(MainActivity.this, NotificationView.class);
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 1, intent, 1);

        // this is it, we'll build the notification!
        // in the addAction method, if you don't want any icon, just set the first param to 0
        Notification mNotification = new Notification.Builder(this)

                .setContentTitle("New Post!")
                .setContentText("Here's an awesome update for you!")
                .setSmallIcon(R.drawable.bottle)
                .setContentIntent(pIntent)
             //   .setSound(soundUri)

               // .addAction(R.drawable.bottle, "View", pIntent)
                //.addAction(0, "Remind", pIntent)

                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // If you want to hide the notification after it was selected, do the code below
        // myNotification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager.notify(0, mNotification);
    }
    public void cancelNotification(int notificationId){

        if (Context.NOTIFICATION_SERVICE!=null) {
            String ns = Context.NOTIFICATION_SERVICE;
            NotificationManager nMgr = (NotificationManager) getApplicationContext().getSystemService(ns);
            nMgr.cancel(notificationId);
        }
    }


    private void initialise() {
        btnShow = (Button) findViewById(R.id.btnShowNotification);
        btnClear = (Button) findViewById(R.id.btnClearNotification);
    }
}