package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.types.IParcelableTypes;

interface ISystemLibrary {
    boolean isMethodSupported(String methodBigInteger);
    boolean isMethodNameSupported(String methodName);
    String getCASIOSerial(out BooleanParcelable unsupported);
    String getModelName();
    boolean getNavigationBarState();
    void setNavigationBarState(boolean state);
}
