package com.casioeurope.mis.edt.service;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

import java.util.Arrays;

public class Camera {
    private static final String TAG = "EDT (Camera)";
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

    public static boolean enableCameras(boolean enable, Context context) {
        logMethodEntranceExit(true, String.format("enableCameras(%b, %s)", enable, context.getPackageName()));
        try {
            DevicePolicyManager manager =
                    (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName adminName = new ComponentName(context, AddDeviceAdminActivity.MyAdmin.class);
            manager.setCameraDisabled(adminName, !enable);
            logMethodEntranceExit(false, String.format("enableCameras(%b, %s) = true", enable, context.getPackageName()));
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in disableCamera():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("enableCameras(%b, %s) = false", enable, context.getPackageName()));
        return false;
    }
}
