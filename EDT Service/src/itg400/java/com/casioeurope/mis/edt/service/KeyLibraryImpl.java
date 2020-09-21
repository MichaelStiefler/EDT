package com.casioeurope.mis.edt.service;

import android.os.RemoteException;

import com.casioeurope.mis.edt.IKeyLibrary;
import com.casioeurope.mis.edt.KeyLibrary;
import com.casioeurope.mis.edt.types.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.types.BooleanParcelable;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("000111000111", 2);
    private static String[] methodNames = {"setUserKeyCode",
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
            "clearFnLaunchApplication"};
    private static volatile jp.casio.ht.devicelibrary.KeyLibrary jpInstance;

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
        return KeyLibrary.CONSTANT.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getFnUserKeyCode(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibrary.CONSTANT.RETURN.ERROR_NOTSUPPORTED;
    }

    public int setFnDefaultKeyCode(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibrary.CONSTANT.RETURN.ERROR_NOTSUPPORTED;
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
        return KeyLibrary.CONSTANT.RETURN.ERROR_NOTSUPPORTED;
    }

    public int getFnLaunchApplication(int nID, ApplicationInfoParcelable appInfo, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibrary.CONSTANT.RETURN.ERROR_NOTSUPPORTED;
    }

    public int clearFnLaunchApplication(int nID, BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return KeyLibrary.CONSTANT.RETURN.ERROR_NOTSUPPORTED;
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
