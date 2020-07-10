package com.casioeurope.mis.edt;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.util.Log;

import java.util.Arrays;

public class PowerManager {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT_TOOLS (PowerManager)";
    private static int shutdownVersion = 0;

    public static final String ACTION_REQUEST_SHUTDOWN_7 = "com.android.intent.action.REQUEST_SHUTDOWN";
    public static final String ACTION_REQUEST_SHUTDOWN_8 = "com.android.internal.intent.action.REQUEST_SHUTDOWN";

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    public static String getApplicationName(Context context) {
        logMethodEntranceExit(true);
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        logMethodEntranceExit(false);
        return stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : context.getString(stringId);
    }

    @SuppressLint("WrongConstant")
    public static boolean shutdown(Context context) {
        logMethodEntranceExit(true);
        if (++shutdownVersion > 3) shutdownVersion = 1;
        Log.d(TAG, String.format("shutdown(%s), attempting version %d...", getApplicationName(context), shutdownVersion));
        switch(shutdownVersion) {
            case 1: {
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
            case 2: {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.ACTION_SHUTDOWN");
                    intent.setFlags(0x10000000); //device.common.MetaKeyConst.META_LOCK_ON --> from CASIOAndroidAddons.java
                    context.sendBroadcast(intent);
                    logMethodEntranceExit(false);
                    return true;
                } catch (Exception e) {
                    Log.d(TAG, "Error in shutdown():");
                    e.printStackTrace();
                    logMethodEntranceExit(false);
                    return false;
                }
            }
            case 3:
            default: {
                try {
                    Intent intent = new Intent();
                    intent.setAction("casio.intent.action.SHUTDOWN");
                    context.sendBroadcast(intent);
                    logMethodEntranceExit(false);
                    return true;
                } catch (Exception e) {
                    Log.d(TAG, "Error in shutdown():");
                    e.printStackTrace();
                    logMethodEntranceExit(false);
                    return false;
                }
            }
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

    public static boolean factoryReset(Context context) {
        logMethodEntranceExit(true);
        Log.d(TAG, "factoryReset()");
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
}
