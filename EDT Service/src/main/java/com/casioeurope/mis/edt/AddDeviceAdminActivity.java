package com.casioeurope.mis.edt;

import android.app.Activity;
import android.app.admin.DeviceAdminReceiver;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.casioeurope.mis.edt.R;
import com.casioeurope.mis.edt.databinding.ActivityAddDeviceAdminBinding;

import java.util.Arrays;

public class AddDeviceAdminActivity extends /*AppCompatActivity*/ Activity {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;
    private static String TAG = "EDT_TOOLS (AddDeviceAdminActivity)";

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
    private Context mContext;
    private DevicePolicyManager mDPM;
    private ComponentName mAdminName;
    public static final int REQUEST_ADD_DEVICE_ADMIN = 1;
    private ActivityAddDeviceAdminBinding activityAddDeviceAdminBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        logMethodEntranceExit(true);
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate 01");
        activityAddDeviceAdminBinding = ActivityAddDeviceAdminBinding.inflate(getLayoutInflater());
        Log.v(TAG, "onCreate 01b");
        View view = activityAddDeviceAdminBinding.getRoot();
        Log.v(TAG, "onCreate 01c");
        setContentView(view);
        //setContentView(R.layout.activity_add_device_admin);
        Log.v(TAG, "onCreate 02");
        mContext = this;
        Log.v(TAG, "onCreate 03");
        mDPM = (DevicePolicyManager) this.getSystemService(Context.DEVICE_POLICY_SERVICE);
        Log.v(TAG, "onCreate 04");
        mAdminName = new ComponentName(this, MyAdmin.class);
        Log.v(TAG, "onCreate 05");
        if (!mDPM.isAdminActive(mAdminName)) {
            // try to become active – must happen here in this activity, to get result
            Log.v(TAG, "onCreate 06");
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            Log.v(TAG, "onCreate 07");
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
                    mAdminName);
            Log.v(TAG, "onCreate 08");
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                    "EDT Tools needs device admin privileges for administrative functionality.");
            Log.v(TAG, "onCreate 09");
            startActivityForResult(intent, REQUEST_ADD_DEVICE_ADMIN);
            Log.v(TAG, "onCreate 10");
        } else {
            // Already is a device administrator, can do security operations now.
            // mDPM.lockNow();
            Log.v(TAG, "onCreate 11");
        }

        // Note: The application should check  the result of the ACTION_ADD_DEVICE_ADMIN. Add below code lines in the onActivityResult() method:
        Log.v(TAG, "onCreate 12");
        logMethodEntranceExit(false);
        this.finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        logMethodEntranceExit(true, "requestCode=" + requestCode + ", resultCode=" + resultCode);
        Log.v(TAG, "onActivityResult 01");
        if (REQUEST_ADD_DEVICE_ADMIN == requestCode) {
            if (resultCode == Activity.RESULT_OK) {
                Log.v(TAG, "onActivityResult 02");
                // Has become the device administrator.
            } else {
                Log.v(TAG, "onActivityResult 03");
                //Canceled or failed.
            }
        }
        Log.v(TAG, "onActivityResult 04");
        logMethodEntranceExit(false);
        Log.v(TAG, "onActivityResult 05");
        Log.v(TAG, "onActivityResult 06");
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static class MyAdmin extends DeviceAdminReceiver {
        void showToast(Context context, String msg) {
            String status = context.getString(R.string.admin_receiver_status, msg);
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onEnabled(Context context, Intent intent) {
            showToast(context, context.getString(R.string.admin_receiver_status_enabled));
        }

        @Override
        public CharSequence onDisableRequested(Context context, Intent intent) {
            return context.getString(R.string.admin_receiver_status_disable_warning);
        }

        @Override
        public void onDisabled(Context context, Intent intent) {
            showToast(context, context.getString(R.string.admin_receiver_status_disabled));
        }

        @Override
        public void onPasswordChanged(Context context, Intent intent, UserHandle userHandle) {
            showToast(context, context.getString(R.string.admin_receiver_status_pw_changed));
        }
    }
}