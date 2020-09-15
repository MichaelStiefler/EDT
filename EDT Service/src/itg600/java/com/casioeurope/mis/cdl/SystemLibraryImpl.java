package com.casioeurope.mis.cdl;

import android.os.RemoteException;

import com.casioeurope.mis.cdl.ISystemLibrary;

import jp.casio.ht.devicelibrary.SystemLibrary;

public class SystemLibraryImpl extends ISystemLibrary.Stub {

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
            synchronized (SystemLibrary.class) {
                if (instance == null) {
                    instance = new SystemLibraryImpl();
                }
            }
        }
        return instance;
    }

    private SystemLibraryImpl() {
    }

    public String getCASIOSerial() throws RemoteException {
        return getJpInstance().getCASIOSerial();
    }

    public String getModelName() throws RemoteException {
        return getJpInstance().getModelName();
    }

    public boolean getNavigationBarState() throws RemoteException {
        return getJpInstance().getNavigationBarState();
    }

    public void setNavigationBarState(boolean state) throws RemoteException {
        getJpInstance().setNavigationBarState(state);
    }

}
