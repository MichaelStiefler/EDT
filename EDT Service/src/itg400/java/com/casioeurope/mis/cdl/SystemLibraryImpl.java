package com.casioeurope.mis.cdl;

import android.os.RemoteException;

public class SystemLibraryImpl {

    private static volatile jp.casio.ht.devicelibrary.SystemLibrary jpInstance;
    private static volatile SystemLibraryImpl instance;

    private static jp.casio.ht.devicelibrary.SystemLibrary getJpInstance() {
        if (jpInstance == null) {
            synchronized (SystemLibraryImpl.class) {
                if (jpInstance == null) {
                    jpInstance = new jp.casio.ht.devicelibrary.SystemLibrary();
                }
            }
        }
        return jpInstance;
    }

    private static SystemLibraryImpl getInstance() {
        if (instance == null) {
            synchronized (SystemLibraryImpl.class) {
                if (instance == null) {
                    instance = new SystemLibraryImpl();
                }
            }
        }
        return instance;
    }

    private SystemLibraryImpl() {
    }

    public static String getCASIOSerial() throws RemoteException {
        return getJpInstance().getCASIOSerial();
    }

    public String getModelName() throws RemoteException {
        return getJpInstance().getModelName();
    }

    public boolean getNavigationBarState() throws RemoteException {
        throw new RemoteException("boolean getNavigationBarState() not supported on IT-G400 devices!");
    }

    public void setNavigationBarState(boolean state) throws RemoteException {
        throw new RemoteException("void setNavigationBarState(boolean state) not supported on IT-G400 devices!");
    }

}
