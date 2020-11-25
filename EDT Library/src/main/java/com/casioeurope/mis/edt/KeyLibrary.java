package com.casioeurope.mis.edt;

import android.os.RemoteException;
import android.view.KeyEvent;

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
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly advised not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link KeyLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link KeyLibrary#setDefaultKeyCode(int) KeyLibrary.setDefaultKeyCode(KeyLibraryConstant.KEYID.CENTERTRIGGER)} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>KeyLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         KeyLibrary.setDefaultKeyCode(KeyLibraryConstant.KEYID.CENTERTRIGGER);
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>KeyLibrary.onLibraryReady(() -&gt; { KeyLibrary.setDefaultKeyCode(KeyLibraryConstant.KEYID.CENTERTRIGGER); });</pre>
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
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static int getUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getUserKeyCode(nID);
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
        return Implementation.setDefaultKeyCode(nID);
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
        return Implementation.setFnUserKeyCode(nID, KeyCode);
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
        return Implementation.setUserKeyCode(nID, KeyCode);
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
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static int getFnUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getFnUserKeyCode(nID);
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
        return Implementation.setFnDefaultKeyCode(nID);
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
        return Implementation.setLaunchApplication(nID, appInfo);
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
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getLaunchApplication(nID, appInfo);
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
        return Implementation.clearLaunchApplication(nID);
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
        return Implementation.setFnLaunchApplication(nID, appInfo);
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
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getFnLaunchApplication(nID, appInfo);
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
        return Implementation.clearFnLaunchApplication(nID);
    }

    /**
     * Sends an ordered broadcast key event to all registered receivers for the given intent.<br/>
     * Use this method to circumvent permission issues on key broadcasts with latest android versions.
     *
     * @param action {@link String String}: Specifies the {@link android.content.Intent Intent}'s action parameter for the broadcast key event. {@link android.content.BroadcastReceiver BroadcastReceiver}s need to register to matching actions to receive the broadcast.
     * @param extra {@link String String}: Specifies the {@link android.content.Intent Intent}'s extra parameter for the broadcast key event.
     * @param event {@link KeyEvent KeyEvent}: Specifies the {@link android.view.KeyEvent KeyEvent} to be broadcasted.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void broadcastKey(String action, String extra, KeyEvent event) throws RemoteException, UnsupportedOperationException {
        Implementation.broadcastKey(action, extra, event);
    }

    /**
     * Change the current Key Character Map File to a new one, providing the bytes that make up the new Key Character Map file
     *
     * @param path {@link String String}: Specifies the <a href="https://source.android.com/devices/input/key-character-map-files">Key Character Map File</a> to be used.<br/>
     *                                  The filename may or may not contain a full path.
     * @param data {@code byte[]}: The data of the new Key Character Map File.
     * @return {@code int}:<br/>
     *     {@link KeyLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *     {@link KeyLibraryConstant.RETURN#ERROR_KCM ERROR_KCM}: Error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int changeKCMapFile(String path, byte[] data) throws RemoteException, UnsupportedOperationException {
        return Implementation.changeKCMapFile(path, data);
    }

    /**
     * Change the current Key Character Map File to the System Default one
     *
     * @return {@code boolean}: Returns true on success and false on failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean changeKCMapFileToDefault() throws RemoteException, UnsupportedOperationException {
        return Implementation.changeKCMapFileToDefault();
    }

    /**
     * Update the Keyboard's Tray Icon to reflect a state as if the given {@link android.view.KeyEvent KeyEvent} would just have occured
     *
     * @param event {@link KeyEvent KeyEvent}: Specifies the {@link android.view.KeyEvent KeyEvent} that reflects the desired Keyboard's Tray Icon state
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void changeTrayIcon(KeyEvent event) throws RemoteException, UnsupportedOperationException {
        Implementation.changeTrayIcon(event);
    }

    /**
     * Get the current Key Character Map File name
     *
     * @return {@link String String}: Returns the name of the current Key Character Map File, or null on failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static String getCurrentKCMapFile() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getCurrentKCMapFile();
    }

    /**
     * Get the currently active Input Mode for the Hardware Keyboard
     *
     * @return {@code int}: Currently active Keyboard Input Mode.<br/>
     *                        Possible values are:<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_NUMERIC INPUT_MODE_NUMERIC}: Numeric Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_SMALL_ALPHA INPUT_MODE_SMALL_ALPHA}: Alpha (Small Letters) Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_CAPITAL_ALPHA INPUT_MODE_CAPITAL_ALPHA}: Alpha (Capital Letters) Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_FN INPUT_MODE_FN}: Fn Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.RETURN#ERROR_NOTSUPPORTED ERROR_NOTSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static int getKeypadMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getKeypadMode();
    }

    /**
     * Check whether the Hardware Keyboard is running in "Test Mode"
     *
     * @return {@code int}: 0 if Test Mode is disabled, other values indicate certain kind of test modes
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static int getTestMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getTestMode();
    }

    /**
     * Check whether a certain keycode corresponds to a key on the Hardware Keyboard
     *
     * @param keyCode {@code int}: Specifies the keycode to be checked
     * @return {@code boolean}: true if the keycode is available on the Hardware Keyboard, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean hasHardwareKey(int keyCode) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.hasHardwareKey(keyCode);
    }

    /**
     * Check whether a certain {@link android.view.KeyEvent KeyEvent} can act as Wakeup Resource
     *
     * @param event {@link KeyEvent KeyEvent}: Specifies the {@link android.view.KeyEvent KeyEvent} to be checked
     * @return {@code boolean}: true if the {@link android.view.KeyEvent KeyEvent} can act as Wakeup Resource, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean hasWakeupRes(KeyEvent event) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.hasWakeupRes(event);
    }

    /**
     * Convert a KeyEvent according to alternative mapping tables and apply it after conversion
     *
     * @param event {@link KeyEvent KeyEvent}: Specifies the {@link android.view.KeyEvent KeyEvent} to be converted and applied
     * @param useCache {@code boolean}: True if events shall be cached, otherwise false
     * @return {@code boolean}: true if the {@link android.view.KeyEvent KeyEvent} could be converted and applied, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean hijackingKey(KeyEvent event, boolean useCache) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.hijackingKey(event, useCache);
    }

    /**
     * Check whether "Direct Input Mode" is active or not.
     *
     * @return {@code boolean}: true if "Direct Input Mode" is active, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean isDirectInputStyle() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.isDirectInputStyle();
    }

    /**
     * Check whether all cached Key Events have been processed
     *
     * @return {@code boolean}: true if all cached Key Events have been processed, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean isFinishedHandle() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.isFinishedHandle();
    }

    /**
     * Check whether key input is routed through the hardware keyboard driver
     *
     * @return {@code boolean}: true if key input is routed through the hardware keyboard driver, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean isKeyControlMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.isKeyControlMode();
    }

    /**
     * Check whether a certain keycode is set to act as Wakeup Resource
     *
     * @param keyCode {@code int}: The keycode to be checked
     * @return {@code boolean}: true if the keycode is set to act as Wakeup Resource, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean isWakeupRes(int keyCode) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.isWakeupRes(keyCode);
    }

    /**
     * Apply the configured Feedback (Sound, Vibration, Wakeup, Tray Icon) for the corresponding {@link KeyEvent KeyEvent}
     *
     * @param event {@link KeyEvent KeyEvent}: Specifies the {@link android.view.KeyEvent KeyEvent} for which the Feedback shall be performed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void performKeyPressFeedback(KeyEvent event) throws RemoteException, UnsupportedOperationException {
        Implementation.performKeyPressFeedback(event);
    }

    /**
     * Removes the current Key Character Map File and changes back to the System Default one
     *
     * @return {@code boolean}: Returns true on success and false on failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean removeKCMapFile() throws RemoteException, UnsupportedOperationException {
        return Implementation.removeKCMapFile();
    }

    /**
     * Set whether the "Direct Input Mode" shall be used
     *
     * @param enable {@code boolean}: true if "Direct Input Mode" shall be used, otherwise false
     * @return {@code boolean}: true if the setting could be applied, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean setDirectInputStyle(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDirectInputStyle(enable);
    }

    /**
     * Set whether the Input Mode shall be fixed to numeric input
     *
     * @param on {@code boolean}: true if the Input Mode shall be fixed to numeric input, otherwise false
     * @return {@code boolean}: true if the setting could be applied, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean setFixedNumberMode(boolean on) throws RemoteException, UnsupportedOperationException {
        return Implementation.setFixedNumberMode(on);
    }

    /**
     * Set whether key input shall be routed through the hardware keyboard driver
     *
     * @param enable {@code boolean}: true if the key input shall be routed through the hardware keyboard driver, otherwise false
     * @return {@code boolean}: true if the setting could be applied, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean setKeyControlMode(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setKeyControlMode(enable);
    }

    /**
     * Activate a certain Input Mode for the Hardware Keyboard
     *
     * @param mode {@code int}: Keyboard Input Mode to be used.<br/>
     *                        Valid values are:<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_NUMERIC INPUT_MODE_NUMERIC}: Numeric Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_SMALL_ALPHA INPUT_MODE_SMALL_ALPHA}: Alpha (Small Letters) Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_CAPITAL_ALPHA INPUT_MODE_CAPITAL_ALPHA}: Alpha (Capital Letters) Keyboard Input Mode<br/>
     *                        {@link KeyLibraryConstant.INPUT_MODE#INPUT_MODE_FN INPUT_MODE_FN}: Fn Keyboard Input Mode<br/>
     * @return {@code boolean}: true if the setting could be applied, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean setKeypadMode(int mode) throws RemoteException, UnsupportedOperationException {
        return Implementation.setKeypadMode(mode);
    }

    /**
     * Activate or deactivate a certain keyboard resource to act as Wakeup Resource
     *
     * @param resourceID {@code int}: The keyboard resource to act as Wakeup Resource
     * @param enable {@code boolean}: true if the keyboard resource shall act as Wakeup Resource, otherwise false
     * @return {@code boolean}: true if the setting could be applied, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean setWakeupRes(int resourceID, boolean enabled) throws RemoteException, UnsupportedOperationException {
        return Implementation.setWakeupRes(resourceID, enabled);
    }

    /**
     * Update the Tray Icon Meta State for the corresponding {@link KeyEvent KeyEvent}
     *
     * @param event {@link KeyEvent KeyEvent}: Specifies the {@link android.view.KeyEvent KeyEvent} for which the Tray Icon Meta State shall be updated
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void updateMetaState(KeyEvent event) throws RemoteException, UnsupportedOperationException {
        Implementation.updateMetaState(event);
    }

    /**
     * Check whether key input is limited to numeric input only
     *
     * @return {@code boolean}: true if key input is limited to numeric input only, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
     */
    public static boolean getRestrictInputMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getRestrictInputMode();
    }

    /**
     * Set whether the Input Mode shall be fixed to numeric input
     *
     * @param enable {@code boolean}: true if the Input Mode shall be fixed to numeric input, otherwise false
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void setRestrictInputMode(boolean enable) throws RemoteException, UnsupportedOperationException {
        Implementation.setRestrictInputMode(enable);
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
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
     *                      In such case, please use {@link KeyLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link KeyLibrary this class} for further details.
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
            checkMethodUnsupported("getUserKeyCode", unsupported);
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
            checkMethodUnsupported("setDefaultKeyCode", unsupported);
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
            checkMethodUnsupported("setFnUserKeyCode", unsupported);
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
            checkMethodUnsupported("setUserKeyCode", unsupported);
            return retVal;
        }

        private static int getFnUserKeyCode(int nID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal=getInstance().keyLibraryService().getFnUserKeyCode(nID, unsupported);
            checkMethodUnsupported("getFnUserKeyCode", unsupported);
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
            checkMethodUnsupported("setFnDefaultKeyCode", unsupported);
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
            checkMethodUnsupported("setLaunchApplication", unsupported);
            return retVal;
        }

        private static int getLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
            int retVal=getInstance().keyLibraryService().getLaunchApplication(nID, appInfoParcelable, unsupported);
            appInfoParcelable.copyTo(appInfo);
            checkMethodUnsupported("getLaunchApplication", unsupported);
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
            checkMethodUnsupported("clearLaunchApplication", unsupported);
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
            checkMethodUnsupported("setFnLaunchApplication", unsupported);
            return retVal;
        }

        private static int getFnLaunchApplication(int nID, ApplicationInfo appInfo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            ApplicationInfoParcelable appInfoParcelable = new ApplicationInfoParcelable(appInfo);
            int retVal=getInstance().keyLibraryService().getFnLaunchApplication(nID, appInfoParcelable, unsupported);
            appInfoParcelable.copyTo(appInfo);
            checkMethodUnsupported("getFnLaunchApplication", unsupported);
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
            checkMethodUnsupported("clearFnLaunchApplication", unsupported);
            return retVal;
        }

        private static void broadcastKey(String action, String extra, KeyEvent event) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().broadcastKey(action, extra, event, unsupported);
                    checkMethodUnsupported("broadcastKey", unsupported);
                });
                return;
            }
            getInstance().keyLibraryService().broadcastKey(action, extra, event, unsupported);
            checkMethodUnsupported("broadcastKey", unsupported);
        }

        private static int changeKCMapFile(String path, byte[] data) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().changeKCMapFile(path, data, unsupported);
                    checkMethodUnsupported("changeKCMapFile", unsupported);
                });
                return KeyLibraryConstant.RETURN.SUCCESS;
            }
            int retVal=getInstance().keyLibraryService().changeKCMapFile(path, data, unsupported);
            checkMethodUnsupported("changeKCMapFile", unsupported);
            return retVal;
        }

        private static boolean changeKCMapFileToDefault() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().changeKCMapFileToDefault(unsupported);
                    checkMethodUnsupported("changeKCMapFileToDefault", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().changeKCMapFileToDefault(unsupported);
            checkMethodUnsupported("changeKCMapFileToDefault", unsupported);
            return retVal;
        }

        private static void changeTrayIcon(KeyEvent event) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().changeTrayIcon(event, unsupported);
                    checkMethodUnsupported("changeTrayIcon", unsupported);
                });
                return;
            }
            getInstance().keyLibraryService().changeTrayIcon(event, unsupported);
            checkMethodUnsupported("changeTrayIcon", unsupported);
        }

        private static String getCurrentKCMapFile() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal=getInstance().keyLibraryService().getCurrentKCMapFile(unsupported);
            checkMethodUnsupported("getCurrentKCMapFile", unsupported);
            return retVal;
        }

        private static int getKeypadMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal=getInstance().keyLibraryService().getKeypadMode(unsupported);
            checkMethodUnsupported("getKeypadMode", unsupported);
            return retVal;
        }

        private static int getTestMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal=getInstance().keyLibraryService().getTestMode(unsupported);
            checkMethodUnsupported("getTestMode", unsupported);
            return retVal;
        }

        private static boolean hasHardwareKey(int keyCode) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().hasHardwareKey(keyCode, unsupported);
            checkMethodUnsupported("hasHardwareKey", unsupported);
            return retVal;
        }

        private static boolean hasWakeupRes(KeyEvent event) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().hasWakeupRes(event, unsupported);
            checkMethodUnsupported("hasWakeupRes", unsupported);
            return retVal;
        }

        private static boolean hijackingKey(KeyEvent event, boolean useCache) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().hijackingKey(event, useCache, unsupported);
            checkMethodUnsupported("hijackingKey", unsupported);
            return retVal;
        }

        private static boolean isDirectInputStyle() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().isDirectInputStyle(unsupported);
            checkMethodUnsupported("isDirectInputStyle", unsupported);
            return retVal;
        }

        private static boolean isFinishedHandle() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().isFinishedHandle(unsupported);
            checkMethodUnsupported("isFinishedHandle", unsupported);
            return retVal;
        }

        private static boolean isKeyControlMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().isKeyControlMode(unsupported);
            checkMethodUnsupported("isKeyControlMode", unsupported);
            return retVal;
        }

        private static boolean isWakeupRes(int keyCode) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().isWakeupRes(keyCode, unsupported);
            checkMethodUnsupported("isWakeupRes", unsupported);
            return retVal;
        }

        private static void performKeyPressFeedback(KeyEvent event) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().performKeyPressFeedback(event, unsupported);
                    checkMethodUnsupported("performKeyPressFeedback", unsupported);
                });
                return;
            }
            getInstance().keyLibraryService().performKeyPressFeedback(event, unsupported);
            checkMethodUnsupported("performKeyPressFeedback", unsupported);
        }

        private static boolean removeKCMapFile() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().removeKCMapFile(unsupported);
                    checkMethodUnsupported("removeKCMapFile", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().removeKCMapFile(unsupported);
            checkMethodUnsupported("removeKCMapFile", unsupported);
            return retVal;
        }

        private static boolean setDirectInputStyle(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setDirectInputStyle(enable, unsupported);
                    checkMethodUnsupported("setDirectInputStyle", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().setDirectInputStyle(enable, unsupported);
            checkMethodUnsupported("setDirectInputStyle", unsupported);
            return retVal;
        }

        private static boolean setFixedNumberMode(boolean on) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setFixedNumberMode(on, unsupported);
                    checkMethodUnsupported("setFixedNumberMode", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().setFixedNumberMode(on, unsupported);
            checkMethodUnsupported("setFixedNumberMode", unsupported);
            return retVal;
        }

        private static boolean setKeyControlMode(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setKeyControlMode(enable, unsupported);
                    checkMethodUnsupported("setKeyControlMode", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().setKeyControlMode(enable, unsupported);
            checkMethodUnsupported("setKeyControlMode", unsupported);
            return retVal;
        }

        private static boolean setKeypadMode(int mode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setKeypadMode(mode, unsupported);
                    checkMethodUnsupported("setKeypadMode", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().setKeypadMode(mode, unsupported);
            checkMethodUnsupported("setKeypadMode", unsupported);
            return retVal;
        }

        private static boolean setWakeupRes(int resourceID, boolean enabled) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setWakeupRes(resourceID, enabled, unsupported);
                    checkMethodUnsupported("setWakeupRes", unsupported);
                });
                return true;
            }
            boolean retVal=getInstance().keyLibraryService().setWakeupRes(resourceID, enabled, unsupported);
            checkMethodUnsupported("setWakeupRes", unsupported);
            return retVal;
        }

        private static void updateMetaState(KeyEvent event) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().updateMetaState(event, unsupported);
                    checkMethodUnsupported("updateMetaState", unsupported);
                });
                return;
            }
            getInstance().keyLibraryService().updateMetaState(event, unsupported);
            checkMethodUnsupported("updateMetaState", unsupported);
        }

        private static boolean getRestrictInputMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().keyLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().keyLibraryService().getRestrictInputMode(unsupported);
            checkMethodUnsupported("getRestrictInputMode", unsupported);
            return retVal;
        }

        private static void setRestrictInputMode(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().keyLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().keyLibraryService().setRestrictInputMode(enable, unsupported);
                    checkMethodUnsupported("setRestrictInputMode", unsupported);
                });
                return;
            }
            getInstance().keyLibraryService().setRestrictInputMode(enable, unsupported);
            checkMethodUnsupported("setRestrictInputMode", unsupported);
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
