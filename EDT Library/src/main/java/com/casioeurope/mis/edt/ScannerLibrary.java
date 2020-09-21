package com.casioeurope.mis.edt;

import android.graphics.Rect;
import android.os.RemoteException;

import com.casioeurope.mis.edt.types.BooleanParcelable;
import com.casioeurope.mis.edt.types.ScanResult;
import com.casioeurope.mis.edt.types.ScanResultParcelable;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Scanner Library<br/><br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public class ScannerLibrary {

    /**
     * Constants used in the {@link ScannerLibrary} class.
     */
    public static class CONSTANT {

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
         * Methods of the {@link ScannerLibrary} class, used e.g. to check availability of said methods using {@link #isMethodSupported(BigInteger)} method.
         */
        public static class METHOD {
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #openScanner()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_OPENSCANNER = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000001", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #closeScanner()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_CLOSESCANNER = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000010", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #isScannerOpen()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_ISSCANNEROPEN = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000000100", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setDefaultAll()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETDEFAULTALL = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000001000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getAPIVersion()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETAPIVERSION = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000010000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getModuleVersion()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETMODULEVERSION = new BigInteger("0000000000000000000000000000000000000000000000000000000000000000100000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getScanResult(ScanResult)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSCANRESULT = new BigInteger("0000000000000000000000000000000000000000000000000000000000000001000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setNotificationLED(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETNOTIFICATIONLED = new BigInteger("0000000000000000000000000000000000000000000000000000000000000010000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getNotificationLED()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETNOTIFICATIONLED = new BigInteger("0000000000000000000000000000000000000000000000000000000000000100000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setNotificationVibrator(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETNOTIFICATIONVIBRATOR = new BigInteger("0000000000000000000000000000000000000000000000000000000000001000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getNotificationVibrator()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETNOTIFICATIONVIBRATOR = new BigInteger("0000000000000000000000000000000000000000000000000000000000010000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setNotificationSound(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETNOTIFICATIONSOUND = new BigInteger("0000000000000000000000000000000000000000000000000000000000100000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getNotificationSound()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETNOTIFICATIONSOUND = new BigInteger("0000000000000000000000000000000000000000000000000000000001000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setLightMode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETLIGHTMODE = new BigInteger("0000000000000000000000000000000000000000000000000000000010000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getLightMode()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETLIGHTMODE = new BigInteger("0000000000000000000000000000000000000000000000000000000100000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #turnAimerOn(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_TURNAIMERON = new BigInteger("0000000000000000000000000000000000000000000000000000001000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #turnIlluminationOn(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_TURNILLUMINATIONON = new BigInteger("0000000000000000000000000000000000000000000000000000010000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getImageDataSize()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETIMAGEDATASIZE = new BigInteger("0000000000000000000000000000000000000000000000000000100000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #captureImage(byte[])} method is supported on the currently active device
             */
            public static final BigInteger METHOD_CAPTUREIMAGE = new BigInteger("0000000000000000000000000000000000000000000000000001000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getStreamDataSize()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSTREAMDATASIZE = new BigInteger("0000000000000000000000000000000000000000000000000010000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getStreamDataSize(Rect, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSTREAMDATASIZE2 = new BigInteger("0000000000000000000000000000000000000000000000000100000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #initializeStream(Rect, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_INITIALIZESTREAM = new BigInteger("0000000000000000000000000000000000000000000000001000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #startStream()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_STARTSTREAM = new BigInteger("0000000000000000000000000000000000000000000000010000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #readStream(byte[])} method is supported on the currently active device
             */
            public static final BigInteger METHOD_READSTREAM = new BigInteger("0000000000000000000000000000000000000000000000100000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #stopStream()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_STOPSTREAM = new BigInteger("0000000000000000000000000000000000000000000001000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #deinitializeStream()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_DEINITIALIZESTREAM = new BigInteger("0000000000000000000000000000000000000000000010000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setSymbologyEnable(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSYMBOLOGYENABLE = new BigInteger("0000000000000000000000000000000000000000000100000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyEnable(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYENABLE = new BigInteger("0000000000000000000000000000000000000000001000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyMaxDefault(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYMAXDEFAULT = new BigInteger("0000000000000000000000000000000000000000010000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyMinDefault(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYMINDEFAULT = new BigInteger("0000000000000000000000000000000000000000100000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setSymbologyMax(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSYMBOLOGYMAX = new BigInteger("0000000000000000000000000000000000000001000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyMax(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYMAX = new BigInteger("0000000000000000000000000000000000000010000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setSymbologyMin(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSYMBOLOGYMIN = new BigInteger("0000000000000000000000000000000000000100000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyMin(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYMIN = new BigInteger("0000000000000000000000000000000000001000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setSymbologyCheckCount(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSYMBOLOGYCHECKCOUNT = new BigInteger("0000000000000000000000000000000000010000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyCheckCount(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYCHECKCOUNT = new BigInteger("0000000000000000000000000000000000100000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setSymbologyProperty(int, int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSYMBOLOGYPROPERTY = new BigInteger("0000000000000000000000000000000001000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSymbologyProperty(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSYMBOLOGYPROPERTY = new BigInteger("0000000000000000000000000000000010000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setOutputType(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETOUTPUTTYPE = new BigInteger("0000000000000000000000000000000100000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getOutputType()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETOUTPUTTYPE = new BigInteger("0000000000000000000000000000001000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setSuffix(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSUFFIX = new BigInteger("0000000000000000000000000000010000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getSuffix()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSUFFIX = new BigInteger("0000000000000000000000000000100000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setInverseMode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETINVERSEMODE = new BigInteger("0000000000000000000000000001000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getInverseMode()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETINVERSEMODE = new BigInteger("0000000000000000000000000010000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setTriggerKeyEnable(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETTRIGGERKEYENABLE = new BigInteger("0000000000000000000000000100000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getTriggerKeyEnable()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETTRIGGERKEYENABLE = new BigInteger("0000000000000000000000001000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setTriggerKeyMode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETTRIGGERKEYMODE = new BigInteger("0000000000000000000000010000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getTriggerKeyMode()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETTRIGGERKEYMODE = new BigInteger("0000000000000000000000100000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setNumberOfBarcodes(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETNUMBEROFBARCODES = new BigInteger("0000000000000000000001000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getNumberOfBarcodes()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETNUMBEROFBARCODES = new BigInteger("0000000000000000000010000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setDelimiter(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETDELIMITER = new BigInteger("0000000000000000000100000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getDelimiter()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETDELIMITER = new BigInteger("0000000000000000001000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setTriggerKeyTimeout(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETTRIGGERKEYTIMEOUT = new BigInteger("0000000000000000010000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getTriggerKeyTimeout()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETTRIGGERKEYTIMEOUT = new BigInteger("0000000000000000100000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setTriggerKeyOn(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETTRIGGERKEYON = new BigInteger("0000000000000001000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setScannerAPO(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETSCANNERAPO = new BigInteger("0000000000000010000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getScannerAPO()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETSCANNERAPO = new BigInteger("0000000000000100000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setCenteringWindow(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETCENTERINGWINDOW = new BigInteger("0000000000001000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getCenteringWindow()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETCENTERINGWINDOW = new BigInteger("0000000000010000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setDetectionAreaSize(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETDETECTIONAREASIZE = new BigInteger("0000000000100000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getDetectionAreaSize()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETDETECTIONAREASIZE = new BigInteger("0000000001000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setLaserSwingWidth(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETLASERSWINGWIDTH = new BigInteger("0000000010000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getLaserSwingWidth()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETLASERSWINGWIDTH = new BigInteger("0000000100000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setLaserHighlightMode(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETLASERHIGHLIGHTMODE = new BigInteger("0000001000000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getLaserHighlightMode()} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETLASERHIGHLIGHTMODE = new BigInteger("0000010000000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setInternalParameter(byte[])} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETINTERNALPARAMETER = new BigInteger("0000100000000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setInternalParameter(int, int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETINTERNALPARAMETER2 = new BigInteger("0001000000000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #setInternalParameter(int, int[], int[])} method is supported on the currently active device
             */
            public static final BigInteger METHOD_SETINTERNALPARAMETER3 = new BigInteger("0010000000000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getInternalParameter(int)} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETINTERNALPARAMETER = new BigInteger("0100000000000000000000000000000000000000000000000000000000000000000000", 2);
            /**
             * Constant to be used with {@link #isMethodSupported(BigInteger)} in order to check whether the {@link #getInternalParameter(int[], int[])} method is supported on the currently active device
             */
            public static final BigInteger METHOD_GETINTERNALPARAMETER2 = new BigInteger("1000000000000000000000000000000000000000000000000000000000000000000000", 2);
        }
    }

    private static ScannerLibrary instance;

    private ScannerLibrary() {
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
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int openScanner() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().openScanner(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Close the barcode scanner.<br/>
     * Call this function when the application terminates.
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int closeScanner() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().closeScanner(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
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
        BooleanParcelable unsupported = new BooleanParcelable();
        boolean retVal = getInstance().edtServiceScannerLibrary().isScannerOpen(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Return all barcode scanner settings to default.
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setDefaultAll() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setDefaultAll(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the Scanner API version.
     *
     * @return {@link String String}: The API Version of the Scanner or Null in case of failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static String getAPIVersion() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        String retVal = getInstance().edtServiceScannerLibrary().getAPIVersion(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the Scanner module version.
     *
     * @return {@link String String}: The Module Version of the Scanner or Null in case of failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static String getModuleVersion() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        String retVal = getInstance().edtServiceScannerLibrary().getModuleVersion(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
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
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getScanResult(ScanResult scanResult) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        ScanResultParcelable scanResultParcelable = new ScanResultParcelable(scanResult);
        int retVal = getInstance().edtServiceScannerLibrary().getScanResult(scanResultParcelable, unsupported);
        scanResultParcelable.copyTo(scanResult);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Set the behavior of the notification LED.
     * @param led {@code int}: The behavior of the notification LED<br/>
     *            {@link ScannerLibrary.CONSTANT.NOTIFICATION#LED_OFF LED_OFF}: The notification LED not lights up in success and failure scanning.<br/>
     *            {@link ScannerLibrary.CONSTANT.NOTIFICATION#LED_ON LED_ON}: notification LED turns on GREEN in success scanning, and RED in failure scanning.
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_PARAMETER ERROR_PARAMETER}: Parameter error<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int setNotificationLED(int led) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setNotificationLED(led, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     * Get the behavior of the notification LED.
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.NOTIFICATION#LED_OFF LED_OFF}: The notification LED not lights up in success and failure scanning.<br/>
     *            {@link ScannerLibrary.CONSTANT.NOTIFICATION#LED_ON LED_ON}: notification LED turns on GREEN in success scanning, and RED in failure scanning.<br/>
     *            {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getNotificationLED() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getNotificationLED(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setNotificationVibrator(int vibrator) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setNotificationVibrator(vibrator, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getNotificationVibrator() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getNotificationVibrator(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setNotificationSound(int sound) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setNotificationSound(sound, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getNotificationSound() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getNotificationSound(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setLightMode(int lightMode) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setLightMode(lightMode, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLightMode() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getLightMode(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int turnAimerOn(int aimerOn) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().turnAimerOn(aimerOn, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int turnIlluminationOn(int illuminationOn) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().turnIlluminationOn(illuminationOn, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getImageDataSize() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getImageDataSize(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int captureImage(byte[] buffer) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().captureImage(buffer, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getStreamDataSize() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getStreamDataSize(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getStreamDataSize(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getStreamDataSize2(rectangle, resolution, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int initializeStream(Rect rectangle, int resolution) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().initializeStream(rectangle, resolution, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int startStream() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().startStream(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int readStream(byte[] buffer) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().readStream(buffer, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int stopStream() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().stopStream(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int deinitializeStream() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().deinitializeStream(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setSymbologyEnable(int symbologyID, int enable) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setSymbologyEnable(symbologyID, enable, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyEnable(int symbologyID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyEnable(symbologyID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyMaxDefault(int symbologyID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMaxDefault(symbologyID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyMinDefault(int symbologyID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMinDefault(symbologyID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setSymbologyMax(int symbologyID, int max) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setSymbologyMax(symbologyID, max, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyMax(int symbologyID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMax(symbologyID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setSymbologyMin(int symbologyID, int min) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setSymbologyMin(symbologyID, min, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyMin(int symbologyID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyMin(symbologyID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setSymbologyCheckCount(int symbologyID, int checkCount) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setSymbologyCheckCount(symbologyID, checkCount, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyCheckCount(int symbologyID) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyCheckCount(symbologyID, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setSymbologyProperty(int symbologyID, int propertyNo, int propertySetting) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setSymbologyProperty(symbologyID, propertyNo, propertySetting, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getSymbologyProperty(int symbologyID, int propertyNo) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSymbologyProperty(symbologyID, propertyNo, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setOutputType(int outputType) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setOutputType(outputType, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getOutputType() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getOutputType(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setSuffix(int suffix) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setSuffix(suffix, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getSuffix() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getSuffix(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setInverseMode(int inverseMode) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setInverseMode(inverseMode, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getInverseMode() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getInverseMode(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setTriggerKeyEnable(int triggerKeyEnable) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyEnable(triggerKeyEnable, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getTriggerKeyEnable() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyEnable(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setTriggerKeyMode(int triggerKeyMode) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyMode(triggerKeyMode, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getTriggerKeyMode() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyMode(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setNumberOfBarcodes(int numberOfBarcodes) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setNumberOfBarcodes(numberOfBarcodes, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getNumberOfBarcodes() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getNumberOfBarcodes(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setDelimiter(int delimiter) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setDelimiter(delimiter, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getDelimiter() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getDelimiter(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setTriggerKeyTimeout(int triggerKeyTimeout) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyTimeout(triggerKeyTimeout, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getTriggerKeyTimeout() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getTriggerKeyTimeout(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setTriggerKeyOn(int triggerKeyOn) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setTriggerKeyOn(triggerKeyOn, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setScannerAPO(int scannerAPOTime) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setScannerAPO(scannerAPOTime, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getScannerAPO() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getScannerAPO(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setCenteringWindow(int centeringWindow) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setCenteringWindow(centeringWindow, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getCenteringWindow() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getCenteringWindow(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setDetectionAreaSize(int detectionAreaSize) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setDetectionAreaSize(detectionAreaSize, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getDetectionAreaSize() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getDetectionAreaSize(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setLaserSwingWidth(int laserSwingWidth) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setLaserSwingWidth(laserSwingWidth, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLaserSwingWidth() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getLaserSwingWidth(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setLaserHighlightMode(int enable) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setLaserHighlightMode(enable, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    /**
     *
     *
     * @return {@code int}: {@link ScannerLibrary.CONSTANT.RETURN#SUCCESS SUCCESS}: Success<br/>
     * {@link ScannerLibrary.CONSTANT.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED}: Unsupported error
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static int getLaserHighlightMode() throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getLaserHighlightMode(unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setInternalParameter(byte[] command) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter(command, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setInternalParameter(int tag, int value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter2(tag, value, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int setInternalParameter(int number, int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().setInternalParameter3(number, tags, values, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getInternalParameter(int tag) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getInternalParameter(tag, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }

    public static int getInternalParameter(int[] tags, int[] values) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtServiceScannerLibrary().getInternalParameter2(tags, values, unsupported);
        checkMethodUnsupported(unsupported);
        return retVal;
    }


}
