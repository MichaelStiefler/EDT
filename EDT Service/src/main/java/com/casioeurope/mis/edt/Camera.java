package com.casioeurope.mis.edt;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

public class Camera {
    private static String TAG = "EDT_TOOLS (Camera)";

    public static boolean enableCameras(boolean enable, Context context) {
        try {
            DevicePolicyManager manager =
                    (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
            ComponentName adminName = new ComponentName(context, AddDeviceAdminActivity.MyAdmin.class);
            manager.setCameraDisabled(adminName, !enable);
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in disableCamera():");
            e.printStackTrace();
        }
        return false;
    }
}
