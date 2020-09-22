package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.type.IParcelableTypes;

interface ISystemLibrary {
    boolean isMethodSupported(String methodBigInteger);
    boolean isMethodNameSupported(String methodName);
    String getCASIOSerial(out BooleanParcelable unsupported);
    String getModelName(out BooleanParcelable unsupported);
    boolean getNavigationBarState(out BooleanParcelable unsupported);
    void setNavigationBarState(boolean state, out BooleanParcelable unsupported);
}
