package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.type.IParcelableTypes;

interface ISamLibrary {
    boolean isMethodNameSupported(String methodName);
    boolean isMethodSupported(String methodBigInteger);
    int openSam(out BooleanParcelable unsupported);
    int closeSam(out BooleanParcelable unsupported);
    int sendPowerOn(out BooleanParcelable unsupported);
    int sendPowerOff(out BooleanParcelable unsupported);
    int receiveATR(out byte[] receiveData, out BooleanParcelable unsupported);
    int communicateAPDU(in byte[] sendData, int sendLength, out byte[] receiveData, int receiveLength, out BooleanParcelable unsupported);
    int communicateDirect(byte command, in byte[] sendData, int sendLength, out byte[] receiveData, int receiveLength, out BooleanParcelable unsupported);
    int getTimeOutDelay(out BooleanParcelable unsupported);
    int setTimeOutDelay(int delayMs, out BooleanParcelable unsupported);
}
