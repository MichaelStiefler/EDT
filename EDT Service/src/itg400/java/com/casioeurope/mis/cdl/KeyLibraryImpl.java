package com.casioeurope.mis.cdl;

import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static volatile jp.casio.ht.devicelibrary.KeyLibrary jpInstance;

    private static final List<Integer> keyCodesItG400 = new ArrayList<>(
            Arrays.asList(278, 277)
    );

    private static final List<Integer> keyCodesItG600Dtx400 = new ArrayList<>(
            Arrays.asList(1012, 1011)
    );

    private static jp.casio.ht.devicelibrary.KeyLibrary getJpInstance() {
        if (jpInstance == null) {
            synchronized (KeyLibraryImpl.class) {
                if (jpInstance == null) {
                    jpInstance = new jp.casio.ht.devicelibrary.KeyLibrary();
                }
            }
        }
        return jpInstance;
    }

    private KeyLibraryImpl() {
    }

    private jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo(ApplicationInfo theAppInfo) {
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo = new jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo();
        appInfo.packageName = theAppInfo.packageName;
        appInfo.activityName = theAppInfo.activityName;
        return appInfo;
    }
	
    private static int setUserKeyCode(int nID, int KeyCode) throws RemoteException {
        int itG600Dtx400KeyCodeIndex;
        if ((itG600Dtx400KeyCodeIndex = keyCodesItG600Dtx400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesItG400.get(itG600Dtx400KeyCodeIndex);
        }
        return getJpInstance().setUserKeyCode(nID, KeyCode);
    }

    public static int getUserKeyCode(int nID) throws RemoteException {
        return getJpInstance().getUserKeyCode(nID);
    }

    public static int setDefaultKeyCode(int nID) throws RemoteException {
        return getJpInstance().setDefaultKeyCode(nID);
    }

    private static int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException {
        throw new RemoteException("int setFnUserKeyCode(int nID, int KeyCode) not supported on IT-G400 devices!");
    }

    public static int getFnUserKeyCode(int nID) throws RemoteException {
        throw new RemoteException("int getFnUserKeyCode(int nID) not supported on IT-G400 devices!");
    }

    public static int setFnDefaultKeyCode(int nID) throws RemoteException {
        throw new RemoteException("int setFnDefaultKeyCode(int nID) not supported on IT-G400 devices!");
    }

    public int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().setLaunchApplication(nID, appInfo(appInfo));
    }

    public int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().getLaunchApplication(nID, appInfo(appInfo));
    }

    public static int clearLaunchApplication(int nID) throws RemoteException {
        return getJpInstance().clearLaunchApplication(nID);
    }

    public static int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        throw new RemoteException("int setFnLaunchApplication(int nID, ApplicationInfo appInfo) not supported on IT-G400 devices!");
    }

    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        throw new RemoteException("int getFnLaunchApplication(int nID, ApplicationInfo appInfo) not supported on IT-G400 devices!");
    }
    public static int clearFnLaunchApplication(int nID) throws RemoteException {
        throw new RemoteException("int clearFnLaunchApplication(int nID) not supported on IT-G400 devices!");
    }

}
