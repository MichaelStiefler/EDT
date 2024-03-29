package com.casioeurope.mis.edt.constant;

import android.view.KeyEvent;

import com.casioeurope.mis.edt.KeyLibrary;
import com.casioeurope.mis.edt.type.ApplicationInfo;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Keyboard Library Constants<br/>
 * Constants to be used in the {@link KeyLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class KeyLibraryConstant {
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
         * An Error occured while specifying a new Key Character Map File
         * @since 2.02
         */
        public static final int ERROR_KCM = -16;
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
     * Input Modes available to hardware keyboards
     * @since 2.02
     */
    public static class INPUT_MODE {
        /**
         * Unknown Keyboard Input Mode.
         * @since 2.02
         */
        public static final int INPUT_MODE_UNKNOWN = 0;
        /**
         * Numeric Keyboard Input Mode.
         * @since 2.02
         */
        public static final int INPUT_MODE_NUMERIC = 1;
        /**
         * Alpha (Small Letters) Keyboard Input Mode.
         * @since 2.02
         */
        public static final int INPUT_MODE_SMALL_ALPHA = 2;
        /**
         * Alpha (Capital Letters) Keyboard Input Mode.
         * @since 2.02
         */
        public static final int INPUT_MODE_CAPITAL_ALPHA = 3;
        /**
         * Fn Keyboard Input Mode<br/>
         * This Input Mode applies for a single key stroke only, after which the Input Mode automatically returns to the previously selected Mode.
         * @since 2.02
         */
        public static final int INPUT_MODE_FN = 4;
    }

    /**
     * Methods of the {@link KeyLibrary} class, used e.g. to check availability of said methods using {@link KeyLibrary#isMethodSupported(BigInteger)} method.
     */
    public static class METHOD {
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setUserKeyCode(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETUSERKEYCODE = new BigInteger("00000000000000000000000000000000001", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getUserKeyCode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETUSERKEYCODE = new BigInteger("00000000000000000000000000000000010", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setDefaultKeyCode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDEFAULTKEYCODE = new BigInteger("00000000000000000000000000000000100", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setFnUserKeyCode(int, int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETFNUSERKEYCODE = new BigInteger("00000000000000000000000000000001000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getFnUserKeyCode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETFNUSERKEYCODE = new BigInteger("00000000000000000000000000000010000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setFnDefaultKeyCode(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETFNDEFAULTKEYCODE = new BigInteger("00000000000000000000000000000100000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETLAUNCHAPPLICATION = new BigInteger("00000000000000000000000000001000000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETLAUNCHAPPLICATION = new BigInteger("00000000000000000000000000010000000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#clearLaunchApplication(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLEARLAUNCHAPPLICATION = new BigInteger("00000000000000000000000000100000000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setFnLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETFNLAUNCHAPPLICATION = new BigInteger("00000000000000000000000001000000000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getFnLaunchApplication(int, ApplicationInfo)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETFNLAUNCHAPPLICATION = new BigInteger("00000000000000000000000010000000000", 2);
        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#clearFnLaunchApplication(int)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_CLEARFNLAUNCHAPPLICATION = new BigInteger("00000000000000000000000100000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#broadcastKey(String, String, KeyEvent)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_BROADCASTKEY = new BigInteger("00000000000000000000001000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#changeKCMapFile(String, byte[])} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_CHANGEKCMAPFILE = new BigInteger("00000000000000000000010000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#changeKCMapFileToDefault()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_CHANGEKCMAPFILETODEFAULT = new BigInteger("00000000000000000000100000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#changeTrayIcon(KeyEvent)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_CHANGETRAYICON = new BigInteger("00000000000000000001000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getCurrentKCMapFile()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_GETCURRENTKCMAPFILE = new BigInteger("00000000000000000010000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getKeypadMode()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_GETKEYPADMODE = new BigInteger("00000000000000000100000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getTestMode()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_GETTESTMODE = new BigInteger("00000000000000001000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#hasHardwareKey(int)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_HASHARDWAREKEY = new BigInteger("00000000000000010000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#hasWakeupRes(KeyEvent)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_HASWAKEUPRES = new BigInteger("00000000000000100000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#hijackingKey(KeyEvent, boolean)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_HIJACKINGKEY = new BigInteger("00000000000001000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#isDirectInputStyle()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_ISDIRECTINPUTSTYLE = new BigInteger("00000000000010000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#isFinishedHandle()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_ISFINISHEDHANDLE = new BigInteger("00000000000100000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#isKeyControlMode()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_ISKEYCONTROLMODE = new BigInteger("00000000001000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#isWakeupRes(int)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_ISWAKEUPRES = new BigInteger("00000000010000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#performKeyPressFeedback(KeyEvent)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_PERFORMKEYPRESSFEEDBACK = new BigInteger("00000000100000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#removeKCMapFile()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_REMOVEKCMAPFILE = new BigInteger("00000001000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setDirectInputStyle(boolean)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_SETDIRECTINPUTSTYLE = new BigInteger("00000010000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#getFixedNumberMode()} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_GETFIXEDNUMBERMODE = new BigInteger("00000100000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setFixedNumberMode(boolean)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_SETFIXEDNUMBERMODE = new BigInteger("00001000000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setKeyControlMode(boolean)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_SETKEYCONTROLMODE = new BigInteger("00010000000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setKeypadMode(int)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_SETKEYPADMODE = new BigInteger("00100000000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#setWakeupRes(int, boolean)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_SETWAKEUPRES = new BigInteger("01000000000000000000000000000000000", 2);

        /**
         * Constant to be used with {@link KeyLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link KeyLibrary#updateMetaState(KeyEvent)} method is supported on the currently active device
         * @since 2.02
         */
        public static final BigInteger METHOD_UPDATEMETASTATE = new BigInteger("10000000000000000000000000000000000", 2);
    }

}
