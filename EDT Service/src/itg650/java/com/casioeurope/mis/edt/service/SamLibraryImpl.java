package com.casioeurope.mis.edt.service;

import android.os.RemoteException;

import com.casioeurope.mis.edt.ISamLibrary;
import com.casioeurope.mis.edt.constant.SamLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantSuppression", "RedundantThrows"})
public class SamLibraryImpl extends ISamLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("000000000", 2);
    private static String[] methodNames = {"openSam",
            "closeSam",
            "sendPowerOn",
            "sendPowerOff",
            "receiveATR",
            "communicateAPDU",
            "communicateDirect",
            "getTimeOutDelay",
            "setTimeOutDelay"};

    public int openSam(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int closeSam(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int sendPowerOn(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int sendPowerOff(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int receiveATR(byte[] receiveData, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int communicateAPDU(byte[] sendData, int sendLength, byte[] receiveData, int receiveLength, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int communicateDirect(byte command, byte[] sendData, int sendLength, byte[] receiveData, int receiveLength, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int getTimeOutDelay(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int setTimeOutDelay(int delayMs, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return SamLibraryConstant.RETURN.ERROR_UNSUPPORTED;
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
