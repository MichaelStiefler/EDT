package com.casioeurope.mis.edt.constant;

import android.graphics.Rect;
import com.casioeurope.mis.edt.ScannerLibrary;
import com.casioeurope.mis.edt.type.ScanResult;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Scanner Library Constants<br/>
 * Constants to be used in the {@link ScannerLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class ScannerLibraryConstant {
    /**
     * List of centering window mode constants. Used to Enable / disable centering window mode.
     */
    public static class CENTERING_WINDOW_MODE {
        /**
         * Disable "Centering window mode"
         */
        public static final int DISABLE = 0;
        /**
         * Enable "Centering window mode"
         */
        public static final int ENABLE = 1;
    }

    /**
     * List of control constants. Controls the barcode scanner by software.
     */
    public static class CONTROL {
        /**
         * Aimer is turned off.
         */
        public static final int AIMER_OFF = 0;
        /**
         * Aimer is turned on.
         */
        public static final int AIMER_ON = 1;
        /**
         * Illumination is turned off.
         */
        public static final int ILLUMINATION_OFF = 0;
        /**
         * Illumination is turned on.
         */
        public static final int ILLUMINATION_ON = 1;
        /**
         * Trigger key is released virtually.
         */
        public static final int TRIGGER_OFF = 0;
        /**
         * Trigger key is pressed virtually.
         */
        public static final int TRIGGER_ON = 1;
    }

    /**
     * List of black and white reverse mode constants. Set black-and-white inverted barcode read enable / disable.
     */
    public static class INVERSE {
        /**
         * Read normal barcode and black-and-white reversing bar code.
         */
        public static final int AUTO = 2;
        /**
         * Only normal bar code (barcode printed in black on white background) is read.
         */
        public static final int DISABLE = 0;
        /**
         * Only black-and-white reversed barcode (barcode printed in black on black background) is read.
         */
        public static final int ENABLE = 1;
    }

    /**
     * List of highlight mode constants. Enable / disable highlight mode of laser.
     */
    public static class LASER_HIGHLIGHT_MODE {
        /**
         * Disable "Highlight mode"
         */
        public static final int DISABLE = 0;
        /**
         * Enable "Highlight mode"
         */
        public static final int ENABLE = 1;
    }

    /**
     * List of light mode constants. Set the illumination and the Aimer operation that light up when scanning, shooting still images and streaming.
     */
    public static class LIGHT_MODE {
        /**
         * Aimer lights up.
         */
        public static final int AIMER_ON = 1;
        /**
         * Illumination and Aimer does not light up.
         */
        public static final int ALL_OFF = 0;
        /**
         * Illumination and Aimer lights up.
         */
        public static final int ALL_ON = 3;
        /**
         * Illumination lights up.
         */
        public static final int ILLUMINATION_ON = 2;
    }

    /**
     * List of notification constants. Sets notification behavior when bar code is read.
     */
    public static class NOTIFICATION {
        /**
         * The notification LED not lights up in success and failure scanning.
         */
        public static final int LED_OFF = 0;
        /**
         * The notification LED turns on GREEN in success scanning, and RED in failure scanning.
         */
        public static final int LED_ON = 1;
        /**
         * The notification sound does not vibrate in success and failure scanning.
         */
        public static final int SOUND_ALL_OFF = 0;
        /**
         * The notification sound sounds in success and failure scanning.
         */
        public static final int SOUND_ALL_ON = 3;
        /**
         * The notification sound sounds in failure scanning.
         */
        public static final int SOUND_FAIL_ON = 1;
        /**
         * The notification sound sounds in success scanning.
         */
        public static final int SOUND_SUCCESS_ON = 2;
        /**
         * The notification vibrator does not vibrate in success and failure scanning.
         */
        public static final int VIBRATOR_ALL_OFF = 0;
        /**
         * The notification vibrator vibrates in success and failure scanning.
         */
        public static final int VIBRATOR_ALL_ON = 3;
        /**
         * The notification vibrator vibrates in failure scanning.
         */
        public static final int VIBRATOR_FAIL_ON = 1;
        /**
         * The notification vibrator vibrates in success scanning.
         */
        public static final int VIBRATOR_SUCCESS_ON = 2;
    }

    /**
     * List of output type constants. Sets the output type of the reading result.
     */
    public static class OUTPUT {
        /**
         * Broadcast output
         */
        public static final int BROADCAST = 3;
        /**
         * Clipboard output
         */
        public static final int CLIP = 0;
        /**
         * Keyboard output
         */
        public static final int KEY = 1;
        /**
         * User message output
         */
        public static final int USER = 2;
    }

    /**
     * List of resolution constants. Controls the resolution for streaming.
     */
    public static class RESOLUTION {
        /**
         * Full resolution of the barcode scanner.
         */
        public static final int FULL = 0;
        /**
         * 1/16 of full resolution.
         */
        public static final int ONE_SIXTEENTH = 2;
        /**
         * 1/4 of full resolution.
         */
        public static final int QUARTER = 1;
    }

    /**
     * List of return value constants. Check execution result of member function with return value constant.
     */
    public static class RETURN {
        /**
         * Non-open error. It returns when a function is called with the barcode scanner not open.
         */
        public static final int ERROR_NOTOPENED = -3;
        /**
         * Parameter error. It returns when the parameter specified as the argument is illegal.
         */
        public static final int ERROR_PARAMETER = -2;
        /**
         * Unsupported error. It returns if the function is not supported.
         */
        public static final int ERROR_UNSUPPORTED = -1;
        /**
         * Successful completion. It returns when the function terminates normally.
         */
        public static final int SUCCESS = 0;
    }

    /**
     * List of "Suffix constants". Sets the qualifier to be appended to the end of the reading result.
     */
    public static class SUFFIX {
        /**
         * Add LF (0x0A)
         */
        public static final int LF = 1;
        /**
         * no suffix
         */
        public static final int NONE = 0;
        /**
         * Add TAB (0x09)
         */
        public static final int TAB = 2;
        /**
         * Add TAB+LF (0x09, 0x0A)
         */
        public static final int TAB_LF = 3;
    }

    /**
     * List of swing width constants. Set swing width of laser.
     */
    public static class SWING_WIDTH {
        /**
         * Swing width MAX
         */
        public static final int MAX = 0;
        /**
         * Swing width WIDE
         */
        public static final int MIDDLE = 2;
        /**
         * Swing width MIDDLE
         */
        public static final int NARROW = 3;
        /**
         * Swing width NARROW
         */
        public static final int WIDE = 1;
    }

    /**
     * List of SymbologyID constants. Used to specify the SymbologyID of each barcode.
     */
    public static class SYMBOLOGY {
        /**
         * All Barcode Types
         */
        public static final int ALL = 999;
        /**
         * Aztec
         */
        public static final int AZTEC = 1;
        /**
         * Codabar
         */
        public static final int CODABAR = 2;
        /**
         * Codablock F
         */
        public static final int CODABLOCKF = 3;
        /**
         * Code 128
         */
        public static final int CODE128 = 4;
        /**
         * Code 32
         */
        public static final int CODE32 = 5;
        /**
         * Code 39
         */
        public static final int CODE39 = 6;
        /**
         * Code 93
         */
        public static final int CODE93 = 7;
        /**
         * Composite
         */
        public static final int COMPOSITE = 8;
        /**
         * DataMatrix
         */
        public static final int DATAMATRIX = 9;
        /**
         * EAN 13
         */
        public static final int EAN13 = 10;
        /**
         * EAN 8
         */
        public static final int EAN8 = 11;
        /**
         * GS1 128 (EAN 128)
         */
        public static final int GS1_128 = 12;
        /**
         * GS1 DataBar (RSS)
         */
        public static final int GS1_DATABAR = 13;
        /**
         * Han Xin
         */
        public static final int HANXIN = 14;
        /**
         * ISBT
         */
        public static final int ISBT = 16;
        /**
         * ITF (Interleaved 2 of 5)
         */
        public static final int ITF = 15;
        /**
         * Maxicode
         */
        public static final int MAXICODE = 17;
        /**
         * Micro PDF
         */
        public static final int MICRO_PDF = 18;
        /**
         * MSI
         */
        public static final int MSI = 19;
        /**
         * PDF 417
         */
        public static final int PDF417 = 20;
        /**
         * QR Code/ Micro QR Code
         */
        public static final int QR = 21;
        /**
         * UPC-A
         */
        public static final int UPCA = 22;
        /**
         * UPC-E (UPC-E0 / UPC-E1)
         */
        public static final int UPCE = 23;
    }

    /**
     * List of symbol parameter constants. Set barcode read enable / disable.
     */
    public static class SYMBOLOGY_PARAMETER {
        /**
         * Read disable.
         */
        public static final int DISABLE = 0;
        /**
         * Read enable.
         */
        public static final int ENABLE = 1;
    }

    /**
     * List of Trigger key constants. Set trigger key enable / disable.
     */
    public static class TRIGGERKEY {
        /**
         * Trigger key disable
         */
        public static final int DISABLE = 0;
        /**
         * Trigger key enable
         */
        public static final int ENABLE = 1;
    }

    /**
     * List of Trigger key mode constants. Set the action when trigger key is pressed.
     */
    public static class TRIGGER_MODE {
        /**
         * Continuous reading. Scanning continuously during trigger key is pressed.
         */
        public static final int CONTINUOUS = 1;
        /**
         * Multi-step reading. This method is for scanning a specified number of barcodes. Once scanning for the specified number of bar codes has been completed, the scanner closes and will not scan again until reopened.
         * Also, the same bar codes that have been scanned previously cannot be scanned again.
         */
        public static final int MULTI_STEP = 2;
        /**
         * Normal reading. Scan is performed every time the trigger key is pressed
         */
        public static final int NORMAL = 0;
        /**
         * Package reading. Scanning continuously reads multiple symbols until when the Trigger key is released and then outputs a result of reading all the symbols.
         */
        public static final int PACKAGE = 3;
    }

    /**
     * Methods of the {@link ScannerLibrary} class, used e.g. to check availability of said methods using {@link ScannerLibrary#isMethodSupported(BigInteger)} method.
     */
    public static class METHOD {
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#openScanner()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_OPENSCANNER = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000001", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#closeScanner()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLOSESCANNER = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000010", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#isScannerOpen()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_ISSCANNEROPEN = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000100", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setDefaultAll()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDEFAULTALL = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000001000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getAPIVersion()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETAPIVERSION = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000010000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getModuleVersion()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETMODULEVERSION = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000100000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getScanResult(ScanResult)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSCANRESULT = new BigInteger("0000000000000000000000000000000000000000000000000000000000000001000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setNotificationLED(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETNOTIFICATIONLED = new BigInteger("0000000000000000000000000000000000000000000000000000000000000010000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getNotificationLED()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETNOTIFICATIONLED = new BigInteger("0000000000000000000000000000000000000000000000000000000000000100000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setNotificationVibrator(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETNOTIFICATIONVIBRATOR = new BigInteger("0000000000000000000000000000000000000000000000000000000000001000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getNotificationVibrator()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETNOTIFICATIONVIBRATOR = new BigInteger("0000000000000000000000000000000000000000000000000000000000010000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setNotificationSound(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETNOTIFICATIONSOUND = new BigInteger("0000000000000000000000000000000000000000000000000000000000100000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getNotificationSound()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETNOTIFICATIONSOUND = new BigInteger("0000000000000000000000000000000000000000000000000000000001000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setLightMode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETLIGHTMODE = new BigInteger("0000000000000000000000000000000000000000000000000000000010000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getLightMode()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETLIGHTMODE = new BigInteger("0000000000000000000000000000000000000000000000000000000100000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#turnAimerOn(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_TURNAIMERON = new BigInteger("0000000000000000000000000000000000000000000000000000001000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#turnIlluminationOn(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_TURNILLUMINATIONON = new BigInteger("0000000000000000000000000000000000000000000000000000010000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getImageDataSize()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETIMAGEDATASIZE = new BigInteger("0000000000000000000000000000000000000000000000000000100000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#captureImage(byte[])} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CAPTUREIMAGE = new BigInteger("0000000000000000000000000000000000000000000000000001000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getStreamDataSize()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSTREAMDATASIZE = new BigInteger("0000000000000000000000000000000000000000000000000010000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getStreamDataSize(Rect, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSTREAMDATASIZE2 = new BigInteger("0000000000000000000000000000000000000000000000000100000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#initializeStream(Rect, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INITIALIZESTREAM = new BigInteger("0000000000000000000000000000000000000000000000001000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#startStream()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_STARTSTREAM = new BigInteger("0000000000000000000000000000000000000000000000010000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#readStream(byte[])} method is supported on the currently active device
         */
        public static final BigInteger METHOD_READSTREAM = new BigInteger("0000000000000000000000000000000000000000000000100000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#stopStream()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_STOPSTREAM = new BigInteger("0000000000000000000000000000000000000000000001000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#deinitializeStream()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_DEINITIALIZESTREAM = new BigInteger("0000000000000000000000000000000000000000000010000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setSymbologyEnable(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSYMBOLOGYENABLE = new BigInteger("0000000000000000000000000000000000000000000100000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyEnable(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYENABLE = new BigInteger("0000000000000000000000000000000000000000001000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyMaxDefault(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYMAXDEFAULT = new BigInteger("0000000000000000000000000000000000000000010000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyMinDefault(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYMINDEFAULT = new BigInteger("0000000000000000000000000000000000000000100000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setSymbologyMax(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSYMBOLOGYMAX = new BigInteger("0000000000000000000000000000000000000001000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyMax(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYMAX = new BigInteger("0000000000000000000000000000000000000010000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setSymbologyMin(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSYMBOLOGYMIN = new BigInteger("0000000000000000000000000000000000000100000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyMin(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYMIN = new BigInteger("0000000000000000000000000000000000001000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setSymbologyCheckCount(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSYMBOLOGYCHECKCOUNT = new BigInteger("0000000000000000000000000000000000010000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyCheckCount(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYCHECKCOUNT = new BigInteger("0000000000000000000000000000000000100000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setSymbologyProperty(int, int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSYMBOLOGYPROPERTY = new BigInteger("0000000000000000000000000000000001000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSymbologyProperty(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSYMBOLOGYPROPERTY = new BigInteger("0000000000000000000000000000000010000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setOutputType(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETOUTPUTTYPE = new BigInteger("0000000000000000000000000000000100000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getOutputType()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETOUTPUTTYPE = new BigInteger("0000000000000000000000000000001000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setSuffix(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSUFFIX = new BigInteger("0000000000000000000000000000010000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getSuffix()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSUFFIX = new BigInteger("0000000000000000000000000000100000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setInverseMode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETINVERSEMODE = new BigInteger("0000000000000000000000000001000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getInverseMode()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETINVERSEMODE = new BigInteger("0000000000000000000000000010000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setTriggerKeyEnable(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETTRIGGERKEYENABLE = new BigInteger("0000000000000000000000000100000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getTriggerKeyEnable()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETTRIGGERKEYENABLE = new BigInteger("0000000000000000000000001000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setTriggerKeyMode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETTRIGGERKEYMODE = new BigInteger("0000000000000000000000010000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getTriggerKeyMode()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETTRIGGERKEYMODE = new BigInteger("0000000000000000000000100000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setNumberOfBarcodes(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETNUMBEROFBARCODES = new BigInteger("0000000000000000000001000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getNumberOfBarcodes()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETNUMBEROFBARCODES = new BigInteger("0000000000000000000010000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setDelimiter(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDELIMITER = new BigInteger("0000000000000000000100000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getDelimiter()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETDELIMITER = new BigInteger("0000000000000000001000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setTriggerKeyTimeout(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETTRIGGERKEYTIMEOUT = new BigInteger("0000000000000000010000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getTriggerKeyTimeout()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETTRIGGERKEYTIMEOUT = new BigInteger("0000000000000000100000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setTriggerKeyOn(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETTRIGGERKEYON = new BigInteger("0000000000000001000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setScannerAPO(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSCANNERAPO = new BigInteger("0000000000000010000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getScannerAPO()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSCANNERAPO = new BigInteger("0000000000000100000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setCenteringWindow(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETCENTERINGWINDOW = new BigInteger("0000000000001000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getCenteringWindow()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETCENTERINGWINDOW = new BigInteger("0000000000010000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setDetectionAreaSize(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDETECTIONAREASIZE = new BigInteger("0000000000100000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getDetectionAreaSize()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETDETECTIONAREASIZE = new BigInteger("0000000001000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setLaserSwingWidth(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETLASERSWINGWIDTH = new BigInteger("0000000010000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getLaserSwingWidth()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETLASERSWINGWIDTH = new BigInteger("0000000100000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setLaserHighlightMode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETLASERHIGHLIGHTMODE = new BigInteger("0000001000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getLaserHighlightMode()} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETLASERHIGHLIGHTMODE = new BigInteger("0000010000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setInternalParameter(byte[])} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETINTERNALPARAMETER = new BigInteger("0000100000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setInternalParameter(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETINTERNALPARAMETER2 = new BigInteger("0001000000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#setInternalParameter(int, int[], int[])} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETINTERNALPARAMETER3 = new BigInteger("0010000000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getInternalParameter(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETINTERNALPARAMETER = new BigInteger("0100000000000000000000000000000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ScannerLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ScannerLibrary#getInternalParameter(int[], int[])} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETINTERNALPARAMETER2 = new BigInteger("1000000000000000000000000000000000000000000000000000000000000000000000", 2);
    }
}
