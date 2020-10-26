package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.constant.KeyLibraryConstant;
import com.casioeurope.mis.edt.type.ApplicationInfo;
import com.casioeurope.mis.edt.type.ApplicationInfoParcelable;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Key Library<br/><br/>
 *
 * @apiNote The SAM Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly adviced not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link KeyLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link KeyLibrary#setDefaultKeyCode(int) KeyLibrary.setDefaultKeyCode(KeyLibraryConstant.KEYID.CENTERTRIGGER)} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>KeyLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         KeyLibrary.setDefaultKeyCode(KeyLibraryConstant.KEYID.CENTERTRIGGER);
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>KeyLibrary.onLibraryReady(() -> { KeyLibrary.setDefaultKeyCode(KeyLibraryConstant.KEYID.CENTERTRIGGER); });</pre>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "deprecation", "JavadocReference", "SpellCheckingInspection"})
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
        checkMethodUnsupported(nameOfCurrentMethod, unsupported);
    }

    private static void checkMethodUnsupported(String methodName, BooleanParcelable unsupported) throws UnsupportedOperationException {
        if (unsupported == null) return;
        if (!unsupported.getValue()) return;
        throw new UnsupportedOperationException("Method \"" + methodName + "\" is not supported on this device type!");
    }

    private static KeyLibrary getInstance() {
        if (KeyLibrary.instance == null) {
            KeyLibrary.instance = new KeyLibrary();
        }
        return KeyLibrary.instance;
    }

    private IKeyLibrary keyLibraryService() {
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
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static int getUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().keyLibraryService().getUserKeyCode(nID, unsupported);
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
        int retVal=getInstance().keyLibraryService().setDefaultKeyCode(nID, unsupported);
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
        int retVal=getInstance().keyLibraryService().setFnUserKeyCode(nID, KeyCode, unsupported);
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
        int retVal=getInstance().keyLibraryService().setUserKeyCode(nID, KeyCode, unsupported);
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
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static int getFnUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal=getInstance().keyLibraryService().getFnUserKeyCode(nID, unsupported);
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
        int retVal=getInstance().keyLibraryService().setFnDefaultKeyCode(nID, unsupported);
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
        int retVal=getInstance().keyLibraryService().setLaunchApplication(nID, appInfoParcelable, unsupported);
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
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
        int retVal=getInstance().keyLibraryService().getLaunchApplication(nID, appInfoParcelable, unsupported);
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
        int retVal=getInstance().keyLibraryService().clearLaunchApplication(nID, unsupported);
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
        int retVal=getInstance().keyLibraryService().setFnLaunchApplication(nID, appInfoParcelable, unsupported);
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
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
        int retVal=getInstance().keyLibraryService().getFnLaunchApplication(nID, appInfoParcelable, unsupported);
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
        int retVal=getInstance().keyLibraryService().clearFnLaunchApplication(nID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
        return Implementation.isMethodSupported(method);
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link String String} methodName parameter is supported on the currently active device
     *
     * @param methodName {@link String String}: Name of the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static boolean isMethodSupported(String methodName) throws IllegalStateException {
        return Implementation.isMethodSupported(methodName);
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the KeyLibrary Service becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addKeyLibraryCallback(callback);
    }

    private static final class Implementation {
        private static int getUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal=getInstance().keyLibraryService().getUserKeyCode(nID, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int setDefaultKeyCode(int nID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setDefaultKeyCode(nID, unsupported);
                    checkMethodUnsupported("setDefaultKeyCode", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().setDefaultKeyCode(nID, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int setFnUserKeyCode(int nID, int KeyCode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setFnUserKeyCode(nID, KeyCode, unsupported);
                    checkMethodUnsupported("setFnUserKeyCode", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().setFnUserKeyCode(nID, KeyCode, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int setUserKeyCode(int nID, int KeyCode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setUserKeyCode(nID, KeyCode, unsupported);
                    checkMethodUnsupported("setUserKeyCode", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().setUserKeyCode(nID, KeyCode, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int getFnUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal=getInstance().keyLibraryService().getFnUserKeyCode(nID, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int setFnDefaultKeyCode(int nID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setFnDefaultKeyCode(nID, unsupported);
                    checkMethodUnsupported("setFnDefaultKeyCode", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().setFnDefaultKeyCode(nID, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int setLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setLaunchApplication(nID, appInfoParcelable, unsupported);
                    checkMethodUnsupported("setLaunchApplication", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().setLaunchApplication(nID, appInfoParcelable, unsupported);
            appInfoParcelable.copyTo(appInfo);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
            int retVal=getInstance().keyLibraryService().getLaunchApplication(nID, appInfoParcelable, unsupported);
            appInfoParcelable.copyTo(appInfo);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int clearLaunchApplication(int nID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().clearLaunchApplication(nID, unsupported);
                    checkMethodUnsupported("clearLaunchApplication", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().clearLaunchApplication(nID, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int setFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setFnLaunchApplication(nID, appInfoParcelable, unsupported);
                    checkMethodUnsupported("setFnLaunchApplication", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().setFnLaunchApplication(nID, appInfoParcelable, unsupported);
            appInfoParcelable.copyTo(appInfo);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
            int retVal=getInstance().keyLibraryService().getFnLaunchApplication(nID, appInfoParcelable, unsupported);
            appInfoParcelable.copyTo(appInfo);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static int clearFnLaunchApplication(int nID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().clearFnLaunchApplication(nID, unsupported);
                    checkMethodUnsupported("clearFnLaunchApplication", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().clearFnLaunchApplication(nID, unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().keyLibraryService().isMethodSupported(method.toString());
            } catch (Exception e) {
                return false;
            }
        }

        private static boolean isMethodSupported(String methodName) throws IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().keyLibraryService().isMethodNameSupported(methodName);
            } catch (Exception e) {
                return false;
            }
        }
    }

}
