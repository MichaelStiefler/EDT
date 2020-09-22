package com.casioeurope.mis.edt;

import android.graphics.Rect;
import android.os.RemoteException;

import com.casioeurope.mis.edt.constant.ScannerLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.ScanResult;
import com.casioeurope.mis.edt.type.ScanResultParcelable;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Scanner Library<br/><br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
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
     */
    public static boolean isMethodSupported(BigInteger method) {
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
     */
    public static boolean isMethodSupported(String methodName) {
        try {
            return getInstance().edtServiceScannerLibrary().isMethodNameSupported(methodName);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Open the barcode scanner.<br/>
     * Call this function when the application starts.<br/>
     * If you call Scanner Library’s function before open the barcode scanner, it may not work correctly.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int openScanner() throws RemoteException, UnsupportedOperationException {
        return Implementation.openScanner();
    }

    /**
     * Close the barcode scanner.<br/>
     * Call this function when the application terminates.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
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
     */
    public static boolean isScannerOpen() throws RemoteException, UnsupportedOperationException {
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
     */
    public static String getAPIVersion() throws RemoteException, UnsupportedOperationException {
        return Implementation.getAPIVersion();
    }

    /**
     * Get the Scanner module version.
     *
     * @return {@link String String}: The Module Version of the Scanner or Null in case of failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static String getModuleVersion() throws RemoteException, UnsupportedOperationException {
        return Implementation.getModuleVersion();
    }

    /**
     * Get the last Scan Result.<br/>
     * When you read multiple barcodes, you can get the final Scan Result.<br/>
     * When you fail scanning, you can get all of data are cleared except scan time.<br/>
     * When you call this function before scanning ever, you can get all of data are cleared.<br/>
     * For the default value, refer to the {@link ScanResult ScanResult class}.
     *
     * @param scanResult {@link ScanResult}: Specify the {@link ScanResult ScanResult} class object to store the Scan Result.<br/>
     * For the member variable of the ScanReslt class, refer to the {@link ScanResult ScanResult class}.
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getScanResult(ScanResult scanResult) throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getNotificationLED() throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getNotificationVibrator() throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getNotificationSound() throws RemoteException, UnsupportedOperationException {
        return Implementation.getNotificationSound();
    }

    /**
     * Set the light mode.<br/>
     * Set the action of Illumination and Aimer when you will scan, capture image, stream.<br/>
     * Specify {@link ScannerLibraryConstant.LIGHT_MODE#ALL_ON ALL_ON} when scanning.<br/>
     * If specify other parameters, reading performance decreased.
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
     */
    public static int setLightMode(int lightMode) throws RemoteException, UnsupportedOperationException {
        return Implementation.setLightMode(lightMode);
    }

    /**
     * Get the light mode.<br/>
     * Get the action of Illumination and Aimer behavior when you will scan, capture image, stream.
     *
     * @return {@code int}: {@link ScannerLibraryConstant.LIGHT_MODE#ALL_OFF ALL_OFF}: Illumination and Aimer does not light up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#AIMER_ON AIMER_ON}: Aimer lights up<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ILLUMINATION_ON ILLUMINATION_ON}: Illumination lights up (default)<br/>
     *            {@link ScannerLibraryConstant.LIGHT_MODE#ALL_ON ALL_ON}: Illumination and Aimer lights up<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLightMode() throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getImageDataSize() throws RemoteException, UnsupportedOperationException {
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
     */
    public static int captureImage(byte[] buffer) throws RemoteException, UnsupportedOperationException {
        return Implementation.captureImage(buffer);
    }

    /**
     * Get the size of data required for streaming. Use this function if do not change the streaming area or resolution.
     *
     * @return {@code int}: Return the data sizes for streaming on success.<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getStreamDataSize() throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getStreamDataSize(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
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
     * Read the stream data.<br/>
     * To realize preview, call this function continuously.
     *
     * @param buffer {@code byte[]}: Buffer to store stream data.<br/>
     *                       Allocate the area necessary for storing stream data.<br/>
     *                       For the detail, refer to {@link #getStreamDataSize() getStreamDataSize()} or {@link #getStreamDataSize(Rect, int)  getStreamDataSize(Rect, int)}.
     * @return {@code int}: Return the acquired stream data sizes on success.<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int readStream(byte[] buffer) throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getSymbologyEnable(int symbologyID) throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getSymbologyMaxDefault(int symbologyID) throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getSymbologyMinDefault(int symbologyID) throws RemoteException, UnsupportedOperationException {
        return Implementation.getSymbologyMinDefault(symbologyID);
    }

    /**
     * Set the reading maximum number of digits of the specified barcode.<br/>
     * Barcodes larger than the set number of digits are not read.
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
     */
    public static int getSymbologyMax(int symbologyID) throws RemoteException, UnsupportedOperationException {
        return Implementation.getSymbologyMax(symbologyID);
    }

    /**
     * Set the reading minimum number of digits of the specified barcode.<br/>
     * Barcodes less than the set number of digits are not read.
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
     */
    public static int getSymbologyMin(int symbologyID) throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getSymbologyCheckCount(int symbologyID) throws RemoteException, UnsupportedOperationException {
        return Implementation.getSymbologyCheckCount(symbologyID);
    }

    /**
     * Set the value of property setting of the specified barcode.<br/>
     * Specify property number and set value to change.
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
     */
    public static int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting) throws RemoteException, UnsupportedOperationException {
        return Implementation.setSymbologyProperty(symbologyID, propertyNo, propertySetting);
    }

    /**
     * Get the value of property setting of the specified barcode.<br/>
     * Specify property number and set value to change.
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
     */
    public static int getSymbologyProperty(int symbologyID, int propertyNo) throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getOutputType() throws RemoteException, UnsupportedOperationException {
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
     */
    public static int getSuffix() throws RemoteException, UnsupportedOperationException {
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
     * @return {@code int}: {@link ScannerLibraryConstant.INVERSE#DISABLE DISABLE}: Only Normal barcode can read<br/>
     *            {@link ScannerLibraryConstant.INVERSE#ENABLE ENABLE}: Only Inverse barcode can read<br/>
     *            {@link ScannerLibraryConstant.INVERSE#AUTO AUTO}: Normal and Inverse barcode can read
     *            {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getInverseMode() throws RemoteException, UnsupportedOperationException {
        return Implementation.getInverseMode();
    }

    public static int setTriggerKeyEnable(int triggerKeyEnable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyEnable(triggerKeyEnable);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getTriggerKeyEnable() throws RemoteException, UnsupportedOperationException {
        return Implementation.getTriggerKeyEnable();
    }

    public static int setTriggerKeyMode(int triggerKeyMode) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyMode(triggerKeyMode);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getTriggerKeyMode() throws RemoteException, UnsupportedOperationException {
        return Implementation.getTriggerKeyMode();
    }

    public static int setNumberOfBarcodes(int numberOfBarcodes) throws RemoteException, UnsupportedOperationException {
        return Implementation.setNumberOfBarcodes(numberOfBarcodes);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getNumberOfBarcodes() throws RemoteException, UnsupportedOperationException {
        return Implementation.getNumberOfBarcodes();
    }

    public static int setDelimiter(int delimiter) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDelimiter(delimiter);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getDelimiter() throws RemoteException, UnsupportedOperationException {
        return Implementation.getDelimiter();
    }

    public static int setTriggerKeyTimeout(int triggerKeyTimeout) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyTimeout(triggerKeyTimeout);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getTriggerKeyTimeout() throws RemoteException, UnsupportedOperationException {
        return Implementation.getTriggerKeyTimeout();
    }

    public static int setTriggerKeyOn(int triggerKeyOn) throws RemoteException, UnsupportedOperationException {
        return Implementation.setTriggerKeyOn(triggerKeyOn);
    }

    public static int setScannerAPO(int scannerAPOTime) throws RemoteException, UnsupportedOperationException {
        return Implementation.setScannerAPO(scannerAPOTime);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getScannerAPO() throws RemoteException, UnsupportedOperationException {
        return Implementation.getScannerAPO();
    }

    public static int setCenteringWindow(int centeringWindow) throws RemoteException, UnsupportedOperationException {
        return Implementation.setCenteringWindow(centeringWindow);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getCenteringWindow() throws RemoteException, UnsupportedOperationException {
        return Implementation.getCenteringWindow();
    }

    public static int setDetectionAreaSize(int detectionAreaSize) throws RemoteException, UnsupportedOperationException {
        return Implementation.setDetectionAreaSize(detectionAreaSize);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getDetectionAreaSize() throws RemoteException, UnsupportedOperationException {
        return Implementation.getDetectionAreaSize();
    }

    public static int setLaserSwingWidth(int laserSwingWidth) throws RemoteException, UnsupportedOperationException {
        return Implementation.setLaserSwingWidth(laserSwingWidth);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLaserSwingWidth() throws RemoteException, UnsupportedOperationException {
        return Implementation.getLaserSwingWidth();
    }

    public static int setLaserHighlightMode(int enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setLaserHighlightMode(enable);
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibraryConstant.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLaserHighlightMode() throws RemoteException, UnsupportedOperationException {
        return Implementation.getLaserHighlightMode();
    }

    public static int setInternalParameter(byte[] command) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInternalParameter(command);
    }

    public static int setInternalParameter(int tag, int value) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInternalParameter(tag, value);
    }

    public static int setInternalParameter(int number, int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
        return Implementation.setInternalParameter(number, tags, values);
    }

    public static int getInternalParameter(int tag) throws RemoteException, UnsupportedOperationException {
        return Implementation.getInternalParameter(tag);
    }

    public static int getInternalParameter(int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
        return Implementation.getInternalParameter(tags, values);
    }


    private static final class Implementation {
        private static int openScanner() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().openScanner(unsupported);
            checkMethodUnsupported("openScanner", unsupported);
            return retVal;
        }

        private static int closeScanner() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().closeScanner(unsupported);
            checkMethodUnsupported("closeScanner", unsupported);
            return retVal;
        }

        private static boolean isScannerOpen() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal = getInstance().edtServiceScannerLibrary().isScannerOpen(unsupported);
            checkMethodUnsupported("isScannerOpen", unsupported);
            return retVal;
        }

        private static int setDefaultAll() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setDefaultAll(unsupported);
            checkMethodUnsupported("setDefaultAll", unsupported);
            return retVal;
        }

        private static String getAPIVersion() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().edtServiceScannerLibrary().getAPIVersion(unsupported);
            checkMethodUnsupported("getAPIVersion", unsupported);
            return retVal;
        }

        private static String getModuleVersion() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().edtServiceScannerLibrary().getModuleVersion(unsupported);
            checkMethodUnsupported("getModuleVersion", unsupported);
            return retVal;
        }

        private static int getScanResult(ScanResult scanResult) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            ScanResultParcelable scanResultParcelable = new ScanResultParcelable(scanResult);
            int retVal = getInstance().edtServiceScannerLibrary().getScanResult(scanResultParcelable, unsupported);
            scanResultParcelable.copyTo(scanResult);
            checkMethodUnsupported("getScanResult", unsupported);
            return retVal;
        }

        private static int setNotificationLED(int led) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setNotificationLED(led, unsupported);
            checkMethodUnsupported("setNotificationLED", unsupported);
            return retVal;
        }

        private static int getNotificationLED() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNotificationLED(unsupported);
            checkMethodUnsupported("getNotificationLED", unsupported);
            return retVal;
        }

        private static int setNotificationVibrator(int vibrator) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setNotificationVibrator(vibrator, unsupported);
            checkMethodUnsupported("setNotificationVibrator", unsupported);
            return retVal;
        }

        private static int getNotificationVibrator() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNotificationVibrator(unsupported);
            checkMethodUnsupported("getNotificationVibrator", unsupported);
            return retVal;
        }

        private static int setNotificationSound(int sound) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setNotificationSound(sound, unsupported);
            checkMethodUnsupported("setNotificationSound", unsupported);
            return retVal;
        }

        private static int getNotificationSound() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNotificationSound(unsupported);
            checkMethodUnsupported("getNotificationSound", unsupported);
            return retVal;
        }

        private static int setLightMode(int lightMode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setLightMode(lightMode, unsupported);
            checkMethodUnsupported("setLightMode", unsupported);
            return retVal;
        }

        private static int getLightMode() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getLightMode(unsupported);
            checkMethodUnsupported("getLightMode", unsupported);
            return retVal;
        }

        private static int turnAimerOn(int aimerOn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().turnAimerOn(aimerOn, unsupported);
            checkMethodUnsupported("turnAimerOn", unsupported);
            return retVal;
        }

        private static int turnIlluminationOn(int illuminationOn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().turnIlluminationOn(illuminationOn, unsupported);
            checkMethodUnsupported("turnIlluminationOn", unsupported);
            return retVal;
        }

        private static int getImageDataSize() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getImageDataSize(unsupported);
            checkMethodUnsupported("getImageDataSize", unsupported);
            return retVal;
        }

        private static int captureImage(byte[] buffer) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().captureImage(buffer, unsupported);
            checkMethodUnsupported("captureImage", unsupported);
            return retVal;
        }

        private static int getStreamDataSize() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getStreamDataSize(unsupported);
            checkMethodUnsupported("getStreamDataSize", unsupported);
            return retVal;
        }

        private static int getStreamDataSize(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getStreamDataSize2(rectangle, resolution, unsupported);
            checkMethodUnsupported("getStreamDataSize", unsupported);
            return retVal;
        }

        private static int initializeStream(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().initializeStream(rectangle, resolution, unsupported);
            checkMethodUnsupported("initializeStream", unsupported);
            return retVal;
        }

        private static int startStream() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().startStream(unsupported);
            checkMethodUnsupported("startStream", unsupported);
            return retVal;
        }

        private static int readStream(byte[] buffer) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().readStream(buffer, unsupported);
            checkMethodUnsupported("readStream", unsupported);
            return retVal;
        }

        private static int stopStream() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().stopStream(unsupported);
            checkMethodUnsupported("stopStream", unsupported);
            return retVal;
        }

        private static int deinitializeStream() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().deinitializeStream(unsupported);
            checkMethodUnsupported("deinitializeStream", unsupported);
            return retVal;
        }

        private static int setSymbologyEnable(int symbologyID, int enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyEnable(symbologyID, enable, unsupported);
            checkMethodUnsupported("setSymbologyEnable", unsupported);
            return retVal;
        }

        private static int getSymbologyEnable(int symbologyID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyEnable(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyEnable", unsupported);
            return retVal;
        }

        private static int getSymbologyMaxDefault(int symbologyID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMaxDefault(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMaxDefault", unsupported);
            return retVal;
        }

        private static int getSymbologyMinDefault(int symbologyID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMinDefault(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMinDefault", unsupported);
            return retVal;
        }

        private static int setSymbologyMax(int symbologyID, int max) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyMax(symbologyID, max, unsupported);
            checkMethodUnsupported("setSymbologyMax", unsupported);
            return retVal;
        }

        private static int getSymbologyMax(int symbologyID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMax(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMax", unsupported);
            return retVal;
        }

        private static int setSymbologyMin(int symbologyID, int min) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyMin(symbologyID, min, unsupported);
            checkMethodUnsupported("setSymbologyMin", unsupported);
            return retVal;
        }

        private static int getSymbologyMin(int symbologyID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMin(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyMin", unsupported);
            return retVal;
        }

        private static int setSymbologyCheckCount(int symbologyID, int checkCount) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyCheckCount(symbologyID, checkCount, unsupported);
            checkMethodUnsupported("setSymbologyCheckCount", unsupported);
            return retVal;
        }

        private static int getSymbologyCheckCount(int symbologyID) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyCheckCount(symbologyID, unsupported);
            checkMethodUnsupported("getSymbologyCheckCount", unsupported);
            return retVal;
        }

        private static int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setSymbologyProperty(symbologyID, propertyNo, propertySetting, unsupported);
            checkMethodUnsupported("setSymbologyProperty", unsupported);
            return retVal;
        }

        private static int getSymbologyProperty(int symbologyID, int propertyNo) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSymbologyProperty(symbologyID, propertyNo, unsupported);
            checkMethodUnsupported("getSymbologyProperty", unsupported);
            return retVal;
        }

        private static int setOutputType(int outputType) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setOutputType(outputType, unsupported);
            checkMethodUnsupported("setOutputType", unsupported);
            return retVal;
        }

        private static int getOutputType() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getOutputType(unsupported);
            checkMethodUnsupported("getOutputType", unsupported);
            return retVal;
        }

        private static int setSuffix(int suffix) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setSuffix(suffix, unsupported);
            checkMethodUnsupported("setSuffix", unsupported);
            return retVal;
        }

        private static int getSuffix() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getSuffix(unsupported);
            checkMethodUnsupported("getSuffix", unsupported);
            return retVal;
        }

        private static int setInverseMode(int inverseMode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setInverseMode(inverseMode, unsupported);
            checkMethodUnsupported("setInverseMode", unsupported);
            return retVal;
        }

        private static int getInverseMode() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getInverseMode(unsupported);
            checkMethodUnsupported("getInverseMode", unsupported);
            return retVal;
        }

        private static int setTriggerKeyEnable(int triggerKeyEnable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyEnable(triggerKeyEnable, unsupported);
            checkMethodUnsupported("setTriggerKeyEnable", unsupported);
            return retVal;
        }

        private static int getTriggerKeyEnable() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyEnable(unsupported);
            checkMethodUnsupported("getTriggerKeyEnable", unsupported);
            return retVal;
        }

        private static int setTriggerKeyMode(int triggerKeyMode) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyMode(triggerKeyMode, unsupported);
            checkMethodUnsupported("setTriggerKeyMode", unsupported);
            return retVal;
        }

        private static int getTriggerKeyMode() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyMode(unsupported);
            checkMethodUnsupported("getTriggerKeyMode", unsupported);
            return retVal;
        }

        private static int setNumberOfBarcodes(int numberOfBarcodes) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setNumberOfBarcodes(numberOfBarcodes, unsupported);
            checkMethodUnsupported("setNumberOfBarcodes", unsupported);
            return retVal;
        }

        private static int getNumberOfBarcodes() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getNumberOfBarcodes(unsupported);
            checkMethodUnsupported("getNumberOfBarcodes", unsupported);
            return retVal;
        }

        private static int setDelimiter(int delimiter) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setDelimiter(delimiter, unsupported);
            checkMethodUnsupported("setDelimiter", unsupported);
            return retVal;
        }

        private static int getDelimiter() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getDelimiter(unsupported);
            checkMethodUnsupported("getDelimiter", unsupported);
            return retVal;
        }

        private static int setTriggerKeyTimeout(int triggerKeyTimeout) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyTimeout(triggerKeyTimeout, unsupported);
            checkMethodUnsupported("setTriggerKeyTimeout", unsupported);
            return retVal;
        }

        private static int getTriggerKeyTimeout() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyTimeout(unsupported);
            checkMethodUnsupported("getTriggerKeyTimeout", unsupported);
            return retVal;
        }

        private static int setTriggerKeyOn(int triggerKeyOn) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyOn(triggerKeyOn, unsupported);
            checkMethodUnsupported("setTriggerKeyOn", unsupported);
            return retVal;
        }

        private static int setScannerAPO(int scannerAPOTime) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setScannerAPO(scannerAPOTime, unsupported);
            checkMethodUnsupported("setScannerAPO", unsupported);
            return retVal;
        }

        private static int getScannerAPO() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getScannerAPO(unsupported);
            checkMethodUnsupported("getScannerAPO", unsupported);
            return retVal;
        }

        private static int setCenteringWindow(int centeringWindow) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setCenteringWindow(centeringWindow, unsupported);
            checkMethodUnsupported("setCenteringWindow", unsupported);
            return retVal;
        }

        private static int getCenteringWindow() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getCenteringWindow(unsupported);
            checkMethodUnsupported("getCenteringWindow", unsupported);
            return retVal;
        }

        private static int setDetectionAreaSize(int detectionAreaSize) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setDetectionAreaSize(detectionAreaSize, unsupported);
            checkMethodUnsupported("setDetectionAreaSize", unsupported);
            return retVal;
        }

        private static int getDetectionAreaSize() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getDetectionAreaSize(unsupported);
            checkMethodUnsupported("getDetectionAreaSize", unsupported);
            return retVal;
        }

        private static int setLaserSwingWidth(int laserSwingWidth) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setLaserSwingWidth(laserSwingWidth, unsupported);
            checkMethodUnsupported("setLaserSwingWidth", unsupported);
            return retVal;
        }

        private static int getLaserSwingWidth() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getLaserSwingWidth(unsupported);
            checkMethodUnsupported("getLaserSwingWidth", unsupported);
            return retVal;
        }

        private static int setLaserHighlightMode(int enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setLaserHighlightMode(enable, unsupported);
            checkMethodUnsupported("setLaserHighlightMode", unsupported);
            return retVal;
        }

        private static int getLaserHighlightMode() throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getLaserHighlightMode(unsupported);
            checkMethodUnsupported("getLaserHighlightMode", unsupported);
            return retVal;
        }

        private static int setInternalParameter(byte[] command) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter(command, unsupported);
            checkMethodUnsupported("setInternalParameter", unsupported);
            return retVal;
        }

        private static int setInternalParameter(int tag, int value) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter2(tag, value, unsupported);
            checkMethodUnsupported("setInternalParameter", unsupported);
            return retVal;
        }

        private static int setInternalParameter(int number, int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter3(number, tags, values, unsupported);
            checkMethodUnsupported("setInternalParameter", unsupported);
            return retVal;
        }

        private static int getInternalParameter(int tag) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getInternalParameter(tag, unsupported);
            checkMethodUnsupported("getInternalParameter", unsupported);
            return retVal;
        }

        private static int getInternalParameter(int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            int retVal = getInstance().edtServiceScannerLibrary().getInternalParameter2(tags, values, unsupported);
            checkMethodUnsupported("getInternalParameter", unsupported);
            return retVal;
        }
    }
}
