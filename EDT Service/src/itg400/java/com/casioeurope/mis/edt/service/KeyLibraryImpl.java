package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.casioeurope.mis.edt.IKeyLibrary;
import com.casioeurope.mis.edt.constant.KeyLibraryConstant;
import com.casioeurope.mis.edt.type.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import device.common.IHiJackService;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("00011000000000001010010000111000111", 2);
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
            "getFixedNumberMode",
            "setFixedNumberMode",
            "setKeyControlMode",
            "setKeypadMode",
            "setWakeupRes",
            "updateMetaState"};

    private static volatile jp.casio.ht.devicelibrary.KeyLibrary jpInstance;
    private static volatile KeyLibraryImpl hijackInstance;
    private IHiJackService hiJackService;

    private static final List<Integer> keyCodesItG400 = new ArrayList<>(
            Arrays.asList(278, 277)
    );

    private static final List<Integer> keyCodesItG600 = new ArrayList<>(
            Arrays.asList(1012, 1011)
    );

    private static jp.casio.ht.devicelibrary.KeyLibrary getJpInstance() {
        if (jpInstance == null) {
            synchronized (KeyLibraryImpl.class) {
                if (jpInstance == null) {
                    jpInstance = new jp.casio.ht.devicelibrary.KeyLibrary();
                }
            }
        }
        return jpInstance;
    }

    private static KeyLibraryImpl getHijackInstance() {
        if (hijackInstance == null) {
            hijackInstance = new KeyLibraryImpl();
            try {
                @SuppressLint("PrivateApi") Method method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
                IBinder binder = (IBinder) method.invoke(null, "HiJackService");
                if (binder != null) {
                    hijackInstance.hiJackService = IHiJackService.Stub.asInterface(binder);
                }
            } catch (InvocationTargetException | NoSuchMethodException | ClassNotFoundException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return hijackInstance;
    }

    public KeyLibraryImpl() {
    }

    private jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo(ApplicationInfoParcelable theAppInfo) {
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo appInfo = new jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo();
        appInfo.packageName = theAppInfo.packageName;
        appInfo.activityName = theAppInfo.activityName;
        return appInfo;
    }

    public int setUserKeyCode(int nID, int KeyCode, BooleanParcelable unsupported) throws RemoteException {
        int itg400KeyCodeIndex;
        if ((itg400KeyCodeIndex = keyCodesItG400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesItG600.get(itg400KeyCodeIndex);
        }
        unsupported.setValue(false);
        return getJpInstance().setUserKeyCode(nID, KeyCode);
    }

    public int getUserKeyCode(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getUserKeyCode(nID);
    }

    public int setDefaultKeyCode(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setDefaultKeyCode(nID);
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

    public int setLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setLaunchApplication(nID, appInfo(appInfo));
    }

    public int getLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getLaunchApplication(nID, appInfo(appInfo));
    }
	
    public int clearLaunchApplication(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().clearLaunchApplication(nID);
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

    public int changeKCMapFile(String path, byte[] data, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.changeKCMapFile(path, data);
    }

    public boolean changeKCMapFileToDefault(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return false;
    }

    public void changeTrayIcon(KeyEvent event, BooleanParcelable unsupported) {
        unsupported.setValue(true);
    }

    public String getCurrentKCMapFile(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.getCurrentKCMapFile();
    }

    public int getKeypadMode(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibraryConstant.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getTestMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.getTestMode();
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

    public boolean getFixedNumberMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.getRestrictInputMode();
    }

    public boolean setFixedNumberMode(boolean on, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        getHijackInstance().hiJackService.setRestrictInputMode(on);
        return true;
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
