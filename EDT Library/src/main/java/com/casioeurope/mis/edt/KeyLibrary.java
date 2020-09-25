package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.constant.KeyLibraryConstant;
import com.casioeurope.mis.edt.type.ApplicationInfo;
import com.casioeurope.mis.edt.type.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Key Library<br/><br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibrary {

    private static KeyLibrary instance;

    private KeyLibrary() {
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

    private static KeyLibrary getInstance() {
        if (KeyLibrary.instance == null) {
            KeyLibrary.instance = new KeyLibrary();
        }
        return KeyLibrary.instance;
    }

    private IKeyLibrary edtServiceKeyLibrary() {
        return EDTServiceConnection.getInstance().getKeyLibrary();
    }

    /**
     * Get the key code generated when the specified key is pushed in 1/A/a mode.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @return {@code int}: The key code generated when the specified key is pushed in 1/A/a mode on success or:<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().getUserKeyCode(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Reset the key code generated when the specified key is pushed in 1/A/a mode to default Key Code setting.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setDefaultKeyCode(int nID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().setDefaultKeyCode(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Set the key code generated when the specified key is pushed in Fn mode.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @param KeyCode {@code int}: Specifies the Key Code for the target key.<br/>
     *                           To specify Android standard Key Codes, please refer to the constant definition from the {@link android.view.KeyEvent KeyEvent} class of the Android API.<br/>
     *                           To specify CASIO device specific Key Codes, please use the Key Codes defined in the {@link KeyLibraryConstant.KEYCODE KEYCODE} constants.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().setFnUserKeyCode(nID, KeyCode, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Set the key code generated when the specified key is pushed in 1/A/a mode.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @param KeyCode {@code int}: Specifies the Key Code for the target key.<br/>
     *                           To specify Android standard Key Codes, please refer to the constant definition from the {@link android.view.KeyEvent KeyEvent} class of the Android API.<br/>
     *                           To specify CASIO device specific Key Codes, please use the Key Codes defined in the {@link KeyLibraryConstant.KEYCODE KEYCODE} constants.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setUserKeyCode(int nID, int KeyCode) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().setUserKeyCode(nID, KeyCode, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the key code generated when the specified key is pushed in Fn mode.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @return {@code int}: The key code generated when the specified key is pushed in Fn mode on success or:<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getFnUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().getFnUserKeyCode(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Reset the key code generated when the specified key is pushed in Fn mode to default Key Code setting.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setFnDefaultKeyCode(int nID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().setFnDefaultKeyCode(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Set the application to be launched when the specified key is pushed in 1/A/a mode.<br/>
     * If you set the launched application with this function, the key code is not generated when the key is pushed. If you clear the launched application, the key code is generated again.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Specifies the {@link ApplicationInfo ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
        int retVal=getInstance().edtServiceKeyLibrary().setLaunchApplication(nID, appInfoParcelable, unsupported);
        appInfoParcelable.copyTo(appInfo);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the application to be launched when the specified key is pushed in 1/A/a mode.<br/>
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Acquires the {@link ApplicationInfo ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
        int retVal=getInstance().edtServiceKeyLibrary().getLaunchApplication(nID, appInfoParcelable, unsupported);
        appInfoParcelable.copyTo(appInfo);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Clear the application information to be launched when the specified key is pushed in 1/A/a mode.<br/>
     * If you clear the launched application, the key code for the given {@link KeyLibraryConstant.KEYID KEYID} is generated again.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int clearLaunchApplication(int nID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().clearLaunchApplication(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Set the application to be launched when the specified key is pushed in Fn mode.<br/>
     * If you set the launched application with this function, the key code is not generated when the key is pushed. If you clear the launched application, the key code is generated again.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Specifies the {@link ApplicationInfo ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
        int retVal=getInstance().edtServiceKeyLibrary().setFnLaunchApplication(nID, appInfoParcelable, unsupported);
        appInfoParcelable.copyTo(appInfo);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the application to be launched when the specified key is pushed in Fn mode.<br/>
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Acquires the {@link ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
        int retVal=getInstance().edtServiceKeyLibrary().getFnLaunchApplication(nID, appInfoParcelable, unsupported);
        appInfoParcelable.copyTo(appInfo);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Clear the application information to be launched when the specified key is pushed in Fn mode.<br/>
     * If you clear the launched application, the key code for the given {@link KeyLibraryConstant.KEYID KEYID} is generated again.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibraryConstant.KEYID KEYID} of the target key. {@link KeyLibraryConstant.KEYID KEYID} constants for each key can be found in the {@link KeyLibraryConstant.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int clearFnLaunchApplication(int nID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().edtServiceKeyLibrary().clearFnLaunchApplication(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     */
    public static boolean isMethodSupported(BigInteger method) {
        try {
            return getInstance().edtServiceKeyLibrary().isMethodSupported(method.toString());
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
            return getInstance().edtServiceKeyLibrary().isMethodNameSupported(methodName);
        } catch (Exception e) {
            return false;
        }
    }

}
