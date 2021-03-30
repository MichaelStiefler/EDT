package com.casioeurope.mis.edt.service;

import com.casioeurope.mis.edt.IEeicCallback;
import com.casioeurope.mis.edt.IEeicLibrary;
import com.casioeurope.mis.edt.constant.EeicLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantThrows", "RedundantSuppression", "SpellCheckingInspection"})
public class EeicLibraryImpl extends IEeicLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("000000000000000000", 2);
    private static final String[] methodNames = {"close",
            "getErrorCount",
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
            "transfer",
            "unregisterCallback",
            "write"};

    private EeicLibraryImpl() {
    }

    private static EeicLibraryImpl instance;

    public static EeicLibraryImpl getInstance() {
        if (instance == null) instance = new EeicLibraryImpl();
        return instance;
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
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int gpioDeviceSetOutputDirection(int pinNo, int value, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int gpioDeviceSetValue(int pinNo, int value, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int gpioDeviceGetValue(int pinNo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int gpioDeviceSetInterruptEdge(int pinNo, int type, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public boolean gpioDeviceRegisterCallback(IEeicCallback callback, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean gpioDeviceUnregisterCallback(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public int serialDeviceOpen(int baudrate, boolean hwflow, int bitLen, int parityBit, int stopBit, boolean parmrk, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public boolean serialDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean serialDeviceWrite(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public int serialDeviceRead1(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int serialDeviceRead2(byte[] buffer, int length, int timeout, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public boolean serialDeviceSendBreak(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public int serialDeviceGetErrorCount(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int i2cDeviceOpen(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int i2cDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int i2cDeviceWrite(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int i2cDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int i2cDeviceSetSlaveAddress(int address, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int spiDeviceOpen(int mode, int cs_state, int frequencyHz, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int spiDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int spiDeviceWrite(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int spiDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

    public int spiDeviceTransfer(byte[] txBuffer, byte[] rxBuffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return EeicLibraryConstant.RESULT.ERROR_NOT_SUPPORT;
    }

}