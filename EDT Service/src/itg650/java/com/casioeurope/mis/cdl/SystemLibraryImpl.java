package com.casioeurope.mis.edt;

import android.os.RemoteException;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class SystemLibraryImpl extends ISystemLibrary.Stub {

    private static volatile jp.casio.ht.devicelibrary.SystemLibrary jpInstance;

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
