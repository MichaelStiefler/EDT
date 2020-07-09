package com.casioeurope.mis.edt;

import android.app.Service;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class EDTService extends Service {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT_TOOLS (Service)";

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameofCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameofCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameofCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameofCurrMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    private boolean isDeviceAdmin() {
        logMethodEntranceExit(true);
        //Context context = this.getApplicationContext();
        DevicePolicyManager dpm = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName adminName = new ComponentName(this, MyAdmin.class);
        logMethodEntranceExit(false);
        return dpm.isAdminActive(adminName);
    }

    public static class MyAdmin extends DeviceAdminReceiver {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        logMethodEntranceExit(true);
        if (!isDeviceAdmin()) {
            Log.v(TAG, "isDeviceAdmin() = false");
            Intent addDeviceAdminActivityIntent = new Intent(getApplicationContext(), AddDeviceAdminActivity.class);
            startActivity(addDeviceAdminActivityIntent);
        } else {
            Log.v(TAG, "isDeviceAdmin() = true");
        }
        logMethodEntranceExit(false);
        return new EDTImpl(getApplicationContext());
    }
}
