package tdmug3.demonotifiaction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;

public class MainActivity extends AppCompatActivity {
    private NotificationManager mNotificationManager;
    private int notificationID = 100;
    private int numMessages = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        showNotification();

    }
    public void showNotification(){

        // define sound URI, the sound to be played when there's a notification
        //Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        // intent triggered, you can add other intent for other actions
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 1, intent,0);

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

    protected void displayNotification() {
		/* Sử dụng dịch vụ */
        Notification.Builder mBuilder = new Notification.Builder(this);

        mBuilder.setContentTitle("Tin nhắn mới");
        mBuilder.setContentText("Có điểm thực hành môn Android.");
        mBuilder.setTicker("Thông báo!");
        mBuilder.setSmallIcon(R.drawable.bottle);

		/* tăng số thông báo */
        mBuilder.setNumber(++numMessages);

		/* Tạo đối tượng chỉ đến activity sẽ mở khi chọn thông báo */
        Intent resultIntent = new Intent(this, NotificationView.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);

        // kèm dữ liệu theo activity để xử lý
        resultIntent.putExtra("events",
                new String[] { "Có điểm thực hành môn Android" });
        resultIntent.putExtra("id", notificationID);

		/* Đăng ký activity được gọi khi chọn thông báo */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		/* cập nhật thông báo */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

    protected void cancelNotification() {
        mNotificationManager.cancel(notificationID);
    }

    protected void updateNotification() {
		/* Sử dụng dịch vu */
        Notification.Builder mBuilder = new Notification.Builder(this);

        mBuilder.setContentTitle("Cập nhật thông báo");
        mBuilder.setContentText("Có điểm thực hành môn Android-L2.");
        mBuilder.setTicker("Thông báo!");
        mBuilder.setSmallIcon(R.drawable.bottle);

		/* tăng số thông báo */
        mBuilder.setNumber(++numMessages);

		/* Tạo đối tượng chỉ đến activity sẽ mở khi chọn thông báo */
        Intent resultIntent = new Intent(this, NotificationView.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(NotificationView.class);

        // kèm dữ liệu theo activity để xử lý
        resultIntent.putExtra("events",
                new String[] { "Có điểm thực hành môn Android-L2" });

		/* Đăng ký activity được gọi khi chọn thông báo */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		/* cập nhật thông báo */
        mNotificationManager.notify(notificationID, mBuilder.build());
    }

    protected void displayNotificationBig() {
		/* Sử dụng dịch vụ thông báo */
        Notification.Builder mBuilder = new Notification.Builder(this);

        mBuilder.setContentTitle("Dánh sách sinh viên");
        mBuilder.setContentText("Danh sách sinh viên mới.");
        mBuilder.setTicker("Thông báo!");
        mBuilder.setSmallIcon(R.drawable.bottle);

		/* tăng số đếm thông báo */
        mBuilder.setNumber(++numMessages);

		/* HIển thị lớn */
        Notification.InboxStyle inboxStyle = new Notification.InboxStyle();

        String[] events = new String[6];
        events[0] = new String("Nguyễn Thanh Long");
        events[1] = new String("Trân Văn Cam");
        events[2] = new String("Lê Thị Lựu");
        events[3] = new String("Nguyễn Văn Mít");
        events[4] = new String("Ngô Văn Bắp");
        events[5] = new String("Trần Thị Bưởi");

        // Thêm vào danh sách
        inboxStyle.setBigContentTitle("Danh sách:");
        // Moves events into the big view
        for (int i = 0; i < events.length; i++) {

            inboxStyle.addLine(events[i]);
        }
        mBuilder.setStyle(inboxStyle);

		/* Tạo đối tượng chỉ đến activity sẽ mở khi chọn thông báo */
        Intent resultIntent = new Intent(this, MainActivity.class);
        // kèm dữ liệu theo activity để xử lý
        resultIntent.putExtra("events", events);
        resultIntent.putExtra("id", notificationID);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);

		/* Đăng ký activity được gọi khi chọn thông báo */
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		/* cập nhật thông báo */
        mNotificationManager.notify(notificationID, mBuilder.build());
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
