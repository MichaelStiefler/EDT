package com.casioeurope.mis.edt.service;

import android.os.RemoteException;

import com.casioeurope.mis.edt.IEeicCallback;
import com.casioeurope.mis.edt.IEeicLibrary;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

import jp.casio.ht.eeiclibrary.EeicManager;

@SuppressWarnings({"unused", "RedundantThrows", "RedundantSuppression", "SpellCheckingInspection"})
public class EeicLibraryImpl extends IEeicLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("1111111111111111", 2);
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

    private IEeicCallback eeicCallback;

    private class MyInterruptCallback extends EeicManager.InterruptCallback {
        @Override
        public void onChanged(int gpio) {
            if (EeicLibraryImpl.this.eeicCallback != null) {
                try {
                    EeicLibraryImpl.this.eeicCallback.onChanged(gpio);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private final MyInterruptCallback myInterruptCallback = new MyInterruptCallback();

    private static volatile EeicManager jpInstance;

    private static EeicManager getJpInstance() {
        if (jpInstance == null) {
            synchronized (EeicLibraryImpl.class) {
                if (jpInstance == null) {
                    jpInstance = new EeicManager();
                }
            }
        }
        return jpInstance;
    }

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
        unsupported.setValue(false);
        return getJpInstance().setPower(enable);
    }

    public boolean isPowerOn(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().isPowerOn();
    }

    public String getLibraryVersion(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getLibraryVersion();
    }

    public int gpioDeviceSetInputDirection(int pinNo, int pinStatus, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getGpioDevice().setInputDirection(pinNo, pinStatus);
    }

    public int gpioDeviceSetOutputDirection(int pinNo, int value, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getGpioDevice().setOutputDirection(pinNo, value);
    }

    public int gpioDeviceSetValue(int pinNo, int value, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getGpioDevice().setValue(pinNo, value);
    }

    public int gpioDeviceGetValue(int pinNo, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getGpioDevice().getValue(pinNo);
    }

    public int gpioDeviceSetInterruptEdge(int pinNo, int type, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getGpioDevice().setInterruptEdge(pinNo, type);
    }

    public boolean gpioDeviceRegisterCallback(IEeicCallback callback, BooleanParcelable unsupported) {
        this.eeicCallback = callback;
        return getJpInstance().getGpioDevice().registerCallback(this.myInterruptCallback, null);
    }

    public boolean gpioDeviceUnregisterCallback(BooleanParcelable unsupported) {
        boolean retVal = getJpInstance().getGpioDevice().unregisterCallback(this.myInterruptCallback);
        this.eeicCallback = null;
        return retVal;
    }

    public boolean serialDeviceOpen1(int baudrate, int flags, boolean hwflow, int bitLen, int parityBit, int stopBit, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().open(baudrate, hwflow, bitLen, parityBit, stopBit);
    }

    public boolean serialDeviceOpen2(int baudrate, int bitLen, int parityBit, int stopBit, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().open(baudrate, bitLen, parityBit, stopBit);
    }

    public boolean serialDeviceOpen3(int baudrate, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().open(baudrate);
    }

    public boolean serialDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().close();
    }

    public boolean serialDeviceWrite1(byte data, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().write(data);
    }

    public boolean serialDeviceWrite2(byte[] buffer, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().write(buffer);
    }

    public boolean serialDeviceWrite3(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().write(buffer, offset, length);
    }

    public int serialDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().read(buffer, length);
    }

    public boolean serialDeviceSendBreak(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSerialDevice().sendBreak();
    }

    public boolean i2cDeviceOpen1(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().open();
    }

    public boolean i2cDeviceOpen2(int flags, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().open(flags);
    }

    public boolean i2cDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().close();
    }

    public boolean i2cDeviceWrite1(byte data, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().write(data);
    }

    public boolean i2cDeviceWrite2(byte[] buffer, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().write(buffer);
    }

    public boolean i2cDeviceWrite3(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().write(buffer, offset, length);
    }

    public int i2cDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().read(buffer, length);
    }

    public boolean i2cDeviceSetSlaveAddress(int address, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getI2cDevice().setSlaveAddress(address);
    }

    public boolean spiDeviceOpen1(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().open();
    }

    public boolean spiDeviceOpen2(int flags, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().open(flags);
    }

    public boolean spiDeviceClose(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().close();
    }

    public boolean spiDeviceWrite1(byte data, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().write(data);
    }

    public boolean spiDeviceWrite2(byte[] buffer, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().write(buffer);
    }

    public boolean spiDeviceWrite3(byte[] buffer, int offset, int length, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().write(buffer, offset, length);
    }

    public int spiDeviceRead(byte[] buffer, int length, BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return getJpInstance().getSpiDevice().read(buffer, length);
    }


}