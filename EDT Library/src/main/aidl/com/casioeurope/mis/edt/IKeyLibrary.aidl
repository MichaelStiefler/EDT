package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.types.IParcelableTypes;

interface IKeyLibrary {
    boolean isMethodSupported(String methodBigInteger);
    boolean isMethodNameSupported(String methodName);
    int setUserKeyCode(int nID, int KeyCode, out BooleanParcelable unsupported);
    int getUserKeyCode(int nID, out BooleanParcelable unsupported);
    int setDefaultKeyCode(int nID, out BooleanParcelable unsupported);
    int setFnUserKeyCode(int nID, int KeyCode, out BooleanParcelable unsupported);
    int getFnUserKeyCode(int nID, out BooleanParcelable unsupported);
    int setFnDefaultKeyCode(int nID, out BooleanParcelable unsupported);
    int setLaunchApplication(int nID, in ApplicationInfo appInfo, out BooleanParcelable unsupported);
    int getLaunchApplication(int nID, in ApplicationInfo appInfo, out BooleanParcelable unsupported);
    int clearLaunchApplication(int nID, out BooleanParcelable unsupported);
    int setFnLaunchApplication(int nID, in ApplicationInfo appInfo, out BooleanParcelable unsupported);
    int getFnLaunchApplication(int nID, in ApplicationInfo appInfo, out BooleanParcelable unsupported);
    int clearFnLaunchApplication(int nID, out BooleanParcelable unsupported);
}
