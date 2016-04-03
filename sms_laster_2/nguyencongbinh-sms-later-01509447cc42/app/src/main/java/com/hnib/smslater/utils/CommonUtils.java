package com.hnib.smslater.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.hnib.smslater.application.AppApplication;
import com.hnib.smslater.model.ContactPojo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by admin on 12/18/15.
 */
public class CommonUtils {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean validateInputNumber(String text){
        boolean digitsOnly = TextUtils.isDigitsOnly(text);
        if(TextUtils.isEmpty(text) || !digitsOnly){
            return false;
        }
        return true;
    }

    public static int findIndexContactInList(List<ContactPojo> list, ContactPojo contact){
        int index = -1;
        for (int i = 0; i < list.size() ; i++) {
            ContactPojo contactPojo = list.get(i);
            if(contactPojo.mobileNumber.equals(contact.mobileNumber)){
                index = i;
            }
        }
        return index;
    }

    public static boolean isNetWorkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) AppApplication.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String getDayBeforeNumberDays(String currentDay, int number) {
        String resultDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date currentDate = sdf.parse(currentDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, -number);
            Date resultDate = calendar.getTime();


            resultDay = sdf.format(resultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultDay;

    }
    public static String getDayAfterNumberDays(String currentDay, int number) {
        String resultDay = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date currentDate = sdf.parse(currentDay);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.DAY_OF_YEAR, +number);
            Date resultDate = calendar.getTime();


            resultDay = sdf.format(resultDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return resultDay;

    }

    public static String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String currentDateAndTime = sdf.format(new Date());
        return currentDateAndTime;
    }


    public static int getDayBetweenTwoDate(String date1, String date2) {
        int result = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date da1 = sdf.parse(date1);
            Date da2 = sdf.parse(date2);
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(da1);
            cal1.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            cal1.set(Calendar.MINUTE, 0);                 // set minute in hour
            cal1.set(Calendar.SECOND, 0);                 // set second in minute
            cal1.set(Calendar.MILLISECOND, 0);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(da2);
            cal2.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
            cal2.set(Calendar.MINUTE, 0);                 // set minute in hour
            cal2.set(Calendar.SECOND, 0);                 // set second in minute
            cal2.set(Calendar.MILLISECOND, 0);


            while (cal1.before(cal2)) {
                cal1.add(Calendar.DAY_OF_MONTH, 1);
                result++;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static int compareTwoDate(String date1, String date2) {
        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date da1 = sdf.parse(date1);
            Date da2 = sdf.parse(date2);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(da1);
            cal2.setTime(da2);


            if (cal1.before(cal2)) {
                result = -1;
            } else if(cal1.after(cal2)){
                result = 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

    }
    public static int compareTwoTime(String time1, String time2) {
        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try {
            Date da1 = sdf.parse(time1);
            Date da2 = sdf.parse(time2);
            Calendar cal1 = Calendar.getInstance();
            Calendar cal2 = Calendar.getInstance();
            cal1.setTime(da1);
            cal2.setTime(da2);


            if (cal1.before(cal2)) {
                result = -1;
            } else {
                result = 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;

    }

    public static String convertToCurrentGMT(String time){
        String gmtTime = "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            Date myDate = simpleDateFormat.parse(time);
            simpleDateFormat.setTimeZone(TimeZone.getDefault());
            gmtTime = simpleDateFormat.format(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return gmtTime;
    }

    public static String getDayOfWeek(String input_date) {
        SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
        Date dt1 = null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2 = new SimpleDateFormat("EE");
        String dayOfWeek = format2.format(dt1);
        return dayOfWeek;

    }

    public static String getContactNameFromNumber(String number, Context context) {
        String name = "";
        Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number));
        Cursor cursor = context.getContentResolver().query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
        }


        return name;

    }

    public static String getDensityName(Context context) {
        float density = context.getResources().getDisplayMetrics().density;
        if (density >= 4.0) {
            return "xxxhdpi";
        }
        if (density >= 3.0) {
            return "xxhdpi";
        }
        if (density >= 2.0) {
            return "xhdpi";
        }
        if (density >= 1.5) {
            return "hdpi";
        }
        if (density >= 1.0) {
            return "mdpi";
        }
        return "ldpi";
    }
    public static int[] getScreenSize(Context context){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int[] screen = {width, height};
        return screen;
    }
    public static String getCatetoryScreenSize(Context context){
        String screen = "";
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE) {
            screen = "X-LARGE";
        }
        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            screen = "LARGE";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            screen = "NORMAL";
        }
        else if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_SMALL) {
            screen = "SMALL";
        }
        else {
            screen = "NORMAL or SMALL";
        }
        return screen;
    }
    public static String getDeviceId(Context context){
        final TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceid = tm.getDeviceId();
        return deviceid;
    }


}
