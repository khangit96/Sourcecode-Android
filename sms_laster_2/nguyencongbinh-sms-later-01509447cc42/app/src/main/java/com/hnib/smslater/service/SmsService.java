package com.hnib.smslater.service;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.util.Log;

import com.activeandroid.query.Select;
import com.hnib.smslater.R;
import com.hnib.smslater.activity.MainActivity;
import com.hnib.smslater.model.ContactPojo;
import com.hnib.smslater.model.SmsPojo;
import com.hnib.smslater.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by caucukien on 27/12/2015.
 */
public class SmsService extends Service {

    private String SMS_SENT = "SMS_SENT";
    private String SMS_DELIVERED = "SMS_DELIVERED";
    private SmsPojo currentSms;
    private int count = 0;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        final List<String> numbers = new ArrayList<>();
        if (intent != null && intent.getLongExtra(AppConstants.KEY_CURRENT_SMS, 0L) > 0) {
            long idSms = intent.getLongExtra(AppConstants.KEY_CURRENT_SMS, 0L);
            loadSmsDataBaseOnId(idSms);
            if (currentSms != null && currentSms.isSent() == false) {
                final int requestCode = (int) idSms;
                if (numbers.size() > 0) {
                    numbers.clear();
                }
                for (ContactPojo contact : currentSms.getContactPojos()) {
                    numbers.add(contact.mobileNumber);
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendSms(numbers, currentSms.content);

                    }
                }).start();

            }

        }
        stopService();

        return START_NOT_STICKY;
    }

    private void loadSmsDataBaseOnId(long id) {

        currentSms = new Select()
                .from(SmsPojo.class)
                .where("ID = ?", id)
                .executeSingle();


    }

    @Override
    public void onDestroy() {

        Log.d("binh", "SmsService onDestroy()");
        super.onDestroy();


    }

    private void sendSms(final List<String> numbers, final String smsBody) {

        for (final String phoneNumber : numbers) {
            // Get the default instance of SmsManager
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> smsBodyParts = smsManager.divideMessage(smsBody);
            int requestCode = new Random().nextInt(50);
            PendingIntent sentPendingIntent = PendingIntent.getBroadcast(this, requestCode, new Intent(SMS_SENT), 0);
            PendingIntent deliveredPendingIntent = PendingIntent.getBroadcast(this, requestCode, new Intent(SMS_DELIVERED), 0);
            ArrayList<PendingIntent> sentPendingIntents = new ArrayList<PendingIntent>();
            ArrayList<PendingIntent> deliveredPendingIntents = new ArrayList<PendingIntent>();

            for (int i = 0; i < smsBodyParts.size(); i++) {
                sentPendingIntents.add(sentPendingIntent);
                deliveredPendingIntents.add(deliveredPendingIntent);
            }

            IntentFilter sentFilter = new IntentFilter(SMS_SENT);
            BroadcastReceiver sentReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:

                            Log.d("binh", "Sent successfully");


                            break;
                        case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                            Log.d("binh", "Generic failure cause");
                            currentSms.status = -1;
                            currentSms.save();
                            //showNotification(getResources().getString(R.string.sent_failed));
                            sendBroadCastSmsStatus();
                            break;
                        case SmsManager.RESULT_ERROR_NO_SERVICE:
                            Log.d("binh", "Service is currently unavailable");
                            currentSms.status = -1;
                            currentSms.save();
                            //showNotification(getResources().getString(R.string.sent_failed));
                            sendBroadCastSmsStatus();
                            break;
                        case SmsManager.RESULT_ERROR_NULL_PDU:
                            Log.d("binh", "No pdu provided");
                            currentSms.status = -1;
                            currentSms.save();
                            //showNotification(getResources().getString(R.string.sent_failed));
                            sendBroadCastSmsStatus();
                            break;
                        case SmsManager.RESULT_ERROR_RADIO_OFF:
                            Log.d("binh", "Radio was explicitly turned off");
                            currentSms.status = -1;
                            currentSms.save();
                            //showNotification(getResources().getString(R.string.sent_failed));
                            sendBroadCastSmsStatus();

                            break;
                    }
                }
            };
            /////
            IntentFilter deliverFilter = new IntentFilter(SMS_DELIVERED);
            final BroadcastReceiver deliverReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Log.d("binh", "SMS delivered successfully");
                            count++;
                            if (count <= numbers.size()) {
                                saveToContentProvider(phoneNumber, smsBody);
                                showNotification(getResources().getString(R.string.sent_success));

                            }
                            currentSms.status = 1;
                            currentSms.save();
                            sendBroadCastSmsStatus();

                            break;
                        case Activity.RESULT_CANCELED:
                            Log.d("binh", "SMS  delivered failed");
                            //Toast.makeText(getBaseContext(), "SMS not delivered", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };


            registerReceiver(sentReceiver, sentFilter);
            registerReceiver(deliverReceiver, deliverFilter);


            smsManager.sendMultipartTextMessage(phoneNumber, null, smsBodyParts, sentPendingIntents, deliveredPendingIntents);



        }


    }

    private void sendBroadCastSmsStatus() {
        //send broadcast to list smsfragment and list sentfragment
        Intent callbackIntent = new Intent();
        callbackIntent.setAction(AppConstants.ACTION_SMS_SENT);
        sendBroadcast(callbackIntent);
    }

    private void saveToContentProvider(String phoneNumber, String smsBody) {
        ContentValues values = new ContentValues();
        values.put("address", phoneNumber);
        values.put("body", smsBody);
        SmsService.this.getContentResolver().insert(
                Uri.parse("content://sms/sent"), values); //store sent sms to content provider
    }

    private void stopService() {

        stopSelf();

    }

    protected void showNotification(String contentText) {

        // Invoking the default notification service
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);

        mBuilder.setContentTitle(contentText);
        mBuilder.setContentText(currentSms.content);
        mBuilder.setTicker("Notify from Sms Later");
        mBuilder.setSmallIcon(R.drawable.ic_notify);
        mBuilder.setAutoCancel(true);

        // Increase notification number every time a new notification arrives
        //mBuilder.setNumber(++numMessagesOne);

        // Creates an explicit intent for an Activity in your app
        MainActivity.tabPosition = 1;
        Intent resultIntent = new Intent(this, MainActivity.class);
        //resultIntent.putExtra("notificationId", notificationIdOne);

        //This ensures that navigating backward from the Activity leads out of the app to Home page
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        // Adds the back stack for the Intent
        stackBuilder.addParentStack(MainActivity.class);

        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        int requestCode = new Random().nextInt(50);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(requestCode, PendingIntent.FLAG_ONE_SHOT //can only be used once
        );
        // start the activity when the user clicks the notification text
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager myNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // pass the Notification object to the system
        myNotificationManager.notify(requestCode, mBuilder.build());
    }


}

