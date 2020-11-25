package com.casioeurope.mis.edt.service;

import android.view.KeyEvent;

import com.casioeurope.mis.edt.IKeyLibrary;
import com.casioeurope.mis.edt.constant.KeyLibraryConstant;
import com.casioeurope.mis.edt.type.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("000000000000000000000000000000000000", 2);
    private static final String[] methodNames = {"setUserKeyCode",
            "getUserKeyCode",
            "setDefaultKeyCode",
            "setFnUserKeyCode",
            "getFnUserKeyCode",
            "setFnDefaultKeyCode",
            "setLaunchApplication",
            "getLaunchApplication",
            "clearLaunchApplication",
            "setFnLaunchApplication",
            "getFnLaunchApplication",
            "clearFnLaunchApplication",
            "broadcastKey",
            "changeKCMapFile",
            "changeKCMapFileToDefault",
            "changeTrayIcon",
            "getCurrentKCMapFile",
            "getKeypadMode",
            "getTestMode",
            "hasHardwareKey",
            "hasWakeupRes",
            "hijackingKey",
            "isDirectInputStyle",
            "isFinishedHandle",
            "isKeyControlMode",
            "isWakeupRes",
            "performKeyPressFeedback",
            "removeKCMapFile",
            "setDirectInputStyle",
            "setFixedNumberMode",
            "setKeyControlMode",
            "setKeypadMode",
            "setWakeupRes",
            "updateMetaState",
            "getRestrictInputMode",
            "setRestrictInputMode"};

    public KeyLibraryImpl() {
    }

    public int setUserKeyCode(int nID, int KeyCode, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getUserKeyCode(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int setDefaultKeyCode(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int setFnUserKeyCode(int nID, int KeyCode, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getFnUserKeyCode(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int setFnDefaultKeyCode(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int setLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }
	
    public int clearLaunchApplication(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int setFnLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getFnLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }
	
    public int clearFnLaunchApplication(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public void broadcastKey(String action, String extra, KeyEvent event, BooleanParcelable unsupported) {
        unsupported.setValue(true);
    }

    public int changeKCMapFile(String path, byte[] data, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public boolean changeKCMapFileToDefault(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public void changeTrayIcon(KeyEvent event, BooleanParcelable unsupported) {
        unsupported.setValue(true);
    }

    public String getCurrentKCMapFile(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return null;
    }

    public int getKeypadMode(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getTestMode(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public boolean hasHardwareKey(int keyCode, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean hasWakeupRes(KeyEvent event, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean hijackingKey(KeyEvent event, boolean useCache, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean isDirectInputStyle(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean isFinishedHandle(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean isKeyControlMode(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean isWakeupRes(int keyCode, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public void performKeyPressFeedback(KeyEvent event, BooleanParcelable unsupported) {
        unsupported.setValue(true);
    }

    public boolean removeKCMapFile(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean setDirectInputStyle(boolean enable, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean setFixedNumberMode(boolean on, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean setKeyControlMode(boolean enable, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean setKeypadMode(int mode, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public boolean setWakeupRes(int resourceID, boolean enabled, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public void updateMetaState(KeyEvent event, BooleanParcelable unsupported) {
        unsupported.setValue(true);
    }

    public boolean getRestrictInputMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
        return false;
    }

    public void setRestrictInputMode(boolean enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(true);
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

}
