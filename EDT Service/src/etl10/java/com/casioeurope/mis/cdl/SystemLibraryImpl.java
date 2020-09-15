package com.casioeurope.mis.cdl;

import android.os.RemoteException;

public class SystemLibraryImpl {

    private SystemLibraryImpl() {
    }

    public static String getCASIOSerial() throws RemoteException {
        throw new RemoteException("String getCASIOSerial() not supported on ET-L10 devices!");
    }

    public String getModelName() throws RemoteException {
        throw new RemoteException("String getModelName() not supported on ET-L10 devices!");
    }

    public boolean getNavigationBarState() throws RemoteException {
        throw new RemoteException("boolean getNavigationBarState() not supported on ET-L10 devices!");
    }

    public void setNavigationBarState(boolean state) throws RemoteException {
        throw new RemoteException("void setNavigationBarState(boolean state) not supported on ET-L10 devices!");
    }

}
