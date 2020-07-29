package com.casioeurope.mis.edt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.Arrays;

public class EDTServiceConnection implements ServiceConnection {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (ServiceConnection)";

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

    private IEDT edtService;

    protected IEDT getEDTService() {
        return this.edtService;
    }

    protected boolean bind(Context context) {
        logMethodEntranceExit(true);
        context.bindService(new Intent("com.casioeurope.mis.edt.service.EDTService").setPackage("com.casioeurope.mis.edt.service")
                ,this, Context.BIND_AUTO_CREATE);
        logMethodEntranceExit(false);
        return true;
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        logMethodEntranceExit(true);
        edtService = IEDT.Stub.asInterface(service);
        logMethodEntranceExit(false);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        logMethodEntranceExit(true);
        logMethodEntranceExit(false);
    }

    private static EDTServiceConnection instance;

    protected static EDTServiceConnection getInstance() {
        if (EDTServiceConnection.instance == null) {
            EDTServiceConnection.instance = new EDTServiceConnection();
        }
        return EDTServiceConnection.instance;
    }

}
