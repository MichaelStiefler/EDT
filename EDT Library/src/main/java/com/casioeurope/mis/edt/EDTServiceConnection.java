package com.casioeurope.mis.edt;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.casioeurope.mis.edt.type.LibraryCallback;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@SuppressWarnings("SpellCheckingInspection")
public class EDTServiceConnection implements ServiceConnection {

    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static final String TAG = "EDT (EDTServiceConnection)";

    @SuppressLint("LongLogTag")
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

    private static IEDT edtService;
    private static ISystemLibrary systemLibraryService;
    private static IKeyLibrary keyLibraryService;
    private static IScannerLibrary scannerLibraryService;
    private static ISamLibrary samLibraryService;
    private static IEeicLibrary eeicLibraryService;

    private final Queue<LibraryCallback> edtServiceCallbacks = new ConcurrentLinkedQueue<>();
    private final Queue<LibraryCallback> systemLibraryServiceCallbacks = new ConcurrentLinkedQueue<>();
    private final Queue<LibraryCallback> keyLibraryServiceCallbacks = new ConcurrentLinkedQueue<>();
    private final Queue<LibraryCallback> scannerLibraryServiceCallbacks = new ConcurrentLinkedQueue<>();
    private final Queue<LibraryCallback> samLibraryServiceCallbacks = new ConcurrentLinkedQueue<>();
    private final Queue<LibraryCallback> eeicLibraryServiceCallbacks = new ConcurrentLinkedQueue<>();

    void addEdtCallback(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        if (this.edtService == null) {
            this.edtServiceCallbacks.offer(callback);
        } else {
            callback.onLibraryReady();
        }
    }
    void addSystemLibraryCallback(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        if (this.systemLibraryService == null) {
            this.systemLibraryServiceCallbacks.offer(callback);
        } else {
            callback.onLibraryReady();
        }
    }
    void addKeyLibraryCallback(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        if (this.keyLibraryService == null) {
            this.keyLibraryServiceCallbacks.offer(callback);
        } else {
            callback.onLibraryReady();
        }
    }
    void addScannerLibraryCallback(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        if (this.scannerLibraryService == null) {
            this.scannerLibraryServiceCallbacks.offer(callback);
        } else {
            callback.onLibraryReady();
        }
    }
    void addSamLibraryCallback(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        if (this.samLibraryService == null) {
            this.samLibraryServiceCallbacks.offer(callback);
        } else {
            callback.onLibraryReady();
        }
    }
    void addEeicLibraryCallback(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        if (this.eeicLibraryService == null) {
            this.eeicLibraryServiceCallbacks.offer(callback);
        } else {
            callback.onLibraryReady();
        }
    }

    public static IEDT getEDTService() {
        return edtService;
    }
    public static ISystemLibrary getSystemLibrary() {
        return systemLibraryService;
    }
    public static IKeyLibrary getKeyLibrary() {
        return keyLibraryService;
    }
    public static IScannerLibrary getScannerLibrary() { return scannerLibraryService; }
    public static ISamLibrary getSamLibrary() {
        return samLibraryService;
    }
    public static IEeicLibrary getEeicLibrary() {
        return eeicLibraryService;
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
        context.bindService(new Intent("com.casioeurope.mis.edt.service.SamLibraryService").setPackage("com.casioeurope.mis.edt.service")
                , this, Context.BIND_AUTO_CREATE);
        context.bindService(new Intent("com.casioeurope.mis.edt.service.EeicLibraryService").setPackage("com.casioeurope.mis.edt.service")
                , this, Context.BIND_AUTO_CREATE);
        logMethodEntranceExit(false);
        return true;
    }

    @SuppressWarnings({"ConstantConditions", "SimplifyStreamApiCallChains", "RedundantSuppression"})
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        try {
            logMethodEntranceExit(true, String.format("onServiceConnected(ComponentName package=%s class=%s, service interfaceDescriptor=%s)", name.getPackageName(), name.getClassName(), service.getInterfaceDescriptor()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            if (service.getInterfaceDescriptor().equals(IEDT.class.getName())) {
                this.edtService = IEDT.Stub.asInterface(service);
                this.edtServiceCallbacks.stream().forEach(libraryCallback -> {
                    try {
                        libraryCallback.onLibraryReady();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else if (service.getInterfaceDescriptor().equals(ISystemLibrary.class.getName())) {
                this.systemLibraryService = ISystemLibrary.Stub.asInterface(service);
                this.systemLibraryServiceCallbacks.stream().forEach(libraryCallback -> {
                    try {
                        libraryCallback.onLibraryReady();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else if (service.getInterfaceDescriptor().equals(IKeyLibrary.class.getName())) {
                this.keyLibraryService = IKeyLibrary.Stub.asInterface(service);
                this.keyLibraryServiceCallbacks.stream().forEach(libraryCallback -> {
                    try {
                        libraryCallback.onLibraryReady();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else if (service.getInterfaceDescriptor().equals(IScannerLibrary.class.getName())) {
                this.scannerLibraryService = IScannerLibrary.Stub.asInterface(service);
                this.scannerLibraryServiceCallbacks.stream().forEach(libraryCallback -> {
                    try {
                        libraryCallback.onLibraryReady();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else if (service.getInterfaceDescriptor().equals(ISamLibrary.class.getName())) {
                this.samLibraryService = ISamLibrary.Stub.asInterface(service);
                this.samLibraryServiceCallbacks.stream().forEach(libraryCallback -> {
                    try {
                        libraryCallback.onLibraryReady();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
            } else if (service.getInterfaceDescriptor().equals(IEeicLibrary.class.getName())) {
                this.eeicLibraryService = IEeicLibrary.Stub.asInterface(service);
                this.eeicLibraryServiceCallbacks.stream().forEach(libraryCallback -> {
                    try {
                        libraryCallback.onLibraryReady();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
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
