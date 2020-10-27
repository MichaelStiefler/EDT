package com.casioeurope.mis.edt;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.casioeurope.mis.edt.constant.ScannerLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;
import com.casioeurope.mis.edt.type.ScanResult;
import com.casioeurope.mis.edt.type.ScanResultParcelable;

import java.math.BigInteger;

import static com.casioeurope.mis.edt.constant.ScannerLibraryConstant.RETURN.SUCCESS;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Scanner Library<br/><br/>
 *
 * @apiNote The Scanner Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly advised not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link ScannerLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link ScannerLibrary#openScanner() ScannerLibrary.openScanner()} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>ScannerLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         ScannerLibrary.openScanner();
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>ScannerLibrary.onLibraryReady(() -&gt; { ScannerLibrary.openScanner(); });</pre>
 *          <br/>Or even further to:<br/>
 * <pre>ScannerLibrary.onLibraryReady(ScannerLibrary::openScanner);</pre>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "JavadocReference", "SpellCheckingInspection"})
public class ScannerLibrary {

    private static ScannerLibrary instance;

    private ScannerLibrary() {
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

    private static ScannerLibrary getInstance() {
        if (ScannerLibrary.instance == null) {
            ScannerLibrary.instance = new ScannerLibrary();
        }
        return ScannerLibrary.instance;
    }

    private IScannerLibrary edtServiceScannerLibrary() {
        return EDTServiceConnection.getInstance().getScannerLibrary();
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
        if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        try {
            return getInstance().edtServiceScannerLibrary().isMethodSupported(method.toString());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link String String} methodName parameter is supported on the currently active device
     *
     * @param methodName {@link String String}: Name of the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static boolean isMethodSupported(String methodName) throws IllegalStateException {
        if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        try {
            return getInstance().edtServiceScannerLibrary().isMethodNameSupported(methodName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Open the barcode scanner.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Call this function when the application starts.<br/>
     * If you call Scanner Library’s function before open the barcode scanner, it may not work correctly.
     */
    public static int openScanner() throws RemoteException, UnsupportedOperationException {
        return Implementation.openScanner();
    }

    /**
     * Close the barcode scanner.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Call this function when the application terminates.
     */
    public static int closeScanner() throws RemoteException, UnsupportedOperationException {
        return Implementation.closeScanner();
    }

    /**
     * Check the barcode scanner is opened.
     *
     * @return {@code boolean}: true: The barcode scanner is opened<br/>
     * false: The barcode scanner is closed
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static boolean isScannerOpen() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.isScannerOpen();
    }

    /**
     * Return all barcode scanner settings to default.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setDefaultAll() throws RemoteException, UnsupportedOperationException {
        return Implementation.setDefaultAll();
    }

    /**
     * Get the Scanner API version.
     *
     * @return {@link String String}: The API Version of the Scanner or Null in case of failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static String getAPIVersion() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getAPIVersion();
    }

    /**
     * Get the Scanner module version.
     *
     * @return {@link String String}: The Module Version of the Scanner or Null in case of failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static String getModuleVersion() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getModuleVersion();
    }

    /**
     * Get the last Scan Result.
     *
     * @param scanResult {@link ScanResult}: Specify the {@link ScanResult ScanResult} class object to store the Scan Result.<br/>
     * For the member variable of the ScanReslt class, refer to the {@link ScanResult ScanResult class}.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     * @apiNote When you read multiple barcodes, you can get the final Scan Result.<br/>
     * When you fail scanning, you can get all of data are cleared except scan time.<br/>
     * When you call this function before scanning ever, you can get all of data are cleared.<br/>
     * For the default value, refer to the {@link ScanResult ScanResult class}.
     */
    public static int getScanResult(ScanResult scanResult) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getScanResult(scanResult);
    }

    /**
     * Set the behavior of the notification LED.
     *
     * @param led {@code int}: The behavior of the notification LED<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#LED_OFF LED_OFF}: The notification LED not lights up in success and failure scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#LED_ON LED_ON}: notification LED turns on GREEN in success scanning, and RED in failure scanning.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setNotificationLED(int led) throws RemoteException, UnsupportedOperationException {
        return Implementation.setNotificationLED(led);
    }

    /**
     * Get the behavior of the notification LED.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.NOTIFICATION#LED_OFF LED_OFF}: The notification LED not lights up in success and failure scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#LED_ON LED_ON}: notification LED turns on GREEN in success scanning, and RED in failure scanning.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getNotificationLED() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getNotificationLED();
    }

    /**
     * Set the behavior of the notification vibrator.
     *
     * @param vibrator {@code int}: The behavior of the notification vibrator<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_ALL_OFF VIBRATOR_ALL_OFF}: The notification vibrator does not vibrate in success and failure scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_FAIL_ON VIBRATOR_FAIL_ON}: The notification vibrator vibrates in failure scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_SUCCESS_ON VIBRATOR_SUCCESS_ON}: The notification vibrator vibrates in success scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_ALL_ON VIBRATOR_ALL_ON}: The notification vibrator vibrates in success and failure scanning.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setNotificationVibrator(int vibrator) throws RemoteException, UnsupportedOperationException {
        return Implementation.setNotificationVibrator(vibrator);
    }

    /**
     * Get the behavior of the notification vibrator.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_ALL_OFF VIBRATOR_ALL_OFF}: The notification vibrator does not vibrate in success and failure scanning. (default)<br/>
     *             {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_FAIL_ON VIBRATOR_FAIL_ON}: The notification vibrator vibrates in failure scanning.<br/>
     *             {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_SUCCESS_ON VIBRATOR_SUCCESS_ON}: The notification vibrator vibrates in success scanning.<br/>
     *             {@link ScannerLibraryConstant.NOTIFICATION#VIBRATOR_ALL_ON VIBRATOR_ALL_ON}: The notification vibrator vibrates in success and failure scanning.<br/>
     *             {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getNotificationVibrator() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getNotificationVibrator();
    }

    /**
     * Set the behavior of the notification sound.
     *
     * @param sound {@code int}: The behavior of the notification sound<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#SOUND_ALL_OFF SOUND_ALL_OFF}: The notification sound does not sounds in success and failure scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#SOUND_FAIL_ON SOUND_FAIL_ON}: The notification sound sounds in failure scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#SOUND_SUCCESS_ON SOUND_SUCCESS_ON}: The notification sound sounds in success scanning.<br/>
     *            {@link ScannerLibraryConstant.NOTIFICATION#SOUND_ALL_ON SOUND_ALL_ON}: The notification sound sounds in success and failure scanning.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setNotificationSound(int sound) throws RemoteException, UnsupportedOperationException {
        return Implementation.setNotificationSound(sound);
    }

    /**
     * Get the behavior of the notification sound.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.NOTIFICATION#SOUND_ALL_OFF SOUND_ALL_OFF}: The notification sound does not sounds in success and failure scanning.<br/>
     *             {@link ScannerLibraryConstant.NOTIFICATION#SOUND_FAIL_ON SOUND_FAIL_ON}: The notification sound sounds in failure scanning.<br/>
     *             {@link ScannerLibraryConstant.NOTIFICATION#SOUND_SUCCESS_ON SOUND_SUCCESS_ON}: The notification sound sounds in success scanning.<br/>
     *             {@link ScannerLibraryConstant.NOTIFICATION#SOUND_ALL_ON SOUND_ALL_ON}: The notification sound sounds in success and failure scanning. (default)<br/>
     *             {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getNotificationSound() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getNotificationSound();
    }

    /**
     * Set the light mode.
     *
     * @param lightMode {@code int}: Light mode<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ALL_OFF ALL_OFF}: Illumination and Aimer does not light up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#AIMER_ON AIMER_ON}: Aimer lights up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ILLUMINATION_ON ILLUMINATION_ON}: Illumination lights up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ALL_ON ALL_ON}: Illumination and Aimer lights up
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Set the action of Illumination and Aimer when you will scan, capture image, stream.<br/>
     * Specify {@link ScannerLibraryConstant.LIGHT_MODE#ALL_ON ALL_ON} when scanning.<br/>
     * If specify other parameters, reading performance decreased.
     */
    public static int setLightMode(int lightMode) throws RemoteException, UnsupportedOperationException {
        return Implementation.setLightMode(lightMode);
    }

    /**
     * Get the light mode.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.LIGHT_MODE#ALL_OFF ALL_OFF}: Illumination and Aimer does not light up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#AIMER_ON AIMER_ON}: Aimer lights up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ILLUMINATION_ON ILLUMINATION_ON}: Illumination lights up (default)<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ALL_ON ALL_ON}: Illumination and Aimer lights up<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     * @apiNote Get the action of Illumination and Aimer behavior when you will scan, capture image, stream.
     */
    public static int getLightMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getLightMode();
    }

    /**
     * Control the behavior of the aimer by software.
     *
     * @param aimerOn {@code int}: The behavior of the aimer<br/>
     *            {@link ScannerLibraryConstant.CONTROL#AIMER_OFF AIMER_OFF}: Aimer is turned off<br/>
     *            {@link ScannerLibraryConstant.CONTROL#AIMER_ON AIMER_ON}: Aimer is turned on
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int turnAimerOn(int aimerOn) throws RemoteException, UnsupportedOperationException {
        return Implementation.turnAimerOn(aimerOn);
    }

    /**
     * Control the behavior of the illumination by software.
     *
     * @param illuminationOn {@code int}: The behavior of the illumination<br/>
     *            {@link ScannerLibraryConstant.CONTROL#ILLUMINATION_OFF ILLUMINATION_OFF}: Illumination is turned off<br/>
     *            {@link ScannerLibraryConstant.CONTROL#ILLUMINATION_ON ILLUMINATION_ON}: Illumination is turned on
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int turnIlluminationOn(int illuminationOn) throws RemoteException, UnsupportedOperationException {
        return Implementation.turnIlluminationOn(illuminationOn);
   }

    /**
     * Get the size of the data required for capturing image.
     *
     * @return {@code int}: Return the data sizes for image capturing on success.<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getImageDataSize() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getImageDataSize();
    }

    /**
     * Capture the image.
     *
     * @param buffer {@code byte[]}: Buffer to store image data.<br/>
     *                       Allocate the area necessary for storing image data.<br/>
     *                       For the detail, refer to {@link #getImageDataSize getImageDataSize}.
     * @return {@code int}: Return the captured image data size on success.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_NOTOPENED ERROR_NOTOPENED}: Not opened error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int captureImage(byte[] buffer) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.captureImage(buffer);
    }

    /**
     * Get the size of data required for streaming. Use this function if do not change the streaming area or resolution.
     *
     * @return {@code int}: Return the data sizes for streaming on success.<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getStreamDataSize() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getStreamDataSize();
    }

    /**
     * Get the size of data required for streaming. Use this function if change the streaming area or resolution.
     *
     * @param rectangle {@link android.graphics.Rect Rect}: Streaming area.<br/>
     *                                                    Specify the resolution for streaming.<br/>
     *                                                    The origin is the upper left, the top and bottom specify 0-799, the left and right specify 0-1279. Specify the height and width of the area so that they are multiples of 2 when specifying 1/4 resolution and multiples of 4 when specifying 1/16 resolution.
     * @param resolution {@code int}: Resolution.<br/>
     *                               Specify the resolution for streaming.<br/>
     *                               {@link ScannerLibraryConstant.RESOLUTION#FULL FULL}: Full resolution<br/>
     *                               {@link ScannerLibraryConstant.RESOLUTION#QUARTER QUARTER}: 1/4 resolution<br/>
     *                               {@link ScannerLibraryConstant.RESOLUTION#ONE_SIXTEENTH ONE_SIXTEENTH}: 1/16 resolution
     * @return {@code int}: Return the data sizes for streaming on success.<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getStreamDataSize(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getStreamDataSize(rectangle, resolution);
    }

    /**
     * Initialization processing for streaming. Use this function if change the streaming area or resolution.
     *
     * @param rectangle {@link android.graphics.Rect Rect}: Streaming area.<br/>
     *                                                    Specify the resolution for streaming.<br/>
     *                                                    The origin is the upper left, the top and bottom specify 0-799, the left and right specify 0-1279. Specify the height and width of the area so that they are multiples of 2 when specifying 1/4 resolution and multiples of 4 when specifying 1/16 resolution.
     * @param resolution {@code int}: Resolution.<br/>
     *                               Specify the resolution for streaming.<br/>
     *                               {@link ScannerLibraryConstant.RESOLUTION#FULL FULL}: Full resolution<br/>
     *                               {@link ScannerLibraryConstant.RESOLUTION#QUARTER QUARTER}: 1/4 resolution<br/>
     *                               {@link ScannerLibraryConstant.RESOLUTION#ONE_SIXTEENTH ONE_SIXTEENTH}: 1/16 resolution
     * @return {@code int}: Return the data sizes for streaming on success.<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int initializeStream(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
        return Implementation.initializeStream(rectangle, resolution);
    }

    /**
     * Start streaming.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int startStream() throws RemoteException, UnsupportedOperationException {
        return Implementation.startStream();
    }

    /**
     * Read the stream data.
     *
     * @param buffer {@code byte[]}: Buffer to store stream data.<br/>
     *                       Allocate the area necessary for storing stream data.<br/>
     *                       For the detail, refer to {@link #getStreamDataSize() getStreamDataSize()} or {@link #getStreamDataSize(Rect, int)  getStreamDataSize(Rect, int)}.
     * @return {@code int}: Return the acquired stream data sizes on success.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     * @apiNote To realize preview, call this function continuously.
     */
    public static int readStream(byte[] buffer) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.readStream(buffer);
    }

    /**
     * Stop streaming.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int stopStream() throws RemoteException, UnsupportedOperationException {
        return Implementation.stopStream();
    }

    /**
     * Deinitialization processing for streaming. Use this function if changed the streaming area or resolution.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int deinitializeStream() throws RemoteException, UnsupportedOperationException {
        return Implementation.deinitializeStream();
    }

    /**
     * Set reading enable/disable of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                               For the SymbologyID of each barcode, refer to the Code identification table.<br/>
     *                               When {@link ScannerLibraryConstant.SYMBOLOGY#ALL ALL} is specified, all barcodes are enabled/disabled.
     * @param enable {@code int}: Reading enable/disable
     *                          {@link ScannerLibraryConstant.SYMBOLOGY_PARAMETER#ENABLE ENABLE}: Read enable<br/>
     *                          {@link ScannerLibraryConstant.SYMBOLOGY_PARAMETER#DISABLE DISABLE}: Read disable
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setSymbologyEnable(int symbologyID, int enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSymbologyEnable(symbologyID, enable);
    }

    /**
     * Get reading enable/disable of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                For the SymbologyID of each barcode, refer to the Code identification table.
     * @return {@code int}: {@link ScannerLibraryConstant.SYMBOLOGY_PARAMETER#ENABLE ENABLE}: Read enable<br/>
     *            {@link ScannerLibraryConstant.SYMBOLOGY_PARAMETER#DISABLE DISABLE}: Read disable<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSymbologyEnable(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyEnable(symbologyID);
    }

    /**
     * Get the default reading maximum number of digits of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                For the SymbologyID of each barcode, refer to the Code identification table.
     * @return {@code int}: Return default reading maximum number of digits on success.<br/>
     *            If you specified the barcode that the number of digits can not be changed (i.e. {@link ScannerLibraryConstant.SYMBOLOGY#EAN13 EAN13} etc.), return 0.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSymbologyMaxDefault(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyMaxDefault(symbologyID);
    }

    /**
     * Get the default reading minimum number of digits of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                For the SymbologyID of each barcode, refer to the Code identification table.
     * @return {@code int}: Return default reading minimum number of digits on success.<br/>
     *            If you specified the barcode that the number of digits can not be changed (i.e. {@link ScannerLibraryConstant.SYMBOLOGY#EAN13 EAN13} etc.), return 0.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSymbologyMinDefault(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyMinDefault(symbologyID);
    }

    /**
     * Set the reading maximum number of digits of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                               For the SymbologyID of each barcode, refer to the Code identification table.<br/>
     *                               When {@link ScannerLibraryConstant.SYMBOLOGY#ALL ALL} is specified, all barcodes are enabled/disabled.
     * @param max {@code int}: reading maximum number of digits.<br/>
     *                       For the number of digits that can be set, refer to Reading digits.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Barcodes larger than the set number of digits are not read.
     */
    public static int setSymbologyMax(int symbologyID, int max) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSymbologyMax(symbologyID, max);
    }

    /**
     * Get the reading maximum number of digits of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                For the SymbologyID of each barcode, refer to the Code identification table.
     * @return {@code int}: Return reading maximum number of digits on success.<br/>
     *            If you specified the barcode that the number of digits can not be changed (i.e. {@link ScannerLibraryConstant.SYMBOLOGY#EAN13 EAN13} etc.), return 0.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSymbologyMax(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyMax(symbologyID);
    }

    /**
     * Set the reading minimum number of digits of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                               For the SymbologyID of each barcode, refer to the Code identification table.<br/>
     *                               When {@link ScannerLibraryConstant.SYMBOLOGY#ALL ALL} is specified, all barcodes are enabled/disabled.
     * @param min {@code int}: reading minimum number of digits.<br/>
     *                       For the number of digits that can be set, refer to Reading digits.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Barcodes less than the set number of digits are not read.
     */
    public static int setSymbologyMin(int symbologyID, int min) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSymbologyMin(symbologyID, min);
    }

