package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.type.IParcelableTypes;
import com.casioeurope.mis.edt.IEeicCallback;

interface IEeicLibrary {
    boolean isMethodNameSupported(String methodName);
    boolean isMethodSupported(String methodBigInteger);
    boolean setPower(boolean enable, out BooleanParcelable unsupported);
    boolean isPowerOn(out BooleanParcelable unsupported);
    String getLibraryVersion(out BooleanParcelable unsupported);
    int gpioDeviceSetInputDirection(int pinNo, int pinStatus, out BooleanParcelable unsupported);
    int gpioDeviceSetOutputDirection(int pinNo, int value, out BooleanParcelable unsupported);
    int gpioDeviceSetValue(int pinNo, int value, out BooleanParcelable unsupported);
    int gpioDeviceGetValue(int pinNo, out BooleanParcelable unsupported);
    int gpioDeviceSetInterruptEdge(int pinNo, int type, out BooleanParcelable unsupported);
    boolean gpioDeviceRegisterCallback(IEeicCallback callback, out BooleanParcelable unsupported);
    boolean gpioDeviceUnregisterCallback(out BooleanParcelable unsupported);
    int serialDeviceOpen(int baudrate, boolean hwflow, int bitLen, int parityBit, int stopBit, boolean parmrk, out BooleanParcelable unsupported);
    boolean serialDeviceClose(out BooleanParcelable unsupported);
    boolean serialDeviceWrite(in byte[] buffer, int offset, int length, out BooleanParcelable unsupported);
    int serialDeviceRead1(out byte[] buffer, int length, out BooleanParcelable unsupported);
    int serialDeviceRead2(out byte[] buffer, int length, int timeout, out BooleanParcelable unsupported);
    boolean serialDeviceSendBreak(out BooleanParcelable unsupported);
    int serialDeviceGetErrorCount(out BooleanParcelable unsupported);
    int i2cDeviceOpen(out BooleanParcelable unsupported);
    int i2cDeviceClose(out BooleanParcelable unsupported);
    int i2cDeviceWrite(in byte[] buffer, int length, out BooleanParcelable unsupported);
    int i2cDeviceRead(out byte[] buffer, int length, out BooleanParcelable unsupported);
    int i2cDeviceSetSlaveAddress(int address, out BooleanParcelable unsupported);
    int spiDeviceOpen(int mode, int cs_state, int frequencyHz, out BooleanParcelable unsupported);
    int spiDeviceClose(out BooleanParcelable unsupported);
    int spiDeviceWrite(in byte[] buffer, int length, out BooleanParcelable unsupported);
    int spiDeviceRead(out byte[] buffer, int length, out BooleanParcelable unsupported);
    int spiDeviceTransfer(in byte[] txBuffer, out byte[] rxBuffer, int length, out BooleanParcelable unsupported);
}
