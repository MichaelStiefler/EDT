package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.type.IParcelableTypes;

interface IKeyLibrary {
    boolean isMethodSupported(String methodBigInteger);
    boolean isMethodNameSupported(String methodName);
    int setUserKeyCode(int nID, int KeyCode, out BooleanParcelable unsupported);
    int getUserKeyCode(int nID, out BooleanParcelable unsupported);
    int setDefaultKeyCode(int nID, out BooleanParcelable unsupported);
    int setFnUserKeyCode(int nID, int KeyCode, out BooleanParcelable unsupported);
    int getFnUserKeyCode(int nID, out BooleanParcelable unsupported);
    int setFnDefaultKeyCode(int nID, out BooleanParcelable unsupported);
    int setLaunchApplication(int nID, in ApplicationInfoParcelable appInfo, out BooleanParcelable unsupported);
    int getLaunchApplication(int nID, in ApplicationInfoParcelable appInfo, out BooleanParcelable unsupported);
    int clearLaunchApplication(int nID, out BooleanParcelable unsupported);
    int setFnLaunchApplication(int nID, in ApplicationInfoParcelable appInfo, out BooleanParcelable unsupported);
    int getFnLaunchApplication(int nID, in ApplicationInfoParcelable appInfo, out BooleanParcelable unsupported);
    int clearFnLaunchApplication(int nID, out BooleanParcelable unsupported);
}
