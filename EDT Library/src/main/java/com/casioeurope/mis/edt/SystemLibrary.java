package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> System Library<br/><br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class SystemLibrary {
    /**
     * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the "{@link java.lang.String String} getCASIOSerial()" method is supported on the currently active device
     */
    public static final BigInteger METHOD_GETCASIOSERIAL = new BigInteger("0001", 2);
    /**
     * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the "{@link java.lang.String String} getModelName()" method is supported on the currently active device
     */
    public static final BigInteger METHOD_GETMODELNAME = new BigInteger("0010", 2);
    /**
     * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the "{@code boolean} getNavigationBarState()" method is supported on the currently active device
     */
    public static final BigInteger METHOD_GETNAVIGATIONBARSTATE = new BigInteger("0100", 2);
    /**
     * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the "{@code void} setNavigationBarState({@code boolean} state)" method is supported on the currently active device
     */
    public static final BigInteger METHOD_SETNAVIGATIONBARSTATE = new BigInteger("1000", 2);
    private static SystemLibrary instance;

    private SystemLibrary() {
    }

    private static void checkMethodUnsupported(BooleanParcelable unsupported) throws UnsupportedOperationException {
        if (unsupported == null) return;
        if (!unsupported.getValue()) return;
        String nameOfCurrentMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrentMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrentMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        throw new UnsupportedOperationException("Method \"" + nameOfCurrentMethod + "\" is not supported on this device type!");
    }

    /**
     * Get the Serial Number {@link java.lang.String String} of your CASIO device
     *
     * @return {@link java.lang.String String}: The Serial Number of your CASIO device
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static String getCASIOSerial() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        String retVal = getInstance().systemLibraryService().getCASIOSerial(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    private static SystemLibrary getInstance() {
        if (SystemLibrary.instance == null) {
            SystemLibrary.instance = new SystemLibrary();
        }
        return SystemLibrary.instance;
    }

    /**
     * Get the Model Name {@link java.lang.String String} of your CASIO device
     *
     * @return {@link java.lang.String String}: The Model Name of your CASIO device
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static String getModelName() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        String retVal = getInstance().systemLibraryService().getModelName(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the Visibility State of the Navigation Bar
     *
     * @return {@code boolean}: Whether or not the Navigation Bar is visible
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean getNavigationBarState() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        boolean retVal=getInstance().systemLibraryService().getNavigationBarState(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Set the Visibility State of the Navigation Bar
     *
     * @param state {@code boolean}: Whether or not the Navigation Bar shall be visible
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void setNavigationBarState(boolean state) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        getInstance().systemLibraryService().setNavigationBarState(state, unsupported);
        checkMethodUnsupported(unsupported);
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     */
    public static boolean isMethodSupported(BigInteger method) {
        try {
            return getInstance().systemLibraryService().isMethodSupported(method.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link String String} methodName parameter is supported on the currently active device
     *
     * @param methodName {@link String String}: Name of the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     */
    public static boolean isMethodSupported(String methodName) {
        try {
            return getInstance().systemLibraryService().isMethodNameSupported(methodName);
        } catch (Exception e) {
            return false;
        }
    }

    private ISystemLibrary systemLibraryService() {
        return EDTServiceConnection.getInstance().getSystemLibrary();
    }
}
