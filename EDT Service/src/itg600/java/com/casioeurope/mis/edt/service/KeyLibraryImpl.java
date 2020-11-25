package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.KeyEvent;

import com.casioeurope.mis.edt.IKeyLibrary;
import com.casioeurope.mis.edt.type.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import device.common.IHiJackService;

@SuppressWarnings({"unused", "RedundantSuppression", "RedundantThrows"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("111111111111111111111111111111111100", 2);
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

    private jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo jpAppInfo(ApplicationInfoParcelable theAppInfo) {
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo jpAppInfo = new jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo();
        jpAppInfo.packageName = theAppInfo.packageName;
        jpAppInfo.activityName = theAppInfo.activityName;
        return jpAppInfo;
    }

    private void copyAppInfo(jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo source, ApplicationInfoParcelable target) {
        target.packageName = source.packageName;
        target.activityName = source.activityName;
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

    public int setFnUserKeyCode(int nID, int KeyCode, BooleanParcelable unsupported) throws RemoteException {
        int itg400KeyCodeIndex;
        if ((itg400KeyCodeIndex = keyCodesItG400.indexOf(KeyCode)) != -1) {
            KeyCode = keyCodesItG600.get(itg400KeyCodeIndex);
        }
        unsupported.setValue(false);
        return getJpInstance().setFnUserKeyCode(nID, KeyCode);
    }

    public int getFnUserKeyCode(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().getFnUserKeyCode(nID);
    }

    public int setFnDefaultKeyCode(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().setFnDefaultKeyCode(nID);
    }

    public int setLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo jpAppInfo = jpAppInfo(appInfo);
        int retVal = getJpInstance().setLaunchApplication(nID, jpAppInfo);
        this.copyAppInfo(jpAppInfo, appInfo);
        return retVal;
    }

    public int getLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo jpAppInfo = jpAppInfo(appInfo);
        int retVal = getJpInstance().getLaunchApplication(nID, jpAppInfo);
        this.copyAppInfo(jpAppInfo, appInfo);
        return retVal;
    }
	
    public int clearLaunchApplication(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().clearLaunchApplication(nID);
    }

    public int setFnLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo jpAppInfo = jpAppInfo(appInfo);
        int retVal = getJpInstance().setFnLaunchApplication(nID, jpAppInfo);
        this.copyAppInfo(jpAppInfo, appInfo);
        return retVal;
    }

    public int getFnLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        jp.casio.ht.devicelibrary.KeyLibrary.ApplicationInfo jpAppInfo = jpAppInfo(appInfo);
        int retVal = getJpInstance().getFnLaunchApplication(nID, jpAppInfo);
        this.copyAppInfo(jpAppInfo, appInfo);
        return retVal;
    }
	
    public int clearFnLaunchApplication(int nID, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getJpInstance().clearFnLaunchApplication(nID);
    }

    public void broadcastKey(String action, String extra, KeyEvent event, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        getHijackInstance().hiJackService.broadcastKey(action, extra, event);
    }

    public int changeKCMapFile(String path, byte[] data, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.changeKCMapFile(path, data);
    }

    public boolean changeKCMapFileToDefault(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.changeKCMapFileToDefault();
    }

    public void changeTrayIcon(KeyEvent event, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        getHijackInstance().hiJackService.changeTrayIcon(event);
    }

    public String getCurrentKCMapFile(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.getCurrentKCMapFile();
    }

    public int getKeypadMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.getKeypadMode();
    }

    public int getTestMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.getTestMode();
    }

    public boolean hasHardwareKey(int keyCode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.hasHardwareKey(keyCode);
    }

    public boolean hasWakeupRes(KeyEvent event, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.hasWakeupRes(event);
    }

    public boolean hijackingKey(KeyEvent event, boolean useCache, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.hijackingKey(event, useCache);
    }

    public boolean isDirectInputStyle(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.isDirectInputStyle();
    }

    public boolean isFinishedHandle(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.isFinishedHandle();
    }

    public boolean isKeyControlMode(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.isKeyControlMode();
    }

    public boolean isWakeupRes(int keyCode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.isWakeupRes(keyCode);
    }

    public void performKeyPressFeedback(KeyEvent event, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        getHijackInstance().hiJackService.performKeyPressFeedback(event);
    }

    public boolean removeKCMapFile(BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.removeKCMapFile();
    }

    public boolean setDirectInputStyle(boolean enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.setDirectInputStyle(enable);
    }

    public boolean setFixedNumberMode(boolean on, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.setFixedNumberMode(on);
    }

    public boolean setKeyControlMode(boolean enable, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.setKeyControlMode(enable);
    }

    public boolean setKeypadMode(int mode, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.setKeypadMode(mode);
    }

    public boolean setWakeupRes(int resourceID, boolean enabled, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        return getHijackInstance().hiJackService.setWakeupRes(resourceID, enabled);
    }

    public void updateMetaState(KeyEvent event, BooleanParcelable unsupported) throws RemoteException {
        unsupported.setValue(false);
        getHijackInstance().hiJackService.updateMetaState(event);
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
