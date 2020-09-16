package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.types.ApplicationInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static volatile jp.casio.ht.devicelibrary.KeyLibrary jpInstance;

    private static final List<Integer> keyCodesItG400 = new ArrayList<>(
            Arrays.asList(278, 277)
    );

    private static final List<Integer> keyCodesItG600 = new ArrayList<>(
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

    public int setUserKeyCode(int nID, int KeyCode) throws RemoteException {
        int itg400KeyCodeIndex;
        if ((itg400KeyCodeIndex = keyCodesItG400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesItG600.get(itg400KeyCodeIndex);
        }
        return getJpInstance().setUserKeyCode(nID, KeyCode);
    }

    public int getUserKeyCode(int nID) throws RemoteException {
        return getJpInstance().getUserKeyCode(nID);
    }

    public int setDefaultKeyCode(int nID) throws RemoteException {
        return getJpInstance().setDefaultKeyCode(nID);
    }

    public int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException {
        int itg400KeyCodeIndex;
        if ((itg400KeyCodeIndex = keyCodesItG400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesItG600.get(itg400KeyCodeIndex);
        }
        return getJpInstance().setFnUserKeyCode(nID, KeyCode);
    }

    public int getFnUserKeyCode(int nID) throws RemoteException {
        return getJpInstance().getFnUserKeyCode(nID);
    }

    public int setFnDefaultKeyCode(int nID) throws RemoteException {
        return getJpInstance().setFnDefaultKeyCode(nID);
    }

    public int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().setLaunchApplication(nID, appInfo(appInfo));
    }

    public int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().getLaunchApplication(nID, appInfo(appInfo));
    }
	
    public int clearLaunchApplication(int nID) throws RemoteException {
        return getJpInstance().clearLaunchApplication(nID);
    }

    public int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().setFnLaunchApplication(nID, appInfo(appInfo));
    }

    public int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().getFnLaunchApplication(nID, appInfo(appInfo));
    }
	
    public int clearFnLaunchApplication(int nID) throws RemoteException {
        return getJpInstance().clearFnLaunchApplication(nID);
    }

}