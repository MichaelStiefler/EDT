package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.types.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class SystemLibraryImpl extends ISystemLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("1111", 2);
    private static String[] methodNames = {"getCASIOSerial",
            "getModelName",
            "getNavigationBarState",
            "setNavigationBarState"};
    private static volatile jp.casio.ht.devicelibrary.SystemLibrary jpInstance;

    private SystemLibraryImpl() {
    }

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

    public String getCASIOSerial(BooleanParcelable unsupported) throws RemoteException {
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

    public boolean isMethodNameSupported(String methodName) {
        int methodIndex = Arrays.asList(methodNames).indexOf(methodName);
        return methodIndex >= 0 && isMethodSupported(BigInteger.ONE.shiftLeft(methodIndex).toString());
    }

    public boolean isMethodSupported(String methodBigInteger) {
        try {
            return !new BigInteger(methodBigInteger).and(METHODS_SUPPORTED).equals(BigInteger.ZERO);
        } catch (Exception e) {
            return false;
        }
    }

}
