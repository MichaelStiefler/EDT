package com.casioeurope.mis.edt.service;

import android.accounts.Account;
import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;

import java.util.Arrays;

public class PowerManager {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static String TAG = "EDT (PowerManager)";

    public static final String ACTION_REQUEST_SHUTDOWN_7 = "com.android.intent.action.REQUEST_SHUTDOWN";
    public static final String ACTION_REQUEST_SHUTDOWN_8 = "com.android.internal.intent.action.REQUEST_SHUTDOWN";

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

//    public static String getApplicationName(Context context) {
//        logMethodEntranceExit(true);
//        ApplicationInfo applicationInfo = context.getApplicationInfo();
//        int stringId = applicationInfo.labelRes;
//        logMethodEntranceExit(false);
//        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
//    }

    @SuppressLint({"WrongConstant", "ObsoleteSdkInt"})
    public static boolean shutdown(Context context) {
        logMethodEntranceExit(true);
        try {
            Intent intent = new Intent();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
                intent.setAction(ACTION_REQUEST_SHUTDOWN_8);
            else
                intent.setAction(ACTION_REQUEST_SHUTDOWN_7);
            intent.setFlags(0x10000000); //device.common.MetaKeyConst.META_LOCK_ON --> from CASIOAndroidAddons.java
            context.startActivity(intent);
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in shutdown():");
            e.printStackTrace();
            logMethodEntranceExit(false);
            return false;
        }
    }

    public static boolean reboot(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "reboot()");
        try {
            android.os.PowerManager pm = (android.os.PowerManager) context.getSystemService(Context.POWER_SERVICE);
            assert pm != null;
            pm.reboot(null);
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in reboot():");
            e.printStackTrace();
            logMethodEntranceExit(false);
            return false;
        }
    }

    public static boolean recovery(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "recovery()");
        try {
            android.os.PowerManager pm = (android.os.PowerManager) context.getSystemService(Context.POWER_SERVICE);
            assert pm != null;
            pm.reboot("recovery");
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in recovery():");
            e.printStackTrace();
            logMethodEntranceExit(false);
            return false;
        }
    }

    public static boolean lockDevice(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "lockDevice()");
        try {

            DevicePolicyManager manager =
                    (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            assert manager != null;
            manager.lockNow();
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in lockDevice():");
            e.printStackTrace();
            logMethodEntranceExit(false);
            return false;
        }
    }

    public static boolean factoryReset(Context context, boolean removeAccounts) {
        logMethodEntranceExit(true);
        Log.d(TAG, String.format("factoryReset(%b)", removeAccounts));
        if (removeAccounts) {
            AccManager.removeAllGoogleAccounts(context);
            Account[] accounts = AccManager.getGoogleAccounts(context);
            if (accounts == null) {
                Log.e(TAG, "factoryReset(true) error: Calling getGoogleAccounts() failed!");
                logMethodEntranceExit(false);
                return false;
            }
            if (accounts.length != 0) {
                Log.e(TAG, String.format("factoryReset(true) error: %d Google accounts remained after calling removeAllGoogleAccounts!", accounts.length));
                logMethodEntranceExit(false);
                return false;
            }
        }
        try {
            DevicePolicyManager manager =
                    (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            assert manager != null;
            manager.wipeData(0);
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in factoryReset():");
            e.printStackTrace();
            logMethodEntranceExit(false);
            return false;
        }
    }

    public static boolean setScreenLockTimeout(Context context, int milliseconds) {
        try {
            int currentTimeout = Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
            if (currentTimeout == milliseconds) return true;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminName = new ComponentName(context, AddDeviceAdminActivity.MyAdmin.class);
        long maximumTimeToLock = mDPM.getMaximumTimeToLock(mAdminName);
        if (maximumTimeToLock != 0 && maximumTimeToLock < milliseconds) {
            mDPM.setMaximumTimeToLock(mAdminName, milliseconds);
        }

        return Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, milliseconds);
    }
}
