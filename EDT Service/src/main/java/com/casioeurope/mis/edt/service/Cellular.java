package com.casioeurope.mis.edt.service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

public class Cellular {
    private static final int THREAD_WAIT_TIMEOUT_IN_MS = 100;
    private static final int RESULT_TIMEOUT_IN_MS = 10000;
    private static String TAG = "EDT (Cellular)";
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

        Log.v(TAG, nameOfCurrentMethod + " " + sb.toString() + (entrance ? " +" : " -"));
    }

    public static boolean enableBackgroundData(boolean enable, Context context) {
        logMethodEntranceExit(true, String.format("enableBackgroundData(%b, %s)", enable, context.getPackageName()));
        boolean result;
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class<?> connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());

            final Method getNetworkPolicyManager = connectivityManagerClass.getDeclaredMethod("getNetworkPolicyManager");
            getNetworkPolicyManager.setAccessible(true);

            final Object iNetworkPolicyManager = getNetworkPolicyManager.invoke(connectivityManager);
            final Class<?> iNetworkPolicyManagerClass = Class.forName(Objects.requireNonNull(iNetworkPolicyManager).getClass().getName());
            final Method setRestrictBackground = iNetworkPolicyManagerClass.getDeclaredMethod("setRestrictBackground", boolean.class);
            final Method getRestrictBackground = iNetworkPolicyManagerClass.getDeclaredMethod("getRestrictBackground");
            setRestrictBackground.setAccessible(true);
            getRestrictBackground.setAccessible(true);
            // Check if the state is already set
            //noinspection ConstantConditions
            result = (Boolean) getRestrictBackground.invoke(iNetworkPolicyManager);
            result = ((result && enable) || ((!result) && (!enable)));

            if (!result) {
                // Set state
                setRestrictBackground.invoke(iNetworkPolicyManager, enable);

                int timeout = RESULT_TIMEOUT_IN_MS;

                while (timeout > 0) {
                    // Check if the state is set
                    //noinspection ConstantConditions
                    result = (Boolean) getRestrictBackground.invoke(iNetworkPolicyManager);
                    result = ((result && enable) || ((!result) && (!enable)));
                    if (result) {
                        break;
                    }
                    try {
                        Thread.sleep(THREAD_WAIT_TIMEOUT_IN_MS);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "InterruptedException in enableBackgroundData():");
                        e.printStackTrace();
                    }
                    timeout -= THREAD_WAIT_TIMEOUT_IN_MS;
                }
            }
            logMethodEntranceExit(false, String.format("enableBackgroundData(%b, %s) = true", enable, context.getPackageName()));
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in enableBackgroundData():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("enableBackgroundData(%b, %s) = false", enable, context.getPackageName()));
        return false;
    }
}
