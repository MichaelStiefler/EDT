package com.casioeurope.mis.edt;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;

public class Security {
    private static String TAG = "EDT_TOOLS (Security)";

    public static boolean clearPassword(Context context) {
        Log.d(TAG, "clearPassword()");
        return resetPassword("", context);
    }

    public static boolean resetPassword(String newPassword, Context context) {
        Log.d(TAG, "passwordReset()");
        try {

            DevicePolicyManager manager =
                    (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            manager.resetPassword(newPassword, 0);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in passwordReset():");
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    public static boolean allowUnknownSources(boolean allow, Context context) {
        Log.d(TAG, "allowUnknownSources(" + allow + ")");
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
        return success;
    }

    @SuppressWarnings("deprecation")
    public static boolean enableDeveloperMode(boolean enable, Context context) {
        Log.d(TAG, "enableDeveloperMode(" + enable + ")");
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
        return success;
    }
}
