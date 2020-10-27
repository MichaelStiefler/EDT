package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.casioeurope.mis.edt.service.databinding.ActivityAddDeviceAdminBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class AddDeviceAdminActivity extends Activity {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static final String TAG = "EDT (AddDeviceAdminActivity)";

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

    public static final int REQUEST_ADD_DEVICE_ADMIN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logMethodEntranceExit(true);
        super.onCreate(savedInstanceState);
        this.doOnCreate();
        logMethodEntranceExit(false);
        this.finish();
    }

    private void doOnCreate() {
        logMethodEntranceExit(true);
        com.casioeurope.mis.edt.service.databinding.ActivityAddDeviceAdminBinding activityAddDeviceAdminBinding = ActivityAddDeviceAdminBinding.inflate(getLayoutInflater());
        View view = activityAddDeviceAdminBinding.getRoot();
        setContentView(view);
        DevicePolicyManager mDPM = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminName = new ComponentName(this, MyAdmin.class);

        if (!mDPM.isAdminActive(mAdminName)) {
            // try to become active – must happen here in this activity, to get result
            boolean silentActivationSuccess = false;
            try {
                @SuppressWarnings("JavaReflectionMemberAccess") @SuppressLint("DiscouragedPrivateApi") Method setActiveAdminMethod = DevicePolicyManager.class.getDeclaredMethod("setActiveAdmin", ComponentName.class, boolean.class);
                setActiveAdminMethod.invoke(mDPM, mAdminName, true);
                silentActivationSuccess = true;
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

            if (!silentActivationSuccess) { // Failed to silently activate device admin mode, fallback to user confirmation
                Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                        mAdminName);
                intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                        "EDT Tools needs device admin privileges for administrative functionality.");
                startActivityForResult(intent, REQUEST_ADD_DEVICE_ADMIN);
            }
        } else {
            // Already is a device administrator, can do security operations now.
            Log.v(TAG, "EDT is Device Admin already!");
        }
        logMethodEntranceExit(false);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        logMethodEntranceExit(true, "requestCode=" + requestCode + ", resultCode=" + resultCode);
        logMethodEntranceExit(false);
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class MyAdmin extends DeviceAdminReceiver {
        @SuppressWarnings({"unused", "RedundantSuppression"})
        private CharSequence doOnDisableRequested(Context context, Intent intent) {
            return context.getString(R.string.admin_receiver_status_disable_warning);
        }

        @SuppressWarnings({"NullableProblems", "RedundantSuppression"})
        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            return doOnDisableRequested(context, intent);
        }
    }
}