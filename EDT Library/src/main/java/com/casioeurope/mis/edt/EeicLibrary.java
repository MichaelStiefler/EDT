package com.casioeurope.mis.edt;

import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

import com.casioeurope.mis.edt.constant.EeicLibraryConstant;
import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * The <b>CASIO Enterprise Developer Tools</b> External Expansion Interface Control (EEIC) Library
 *
 * @apiNote The External Expansion Interface Control (EEIC) Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly adviced not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link EeicLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link EeicLibrary.SerialDevice#sendBreak() EeicLibrary.SerialDevice.sendBreak()} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>EeicLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         EeicLibrary.SerialDevice.sendBreak();
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>EeicLibrary.onLibraryReady(() -> { EeicLibrary.SerialDevice.sendBreak(); });</pre>
 *          <br/>Or even further to:<br/>
 * <pre>EeicLibrary.onLibraryReady(EeicLibrary.SerialDevice::sendBreak);</pre>
 *
 * @version 2.00
 * @since 2.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "deprecation", "JavadocReference", "SpellCheckingInspection"})
public class EeicLibrary {

    private static class MyInterruptCallback implements Comparable<MyInterruptCallback> {
        private final InterruptCallback interruptCallback;
        private final Handler handler;

        public MyInterruptCallback(InterruptCallback interruptCallback, Handler handler) {
            this.interruptCallback = interruptCallback;
            this.handler = handler;
        }

        @Override
        public int compareTo(MyInterruptCallback other) {
            int thisHashCode = this.interruptCallback.hashCode();
            int otherHashCode = other.getInterruptCallback().hashCode();
            return Integer.compare(thisHashCode, otherHashCode);
        }

        public InterruptCallback getInterruptCallback() {
            return this.interruptCallback;
        }

