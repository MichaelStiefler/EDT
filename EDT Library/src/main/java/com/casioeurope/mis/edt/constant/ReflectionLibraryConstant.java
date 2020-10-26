package com.casioeurope.mis.edt.constant;

import com.casioeurope.mis.edt.ReflectionLibrary;

import java.math.BigInteger;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Reflection Library Constants<br/>
 * Constants to be used in the {@link ReflectionLibrary} class.<br/>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "SpellCheckingInspection"})
public class ReflectionLibraryConstant {
    /**
     * Methods of the {@link ReflectionLibrary} class, used e.g. to check availability of said methods using {@link ReflectionLibrary#isMethodSupported(BigInteger)} method.
     */
    public static class METHOD {
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getBoolean(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETBOOLEAN                                  = new BigInteger("0000000000000000000000000000000000000000000001", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getBoolean(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETBOOLEANSTATIC                            = new BigInteger("0000000000000000000000000000000000000000000010", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getByte(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETBYTE                                     = new BigInteger("0000000000000000000000000000000000000000000100", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getByte(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETBYTESTATIC                               = new BigInteger("0000000000000000000000000000000000000000001000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getChar(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETCHAR                                     = new BigInteger("0000000000000000000000000000000000000000010000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getChar(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETCHARSTATIC                               = new BigInteger("0000000000000000000000000000000000000000100000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getDouble(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETDOUBLE                                   = new BigInteger("0000000000000000000000000000000000000001000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getDouble(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETDOUBLESTATIC                             = new BigInteger("0000000000000000000000000000000000000010000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getFloat(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETFLOAT                                    = new BigInteger("0000000000000000000000000000000000000100000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getFloat(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETFLOATSTATIC                              = new BigInteger("0000000000000000000000000000000000001000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getInt(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETINT                                      = new BigInteger("0000000000000000000000000000000000010000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getInt(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETINTSTATIC                                = new BigInteger("0000000000000000000000000000000000100000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getLong(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETLONG                                     = new BigInteger("0000000000000000000000000000000001000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getLong(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETLONGSTATIC                               = new BigInteger("0000000000000000000000000000000010000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getShort(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSHORT                                    = new BigInteger("0000000000000000000000000000000100000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getShort(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSHORTSTATIC                              = new BigInteger("0000000000000000000000000000001000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getString(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSTRING                                   = new BigInteger("0000000000000000000000000000010000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getString(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETSTRINGSTATIC                             = new BigInteger("0000000000000000000000000000100000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getType(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETTYPE                                     = new BigInteger("0000000000000000000000000001000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getType(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETTYPESTATIC                               = new BigInteger("0000000000000000000000000010000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getValue(Object obj, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETVALUE                                    = new BigInteger("0000000000000000000000000100000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#getValue(Class declaringClass, String fieldName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_GETVALUESTATIC                              = new BigInteger("0000000000000000000000001000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#invokeMethod(Object obj, String methodName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INVOKEMETHOD                                = new BigInteger("0000000000000000000000010000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#invokeMethod(Class declaringClass, String methodName)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INVOKEMETHODSTATIC                          = new BigInteger("0000000000000000000000100000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#invokeMethod(Class declaringClass, String methodName, Class[] classParams, Object[] params)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INVOKEMETHODSTATICWITHPARAMS                = new BigInteger("0000000000000000000001000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#invokeMethod(Object obj, String methodName, Class[] classParams, Object[] params)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_INVOKEMETHODWITHPARAMS                      = new BigInteger("0000000000000000000010000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setBoolean(Object obj, String fieldName, boolean value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETBOOLEAN                                  = new BigInteger("0000000000000000000100000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setBoolean(Class declaringClass, String fieldName, boolean value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETBOOLEANSTATIC                            = new BigInteger("0000000000000000001000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setByte(Object obj, String fieldName, byte value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETBYTE                                     = new BigInteger("0000000000000000010000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setByte(Class declaringClass, String fieldName, byte value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETBYTESTATIC                               = new BigInteger("0000000000000000100000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setChar(Object obj, String fieldName, char value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETCHAR                                     = new BigInteger("0000000000000001000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setChar(Class declaringClass, String fieldName, char value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETCHARSTATIC                               = new BigInteger("0000000000000010000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setDouble(Object obj, String fieldName, double value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDOUBLE                                   = new BigInteger("0000000000000100000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setDouble(Class declaringClass, String fieldName, double value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETDOUBLESTATIC                             = new BigInteger("0000000000001000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setFloat(Object obj, String fieldName, float value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETFLOAT                                    = new BigInteger("0000000000010000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setFloat(Class declaringClass, String fieldName, float value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETFLOATSTATIC                              = new BigInteger("0000000000100000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setInt(Object obj, String fieldName, int value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETINT                                      = new BigInteger("0000000001000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setInt(Class declaringClass, String fieldName, int value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETINTSTATIC                                = new BigInteger("0000000010000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setLong(Object obj, String fieldName, long value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETLONG                                     = new BigInteger("0000000100000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setLong(Class declaringClass, String fieldName, long value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETLONGSTATIC                               = new BigInteger("0000001000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setShort(Object obj, String fieldName, short value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSHORT                                    = new BigInteger("0000010000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setShort(Class declaringClass, String fieldName, short value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSHORTSTATIC                              = new BigInteger("0000100000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setString(Object obj, String fieldName, String value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSTRING                                   = new BigInteger("0001000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setString(Class declaringClass, String fieldName, String value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETSTRINGSTATIC                             = new BigInteger("0010000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setValue(Object obj, String fieldName, Object value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETVALUE                                    = new BigInteger("0100000000000000000000000000000000000000000000", 2);
        /**
         * Constant to be used with {@link ReflectionLibrary#isMethodSupported(BigInteger)} in order to check whether the {@link ReflectionLibrary#setValue(Class declaringClass, String fieldName, Object value)} method is supported on the currently active device
         */
        public static final BigInteger METHOD_SETVALUESTATIC                              = new BigInteger("1000000000000000000000000000000000000000000000", 2);
    }
}
