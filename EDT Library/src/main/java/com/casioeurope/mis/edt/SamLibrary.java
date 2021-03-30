package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.constant.SamLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> SAM Library<br/><br/>
 *
 * @apiNote The SAM Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly advised not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link SamLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link SamLibrary#openSam() SamLibrary.openSam()} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>SamLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         SamLibrary.openSam();
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>SamLibrary.onLibraryReady(() -&gt; { SamLibrary.openSam(); });</pre>
 *          <br/>Or even further to:<br/>
 * <pre>SamLibrary.onLibraryReady(SamLibrary::openSam);</pre>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "deprecation", "JavadocReference", "SpellCheckingInspection"})
public class SamLibrary {
    private static SamLibrary instance;

    private SamLibrary() {
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

    private static SamLibrary getInstance() {
        if (SamLibrary.instance == null) {
            SamLibrary.instance = new SamLibrary();
        }
        return SamLibrary.instance;
    }

    /**
     * Open the SAM.
     *
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Call this function when the application starts.
     */
    public static int openSam() throws RemoteException, UnsupportedOperationException {
        return Implementation.openSam();
    }

    /**
     * Close the SAM.
     *
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Call this function when the application terminates.
     */
    public static int closeSam() throws RemoteException, UnsupportedOperationException {
        return Implementation.closeSam();
    }

    /**
     * Supply 5V power to the SAM card.
     *
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_NOTOPENED ERROR_NOTOPENED }: Non-open error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_CARDDETECT ERROR_CARDDETECT }: Card detection error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_COMMUNICATION ERROR_COMMUNICATION}: Communication error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_INTERNAL ERROR_INTERNAL}: Internal error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Send 5V power on command in ATR format.
     */
    public static int sendPowerOn() throws RemoteException, UnsupportedOperationException {
        return Implementation.sendPowerOn();
    }

    /**
     * Turn off the power of SAM card.
     *
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_NOTOPENED ERROR_NOTOPENED }: Non-open error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_CARDDETECT ERROR_CARDDETECT }: Card detection error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_COMMUNICATION ERROR_COMMUNICATION}: Communication error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_INTERNAL ERROR_INTERNAL}: Internal error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Send power off command in ATR format.
     */
    public static int sendPowerOff() throws RemoteException, UnsupportedOperationException {
        return Implementation.sendPowerOff();
    }

    /**
     * Get the ATR response data.
     *
     * @param receiveData {@code byte[]}: A buffer to store receive data.
     * @return {@code int}: Return the length of received data.<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_NOTOPENED ERROR_NOTOPENED }: Non-open error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static int receiveATR(byte[] receiveData) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.receiveATR(receiveData);
    }

    /**
     * Communicate with SAM card in APDU format.
     *
     * @param sendData {@code byte[]}: A buffer to store send data.
     * @param sendLength {@code int}: Send data length.
     * @param receiveData {@code byte[]}: A buffer to store receive data.
     * @param receiveLength {@code int}: Receive data length.
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_NOTOPENED ERROR_NOTOPENED}: Non-open error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_CARDDETECT ERROR_CARDDETECT}: Card detection error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_COMMUNICATION ERROR_COMMUNICATION}: Communication error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_DATA_LENGTH ERROR_DATA_LENGTH}: Data length error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_DATA_CHECKSUM ERROR_DATA_CHECKSUM}: Data checksum error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_DATA_SIZE ERROR_DATA_SIZE}: Data size error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_EXECUTION ERROR_EXECUTION}: Execution error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_PATTERN ERROR_PATTERN}: Pattern error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_COMMAND ERROR_COMMAND}: Command error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_INTERNAL ERROR_INTERNAL}: Internal error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     * @apiNote Specify APDU format command.
     */
    public static int communicateAPDU(byte[] sendData, int sendLength, byte[] receiveData, int receiveLength) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.communicateAPDU(sendData, sendLength, receiveData, receiveLength);
    }

