package com.casioeurope.mis.edt.constant;

import com.casioeurope.mis.edt.SamLibrary;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> SAM Library Constants<br/>
 * Constants to be used in the {@link SamLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class SamLibraryConstant {
    /**
     * List of return value constants. Check execution result of member function with return value constant.
     */
    public static class RETURN {
        /**
         * Card detection error. It returns when the SAM card can not be detected.
         */
        public static final int ERROR_CARDDETECT = 9;
        /**
         * Command error. It returns when a command error occurs.
         */
        public static final int ERROR_COMMAND = 4;
        /**
         * Communication error. It returns when a communication error occurs.
         */
        public static final int ERROR_COMMUNICATION = 7;
        /**
         * Data checksum error. It returns when a checksum error occurs.
         */
        public static final int ERROR_DATA_CHECKSUM = 8;
        /**
         * Data length error. It returns when the length does not correspond to the data.
         */
        public static final int ERROR_DATA_LENGTH = 5;
        /**
         * Data size error. It returns when data lager than the buffer is received.
         */
        public static final int ERROR_DATA_SIZE = 6;
        /**
         * Execution error. It returns when the first byte of data is 0xE0 and the received data is normal.
         */
        public static final int ERROR_EXECUTION = 2;
        /**
         * Internal error. It returns when an internal error occurs.
         */
        public static final int ERROR_INTERNAL = 1;
        /**
         * Non-open error. It returns when the function is called with SAM closed.
         */
        public static final int ERROR_NOTOPENED = -2;
        /**
         * Pattern error. It returns when the first byte of the data or the data itself is abnormal.
         */
        public static final int ERROR_PATTERN = 3;
        /**
         * Unsupported error. It returns when the function unsupported by your device.
         */
        public static final int ERROR_UNSUPPORTED = -1;
        /**
         * Success. It returns when the function returned successfully.
         */
        public static final int SUCCESS = 0;
    }

    /**
     * Methods of the {@link SamLibrary} class, used e.g. to check availability of said methods using {@link SamLibrary#isMethodSupported(BigInteger)} method.
     */
    public static class METHOD {
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#openSam()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_OPENSAM = new BigInteger("000000001", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#closeSam()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLOSESAM = new BigInteger("000000010", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#sendPowerOn()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_SENDPOWERON = new BigInteger("000000100", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#sendPowerOff()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_SENDPOWEROFF = new BigInteger("000001000", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#receiveATR(byte[])" method is supported on the currently active device
         */
        public static final BigInteger METHOD_RECEIVEATR = new BigInteger("000010000", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#communicateAPDU(byte[], int, byte[], int)" method is supported on the currently active device
         */
        public static final BigInteger METHOD_COMMUNICATEAPDU = new BigInteger("000100000", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#communicateDirect(byte, byte[], int, byte[], int)" method is supported on the currently active device
         */
        public static final BigInteger METHOD_COMMUNICATEDIRECT = new BigInteger("001000000", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#getTimeOutDelay()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETTIMEOUTDELAY = new BigInteger("010000000", 2);
        /**
         * Constant to be used with {@link SamLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code int} SamLibrary#setTimeOutDelay()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETTIMEOUTDELAY = new BigInteger("100000000", 2);
    }

    }
