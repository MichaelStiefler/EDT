package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.types.ApplicationInfo;
import com.casioeurope.mis.edt.types.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.types.BooleanParcelable;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Key Library<br/><br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public class KeyLibrary {

    /**
     * Constants used in the {@link KeyLibrary} class.
     */
    public static class CONSTANT {

        /**
         * Key Codes used in the {@link KeyLibrary} class.
         */
        public static class KEYCODE {
            /**
             * Key Code for No Key
             */
            public static final int KEYCODE_BLANK = 0x70000001;
            /**
             * Key Code for Backside Trigger Key
             */
            public static final int KEYCODE_TRIGGER_BACK = 1013;
            /**
             * Key Code for Center Trigger Key
             */
            public static final int KEYCODE_TRIGGER_CENTER = 1010;
            /**
             * Key Code for Left Trigger Key
             */
            public static final int KEYCODE_TRIGGER_LEFT = 1012;
            /**
             * Key Code for Right Trigger Key
             */
            public static final int KEYCODE_TRIGGER_RIGHT = 1011;
        }

        /**
         * Key IDs used in the {@link KeyLibrary} class.
         */
        public static class KEYID {
            /**
             * Key ID for App Switch Key
             */
            public static final int APPSWITCH = 3;
            /**
             * Key ID for Back Key
             */
            public static final int BACK = 4;
            /**
             * Key ID for Backside Trigger Key
             */
            public static final int BACKTRIGGER = 27;
            /**
             * Key ID for Center Trigger Key
             */
            public static final int CENTERTRIGGER = 26;
            /**
             * Key ID for Clear (CLR) Key
             */
            public static final int CLEAR = 28;
            /**
             * Key ID for Down Key
             */
            public static final int DOWN = 32;
            /**
             * Key ID for Enter Key
             */
            public static final int ENTER = 29;
            /**
             * Key ID for F1 Key
             */
            public static final int F1 = 18;
            /**
             * Key ID for F2 Key
             */
            public static final int F2 = 19;
            /**
             * Key ID for F3 Key
             */
            public static final int F3 = 20;
            /**
             * Key ID for F4 Key
             */
            public static final int F4 = 21;
            /**
             * Key ID for F5 Key
             */
            public static final int F5 = 22;
            /**
             * Key ID for F6 Key
             */
            public static final int F6 = 23;
            /**
             * Key ID for F7 Key
             */
            public static final int F7 = 24;
            /**
             * Key ID for F8 Key
             */
            public static final int F8 = 25;
            /**
             * Key ID for Function (Fn) Key
             */
            public static final int FUNCTION = 5;
            /**
             * Key ID for numeric 0 Key
             */
            public static final int KEY0 = 17;
            /**
             * Key ID for numeric 1 Key
             */
            public static final int KEY1 = 8;
            /**
             * Key ID for numeric 2 Key
             */
            public static final int KEY2 = 9;
            /**
             * Key ID for numeric 3 Key
             */
            public static final int KEY3 = 10;
            /**
             * Key ID for numeric 4 Key
             */
            public static final int KEY4 = 11;
            /**
             * Key ID for numeric 5 Key
             */
            public static final int KEY5 = 12;
            /**
             * Key ID for numeric 6 Key
             */
            public static final int KEY6 = 13;
            /**
             * Key ID for numeric 7 Key
             */
            public static final int KEY7 = 14;
            /**
             * Key ID for numeric 8 Key
             */
            public static final int KEY8 = 15;
            /**
             * Key ID for numeric 9 Key
             */
            public static final int KEY9 = 16;
            /**
             * Key ID for Left Key
             */
            public static final int LEFT = 33;
            /**
             * Key ID for Left Trigger Key
             */
            public static final int LEFTTRIGGER = 2;
            /**
             * Key ID for Minus (-) Key
             */
            public static final int MINUS = 36;
            /**
             * Key ID for Period (.) Key
             */
            public static final int PERIOD = 30;
            /**
             * Key ID for Right Key
             */
            public static final int RIGHT = 34;
            /**
             * Key ID for Right Trigger Key
             */
            public static final int RIGHTTRIGGER = 1;
            /**
             * Key ID for Space Key
             */
            public static final int SPACE = 35;
            /**
             * Key ID for Up Key
             */
            public static final int UP = 31;
            /**
             * Key ID for Volume Down Key
             */
            public static final int VOLUMEDOWN = 7;
            /**
             * Key ID for Volume Up Key
             */
            public static final int VOLUMEUP = 6;
        }

        /**
         * Return Codes to be returned by the methods of the {@link KeyLibrary} class.
         */
        public static class RETURN {
            /**
             * An internal Error occured
             */
            public static final int ERROR_FUNCTION = -2;
            /**
             * The Method call is unsupported
             */
            public static final int ERROR_NOTSUPPORTED = -1;
            /**
             * The method has been called successfully
             */
            public static final int SUCCESS = 0;
        }

        /**
         * Methods of the {@link KeyLibrary} class, used e.g. to check availability of said methods using {@link #isMethodSupported(BigInteger)} method.
         */
        public static class METHOD {
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setUserKeyCode(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETUSERKEYCODE = new BigInteger("000000000001", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getUserKeyCode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETUSERKEYCODE = new BigInteger("000000000010", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setDefaultKeyCode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETDEFAULTKEYCODE = new BigInteger("000000000100", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setFnUserKeyCode(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETFNUSERKEYCODE = new BigInteger("000000001000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getFnUserKeyCode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETFNUSERKEYCODE = new BigInteger("000000010000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setFnDefaultKeyCode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETFNDEFAULTKEYCODE = new BigInteger("000000100000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETLAUNCHAPPLICATION = new BigInteger("000001000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETLAUNCHAPPLICATION = new BigInteger("000010000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #clearLaunchApplication(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_CLEARLAUNCHAPPLICATION = new BigInteger("000100000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setFnLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETFNLAUNCHAPPLICATION = new BigInteger("001000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getFnLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETFNLAUNCHAPPLICATION = new BigInteger("010000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #clearFnLaunchApplication(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_CLEARFNLAUNCHAPPLICATION = new BigInteger("100000000000", 2);
        }
    }

    private static KeyLibrary instance;

    private KeyLibrary() {
    }

    private static void checkMethodUnsupported(BooleanParcelable unsupported) throws UnsupportedOperationException {
        if (unsupported == null) return;
        if (!unsupported.isUnsupported()) return;
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @return {@code int}: The key code generated when the specified key is pushed in 1/A/a mode on success or:<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @param KeyCode {@code int}: Specifies the Key Code for the target key.<br/>
     *                           To specify Android standard Key Codes, please refer to the constant definition from the {@link android.view.KeyEvent KeyEvent} class of the Android API.<br/>
     *                           To specify CASIO device specific Key Codes, please use the Key Codes defined in the {@link KeyLibrary.CONSTANT.KEYCODE KEYCODE} constants.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @param KeyCode {@code int}: Specifies the Key Code for the target key.<br/>
     *                           To specify Android standard Key Codes, please refer to the constant definition from the {@link android.view.KeyEvent KeyEvent} class of the Android API.<br/>
     *                           To specify CASIO device specific Key Codes, please use the Key Codes defined in the {@link KeyLibrary.CONSTANT.KEYCODE KEYCODE} constants.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @return {@code int}: The key code generated when the specified key is pushed in Fn mode on success or:<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Specifies the {@link ApplicationInfo ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Acquires the {@link ApplicationInfo ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * If you clear the launched application, the key code for the given {@link KeyLibrary.CONSTANT.KEYID KEYID} is generated again.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Specifies the {@link ApplicationInfo ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @param appInfo {@link ApplicationInfo}: Acquires the {@link ApplicationInfo} of the Application to be launched.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
     * If you clear the launched application, the key code for the given {@link KeyLibrary.CONSTANT.KEYID KEYID} is generated again.
     *
     * @param nID {@code int}: Specifies the {@link KeyLibrary.CONSTANT.KEYID KEYID} of the target key. {@link KeyLibrary.CONSTANT.KEYID KEYID} constants for each key can be found in the {@link KeyLibrary.CONSTANT.KEYID KEYID} class.
     * @return {@code int}:<br/>
     *     {@link CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link CONSTANT.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error<br/>
     *     {@link CONSTANT.RETURN#ERROR_FUNCTION ERROR_FUNCTION}: Internal error
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