    /**
     * Specify the command format and communicate directly with the SAM card.
     *
     * @param command {@code byte}: Specify the command format.
     * @param sendData {@code byte[]}: A buffer to store send data.
     * @param sendLength {@code int}: Send data length.
     * @param receiveData {@code byte[]}: A buffer to store receive data.
     * @param receiveLength {@code int}: Receive data length.
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_NOTOPENED ERROR_NOTOPENED}: Non-open error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_CARDDETECT ERROR_CARDDETECT}: Card detection error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_COMMUNICATION ERROR_COMMUNICATION}: Communication error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_DATA_LENGTH ERROR_DATA_LENGTH}: Data length error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_DATA_CHECKSUM ERROR_DATA_CHECKSUM}: Data checksum error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_DATA_SIZE ERROR_DATA_SIZE}: Data size error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_EXECUTION ERROR_EXECUTION}: Execution error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_PATTERN ERROR_PATTERN}: Pattern error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_COMMAND ERROR_COMMAND}: Command error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_INTERNAL ERROR_INTERNAL}: Internal error<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     * @apiNote Use this function when sending a command that can not be specified by communicateAPDU or sending any ATR format command.
     */
    public static int communicateDirect(byte command, byte[] sendData, int sendLength, byte[] receiveData, int receiveLength) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.communicateDirect(command, sendData, sendLength, receiveData, receiveLength);
    }

    /**
     * Get card timeout delay value
     *
     * @return {@code int}: Returns int value in milliseconds<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link SamLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link SamLibrary this class} for further details.
     */
    public static int getTimeOutDelay() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getTimeOutDelay();
    }

    /**
     * Set card timeout delay value in milliseconds
     *
     * @param delayMs {@code int}: Card response timeout in milliseconds
     * @return {@code int}: {@link SamLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link SamLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setTimeOutDelay(int delayMs) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTimeOutDelay(delayMs);
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

    private ISamLibrary samLibraryService() {
        return EDTServiceConnection.getSamLibrary();
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the SAM Library Service becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addSamLibraryCallback(callback);
    }

    private static final class Implementation {
        private static int openSam() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().samLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().samLibraryService().openSam(unsupported);
                    checkMethodUnsupported("openSam", unsupported);
                });
                return SamLibraryConstant.RETURN.SUCCESS;
            }
            int retVal = getInstance().samLibraryService().openSam(unsupported);
            checkMethodUnsupported("openSam", unsupported);
            return retVal;
        }

        private static int closeSam() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().samLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().samLibraryService().closeSam(unsupported);
                    checkMethodUnsupported("closeSam", unsupported);
                });
                return SamLibraryConstant.RETURN.SUCCESS;
            }
            int retVal = getInstance().samLibraryService().closeSam(unsupported);
            checkMethodUnsupported("closeSam", unsupported);
            return retVal;
        }

        private static int sendPowerOn() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().samLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().samLibraryService().sendPowerOn(unsupported);
                    checkMethodUnsupported("sendPowerOn", unsupported);
                });
                return SamLibraryConstant.RETURN.SUCCESS;
            }
            int retVal = getInstance().samLibraryService().sendPowerOn(unsupported);
            checkMethodUnsupported("sendPowerOn", unsupported);
            return retVal;
        }

        private static int sendPowerOff() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().samLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().samLibraryService().sendPowerOff(unsupported);
                    checkMethodUnsupported("sendPowerOff", unsupported);
                });
                return SamLibraryConstant.RETURN.SUCCESS;
            }
            int retVal = getInstance().samLibraryService().sendPowerOff(unsupported);
            checkMethodUnsupported("sendPowerOff", unsupported);
            return retVal;
        }

        private static int receiveATR(byte[] receiveData) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().samLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().samLibraryService().receiveATR(receiveData, unsupported);
            checkMethodUnsupported("receiveATR", unsupported);
            return retVal;
        }

        private static int communicateAPDU(byte[] sendData, int sendLength, byte[] receiveData, int receiveLength) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().samLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().samLibraryService().communicateAPDU(sendData, sendLength, receiveData, receiveLength, unsupported);
            checkMethodUnsupported("communicateAPDU", unsupported);
            return retVal;
        }

        private static int communicateDirect(byte command, byte[] sendData, int sendLength, byte[] receiveData, int receiveLength) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().samLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().samLibraryService().communicateDirect(command, sendData, sendLength, receiveData, receiveLength, unsupported);
            checkMethodUnsupported("communicateDirect", unsupported);
            return retVal;
        }

        private static int getTimeOutDelay() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().samLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().samLibraryService().getTimeOutDelay(unsupported);
            checkMethodUnsupported("getTimeOutDelay", unsupported);
            return retVal;
        }

        private static int setTimeOutDelay(int delayMs) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().samLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().samLibraryService().setTimeOutDelay(delayMs, unsupported);
                    checkMethodUnsupported("setTimeOutDelay", unsupported);
                });
                return SamLibraryConstant.RETURN.SUCCESS;
            }
            int retVal = getInstance().samLibraryService().setTimeOutDelay(delayMs, unsupported);
            checkMethodUnsupported("setTimeOutDelay", unsupported);
            return retVal;
        }

        private static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
            if (getInstance().samLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().samLibraryService().isMethodSupported(method.toString());
            } catch (Exception e) {
                return false;
            }
        }

        private static boolean isMethodSupported(String methodName) throws IllegalStateException {
            if (getInstance().samLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().samLibraryService().isMethodNameSupported(methodName);
            } catch (Exception e) {
                return false;
            }
        }


    }
}
