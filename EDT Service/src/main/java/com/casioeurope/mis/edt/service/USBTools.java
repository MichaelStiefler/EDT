package com.casioeurope.mis.edt.service;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class USBTools {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static final String TAG = "EDT (USB Tools)";

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrentMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrentMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrentMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrentMethod + " " + sb.toString() + (entrance ? " +" : " -"));
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableAdb(Context context, boolean enable) {
        logMethodEntranceExit(true);
        try {
            return Settings.Global.putString(context.getContentResolver(), Settings.Global.ADB_ENABLED, enable ? "1" : "0");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean enableMassStorage(Context context, boolean enable) {
        logMethodEntranceExit(true);
        try {
            return Settings.Global.putString(context.getContentResolver(), Settings.Global.USB_MASS_STORAGE_ENABLED, enable ? "1" : "0");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }
}
