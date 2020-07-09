package com.casioeurope.mis.edt;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import java.lang.reflect.Method;

public class Cellular {
    private static final int THREAD_WAIT_TIMEOUT_IN_MS = 100;
    private static final int RESULT_TIMEOUT_IN_MS = 10000;
    private static String TAG = "EDT_TOOLS (Cellular)";

    public static boolean enableBackgroundData(boolean enable, Context context) {
        boolean result = false;
        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            Class<?> connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());

            final Method getNetworkPolicyManager = connectivityManagerClass.getDeclaredMethod( "getNetworkPolicyManager");
            getNetworkPolicyManager.setAccessible(true);

            final Object iNetworkPolicyManager = getNetworkPolicyManager.invoke(connectivityManager);
            final Class<?> iNetworkPolicyManagerClass = Class.forName(iNetworkPolicyManager.getClass().getName());
            final Method setRestrictBackground = iNetworkPolicyManagerClass.getDeclaredMethod("setRestrictBackground", boolean.class);
            final Method getRestrictBackground = iNetworkPolicyManagerClass.getDeclaredMethod("getRestrictBackground");
            setRestrictBackground.setAccessible(true);
            getRestrictBackground.setAccessible(true);
            // Check if the state is already set
            result = (Boolean) getRestrictBackground.invoke(iNetworkPolicyManager);
            result = ((result && enable) || ((!result) && (!enable)));

            if (!result) {
                // Set state
                setRestrictBackground.invoke(iNetworkPolicyManager, enable);

                int timeout = RESULT_TIMEOUT_IN_MS;

                while (timeout > 0) {
                    // Check if the state is set
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
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in enableBackgroundData():");
            e.printStackTrace();
        }
        return false;
    }
}
