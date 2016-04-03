package com.hnib.smslater.utils;

/**
 * Created by admin on 12/18/15.
 */
public class AppConstants {

    public static final String UNKNOWN = "unknown";
    public static final String KEY_CURRENT_SMS = "current_sms";

    public static final String ACTION_SMS_SENT = "action_sent";
    public static final String SMS_PENDING = "SMS PENDING";
    public static final String SMS_SENT = "SMS SENT";

    /**
     * Enable disable admob in pending screen
     */
    public static boolean isAdmobEnabledPendingSmsScreen = false;

    /**
     * Enable disable admob in compose screen
     */
    public static boolean isAdmobEnabledComposeScreen = true;

    /**
     * Enable disable admob in sent screen
     */
    public static boolean isAdmobEnabledSentSmsScreen = false;
}