        public Handler getHandler() {
            return this.handler;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof MyInterruptCallback)) return false;
            return this.interruptCallback.equals(((MyInterruptCallback) obj).getInterruptCallback());
        }

        @Override
        public int hashCode() {
            return this.interruptCallback.hashCode();
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public String toString() {
            return this.interruptCallback.toString();
        }

    }

    private final Set<MyInterruptCallback> myInterruptCallback = new HashSet<>();

    private static class NotificationRunnable implements Runnable {
        private final int gpio;
        private final InterruptCallback interruptCallback;

        public NotificationRunnable(int gpio, InterruptCallback interruptCallback) {
            this.gpio = gpio;
            this.interruptCallback = interruptCallback;
        }

        public void run() {
            this.interruptCallback.onChanged(this.gpio);
        }
    }

    private final IEeicCallback myEeicCallback = new IEeicCallback.Stub() {
        @Override
        public void onChanged(int gpio) {
            for (MyInterruptCallback element : myInterruptCallback) {
                if (element.getHandler() == null) {
                    element.getInterruptCallback().onChanged(gpio);
                } else {
                    element.getHandler().post(new NotificationRunnable(gpio, element.getInterruptCallback()));
                }
            }
        }
    };

    private static EeicLibrary instance;

    private EeicLibrary() {
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

    private static EeicLibrary getInstance() {
        if (EeicLibrary.instance == null) {
            EeicLibrary.instance = new EeicLibrary();
        }
        return EeicLibrary.instance;
    }

    private IEeicLibrary eeicLibraryService() {
        return EDTServiceConnection.getInstance().getEeicLibrary();
    }

    /**
     * This callback class called when an interrupt occurs.
     *
     * @apiNote Create a class extended from this class and register it with the registerCallback function.<br/>
     * Import: {@code import com.casioeurope.mis.edt.EeicLibrary.InterruptCallback;}<br/>
     * Instance:<br/>
     * <pre> private class MyInterruptCallback extends InterruptCallback {
     *     public void onChanged (int gpio) {
     *         ... do something here ...
     *     }
     * }
     * private MyInterruptCallback myInterruptCallback = new MyInterruptCallback();</pre>
     */
    public static abstract class InterruptCallback {
        public abstract void onChanged(int i);
    }

    /**
     * Gets the EEIC Library Version.
     *
     * @return {@link String}: Returns the String Representation of the EEIC Library Version currently being used, or null on failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
     */
    public static String getLibraryVersion() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.getLibraryVersion();
    }

    /**
     * Gets the power status of the external expansion interface.
     *
     * @return {@code boolean}: Returns true on success and false on failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
     */
    public static boolean isPowerOn() throws RemoteException, UnsupportedOperationException, IllegalStateException {
        return Implementation.isPowerOn();
    }

    /**
     * Turns the power of the external expansion interface on and off.
     *
     * @param enable {@code boolean}: On/Off<br/>
     *                              Specify the power status of the external expansion interface.<br/>
     *                              Turn on with true, turn off with false.
     * @return {@code boolean}: Returns true on success and false on failure.
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static boolean setPower(boolean enable) throws RemoteException, UnsupportedOperationException {
        return Implementation.setPower(enable);
    }

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
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
     *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
     */
    public static boolean isMethodSupported(String methodName) throws IllegalStateException {
        return Implementation.isMethodSupported(methodName);
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the EeicLibrary Service becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addEeicLibraryCallback(callback);
    }


    private static final class Implementation {
        private static boolean setPower(boolean enable) throws RemoteException, UnsupportedOperationException {
            BooleanParcelable unsupported = new BooleanParcelable();
            if (getInstance().eeicLibraryService() == null) {
                onLibraryReady(() -> {
                    getInstance().eeicLibraryService().setPower(enable, unsupported);
                    checkMethodUnsupported("setPower", unsupported);
                });
                return true;
            }
            boolean retVal = getInstance().eeicLibraryService().setPower(enable, unsupported);
            checkMethodUnsupported("setPower", unsupported);
            return retVal;
        }

        private static boolean isPowerOn() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            boolean retVal = getInstance().eeicLibraryService().isPowerOn(unsupported);
            checkMethodUnsupported("isPowerOn", unsupported);
            return retVal;
        }

        private static String getLibraryVersion() throws RemoteException, UnsupportedOperationException, IllegalStateException {
            if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            BooleanParcelable unsupported = new BooleanParcelable();
            String retVal = getInstance().eeicLibraryService().getLibraryVersion(unsupported);
            checkMethodUnsupported("getLibraryVersion", unsupported);
            return retVal;
        }

        private static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
            if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().eeicLibraryService().isMethodSupported(method.toString());
            } catch (Exception e) {
                return false;
            }
        }

        private static boolean isMethodSupported(String methodName) throws IllegalStateException {
            if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
            try {
                return getInstance().eeicLibraryService().isMethodNameSupported(methodName);
            } catch (Exception e) {
                return false;
            }
        }
    }

    /**
     * The GpioDevice class is used to control general purpose input / output pins
     *
     * @version 2.00
     * @since 2.00
     */
    public static class GpioDevice {

        /**
         * Sets the specified GPIO as an input pin.
         *
         * @param pinNo {@code int}: GPIO number<br/>
         *                      Specify the target GPIO.<br/>
         *                      For details of the GPIO number, refer to Constants
         * @param pinStatus {@code int}: Pin status<br/>
         *                      Specify the pin status by Pull up, Pull down, or High impedance.<br/>
         *                      For details of the pin status, refer to {@link EeicLibraryConstant.GPIO_DEVICE GPIO_DEVICE Constants}
         * @return {@code int}: {@link EeicLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED} in case of failure, other values indicate success.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static int setInputDirection(int pinNo, int pinStatus) throws RemoteException, UnsupportedOperationException {
            return Implementation.setInputDirection(pinNo, pinStatus);
        }

        /**
         * Sets the specified GPIO as an output pin.
         *
         * @param pinNo {@code int}: GPIO number<br/>
         *                      Specify the target GPIO.<br/>
         *                      For details of the GPIO number, refer to Constants
         * @param value {@code int}: Output value<br/>
         *                      Specify the initial output by High or Low.<br/>
         *                      For details of output value, refer to {@link EeicLibraryConstant.GPIO_DEVICE GPIO_DEVICE Constants}
         * @return {@code int}: {@link EeicLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED} in case of failure, other values indicate success.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static int setOutputDirection(int pinNo, int value) throws RemoteException, UnsupportedOperationException {
            return Implementation.setOutputDirection(pinNo, value);
        }

        /**
         * Output the value to the specified GPIO. Only GPIO set as output direction can be used.
         *
         * @param pinNo {@code int}: GPIO number<br/>
         *                      Specify the target GPIO.<br/>
         *                      For details of the GPIO number, refer to {@link EeicLibraryConstant.GPIO_DEVICE GPIO_DEVICE Constants}
         * @param value {@code int}: Output<br/>
         *                      Specify the output by High or Low.<br/>
         *                      For details of output value, refer to {@link EeicLibraryConstant.GPIO_DEVICE GPIO_DEVICE Constants}
         * @return {@code int}: {@link EeicLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED} in case of failure, other values indicate success.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static int setValue(int pinNo, int value) throws RemoteException, UnsupportedOperationException {
            return Implementation.setValue(pinNo, value);
        }

        /**
         * Gets the input value of the specified GPIO. Only GPIO set as input direction can be used.
         *
         * @param pinNo {@code int}: GPIO number<br/>
         *                      Specify the target GPIO.<br/>
         *                      For details of the GPIO number, refer to {@link EeicLibraryConstant.GPIO_DEVICE GPIO_DEVICE Constants}
         * @return {@code int}: {@link EeicLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED} in case of failure, otherwise returns the value of the specified GPIO.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
         *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
         */
        public static int getValue(int pinNo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            return Implementation.getValue(pinNo);
        }

        /**
         * Sets the interrupt edge of the specified GPIO.
         *
         * @param pinNo {@code int}: GPIO number<br/>
         *                      Specify the target GPIO.<br/>
         *                      For details of the GPIO number, refer to Constants
         * @param type {@code int}: Interrupt type<br/>
         *                      Specify the interrupt type by none, rising, falling, and both.<br/>
         *                      For details of the interrupt type, refer to {@link EeicLibraryConstant.GPIO_DEVICE GPIO_DEVICE Constants}
         * @return {@code int}: {@link EeicLibraryConstant.RETURN#ERROR_UNSUPPORTED ERROR_UNSUPPORTED} in case of failure, other values indicate success.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static int setInterruptEdge(int pinNo, int type) throws RemoteException, UnsupportedOperationException {
            return Implementation.setInterruptEdge(pinNo, type);
        }

        /**
         * Register a callback for an edge interrupt. Handle all GPIOs with one callback. When an interrupt occurs, the GPIO number is notified by a callback argument.
         *
         * @param callback {@link InterruptCallback InterruptCallback}: Callback<br>
         *                                                            Specify the callback that handles the interrupt.<br/>
         *                                                            For details of the callback, refer to {@link InterruptCallback InterruptCallback}
         * @param handler {@link Handler Handler}: If specified, the callback method will be posted on the thread belonging to this {@link Handler Handler}<br/>
         *                                       Use {@code null} if you don't need a specific thread to handle the callback method.
         * @return {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         * @apiNote The {@link Handler handler} parameter is particularly useful in case you need to update a User Interface, in which case for instance you can use an Activity's Looper to get the Main UI Thread Handler like this:<br/>
         * {@link Handler Handler handler} = new {@link Handler Handler}({@link Looper Looper}.{@link Looper#getMainLooper getMainLooper()});<br/>
         * Registered callbacks are being identified by their {@link InterruptCallback InterruptCallback} object instance only and it's not allowed to register multiple callbacks with the same {@link InterruptCallback InterruptCallback} object instance.<br/>
         * You can register as many unique {@link InterruptCallback InterruptCallback} callback objects as you like and all of them will get notified when a {@link InterruptCallback callback} is getting received.
         */
        public static boolean registerCallback(InterruptCallback callback, Handler handler) throws RemoteException, UnsupportedOperationException {
            return Implementation.registerCallback(callback, handler);
        }

        /**
         * Unregisters an interrupt detection callback.
         *
         * @param callback {@link InterruptCallback InterruptCallback}: Callback<br>
         *                                                            Specify the registered callback.<br/>
         *                                                            For details of the callback, refer to {@link InterruptCallback InterruptCallback}
         * @return {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean unregisterCallback(InterruptCallback callback) throws RemoteException, UnsupportedOperationException {
            return Implementation.unregisterCallback(callback);
        }

        private static final class Implementation {

            private static int setInputDirection(int pinNo, int pinStatus) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().gpioDeviceSetInputDirection(pinNo, pinStatus, unsupported);
                        checkMethodUnsupported("setInputDirection", unsupported);
                    });
                    return EeicLibraryConstant.RETURN.SUCCESS;
                }
                int retVal = getInstance().eeicLibraryService().gpioDeviceSetInputDirection(pinNo, pinStatus, unsupported);
                checkMethodUnsupported("setInputDirection", unsupported);
                return retVal;
            }

            private static int setOutputDirection(int pinNo, int value) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().gpioDeviceSetOutputDirection(pinNo, value, unsupported);
                        checkMethodUnsupported("setOutputDirection", unsupported);
                    });
                    return EeicLibraryConstant.RETURN.SUCCESS;
                }
                int retVal = getInstance().eeicLibraryService().gpioDeviceSetOutputDirection(pinNo, value, unsupported);
                checkMethodUnsupported("setOutputDirection", unsupported);
                return retVal;
            }

            private static int setValue(int pinNo, int value) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().gpioDeviceSetValue(pinNo, value, unsupported);
                        checkMethodUnsupported("setValue", unsupported);
                    });
                    return EeicLibraryConstant.RETURN.SUCCESS;
                }
                int retVal = getInstance().eeicLibraryService().gpioDeviceSetValue(pinNo, value, unsupported);
                checkMethodUnsupported("setValue", unsupported);
                return retVal;
            }

            private static int getValue(int pinNo) throws RemoteException, UnsupportedOperationException, IllegalStateException {
                if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
                BooleanParcelable unsupported = new BooleanParcelable();
                int retVal = getInstance().eeicLibraryService().gpioDeviceGetValue(pinNo, unsupported);
                checkMethodUnsupported("getValue", unsupported);
                return retVal;
            }

            private static int setInterruptEdge(int pinNo, int type) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().gpioDeviceSetInterruptEdge(pinNo, type, unsupported);
                        checkMethodUnsupported("setInterruptEdge", unsupported);
                    });
                    return EeicLibraryConstant.RETURN.SUCCESS;
                }
                int retVal = getInstance().eeicLibraryService().gpioDeviceSetInterruptEdge(pinNo, type, unsupported);
                checkMethodUnsupported("setInterruptEdge", unsupported);
                return retVal;
            }

            private static boolean registerCallback(InterruptCallback callback, Handler handler) throws RemoteException, UnsupportedOperationException {
                if (callback == null) return false;
                boolean myInterruptCallbackWasEmpty = getInstance().myInterruptCallback.isEmpty();
                getInstance().myInterruptCallback.add(new MyInterruptCallback(callback, handler));
                if (myInterruptCallbackWasEmpty) {
                    BooleanParcelable unsupported = new BooleanParcelable();
                    if (getInstance().eeicLibraryService() == null) {
                        onLibraryReady(() -> {
                            getInstance().eeicLibraryService().gpioDeviceRegisterCallback(getInstance().myEeicCallback, unsupported);
                            checkMethodUnsupported("registerCallback", unsupported);
                        });
                        return true;
                    }
                    getInstance().eeicLibraryService().gpioDeviceRegisterCallback(getInstance().myEeicCallback, unsupported);
                    checkMethodUnsupported("registerCallback", unsupported);
                }
                return true;
            }

            private static boolean unregisterCallback(InterruptCallback callback) throws RemoteException, UnsupportedOperationException {
                if (callback == null) return false;
                boolean myInterruptCallbackWasEmpty = getInstance().myInterruptCallback.isEmpty();
                for (Iterator<MyInterruptCallback> i = getInstance().myInterruptCallback.iterator(); i.hasNext(); ) {
                    MyInterruptCallback element = i.next();
                    if (element.getInterruptCallback().equals(callback)) {
                        i.remove();
                    }
                }
                boolean myInterruptCallbackIsEmpty = getInstance().myInterruptCallback.isEmpty();
                if (!myInterruptCallbackWasEmpty && myInterruptCallbackIsEmpty) {
                    BooleanParcelable unsupported = new BooleanParcelable();
                    if (getInstance().eeicLibraryService() == null) {
                        onLibraryReady(() -> {
                            getInstance().eeicLibraryService().gpioDeviceUnregisterCallback(unsupported);
                            checkMethodUnsupported("unregisterCallback", unsupported);
                        });
                        return true;
                    }
                    getInstance().eeicLibraryService().gpioDeviceUnregisterCallback(unsupported);
                    checkMethodUnsupported("unregisterCallback", unsupported);
                }
                return true;
            }
        }
    }

    /**
     * The SerialDevice class is used to communicate with an external expansion device connected via Serial Interface.
     *
     * @version 2.00
     * @since 2.00
     */
    public static class SerialDevice {

        /**
         * Open the Serial Interface Port
         *
         * @param baudrate {@code int}: Specify the baud rate.<br/>
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @param flags {@code int}: Specify additional flags for Serial Port Operation.
         * @param hwflow {@code int}: Specify Hardware Flow Control Settings.
         * @param bitLen {@code int}: Specify 7 or 8 bit word lenght.
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @param parityBit {@code int}: Specify Parity Bit (None/Even/Odd).
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @param stopBit {@code int}: Specify Stopbit (1/2).
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open(int baudrate, int flags, boolean hwflow, int bitLen, int parityBit, int stopBit) throws RemoteException, UnsupportedOperationException {
            return Implementation.open(baudrate, flags, hwflow, bitLen, parityBit, stopBit);
        }

        /**
         * Open the Serial Interface Port
         *
         * @param baudrate {@code int}: Specify the baud rate.<br/>
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @param bitLen {@code int}: Specify 7 or 8 bit word lenght.
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @param parityBit {@code int}: Specify Parity Bit (None/Even/Odd).
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @param stopBit {@code int}: Specify Stopbit (1/2).
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open(int baudrate, int bitLen, int parityBit, int stopBit) throws RemoteException, UnsupportedOperationException {
            return Implementation.open(baudrate, bitLen, parityBit, stopBit);
        }

        /**
         * Open the Serial Interface Port
         *
         * @param baudrate {@code int}: Specify the baud rate.<br/>
         *                            See {@link EeicLibraryConstant.SERIAL_DEVICE SERIAL_DEVICE Constants} for details
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open(int baudrate) throws RemoteException, UnsupportedOperationException {
            return Implementation.open(baudrate);
        }

        /**
         * Close the Serial Interface Port
         *
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean close() throws RemoteException, UnsupportedOperationException {
            return Implementation.close();
        }

        /**
         * Write one single Byte of Data to the Serial Interface Port
         *
         * @param data {@code byte}: Specify the data Byte to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte data) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(data);
        }

        /**
         * Write Data from a buffer to the Serial Interface Port
         *
         * @param buffer {@code byte[]}: Specify the data buffer to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte[] buffer) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(buffer);
        }

        /**
         * Write Data of a given length from a buffer to the Serial Interface Port, starting at the given offset.
         *
         * @param buffer {@code byte[]}: Specify the data buffer to be written.
         * @param offset {@code int}: Specify the offset of the buffer at which the write operation will start.
         * @param length {@code int}: Specify the number of bytes to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte[] buffer, int offset, int length) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(buffer, offset, length);
        }

        /**
         * Read Data of a given length to a buffer from the Serial Interface Port.
         *
         * @param buffer {@code byte[]}: Specify the data buffer holding the data being read from the Serial Interface Port.
         * @param length {@code int}: Specify the number of bytes to be read.
         * @return  {@code int}: Returns the number of read bytes on success and -1 on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
         *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
         */
        public static int read(byte[] buffer, int length) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            return Implementation.read(buffer, length);
        }

        /**
         * Send a "break" command to the Serial Interface Port.
         *
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean sendBreak() throws RemoteException, UnsupportedOperationException {
            return Implementation.sendBreak();
        }

        private static final class Implementation {
            private static boolean open(int baudrate, int flags, boolean hwflow, int bitLen, int parityBit, int stopBit) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceOpen1(baudrate, flags, hwflow, bitLen, parityBit, stopBit, unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceOpen1(baudrate, flags, hwflow, bitLen, parityBit, stopBit, unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean open(int baudrate, int bitLen, int parityBit, int stopBit) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceOpen2(baudrate, bitLen, parityBit, stopBit, unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceOpen2(baudrate, bitLen, parityBit, stopBit, unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean open(int baudrate) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceOpen3(baudrate, unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceOpen3(baudrate, unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean close() throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceClose(unsupported);
                        checkMethodUnsupported("close", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceClose(unsupported);
                checkMethodUnsupported("close", unsupported);
                return retVal;
            }

            private static boolean write(byte data) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceWrite1(data, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceWrite1(data, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static boolean write(byte[] buffer) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceWrite2(buffer, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceWrite2(buffer, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static boolean write(byte[] buffer, int offset, int length) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceWrite3(buffer, offset, length, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceWrite3(buffer, offset, length, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static int read(byte[] buffer, int length) throws RemoteException, UnsupportedOperationException, IllegalStateException {
                if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
                BooleanParcelable unsupported = new BooleanParcelable();
                int retVal = getInstance().eeicLibraryService().serialDeviceRead(buffer, length, unsupported);
                checkMethodUnsupported("read", unsupported);
                return retVal;
            }

            private static boolean sendBreak() throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().serialDeviceSendBreak(unsupported);
                        checkMethodUnsupported("sendBreak", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().serialDeviceSendBreak(unsupported);
                checkMethodUnsupported("sendBreak", unsupported);
                return retVal;
            }
        }
    }

    /**
     * The I2cDevice class is used to communicate with an external expansion device connected via I&sup2;C Interface.
     *
     * @version 2.00
     * @since 2.00
     */
    public static class I2cDevice {

        /**
         * Open the I&sup2;C Interface Port
         *
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open() throws RemoteException, UnsupportedOperationException {
            return Implementation.open();
        }

        /**
         * Open the I&sup2;C Interface Port
         *
         * @param flags {@code int}: Specify additional flags for I&sup2;C Port Operation.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open(int flags) throws RemoteException, UnsupportedOperationException {
            return Implementation.open(flags);
        }

        /**
         * Close the I&sup2;C Interface Port
         *
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean close() throws RemoteException, UnsupportedOperationException {
            return Implementation.close();
        }

        /**
         * Write one single Byte of Data to the I&sup2;C Interface Port
         *
         * @param data {@code byte}: Specify the data Byte to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte data) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(data);
        }

        /**
         * Write Data from a buffer to the I&sup2;C Interface Port
         *
         * @param buffer {@code byte[]}: Specify the data buffer to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte[] buffer) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(buffer);
        }

        /**
         * Write Data of a given length from a buffer to the I&sup2;C Interface Port, starting at the given offset.
         *
         * @param buffer {@code byte[]}: Specify the data buffer to be written.
         * @param offset {@code int}: Specify the offset of the buffer at which the write operation will start.
         * @param length {@code int}: Specify the number of bytes to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte[] buffer, int offset, int length) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(buffer, offset, length);
        }

        /**
         * Read Data of a given length to a buffer from the I&sup2;C Interface Port.
         *
         * @param buffer {@code byte[]}: Specify the data buffer holding the data being read from the I&sup2;C Interface Port.
         * @param length {@code int}: Specify the number of bytes to be read.
         * @return  {@code int}: Returns the number of read bytes on success and -1 on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
         *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
         */
        public static int read(byte[] buffer, int length) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            return Implementation.read(buffer, length);
        }

        /**
         * Set the Slave Address to be used for Communication on the I&sup2;C Interface Port.
         *
         * @param address {@code int}: Specify the I&sup2;C Interface Port Slave Address to be used.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean setSlaveAddress(int address) throws RemoteException, UnsupportedOperationException {
            return Implementation.setSlaveAddress(address);
        }

        private static final class Implementation {
            private static boolean open() throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceOpen1(unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceOpen1(unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean open(int flags) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceOpen2(flags, unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceOpen2(flags, unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean close() throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceClose(unsupported);
                        checkMethodUnsupported("close", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceClose(unsupported);
                checkMethodUnsupported("close", unsupported);
                return retVal;
            }

            private static boolean write(byte data) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceWrite1(data, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceWrite1(data, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static boolean write(byte[] buffer) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceWrite2(buffer, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceWrite2(buffer, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static boolean write(byte[] buffer, int offset, int length) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceWrite3(buffer, offset, length, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceWrite3(buffer, offset, length, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static int read(byte[] buffer, int length) throws RemoteException, UnsupportedOperationException, IllegalStateException {
                if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
                BooleanParcelable unsupported = new BooleanParcelable();
                int retVal = getInstance().eeicLibraryService().i2cDeviceRead(buffer, length, unsupported);
                checkMethodUnsupported("read", unsupported);
                return retVal;
            }

            private static boolean setSlaveAddress(int address) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().i2cDeviceSetSlaveAddress(address, unsupported);
                        checkMethodUnsupported("setSlaveAddress", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().i2cDeviceSetSlaveAddress(address, unsupported);
                checkMethodUnsupported("setSlaveAddress", unsupported);
                return retVal;
            }
        }
    }

    /**
     * The SpiDevice class is used to communicate with an external expansion device connected via the Serial Peripheral Interface (SPI).
     *
     * @version 2.00
     * @since 2.00
     */
    public static class SpiDevice {

        /**
         * Open the SPI Port
         *
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open() throws RemoteException, UnsupportedOperationException {
            return Implementation.open();
        }

        /**
         * Open the SPI Port
         *
         * @param flags {@code int}: Specify additional flags for SPI Port Operation.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean open(int flags) throws RemoteException, UnsupportedOperationException {
            return Implementation.open(flags);
        }

        /**
         * Close the SPI Port
         *
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean close() throws RemoteException, UnsupportedOperationException {
            return Implementation.close();
        }

        /**
         * Write one single Byte of Data to the SPI Port
         *
         * @param data {@code byte}: Specify the data Byte to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte data) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(data);
        }

        /**
         * Write Data from a buffer to the SPI Port
         *
         * @param buffer {@code byte[]}: Specify the data buffer to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte[] buffer) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(buffer);
        }

        /**
         * Write Data of a given length from a buffer to the SPI Port, starting at the given offset.
         *
         * @param buffer {@code byte[]}: Specify the data buffer to be written.
         * @param offset {@code int}: Specify the offset of the buffer at which the write operation will start.
         * @param length {@code int}: Specify the number of bytes to be written.
         * @return  {@code boolean}: Returns true on success and false on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         */
        public static boolean write(byte[] buffer, int offset, int length) throws RemoteException, UnsupportedOperationException {
            return Implementation.write(buffer, offset, length);
        }

        /**
         * Read Data of a given length to a buffer from the SPI Port.
         *
         * @param buffer {@code byte[]}: Specify the data buffer holding the data being read from the SPI Port.
         * @param length {@code int}: Specify the number of bytes to be read.
         * @return  {@code int}: Returns the number of read bytes on success and -1 on failure.
         * @throws RemoteException Gets thrown when access to the system service fails.
         * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
         * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
         *                      In such case, please use {@link EeicLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link EeicLibrary this class} for further details.
         */
        public static int read(byte[] buffer, int length) throws RemoteException, UnsupportedOperationException, IllegalStateException {
            return Implementation.read(buffer, length);
        }

        private static final class Implementation {
            private static boolean open() throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().spiDeviceOpen1(unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().spiDeviceOpen1(unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean open(int flags) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().spiDeviceOpen2(flags, unsupported);
                        checkMethodUnsupported("open", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().spiDeviceOpen2(flags, unsupported);
                checkMethodUnsupported("open", unsupported);
                return retVal;
            }

            private static boolean close() throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().spiDeviceClose(unsupported);
                        checkMethodUnsupported("close", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().spiDeviceClose(unsupported);
                checkMethodUnsupported("close", unsupported);
                return retVal;
            }

            private static boolean write(byte data) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().spiDeviceWrite1(data, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().spiDeviceWrite1(data, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static boolean write(byte[] buffer) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().spiDeviceWrite2(buffer, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().spiDeviceWrite2(buffer, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static boolean write(byte[] buffer, int offset, int length) throws RemoteException, UnsupportedOperationException {
                BooleanParcelable unsupported = new BooleanParcelable();
                if (getInstance().eeicLibraryService() == null) {
                    onLibraryReady(() -> {
                        getInstance().eeicLibraryService().spiDeviceWrite3(buffer, offset, length, unsupported);
                        checkMethodUnsupported("write", unsupported);
                    });
                    return true;
                }
                boolean retVal = getInstance().eeicLibraryService().spiDeviceWrite3(buffer, offset, length, unsupported);
                checkMethodUnsupported("write", unsupported);
                return retVal;
            }

            private static int read(byte[] buffer, int length) throws RemoteException, UnsupportedOperationException {
                if (getInstance().eeicLibraryService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
                BooleanParcelable unsupported = new BooleanParcelable();
                int retVal = getInstance().eeicLibraryService().spiDeviceRead(buffer, length, unsupported);
                checkMethodUnsupported("read", unsupported);
                return retVal;
            }
        }
    }
}
