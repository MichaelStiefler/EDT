package com.casioeurope.mis.cdl;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

import com.casioeurope.mis.edt.EDTServiceConnection;

public class KeyLibrary {

    private static KeyLibrary instance;

    private KeyLibrary() {
    }

    private static KeyLibrary getInstance() {
        if (KeyLibrary.instance == null) {
            KeyLibrary.instance = new KeyLibrary();
        }
        return KeyLibrary.instance;
    }

    private IKeyLibrary cdlServiceKeyLibrary() {
        return EDTServiceConnection.getInstance().getKeyLibrary();
    }

    public static int getUserKeyCode(int nID) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().getUserKeyCode(nID);
    }

    public static int setDefaultKeyCode(int nID) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().setDefaultKeyCode(nID);
    }

    private static int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().setFnUserKeyCode(nID, KeyCode);
    }

    public static int getFnUserKeyCode(int nID) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().getFnUserKeyCode(nID);
    }

    public static int setFnDefaultKeyCode(int nID) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().setFnDefaultKeyCode(nID);
    }

    public static int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().setLaunchApplication(nID, appInfo);
    }

    public static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().getLaunchApplication(nID, appInfo);
    }
    public static int clearLaunchApplication(int nID) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().clearLaunchApplication(nID);
    }

    public static int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().setFnLaunchApplication(nID, appInfo);
    }

    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().getFnLaunchApplication(nID, appInfo);
    }
    public static int clearFnLaunchApplication(int nID) throws RemoteException {
        return getInstance().cdlServiceKeyLibrary().clearFnLaunchApplication(nID);
    }
}