    /**
     * Get the reading minimum number of digits of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                For the SymbologyID of each barcode, refer to the Code identification table.
     * @return {@code int}: Return reading minimum number of digits on success.<br/>
     *            If you specified the barcode that the number of digits can not be changed (i.e. {@link ScannerLibraryConstant.SYMBOLOGY#EAN13 EAN13} etc.), return 0.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSymbologyMin(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyMin(symbologyID);
    }

    /**
     * Set the value of check count of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                               For the SymbologyID of each barcode, refer to the Code identification table.<br/>
     *                               The barcodes that can be set to the number of check count on IT-G600 are {@link ScannerLibraryConstant.SYMBOLOGY#CODABAR Codabar}Codabar, {@link ScannerLibraryConstant.SYMBOLOGY#CODE128 Code 128}, {@link ScannerLibraryConstant.SYMBOLOGY#CODE39 Code 39}, {@link ScannerLibraryConstant.SYMBOLOGY#PDF417 PDF 417}, {@link ScannerLibraryConstant.SYMBOLOGY#QR QR}.
     * @param checkCount {@code int}: Check count<br/>
     *            Specify the check count between 0 and 10. If you specified 0, do not match the result.<br/>
     *            Increasing the number of check count has the effect of reducing the misreading rate.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setSymbologyCheckCount(int symbologyID, int checkCount) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSymbologyCheckCount(symbologyID, checkCount);
    }

    /**
     * Get the value of check count of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                For the SymbologyID of each barcode, refer to the Code identification table.
     * @return {@code int}: Return the value of check count on success.<br/>
     *                      The barcodes that can be set to the number of check count on IT-G600 are {@link ScannerLibraryConstant.SYMBOLOGY#CODABAR Codabar}Codabar, {@link ScannerLibraryConstant.SYMBOLOGY#CODE128 Code 128}, {@link ScannerLibraryConstant.SYMBOLOGY#CODE39 Code 39}, {@link ScannerLibraryConstant.SYMBOLOGY#PDF417 PDF 417}, {@link ScannerLibraryConstant.SYMBOLOGY#QR QR}.
     *                      If you specified the barcode where the number of check count can not be changed (i.e. {@link ScannerLibraryConstant.SYMBOLOGY#EAN13 EAN13} etc.), return 0.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSymbologyCheckCount(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyCheckCount(symbologyID);
    }

    /**
     * Set the value of property setting of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                 For the SymbologyID of each barcode, refer to the Code identification table.
     * @param propertyNo {@code int}: Property number<br/>
     *                                 Specify the property number.<br/>
     *                                 For the property number, refer to Property of barcode.
     * @param propertySetting {@code int}: Setting value for property<br/>
     *                                 Set the property setting value.<br/>
     *                                 For the property number, refer to Property of barcode.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Specify property number and set value to change.
     */
    public static int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSymbologyProperty(symbologyID, propertyNo, propertySetting);
    }

    /**
     * Get the value of property setting of the specified barcode.
     *
     * @param symbologyID {@code int}: Specify the SymbologyID of each barcode.<br/>
     *                                 For the SymbologyID of each barcode, refer to the Code identification table.
     * @param propertyNo {@code int}: Property number<br/>
     *                                 Specify the property number.<br/>
     *                                 For the property number, refer to Property of barcode.
     * @return {@code int}: Return the value of property setting on success.
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     * @apiNote Specify property number and set value to change.
     */
    public static int getSymbologyProperty(int symbologyID, int propertyNo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSymbologyProperty(symbologyID, propertyNo);
    }

    /**
     * Set the output type of the scan result.
     *
     * @param outputType {@code int}: The output type of the scan result<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#CLIP CLIP}: Clipboard output<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#KEY KEY}: Keyboard output<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#USER USER}: User message output<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#BROADCAST BROADCAST}: Broadcast output
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Intent issued when user message output setting is enabled is "device.common.USERMSG".<br/>
     *          Intent issued when broadcast output setting is enabled is "casio.intent.action.BROADCAST". To receive the intent, install the IntentManager.<br/>
     *          For detail of the intent refer to Output type control.
     */
    public static int setOutputType(int outputType) throws RemoteException, UnsupportedOperationException {
        return Implementation.setOutputType(outputType);
    }

    /**
     * Get the output type of the scan result.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.OUTPUT#CLIP CLIP}: Clipboard output<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#KEY KEY}: Keyboard output<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#USER USER}: User message output<br/>
     *            {@link ScannerLibraryConstant.OUTPUT#BROADCAST BROADCAST}: Broadcast output
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getOutputType() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getOutputType();
    }

    /**
     * Set the suffix type added at the end of the scan result.
     *
     * @param suffix {@code int}: Suffix to be added.<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#NONE NONE}: no suffix<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#LF LF}: LF (0x0A)<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#TAB TAB}: TAB (0x09)<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#TAB_LF TAB_LF}: TAB+LF (0x09, 0x0A)
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setSuffix(int suffix) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSuffix(suffix);
    }

    /**
     * Get the suffix type added at the end of the scan result.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.SUFFIX#NONE NONE}: no suffix<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#LF LF}: LF (0x0A)<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#TAB TAB}: TAB (0x09)<br/>
     *            {@link ScannerLibraryConstant.SUFFIX#TAB_LF TAB_LF}: TAB+LF (0x09, 0x0A)
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getSuffix() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getSuffix();
    }

    /**
     * Set the inverse barcode reading mode.
     *
     * @param inverseMode {@code int}: Inverse barcode reading mode.<br/>
     *            {@link ScannerLibraryConstant.INVERSE#DISABLE DISABLE}: Only Normal barcode can read<br/>
     *            {@link ScannerLibraryConstant.INVERSE#ENABLE ENABLE}: Only Inverse barcode can read<br/>
     *            {@link ScannerLibraryConstant.INVERSE#AUTO AUTO}: Normal and Inverse barcode can read
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setInverseMode(int inverseMode) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInverseMode(inverseMode);
    }

    /**
     * Get the inverse barcode reading mode.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.INVERSE#DISABLE DISABLE}: Only Normal barcode can read (default)<br/>
     *            {@link ScannerLibraryConstant.INVERSE#ENABLE ENABLE}: Only Inverse barcode can read<br/>
     *            {@link ScannerLibraryConstant.INVERSE#AUTO AUTO}: Normal and Inverse barcode can read
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getInverseMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getInverseMode();
    }

    /**
     * Set the Trigger key enable/disable.
     *
     * @param triggerKeyEnable {@code int}: Trigger key enable/disable.<br/>
     *            {@link ScannerLibraryConstant.TRIGGERKEY#DISABLE DISABLE}: Trigger key disable<br/>
     *            {@link ScannerLibraryConstant.TRIGGERKEY#ENABLE ENABLE}: Trigger key enable
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setTriggerKeyEnable(int triggerKeyEnable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyEnable(triggerKeyEnable);
    }

    /**
     * Get the Trigger key enable/disable.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.TRIGGERKEY#DISABLE DISABLE}: Trigger key disable<br/>
     *            {@link ScannerLibraryConstant.TRIGGERKEY#ENABLE ENABLE}: Trigger key enable (default)<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getTriggerKeyEnable() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getTriggerKeyEnable();
    }

    /**
     * Set the Trigger key mode.
     *
     * @param triggerKeyMode {@code int}: Trigger key enable/disable.<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#NORMAL NORMAL}: Normal Scan<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#CONTINUOUS CONTINUOUS}: Continuous reading<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#MULTI_STEP MULTI_STEP}: Multi-step reading<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#PACKAGE PACKAGE}: Package reading<br/>
     *                                  This setting returns to the default after rebooting. To use this setting, please set it when starting the application.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setTriggerKeyMode(int triggerKeyMode) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyMode(triggerKeyMode);
    }

    /**
     * Get the Trigger key mode.
     *
     * @return {@code int}:{@link ScannerLibraryConstant.TRIGGER_MODE#NORMAL NORMAL}: Normal Scan (default)<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#CONTINUOUS CONTINUOUS}: Continuous reading<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#MULTI_STEP MULTI_STEP}: Multi-step reading<br/>
     *            {@link ScannerLibraryConstant.TRIGGER_MODE#PACKAGE PACKAGE}: Package reading<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getTriggerKeyMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getTriggerKeyMode();
    }

    /**
     * Set the number of Barcodes to be scanned in Multi-step scan or Package scan.
     *
     * @param numberOfBarcodes {@code int}: The number of Barcodes to be scanned in Multi-step scan or Package scan.<br/>
     *            Specify a value between 2 and 10.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setNumberOfBarcodes(int numberOfBarcodes) throws RemoteException, UnsupportedOperationException {
        return Implementation.setNumberOfBarcodes(numberOfBarcodes);
    }

    /**
     * Get the number of barcodes to be scanned in Multi-step scan or Package scan.
     *
     * @return {@code int}: Return the number of barcodes to be scanned in Multi-step scan or Package scan. Default value is 4.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getNumberOfBarcodes() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getNumberOfBarcodes();
    }

    /**
     * Set the delimiter for Package scan.
     *
     * @param delimiter {@code int}: The delimiter for Package scan.<br/>
     *            Specify a value between 0x00 and 0x7f as ASCII.<br/>
     *            When 0x00 is set, it operates None (No delimiter).
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setDelimiter(int delimiter) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDelimiter(delimiter);
    }

    /**
     * Get the delimiter for Package scan.
     *
     * @return {@code int}: Return the delimiter as ASCII for Package scan. Default value is "US(0x1f)".<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getDelimiter() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getDelimiter();
    }

    /**
     * Set the Trigger key timeout.
     *
     * @param triggerKeyTimeout {@code int}: Trigger timeout<br/>
     *            Specify a value in milliseconds.<br/>
     *            The setting range is 1000 to 10000.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setTriggerKeyTimeout(int triggerKeyTimeout) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyTimeout(triggerKeyTimeout);
    }

    /**
     * Get the Trigger key timeout.
     *
     * @return {@code int}: Return the value of Trigger key timeout on success.<br/>
     *            Default value is 10000.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getTriggerKeyTimeout() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getTriggerKeyTimeout();
    }

    /**
     * Control the behavior of trigger key by software.
     *
     * @param triggerKeyOn {@code int}: Status of trigger key<br/>
     *            {@link ScannerLibraryConstant.CONTROL#TRIGGER_OFF TRIGGER_OFF}: Trigger key is released virtually<br/>
     *            {@link ScannerLibraryConstant.CONTROL#TRIGGER_ON TRIGGER_ON}: Trigger key is pressed virtually
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setTriggerKeyOn(int triggerKeyOn) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyOn(triggerKeyOn);
    }

    /**
     * Set the Auto Power Off(APO) time of the barcode scanner.
     *
     * @param scannerAPOTime {@code int}: The APO time of the barcode scanner.<br/>
     *            Specify a value between 0 and 65535 in seconds.<br/>
     *            When set to 0, APO is disabled.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setScannerAPO(int scannerAPOTime) throws RemoteException, UnsupportedOperationException {
        return Implementation.setScannerAPO(scannerAPOTime);
    }

    /**
     * Get the Auto Power Off(APO) time of the barcode scanner.
     *
     * @return {@code int}: Return the APO time of barcode scanner. Default value is 60.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getScannerAPO() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getScannerAPO();
    }

    /**
     * Set enable/disable of the centering window mode.
     *
     * @param centeringWindow {@code int}: Enable/disable of the centering window mode.<br/>
     *            {@link ScannerLibraryConstant.CENTERING_WINDOW_MODE#DISABLE DISABLE}: Centering window mode disable<br/>
     *            {@link ScannerLibraryConstant.CENTERING_WINDOW_MODE#ENABLE ENABLE}: Centering window mode enable
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Refer to the figure below for the barcode that can be read while the center reading mode is in effect. If part of the barcode is included in the detection area, read the barcode.<br/>
     *            <img src="./doc-files/setcenteringwindow_01.png" /><br/>
     *            <img src="./doc-files/setcenteringwindow_02.png" />
     */
    public static int setCenteringWindow(int centeringWindow) throws RemoteException, UnsupportedOperationException {
        return Implementation.setCenteringWindow(centeringWindow);
    }

    /**
     * Get enable/disable of the centering window mode.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.CENTERING_WINDOW_MODE#DISABLE DISABLE}: Centering window mode disable (default)<br/>
     *            {@link ScannerLibraryConstant.CENTERING_WINDOW_MODE#ENABLE ENABLE}: Centering window mode enable<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getCenteringWindow() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getCenteringWindow();
    }

    /**
     * Set the size of barcode detection area for the centering window mode.
     *
     * @param detectionAreaSize {@code int}: The size of barcode detection area for the centering window mode.<br/>
     *            Specify a value between 0 and 10.<br/>
     *            If you set smaller value, the barcode near the center is detected.<br/>
     *            If you specified 0, the center point becomes the detection area.<br/>
     *            Please change the value according to the actual use environment.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote Refer to the figure below for the detection area size guide:<br/>
     *            <img src="./doc-files/setdetectionareasize_01.png" />
     */
    public static int setDetectionAreaSize(int detectionAreaSize) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDetectionAreaSize(detectionAreaSize);
    }

    /**
     * Get the size of barcode detection area for the centering window mode.
     *
     * @return {@code int}: Return the size of barcode detection area for the centering window mode.<br/>
     *            Default value is 5.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getDetectionAreaSize() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getDetectionAreaSize();
    }

    /**
     * Set the laser swing width of 1D scanner.
     *
     * @param laserSwingWidth {@code int}: Swing width<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#MAX MAX}: Swing width MAX<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#WIDE WIDE}: Swing width WIDE<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#MIDDLE MIDDLE}: Swing width MIDDLE<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#NARROW NARROW}: Swing width NARROW
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setLaserSwingWidth(int laserSwingWidth) throws RemoteException, UnsupportedOperationException {
        return Implementation.setLaserSwingWidth(laserSwingWidth);
    }

    /**
     * Get the laser swing width of 1D scanner.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.SWING_WIDTH#MAX MAX}: Swing width MAX (default)<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#WIDE WIDE}: Swing width WIDE<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#MIDDLE MIDDLE}: Swing width MIDDLE<br/>
     *            {@link ScannerLibraryConstant.SWING_WIDTH#NARROW NARROW}: Swing width NARROW
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getLaserSwingWidth() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getLaserSwingWidth();
    }

    /**
     * Set enable/disable of the laser highlight mode of 1D scanner.
     *
     * @param enable {@code int}: Enable/disable of the laser highlight mode<br/>
     *            {@link ScannerLibraryConstant.LASER_HIGHLIGHT_MODE#DISABLE DISABLE}: Laser highlight mode disable<br/>
     *            {@link ScannerLibraryConstant.LASER_HIGHLIGHT_MODE#ENABLE ENABLE}: Laser highlight mode enable
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setLaserHighlightMode(int enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setLaserHighlightMode(enable);
    }

    /**
     * Get enable/disable of the laser highlight mode of 1D scanner.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.LASER_HIGHLIGHT_MODE#DISABLE DISABLE}: Laser highlight mode disable (default)<br/>
     *            {@link ScannerLibraryConstant.LASER_HIGHLIGHT_MODE#ENABLE ENABLE}: Laser highlight mode enable
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote When highlight mode is enabled, the barcode read is emphasized. It is useful for checking the barcode read when two or more barcodes are placed nearby.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getLaserHighlightMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getLaserHighlightMode();
    }

    /**
     * Set internal parameters. Detailed specifications are not disclosed.
     *
     * @param command {@code byte[]}
     * @return {@code int}
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setInternalParameter(byte[] command) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInternalParameter(command);
    }

    /**
     * Set internal parameters. Detailed specifications are not disclosed.
     *
     * @param tag {@code int}
     * @param value {@code int}
     * @return {@code int}
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setInternalParameter(int tag, int value) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInternalParameter(tag, value);
    }

    /**
     * Set internal parameters. Detailed specifications are not disclosed.
     *
     * @param number {@code int}
     * @param tags {@code int[]}
     * @param values {@code int[]}
     * @return {@code int}
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setInternalParameter(int number, int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInternalParameter(number, tags, values);
    }

    /**
     * Get internal parameters. Detailed specifications are not disclosed.
     *
     * @param tag {@code int}
     * @return {@code int}
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ScannerLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ScannerLibrary this class} for further details.
     */
    public static int getInternalParameter(int tag) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getInternalParameter(tag);
    }

    /**
     * Get internal parameters. Detailed specifications are not disclosed.
     *
     * @param tags {@code int[]}
     * @param values {@code int[]}
     * @return {@code int}
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getInternalParameter(int[] tags, int[] values) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getInternalParameter(tags, values);
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the Scanner becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addScannerLibraryCallback(callback);
    }


    private static final class Implementation {
        private static int openScanner() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().openScanner(unsupported);
                    checkMethodUnsupported("openScanner", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().openScanner(unsupported);
            checkMethodUnsupported("openScanner", unsupported);
            return retVal;
        }

        private static int closeScanner() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().closeScanner(unsupported);
                    checkMethodUnsupported("closeScanner", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().closeScanner(unsupported);
            checkMethodUnsupported("closeScanner", unsupported);
            return retVal;
        }

        private static boolean isScannerOpen() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal = getInstance().edtServiceScannerLibrary().isScannerOpen(unsupported);
            checkMethodUnsupported("isScannerOpen", unsupported);
            return retVal;
        }

        private static int setDefaultAll() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setDefaultAll(unsupported);
                    checkMethodUnsupported("setDefaultAll", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setDefaultAll(unsupported);
            checkMethodUnsupported("setDefaultAll", unsupported);
            return retVal;
        }

        private static String getAPIVersion() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().edtServiceScannerLibrary().getAPIVersion(unsupported);
            checkMethodUnsupported("getAPIVersion", unsupported);
            return retVal;
        }

        private static String getModuleVersion() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().edtServiceScannerLibrary().getModuleVersion(unsupported);
            checkMethodUnsupported("getModuleVersion", unsupported);
            return retVal;
        }

        private static int getScanResult(ScanResult scanResult) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            ScanResultParcelable scanResultParcelable = new ScanResultParcelable(scanResult);
            int retVal = getInstance().edtServiceScannerLibrary().getScanResult(scanResultParcelable, unsupported);
            scanResultParcelable.copyTo(scanResult);
            checkMethodUnsupported("getScanResult", unsupported);
            return retVal;
        }

        private static int setNotificationLED(int led) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setNotificationLED(led, unsupported);
                    checkMethodUnsupported("setNotificationLED", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setNotificationLED(led, unsupported);
            checkMethodUnsupported("setNotificationLED", unsupported);
            return retVal;
        }

        private static int getNotificationLED() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNotificationLED(unsupported);
            checkMethodUnsupported("getNotificationLED", unsupported);
            return retVal;
        }

        private static int setNotificationVibrator(int vibrator) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setNotificationVibrator(vibrator, unsupported);
                    checkMethodUnsupported("setNotificationVibrator", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setNotificationVibrator(vibrator, unsupported);
            checkMethodUnsupported("setNotificationVibrator", unsupported);
            return retVal;
        }

        private static int getNotificationVibrator() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNotificationVibrator(unsupported);
            checkMethodUnsupported("getNotificationVibrator", unsupported);
            return retVal;
        }

        private static int setNotificationSound(int sound) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setNotificationSound(sound, unsupported);
                    checkMethodUnsupported("setNotificationSound", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setNotificationSound(sound, unsupported);
            checkMethodUnsupported("setNotificationSound", unsupported);
            return retVal;
        }

        private static int getNotificationSound() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNotificationSound(unsupported);
            checkMethodUnsupported("getNotificationSound", unsupported);
            return retVal;
        }

        private static int setLightMode(int lightMode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setLightMode(lightMode, unsupported);
                    checkMethodUnsupported("setLightMode", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setLightMode(lightMode, unsupported);
            checkMethodUnsupported("setLightMode", unsupported);
            return retVal;
        }

        private static int getLightMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getLightMode(unsupported);
            checkMethodUnsupported("getLightMode", unsupported);
            return retVal;
        }

        private static int turnAimerOn(int aimerOn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().turnAimerOn(aimerOn, unsupported);
                    checkMethodUnsupported("turnAimerOn", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().turnAimerOn(aimerOn, unsupported);
            checkMethodUnsupported("turnAimerOn", unsupported);
            return retVal;
        }

        private static int turnIlluminationOn(int illuminationOn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().turnIlluminationOn(illuminationOn, unsupported);
                    checkMethodUnsupported("turnAimerOn", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().turnIlluminationOn(illuminationOn, unsupported);
            checkMethodUnsupported("turnIlluminationOn", unsupported);
            return retVal;
        }

        private static int getImageDataSize() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getImageDataSize(unsupported);
            checkMethodUnsupported("getImageDataSize", unsupported);
            return retVal;
        }

        private static int captureImage(byte[] buffer) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().captureImage(buffer, unsupported);
            checkMethodUnsupported("captureImage", unsupported);
            return retVal;
        }

        private static int getStreamDataSize() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getStreamDataSize(unsupported);
            checkMethodUnsupported("getStreamDataSize", unsupported);
            return retVal;
        }

        private static int getStreamDataSize(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getStreamDataSize2(rectangle, resolution, unsupported);
            checkMethodUnsupported("getStreamDataSize", unsupported);
            return retVal;
        }

        private static int initializeStream(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().initializeStream(rectangle, resolution, unsupported);
                    checkMethodUnsupported("initializeStream", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().initializeStream(rectangle, resolution, unsupported);
            checkMethodUnsupported("initializeStream", unsupported);
            return retVal;
        }

        private static int startStream() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().startStream(unsupported);
                    checkMethodUnsupported("startStream", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().startStream(unsupported);
            checkMethodUnsupported("startStream", unsupported);
            return retVal;
        }

        private static int readStream(byte[] buffer) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().readStream(buffer, unsupported);
            checkMethodUnsupported("readStream", unsupported);
            return retVal;
        }

        private static int stopStream() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().stopStream(unsupported);
                    checkMethodUnsupported("stopStream", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().stopStream(unsupported);
            checkMethodUnsupported("stopStream", unsupported);
            return retVal;
        }

        private static int deinitializeStream() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().deinitializeStream(unsupported);
                    checkMethodUnsupported("deinitializeStream", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().deinitializeStream(unsupported);
            checkMethodUnsupported("deinitializeStream", unsupported);
            return retVal;
        }

        private static int setSymbologyEnable(int symbologyID, int enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setSymbologyEnable(symbologyID, enable, unsupported);
                    checkMethodUnsupported("setSymbologyEnable", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyEnable(symbologyID, enable, unsupported);
            checkMethodUnsupported("setSymbologyEnable", unsupported);
            return retVal;
        }

        private static int getSymbologyEnable(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyEnable(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyEnable", unsupported);
            return retVal;
        }

        private static int getSymbologyMaxDefault(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMaxDefault(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMaxDefault", unsupported);
            return retVal;
        }

        private static int getSymbologyMinDefault(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMinDefault(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMinDefault", unsupported);
            return retVal;
        }

        private static int setSymbologyMax(int symbologyID, int max) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setSymbologyMax(symbologyID, max, unsupported);
                    checkMethodUnsupported("setSymbologyMax", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyMax(symbologyID, max, unsupported);
            checkMethodUnsupported("setSymbologyMax", unsupported);
            return retVal;
        }

        private static int getSymbologyMax(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMax(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMax", unsupported);
            return retVal;
        }

        private static int setSymbologyMin(int symbologyID, int min) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setSymbologyMin(symbologyID, min, unsupported);
                    checkMethodUnsupported("setSymbologyMin", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyMin(symbologyID, min, unsupported);
            checkMethodUnsupported("setSymbologyMin", unsupported);
            return retVal;
        }

        private static int getSymbologyMin(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMin(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMin", unsupported);
            return retVal;
        }

        private static int setSymbologyCheckCount(int symbologyID, int checkCount) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setSymbologyCheckCount(symbologyID, checkCount, unsupported);
                    checkMethodUnsupported("setSymbologyCheckCount", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyCheckCount(symbologyID, checkCount, unsupported);
            checkMethodUnsupported("setSymbologyCheckCount", unsupported);
            return retVal;
        }

        private static int getSymbologyCheckCount(int symbologyID) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyCheckCount(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyCheckCount", unsupported);
            return retVal;
        }

        private static int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setSymbologyProperty(symbologyID, propertyNo, propertySetting, unsupported);
                    checkMethodUnsupported("setSymbologyProperty", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyProperty(symbologyID, propertyNo, propertySetting, unsupported);
            checkMethodUnsupported("setSymbologyProperty", unsupported);
            return retVal;
        }

        private static int getSymbologyProperty(int symbologyID, int propertyNo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyProperty(symbologyID, propertyNo, unsupported);
            checkMethodUnsupported("getSymbologyProperty", unsupported);
            return retVal;
        }

        private static int setOutputType(int outputType) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                Log.d("[EDT ScannerLibrary]", "edtServiceScannerLibrary is null, postponing setOutputType call!");
                onLibraryReady(() -> {
                    Log.d("[EDT ScannerLibrary]", "onLibraryReady setOutputType callback processing!");
                    getInstance().edtServiceScannerLibrary().setOutputType(outputType, unsupported);
                    checkMethodUnsupported("setOutputType", unsupported);
                    Log.d("[EDT ScannerLibrary]", "onLibraryReady setOutputType callback processed!");
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setOutputType(outputType, unsupported);
            checkMethodUnsupported("setOutputType", unsupported);
            return retVal;
        }

        private static int getOutputType() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getOutputType(unsupported);
            checkMethodUnsupported("getOutputType", unsupported);
            return retVal;
        }

        private static int setSuffix(int suffix) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setSuffix(suffix, unsupported);
                    checkMethodUnsupported("setSuffix", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setSuffix(suffix, unsupported);
            checkMethodUnsupported("setSuffix", unsupported);
            return retVal;
        }

        private static int getSuffix() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSuffix(unsupported);
            checkMethodUnsupported("getSuffix", unsupported);
            return retVal;
        }

        private static int setInverseMode(int inverseMode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setInverseMode(inverseMode, unsupported);
                    checkMethodUnsupported("setInverseMode", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setInverseMode(inverseMode, unsupported);
            checkMethodUnsupported("setInverseMode", unsupported);
            return retVal;
        }

        private static int getInverseMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getInverseMode(unsupported);
            checkMethodUnsupported("getInverseMode", unsupported);
            return retVal;
        }

        private static int setTriggerKeyEnable(int triggerKeyEnable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setTriggerKeyEnable(triggerKeyEnable, unsupported);
                    checkMethodUnsupported("setTriggerKeyEnable", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyEnable(triggerKeyEnable, unsupported);
            checkMethodUnsupported("setTriggerKeyEnable", unsupported);
            return retVal;
        }

        private static int getTriggerKeyEnable() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyEnable(unsupported);
            checkMethodUnsupported("getTriggerKeyEnable", unsupported);
            return retVal;
        }

        private static int setTriggerKeyMode(int triggerKeyMode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setTriggerKeyMode(triggerKeyMode, unsupported);
                    checkMethodUnsupported("setTriggerKeyMode", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyMode(triggerKeyMode, unsupported);
            checkMethodUnsupported("setTriggerKeyMode", unsupported);
            return retVal;
        }

        private static int getTriggerKeyMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyMode(unsupported);
            checkMethodUnsupported("getTriggerKeyMode", unsupported);
            return retVal;
        }

        private static int setNumberOfBarcodes(int numberOfBarcodes) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setNumberOfBarcodes(numberOfBarcodes, unsupported);
                    checkMethodUnsupported("setNumberOfBarcodes", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setNumberOfBarcodes(numberOfBarcodes, unsupported);
            checkMethodUnsupported("setNumberOfBarcodes", unsupported);
            return retVal;
        }

        private static int getNumberOfBarcodes() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNumberOfBarcodes(unsupported);
            checkMethodUnsupported("getNumberOfBarcodes", unsupported);
            return retVal;
        }

        private static int setDelimiter(int delimiter) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setDelimiter(delimiter, unsupported);
                    checkMethodUnsupported("setDelimiter", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setDelimiter(delimiter, unsupported);
            checkMethodUnsupported("setDelimiter", unsupported);
            return retVal;
        }

        private static int getDelimiter() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getDelimiter(unsupported);
            checkMethodUnsupported("getDelimiter", unsupported);
            return retVal;
        }

        private static int setTriggerKeyTimeout(int triggerKeyTimeout) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setTriggerKeyTimeout(triggerKeyTimeout, unsupported);
                    checkMethodUnsupported("setTriggerKeyTimeout", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyTimeout(triggerKeyTimeout, unsupported);
            checkMethodUnsupported("setTriggerKeyTimeout", unsupported);
            return retVal;
        }

        private static int getTriggerKeyTimeout() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyTimeout(unsupported);
            checkMethodUnsupported("getTriggerKeyTimeout", unsupported);
            return retVal;
        }

        private static int setTriggerKeyOn(int triggerKeyOn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setTriggerKeyOn(triggerKeyOn, unsupported);
                    checkMethodUnsupported("setTriggerKeyOn", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyOn(triggerKeyOn, unsupported);
            checkMethodUnsupported("setTriggerKeyOn", unsupported);
            return retVal;
        }

        private static int setScannerAPO(int scannerAPOTime) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setScannerAPO(scannerAPOTime, unsupported);
                    checkMethodUnsupported("setScannerAPO", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setScannerAPO(scannerAPOTime, unsupported);
            checkMethodUnsupported("setScannerAPO", unsupported);
            return retVal;
        }

        private static int getScannerAPO() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getScannerAPO(unsupported);
            checkMethodUnsupported("getScannerAPO", unsupported);
            return retVal;
        }

        private static int setCenteringWindow(int centeringWindow) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setCenteringWindow(centeringWindow, unsupported);
                    checkMethodUnsupported("setCenteringWindow", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setCenteringWindow(centeringWindow, unsupported);
            checkMethodUnsupported("setCenteringWindow", unsupported);
            return retVal;
        }

        private static int getCenteringWindow() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getCenteringWindow(unsupported);
            checkMethodUnsupported("getCenteringWindow", unsupported);
            return retVal;
        }

        private static int setDetectionAreaSize(int detectionAreaSize) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setDetectionAreaSize(detectionAreaSize, unsupported);
                    checkMethodUnsupported("setDetectionAreaSize", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setDetectionAreaSize(detectionAreaSize, unsupported);
            checkMethodUnsupported("setDetectionAreaSize", unsupported);
            return retVal;
        }

        private static int getDetectionAreaSize() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getDetectionAreaSize(unsupported);
            checkMethodUnsupported("getDetectionAreaSize", unsupported);
            return retVal;
        }

        private static int setLaserSwingWidth(int laserSwingWidth) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setLaserSwingWidth(laserSwingWidth, unsupported);
                    checkMethodUnsupported("setLaserSwingWidth", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setLaserSwingWidth(laserSwingWidth, unsupported);
            checkMethodUnsupported("setLaserSwingWidth", unsupported);
            return retVal;
        }

        private static int getLaserSwingWidth() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getLaserSwingWidth(unsupported);
            checkMethodUnsupported("getLaserSwingWidth", unsupported);
            return retVal;
        }

        private static int setLaserHighlightMode(int enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setLaserHighlightMode(enable, unsupported);
                    checkMethodUnsupported("setLaserHighlightMode", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setLaserHighlightMode(enable, unsupported);
            checkMethodUnsupported("setLaserHighlightMode", unsupported);
            return retVal;
        }

        private static int getLaserHighlightMode() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getLaserHighlightMode(unsupported);
            checkMethodUnsupported("getLaserHighlightMode", unsupported);
            return retVal;
        }

        private static int setInternalParameter(byte[] command) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setInternalParameter(command, unsupported);
                    checkMethodUnsupported("setInternalParameter", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter(command, unsupported);
            checkMethodUnsupported("setInternalParameter", unsupported);
            return retVal;
        }

        private static int setInternalParameter(int tag, int value) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setInternalParameter2(tag, value, unsupported);
                    checkMethodUnsupported("setInternalParameter", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter2(tag, value, unsupported);
            checkMethodUnsupported("setInternalParameter", unsupported);
            return retVal;
        }

        private static int setInternalParameter(int number, int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().edtServiceScannerLibrary() == null) {
                onLibraryReady(() -> {
                    getInstance().edtServiceScannerLibrary().setInternalParameter3(number, tags, values, unsupported);
                    checkMethodUnsupported("setInternalParameter", unsupported);
                });
                return SUCCESS;
            }
            int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter3(number, tags, values, unsupported);
            checkMethodUnsupported("setInternalParameter", unsupported);
            return retVal;
        }

        private static int getInternalParameter(int tag) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getInternalParameter(tag, unsupported);
            checkMethodUnsupported("getInternalParameter", unsupported);
            return retVal;
        }

        private static int getInternalParameter(int[] tags, int[] values) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().edtServiceScannerLibrary() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getInternalParameter2(tags, values, unsupported);
            checkMethodUnsupported("getInternalParameter", unsupported);
            return retVal;
        }
    }
}
