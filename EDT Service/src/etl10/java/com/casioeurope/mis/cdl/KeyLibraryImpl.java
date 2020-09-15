package com.casioeurope.mis.cdl;

import android.os.RemoteException;

public class KeyLibraryImpl {

    private KeyLibraryImpl() {
    }

    private static int setUserKeyCode(int nID, int KeyCode) throws RemoteException {
        throw new RemoteException("int setUserKeyCode(int nID, int KeyCode) not supported on ET-L10 devices!");
    }

    public static int getUserKeyCode(int nID) throws RemoteException {
        throw new RemoteException("int getUserKeyCode(int nID) not supported on ET-L10 devices!");
    }

    public static int setDefaultKeyCode(int nID) throws RemoteException {
        throw new RemoteException("int setDefaultKeyCode(int nID) not supported on ET-L10 devices!");
    }

    private static int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException {
        throw new RemoteException("int setFnUserKeyCode(int nID, int KeyCode) not supported on ET-L10 devices!");
    }

    public static int getFnUserKeyCode(int nID) throws RemoteException {
        throw new RemoteException("int getFnUserKeyCode(int nID) not supported on ET-L10 devices!");
    }

    public static int setFnDefaultKeyCode(int nID) throws RemoteException {
        throw new RemoteException("int setFnDefaultKeyCode(int nID) not supported on ET-L10 devices!");
    }

    public static int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        throw new RemoteException("int setLaunchApplication(int nID, ApplicationInfo appInfo) not supported on ET-L10 devices!");
    }

    public static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        throw new RemoteException("int getLaunchApplication(int nID, ApplicationInfo appInfo) not supported on ET-L10 devices!");
    }
    public static int clearLaunchApplication(int nID) throws RemoteException {
        throw new RemoteException("int clearLaunchApplication(int nID) not supported on ET-L10 devices!");
    }

    public static int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        throw new RemoteException("int setFnLaunchApplication(int nID, ApplicationInfo appInfo) not supported on ET-L10 devices!");
    }

    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException {
        throw new RemoteException("int getFnLaunchApplication(int nID, ApplicationInfo appInfo) not supported on ET-L10 devices!");
    }
    public static int clearFnLaunchApplication(int nID) throws RemoteException {
        throw new RemoteException("int clearFnLaunchApplication(int nID) not supported on ET-L10 devices!");
    }

}
