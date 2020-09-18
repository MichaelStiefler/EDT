package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class EDTService extends Service {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static String TAG = "EDT (EDTService)";

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

    private boolean isDeviceAdmin() {
        logMethodEntranceExit(true);
        //Context context = this.getApplicationContext();
        DevicePolicyManager dpm = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminName = new ComponentName(this, AddDeviceAdminActivity.MyAdmin.class);
        logMethodEntranceExit(false);
        return dpm.isAdminActive(adminName);
    }

//    public static class MyAdmin extends DeviceAdminReceiver {
//    }

    private boolean makeDeviceAdmin() {
        logMethodEntranceExit(true);
        DevicePolicyManager mDPM = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminName = new ComponentName(this, AddDeviceAdminActivity.MyAdmin.class);
        try {
            @SuppressWarnings("JavaReflectionMemberAccess") @SuppressLint("DiscouragedPrivateApi") Method setActiveAdminMethod = DevicePolicyManager.class.getDeclaredMethod("setActiveAdmin", ComponentName.class, boolean.class);
            setActiveAdminMethod.invoke(mDPM, mAdminName, true);
            logMethodEntranceExit(false, "makeDeviceAdmin() = true");
            return true;
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false, "makeDeviceAdmin() = false");
        return false;
    }

    private IBinder doOnBind(@SuppressWarnings("unused") Intent intent) {
        logMethodEntranceExit(true);
        if (!isDeviceAdmin()) {
            Log.v(TAG, "isDeviceAdmin() = false");
            if (!makeDeviceAdmin()) {
                Intent addDeviceAdminActivityIntent = new Intent(getApplicationContext(), AddDeviceAdminActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(addDeviceAdminActivityIntent);
            }
        } else {
            Log.v(TAG, "isDeviceAdmin() = true");
        }
        logMethodEntranceExit(false);
        return new EDTImpl(getApplicationContext());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        logMethodEntranceExit(true);
        logMethodEntranceExit(false);
        return doOnBind(intent);
    }

}
