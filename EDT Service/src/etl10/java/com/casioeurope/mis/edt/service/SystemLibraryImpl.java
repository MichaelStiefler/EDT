package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.casioeurope.mis.edt.ISystemLibrary;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;
import java.util.Arrays;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class SystemLibraryImpl extends ISystemLibrary.Stub {

    private static final BigInteger METHODS_SUPPORTED = new BigInteger("0011", 2);
    private static final String[] methodNames = {"getCASIOSerial",
            "getModelName",
            "getNavigationBarState",
            "setNavigationBarState"};

    public SystemLibraryImpl() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds")
    public String getCASIOSerial(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        return Build.getSerial();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getModelName(BooleanParcelable unsupported) {
        unsupported.setValue(false);
        try {
            String serialNumber = getCASIOSerial(unsupported);
            if (serialNumber != null) {
                if (serialNumber.startsWith("N7")) return "ET-L10-WC21";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "ET-L10";
    }

    public boolean getNavigationBarState(BooleanParcelable unsupported) {
        unsupported.setValue(true);
        return true;
    }

    public void setNavigationBarState(boolean state, BooleanParcelable unsupported) {
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
