package com.casioeurope.mis.edt.constant;

import com.casioeurope.mis.edt.SystemLibrary;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> System Library Constants<br/>
 * Constants to be used in the {@link SystemLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class SystemLibraryConstant {
    /**
     * Methods of the {@link SystemLibrary} class, used e.g. to check availability of said methods using {@link SystemLibrary#isMethodSupported(BigInteger)} method.
     */
    public static class METHOD {
        /**
         * Constant to be used with {@link SystemLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@link java.lang.String String} getCASIOSerial()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETCASIOSERIAL = new BigInteger("0001", 2);
        /**
         * Constant to be used with {@link SystemLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@link java.lang.String String} getModelName()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETMODELNAME = new BigInteger("0010", 2);
        /**
         * Constant to be used with {@link SystemLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code boolean} getNavigationBarState()" method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETNAVIGATIONBARSTATE = new BigInteger("0100", 2);
        /**
         * Constant to be used with {@link SystemLibrary#isMethodSupported(BigInteger)} in order to check whether the "{@code void} setNavigationBarState({@code boolean} state)" method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETNAVIGATIONBARSTATE = new BigInteger("1000", 2);

    }

}
