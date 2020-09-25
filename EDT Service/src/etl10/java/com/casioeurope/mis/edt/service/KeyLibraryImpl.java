package com.casioeurope.mis.edt.service;

import com.casioeurope.mis.edt.IKeyLibrary;
import com.casioeurope.mis.edt.constant.KeyLibraryConstant;
import com.casioeurope.mis.edt.type.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibraryImpl extends IKeyLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("000000000000", 2);
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
