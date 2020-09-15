package com.casioeurope.mis.edt;
import com.casioeurope.mis.edt.IParcelableTypes;

interface IKeyLibrary {
    int getUserKeyCode(int nID);
    int setDefaultKeyCode(int nID);
    int setFnUserKeyCode(int nID, int KeyCode);
    int getFnUserKeyCode(int nID);
    int setFnDefaultKeyCode(int nID);
    int setLaunchApplication(int nID, in ApplicationInfo appInfo);
    int getLaunchApplication(int nID, in ApplicationInfo appInfo);
    int clearLaunchApplication(int nID);
    int setFnLaunchApplication(int nID, in ApplicationInfo appInfo);
    int getFnLaunchApplication(int nID, in ApplicationInfo appInfo);
    int clearFnLaunchApplication(int nID);
}
