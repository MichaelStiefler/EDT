package com.casioeurope.mis.edt.service;

import com.casioeurope.mis.edt.IEeicCallback;
import com.casioeurope.mis.edt.IEeicLibrary;
import com.casioeurope.mis.edt.constant.EeicLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantThrows", "RedundantSuppression", "SpellCheckingInspection"})
public class EeicLibraryImpl extends IEeicLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("0000000000000000", 2);
    private static final String[] methodNames = {"close",
            "getLibraryVersion",
            "getValue",
            "isPowerOn",
            "open",
            "read",
            "registerCallback",
            "sendBreak",
            "setInputDirection",
            "setInterruptEdge",
            "setOutputDirection",
            "setPower",
            "setSlaveAddress",
            "setValue",
            "unregisterCallback",
            "write"};

    public EeicLibraryImpl() {
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

    public boolean setPower(boolean enable, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean isPowerOn(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public String getLibraryVersion(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return null;
   }

    public int gpioDeviceSetInputDirection(int pinNo, int pinStatus, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int gpioDeviceSetOutputDirection(int pinNo, int value, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int gpioDeviceSetValue(int pinNo, int value, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int gpioDeviceGetValue(int pinNo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public int gpioDeviceSetInterruptEdge(int pinNo, int type, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public boolean gpioDeviceRegisterCallback(IEeicCallback callback, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean gpioDeviceUnregisterCallback(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceOpen1(int baudrate, int flags, boolean hwflow, int bitLen, int parityBit, int stopBit, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceOpen2(int baudrate, int bitLen, int parityBit, int stopBit, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceOpen3(int baudrate, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceWrite1(byte data, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceWrite2(byte[] buffer, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceWrite3(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public int serialDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public boolean serialDeviceSendBreak(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean i2cDeviceOpen1(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean i2cDeviceOpen2(int flags, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean i2cDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean i2cDeviceWrite1(byte data, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean i2cDeviceWrite2(byte[] buffer, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean i2cDeviceWrite3(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public int i2cDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

    public boolean i2cDeviceSetSlaveAddress(int address, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean spiDeviceOpen1(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean spiDeviceOpen2(int flags, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean spiDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean spiDeviceWrite1(byte data, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean spiDeviceWrite2(byte[] buffer, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean spiDeviceWrite3(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public int spiDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RETURN.ERROR_UNSUPPORTED;
    }

}