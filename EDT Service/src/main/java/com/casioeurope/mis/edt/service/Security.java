package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Security {
    private static String TAG = "EDT (Security)";
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

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

        Log.v(TAG, nameOfCurrentMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    public static boolean clearPassword(Context context) {
        logMethodEntranceExit(true, String.format("resetPassword(%s)", context.getPackageName()));
        logMethodEntranceExit(false, String.format("resetPassword(%s)", context.getPackageName()));
        return resetPassword("", context);
    }

    @SuppressWarnings({"deprecation", "RedundantSuppression"})
    public static boolean resetPassword(String newPassword, Context context) {
        logMethodEntranceExit(true, String.format("resetPassword(%s, %s)", newPassword, context.getPackageName()));
        try {

            DevicePolicyManager manager =
                    (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            manager.resetPassword(newPassword, 0);
            logMethodEntranceExit(false, String.format("resetPassword(%s, %s) = true", newPassword, context.getPackageName()));
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in passwordReset():");
            e.printStackTrace();
            logMethodEntranceExit(false, String.format("resetPassword(%s, %s)= false", newPassword, context.getPackageName()));
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean allowUnknownSources(boolean allow, Context context) {
        logMethodEntranceExit(true, String.format("allowUnknownSources(%b, %s)", allow, context.getPackageName()));
        String oldUnknownSourcesAllowed="";
        try {
            oldUnknownSourcesAllowed = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        try {
            if (oldUnknownSourcesAllowed.isEmpty()) {
                oldUnknownSourcesAllowed = Settings.Global.getString(context.getContentResolver(), Settings.Global.INSTALL_NON_MARKET_APPS);
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        boolean isUnknownSourcesAllowed = (oldUnknownSourcesAllowed.equals("1"));
        if (isUnknownSourcesAllowed == allow) return true;
        boolean success = false;
        try {
            success = Settings.Secure.putString(context.getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, allow?"1":"0");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        try {
            success |= Settings.Global.putString(context.getContentResolver(), Settings.Global.INSTALL_NON_MARKET_APPS, allow?"1":"0");
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false, String.format("allowUnknownSources(%b, %s) = %s", allow, context.getPackageName(), success));
        return success;
    }

    @SuppressWarnings("deprecation")
    public static boolean enableDeveloperMode(boolean enable, Context context) {
        logMethodEntranceExit(true, String.format("enableDeveloperMode(%b, %s)", enable, context.getPackageName()));
        int oldDeveloperModeEnabled=-1;
        try {
            oldDeveloperModeEnabled = Settings.Global.getInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        try {
            if (oldDeveloperModeEnabled < 1) oldDeveloperModeEnabled = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        boolean isDeveloperModeEnabled = oldDeveloperModeEnabled == 1;
        if (isDeveloperModeEnabled == enable) return true;
        boolean success = false;
        try {
            success = Settings.Global.putInt(context.getContentResolver(), Settings.Global.DEVELOPMENT_SETTINGS_ENABLED, enable?1:0);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        try {
            success |= Settings.Secure.putInt(context.getContentResolver(), Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED, enable?1:0);
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false, String.format("enableDeveloperMode(%b, %s) = %s", enable, context.getPackageName(), success));
        return success;
    }

    public static boolean enableDeviceAdmin(Context context, String packageName, String className, boolean makeAdmin) {
        logMethodEntranceExit(true, String.format("enableDeviceAdmin(%s, %s, %s, %b)", context.getPackageName(), packageName, className, makeAdmin));
        DevicePolicyManager mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminName = new ComponentName(packageName, className);
        try {
            @SuppressWarnings("JavaReflectionMemberAccess") @SuppressLint("DiscouragedPrivateApi") Method setActiveAdminMethod = DevicePolicyManager.class.getDeclaredMethod("setActiveAdmin", ComponentName.class, boolean.class);
            setActiveAdminMethod.invoke(mDPM, mAdminName, true);
            logMethodEntranceExit(false, String.format("enableDeviceAdmin(%s, %s, %s, %b) = true", context.getPackageName(), packageName, className, makeAdmin));
            return true;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("enableDeviceAdmin(%s, %s, %s, %b) = false", context.getPackageName(), packageName, className, makeAdmin));
        return false;
    }
}
