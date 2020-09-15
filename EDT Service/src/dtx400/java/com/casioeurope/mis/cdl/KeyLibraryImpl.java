package com.casioeurope.mis.cdl;

import android.os.RemoteException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeyLibraryImpl {

    private static volatile jp.casio.ht.devicelibrary.KeyLibrary jpInstance;
    private static volatile KeyLibraryImpl instance;

    private static final List<Integer> keyCodesItG400 = new ArrayList<>(
            Arrays.asList(278, 277)
    );

    private static final List<Integer> keyCodesDtx400 = new ArrayList<>(
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

    private static KeyLibraryImpl getInstance() {
        if (instance == null) {
            synchronized (KeyLibraryImpl.class) {
                if (instance == null) {
                    instance = new KeyLibraryImpl();
                }
            }
        }
        return instance;
    }

    private KeyLibraryImpl() {
    }

    private static int setUserKeyCode(int nID, int KeyCode) throws RemoteException {
        int itg400KeyCodeIndex;
        if ((itg400KeyCodeIndex = keyCodesItG400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesDtx400.get(itg400KeyCodeIndex);
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
        int itg400KeyCodeIndex;
        if ((itg400KeyCodeIndex = keyCodesItG400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesDtx400.get(itg400KeyCodeIndex);
        }
        return getJpInstance().setFnUserKeyCode(nID, KeyCode);
    }

    public static int getFnUserKeyCode(int nID) throws RemoteException {
        return getJpInstance().getFnUserKeyCode(nID);
    }

    public static int setFnDefaultKeyCode(int nID) throws RemoteException {
        return getJpInstance().setFnDefaultKeyCode(nID);
    }

    public static int setLaunchApplication(int nID, jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().setLaunchApplication(nID, appInfo);
    }

    public static int getLaunchApplication(int nID, jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().getLaunchApplication(nID, appInfo);
    }
    public static int clearLaunchApplication(int nID) throws RemoteException {
        return getJpInstance().clearLaunchApplication(nID);
    }

    public static int setFnLaunchApplication(int nID, jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().setFnLaunchApplication(nID, appInfo);
    }

    public static int getFnLaunchApplication(int nID, jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo) throws RemoteException {
        return getJpInstance().getFnLaunchApplication(nID, appInfo);
    }
    public static int clearFnLaunchApplication(int nID) throws RemoteException {
        return getJpInstance().clearFnLaunchApplication(nID);
    }

}
