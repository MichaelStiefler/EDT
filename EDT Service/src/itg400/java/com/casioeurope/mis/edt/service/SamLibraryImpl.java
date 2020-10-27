package com.casioeurope.mis.edt.service;

import android.os.RemoteException;

import com.casioeurope.mis.edt.ISamLibrary;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

import device.sdk.SamManager;

import static com.casioeurope.mis.edt.constant.SamLibraryConstant.RETURN.ERROR_NOTOPENED;
import static com.casioeurope.mis.edt.constant.SamLibraryConstant.RETURN.SUCCESS;

@SuppressWarnings({"unused", "RedundantSuppression", "RedundantThrows"})
public class SamLibraryImpl extends ISamLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("111111111", 2);
    private static final String[] methodNames = {"openSam",
            "closeSam",
            "sendPowerOn",
            "sendPowerOff",
            "receiveATR",
            "communicateAPDU",
            "communicateDirect",
            "getTimeOutDelay",
            "setTimeOutDelay"};
    private static volatile Implementation jpInstance;

    private SamManager mSamManager;


    public SamLibraryImpl() {
    }

    private static Implementation getJpInstance() {
        if (jpInstance == null) {
            synchronized (SystemLibraryImpl.class) {
                if (jpInstance == null) {
                    jpInstance = new Implementation();
                }
            }
        }
        return jpInstance;
    }

    public int openSam(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().openSam();
    }

    public int closeSam(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().closeSam();
    }

    public int sendPowerOn(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().sendPowerOn();
    }

    public int sendPowerOff(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().sendPowerOff();
    }

    public int receiveATR(byte[] receiveData, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().receiveATR(receiveData);
    }

    public int communicateAPDU(byte[] sendData, int sendLength, byte[] receiveData, int receiveLength, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().communicateAPDU(sendData, sendLength, receiveData, receiveLength);
    }

    public int communicateDirect(byte command, byte[] sendData, int sendLength, byte[] receiveData, int receiveLength, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().communicateDirect(command, sendData, sendLength, receiveData, receiveLength);
    }

    public int getTimeOutDelay(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getTimeOutDelay();
    }

    public int setTimeOutDelay(int delayMs, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setTimeOutDelay(delayMs);
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

    @SuppressWarnings("SpellCheckingInspection")
    private static class Implementation {
        private SamManager mSamManager;

        private SamManager getSamManager() {
            if (this.mSamManager == null) {
                this.mSamManager = new SamManager();
            }
            return this.mSamManager;
        }

        private int openSam() {
            if (getSamManager().isEnabled()) {
                return SUCCESS;
            }
            this.mSamManager.setEnabled(true);
            return SUCCESS;
        }

        private int closeSam() {
            if (getSamManager().isEnabled()) {
                this.mSamManager.setEnabled(false);
            }
            return SUCCESS;
        }

        private int sendPowerOn() {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            return this.mSamManager.sendAtrCommand();
        }

        private int sendPowerOff() {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            return this.mSamManager.sendPowerDownCommand();
        }

        private int receiveATR(byte[] receiveData) {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            return this.mSamManager.GetAtrResponse(receiveData);
        }

        private int communicateAPDU(byte[] sendData, int sendLength, byte[] receiveData, int receiveLength) {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            return this.mSamManager.sendApduCommand(sendData, sendLength, receiveData, receiveLength);
        }

        private int communicateDirect(byte command, byte[] sendData, int sendLength, byte[] receiveData, int receiveLength) {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            return this.mSamManager.sendDirectCommand(command, sendData, sendLength, receiveData, receiveLength);
        }

        private int getTimeOutDelay() {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            return this.mSamManager.getTimeoutDelay();
        }

        private int setTimeOutDelay(int delayMs) {
            if (!getSamManager().isEnabled()) {
                return ERROR_NOTOPENED;
            }
            this.mSamManager.setTimeoutDelay(delayMs);
            return SUCCESS;
        }
    }
}
