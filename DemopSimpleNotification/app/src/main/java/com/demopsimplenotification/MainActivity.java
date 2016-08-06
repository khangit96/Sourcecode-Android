package com.demopsimplenotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    NotificationManager notificationManager;
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        findViewById(R.id.btSimple).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notification notification = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("New message")
                        .setContentText("You got a new message from Khangit")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .build();
                notificationManager.notify(1, notification);
            }
        });
        findViewById(R.id.btCounter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Notification notification=new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle(count+"New message")
                        .setContentText("You got a new message from Khangit")
                         .setNumber(count)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .build();
                notificationManager.notify(2,notification);
            }
        });
        findViewById(R.id.btOpen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(getBaseContext(),DetailActivity.class);
                mainIntent.putExtra("messages","Hi, i am Khangit");
                PendingIntent pendingIntentMain=PendingIntent.getActivity(getBaseContext(), 0, mainIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("New Message")
                        .setContentText("You got a new message from Khangit")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentIntent(pendingIntentMain)
                        .build();
                notificationManager.notify(3,notification);
            }
        });
        findViewById(R.id.btOpenActivityWithBackstack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent=new Intent(getBaseContext(),DetailActivity.class);
                TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(getBaseContext());
                taskStackBuilder.addParentStack(DetailActivity.class);
                taskStackBuilder.addNextIntent(mainIntent);
                mainIntent.putExtra("messages","Hi, i am Khangit");
                PendingIntent pendingDetail=taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("New Message")
                        .setContentText("You got a new message from Khangit")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setContentIntent(pendingDetail)
                        .build();
                notificationManager.notify(4,notification);
            }
        });
        findViewById(R.id.btAction).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent replyIntent=new Intent(getBaseContext(),ReplyActivity.class);
                replyIntent.putExtra("reply_to","khangit");
                Intent deleteIntent=new Intent();
                deleteIntent.setAction("delete_message");
                PendingIntent pendingDelete=PendingIntent.getBroadcast(getBaseContext(),0,deleteIntent,PendingIntent.FLAG_UPDATE_CURRENT);
                PendingIntent pendingReply=PendingIntent.getActivity(getBaseContext(), 0, replyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("New Message")
                        .setContentText("You got a new message from Khangit")
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .addAction(R.mipmap.ic_launcher,"Reply",pendingReply)
                        .addAction(R.mipmap.ic_launcher, "Delete", pendingDelete)
                        .build();
                notificationManager.notify(7,notification);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
