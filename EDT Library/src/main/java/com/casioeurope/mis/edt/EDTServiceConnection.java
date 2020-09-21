package com.casioeurope.mis.edt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.Arrays;

public class EDTServiceConnection implements ServiceConnection {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (EDTServiceConnection)";

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

    private IEDT edtService;
    private ISystemLibrary edtServiceSystemLibrary;
    private IKeyLibrary edtServiceKeyLibrary;
    private IScannerLibrary edtServiceScannerLibrary;

    public IEDT getEDTService() {
        return this.edtService;
    }
    public ISystemLibrary getSystemLibrary() {
        return this.edtServiceSystemLibrary;
    }
    public IKeyLibrary getKeyLibrary() {
        return this.edtServiceKeyLibrary;
    }
    public IScannerLibrary getScannerLibrary() {
        return this.edtServiceScannerLibrary;
    }

    @SuppressWarnings("UnusedReturnValue")
    protected boolean bind(Context context) {
        logMethodEntranceExit(true);
        context.bindService(new Intent("com.casioeurope.mis.edt.service.EDTService").setPackage("com.casioeurope.mis.edt.service")
                , this, Context.BIND_AUTO_CREATE);
        context.bindService(new Intent("com.casioeurope.mis.edt.service.SystemLibraryService").setPackage("com.casioeurope.mis.edt.service")
                , this, Context.BIND_AUTO_CREATE);
        context.bindService(new Intent("com.casioeurope.mis.edt.service.KeyLibraryService").setPackage("com.casioeurope.mis.edt.service")
                , this, Context.BIND_AUTO_CREATE);
        context.bindService(new Intent("com.casioeurope.mis.edt.service.ScannerLibraryService").setPackage("com.casioeurope.mis.edt.service")
                , this, Context.BIND_AUTO_CREATE);
        logMethodEntranceExit(false);
        return true;
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        try {
            logMethodEntranceExit(true, String.format("onServiceConnected(ComponentName package=%s class=%s, service interfaceDescriptor=%s)", name.getPackageName(), name.getClassName(), service.getInterfaceDescriptor()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            if (service.getInterfaceDescriptor().equals(IEDT.class.getName())) {
                edtService = IEDT.Stub.asInterface(service);
            } else if (service.getInterfaceDescriptor().equals(ISystemLibrary.class.getName())) {
                edtServiceSystemLibrary = ISystemLibrary.Stub.asInterface(service);
            } else if (service.getInterfaceDescriptor().equals(IKeyLibrary.class.getName())) {
                edtServiceKeyLibrary = IKeyLibrary.Stub.asInterface(service);
            } else if (service.getInterfaceDescriptor().equals(IScannerLibrary.class.getName())) {
                edtServiceScannerLibrary = IScannerLibrary.Stub.asInterface(service);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
