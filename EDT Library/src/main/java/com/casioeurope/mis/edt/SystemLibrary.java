package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> System Library<br/><br/>
 *
 * @apiNote The System Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly adviced not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link SystemLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link SystemLibrary#setNavigationBarState(boolean) SystemLibrary.setNavigationBarState(false)} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>SystemLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         SystemLibrary.setNavigationBarState(false);
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>SystemLibrary.onLibraryReady(() -> { SystemLibrary.setNavigationBarState(false); });</pre>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "deprecation", "JavadocReference", "SpellCheckingInspection"})
public class SystemLibrary {
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
        checkMethodUnsupported(nameOfCurrentMethod, unsupported);
    }

    private static void checkMethodUnsupported(String methodName, BooleanParcelable unsupported) throws UnsupportedOperationException {
        if (unsupported == null) return;
        if (!unsupported.getValue()) return;
        throw new UnsupportedOperationException("Method \"" + methodName + "\" is not supported on this device type!");
    }

    /**
     * Get the Serial Number {@link java.lang.String String} of your CASIO device
     *
     * @return {@link java.lang.String String}: The Serial Number of your CASIO device
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SystemLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SystemLibrary this class} for further details.
     */
    public static String getCASIOSerial() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getCASIOSerial();
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
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SystemLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SystemLibrary this class} for further details.
     */
    public static String getModelName() throws RemoteException, UnsupportedOperationException, IllegalStateException{
        return Implementation.getModelName();
    }

    /**
     * Get the Visibility State of the Navigation Bar
     *
     * @return {@code boolean}: Whether or not the Navigation Bar is visible
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SystemLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SystemLibrary this class} for further details.
     */
    public static boolean getNavigationBarState() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getNavigationBarState();
    }

    /**
     * Set the Visibility State of the Navigation Bar
     *
     * @param state {@code boolean}: Whether or not the Navigation Bar shall be visible
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void setNavigationBarState(boolean state) throws RemoteException, UnsupportedOperationException {
        Implementation.setNavigationBarState(state);
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SystemLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SystemLibrary this class} for further details.
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
     *                      In such case, please use {@link SystemLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SystemLibrary this class} for further details.
     */
    public static boolean isMethodSupported(String methodName) throws IllegalStateException {
        return Implementation.isMethodSupported(methodName);
    }

    private ISystemLibrary systemLibraryService() {
        return EDTServiceConnection.getInstance().getSystemLibrary();
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the System Library Service becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addSystemLibraryCallback(callback);
    }

    @SuppressWarnings("SpellCheckingInspection")
    private static final class Implementation {
        private static String getCASIOSerial() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().systemLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().systemLibraryService().getCASIOSerial(unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static String getModelName() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().systemLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().systemLibraryService().getModelName(unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static boolean getNavigationBarState() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().systemLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal=getInstance().systemLibraryService().getNavigationBarState(unsupported);
            checkMethodUnsupported(unsupported);
            return retVal;
        }

        private static void setNavigationBarState(boolean state) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().systemLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().systemLibraryService().setNavigationBarState(state, unsupported);
                    checkMethodUnsupported("setNavigationBarState", unsupported);
                });
                return;
            }
            getInstance().systemLibraryService().setNavigationBarState(state, unsupported);
            checkMethodUnsupported(unsupported);
        }

        private static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
            if (getInstance().systemLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().systemLibraryService().isMethodSupported(method.toString());
            } catch (Exception e) {
                return false;
            }
        }

        private static boolean isMethodSupported(String methodName) throws IllegalStateException {
            if (getInstance().systemLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().systemLibraryService().isMethodNameSupported(methodName);
            } catch (Exception e) {
                return false;
            }
        }

    }

}
