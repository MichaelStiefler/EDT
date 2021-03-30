package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.type.BooleanParcelable;
import com.casioeurope.mis.edt.type.LibraryCallback;
import com.casioeurope.mis.edt.type.ObjectParcelable;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * The <b>CASIO Enterprise Developer Tools</b> Reflection Library<br/><br/>
 *
 * <p>
 * This Class is used to gain access to fields and methods of other classes where the access modifier (private/protected/(none)/public) prohibits access to that field/method.<BR/>
 * Furthermore, using this class methods can be called using the shared "system" user access rights provided by EDT.
 * <p>
 * <b>NOTE:</b> Use of this API is at your own discretion and risk. You will be fully responsible for the development, functioning, maintenance and service of your Application.<br/>
 * In no event will CASIO or CASIO Europe GmbH be liable for any special damages, including but not limited to costs of procurement of substitute goods or services or any special, indirect, incidental, exemplary, or consequential damages, including but not limited to lost profits, loss of goodwill, business interruption, or loss of information, of any party, including third parties, regardless of whether such party was advised of the possibility of the foregoing.
 *
 * @apiNote The Reflection Library is bound to the calling application on application startup time automatically.<br/>
 *          The Library's lifecycle therefore depends on the application lifecycle.<br/>
 *          Due to the <a href="https://developer.android.com/guide/components/activities/activity-lifecycle">Lifecycle of Android Applications</a> and the underlying timing, <b><i>it is strongly advised not to call any Library Methods inside the {@link android.app.Activity#onCreate(Bundle) onCreate} method</i></b>.<br/>
 *          When the activity is being launched (and hence the process gets created), <i>the same applies to the {@link android.app.Activity#onStart() onStart} and {@link android.app.Activity#onResume() onResume} methods</i>.<br/>
 *          If you need to call any Library methods at application start in one of the above mentioned methods, you should use the {@link LibraryCallback Callback} Mechanism offered by the {@link ReflectionLibrary#onLibraryReady onLibraryReady} method instead.<br/>
 *          For instance, instead of calling {@link ReflectionLibrary#getBoolean(Object obj, String fieldName) ReflectionLibrary.getBoolean(Object obj, String fieldName)} directly in {@link android.app.Activity#onCreate(Bundle) onCreate}, use this code to postpone it to a {@link LibraryCallback Callback} appropriately:<br/>
 * <pre>ReflectionLibrary.onLibraryReady(new LibraryCallback() {
 *     public void onLibraryReady() {
 *         ReflectionLibrary.getBoolean(this, "booleanFieldName");
 *     }
 * });</pre>
 *          <br/>Which can be simplified to:<br/>
 * <pre>ReflectionLibrary.onLibraryReady(() -&gt; { ReflectionLibrary.getBoolean(this, "booleanFieldName"); });</pre>
 *
 * @version 2.00
 * @since 1.00
 */
@SuppressWarnings({"unused", "RedundantSuppression", "JavadocReference", "SpellCheckingInspection"})
public class ReflectionLibrary {

    private static ReflectionLibrary instance;

    private ReflectionLibrary() {
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

    /**
     * Check whether the {@link java.lang.reflect.Method Method} indicated by the {@link BigInteger BigInteger} method parameter is supported on the currently active device
     *
     * @param method {@link BigInteger BigInteger}: Constant referencing the method to be checked
     * @return {@code boolean}: {@code true} if the method is supported on the currently active device, otherwise {@code false}
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    public static boolean isMethodSupported(BigInteger method) throws IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        try {
            return getInstance().edtService().isMethodSupportedReflection(method.toString());
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
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    public static boolean isMethodSupported(String methodName) throws IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        try {
            return getInstance().edtService().isMethodNameSupportedReflection(methodName);
        } catch (Exception e) {
            return false;
        }
    }

    // *****************************************************************************************************************************************************************************************************
    // Public interface section.
    // Methods and Arguments are supposed to be final here.
    // Take care of encapsulation and don't modify methods or arguments declared on this interface
    // to ensure future backward compatibility
    // If you need a new method with the same name but different parameters or return types,
    // simply overload the given methods in this interface.
    // *****************************************************************************************************************************************************************************************************

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getBoolean(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        boolean retVal = getInstance().edtService().getBoolean(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getBoolean(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        boolean retVal = getInstance().edtService().getBooleanStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static byte getByte(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        byte retVal = getInstance().edtService().getByte(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static byte getByte(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        byte retVal = getInstance().edtService().getByteStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static char getChar(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        char retVal = getInstance().edtService().getChar(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static char getChar(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        char retVal = getInstance().edtService().getCharStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static double getDouble(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        double retVal = getInstance().edtService().getDouble(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static double getDouble(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        double retVal = getInstance().edtService().getDoubleStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static float getFloat(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        float retVal = getInstance().edtService().getFloat(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static float getFloat(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        float retVal = getInstance().edtService().getFloatStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    private static ReflectionLibrary getInstance() {
        if (ReflectionLibrary.instance == null) {
            ReflectionLibrary.instance = new ReflectionLibrary();
        }
        return ReflectionLibrary.instance;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static int getInt(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtService().getInt(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static int getInt(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        int retVal = getInstance().edtService().getIntStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static long getLong(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        long retVal = getInstance().edtService().getLong(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static long getLong(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        long retVal = getInstance().edtService().getLongStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static short getShort(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        short retVal = (short) getInstance().edtService().getShort(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static short getShort(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        short retVal = (short) getInstance().edtService().getShortStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static String getString(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        String retVal = getInstance().edtService().getString(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static String getString(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        String retVal = getInstance().edtService().getStringStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field type will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field type
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @throws ClassNotFoundException Gets thrown when the given Class does not exist.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Class<?> getType(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException, ClassNotFoundException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        Class<?> retVal = Class.forName(getInstance().edtService().getType(unsupported, new ObjectParcelable(obj), fieldName));
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field type will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field type
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @throws ClassNotFoundException Gets thrown when the given Class does not exist.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Class<?> getType(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException, ClassNotFoundException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        Class<?> retVal = Class.forName(getInstance().edtService().getTypeStatic(unsupported, declaringClass.getName(), fieldName));
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object getValue(Object obj, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        Object retVal = getInstance().edtService().getValue(unsupported, new ObjectParcelable(obj), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object getValue(Class<?> declaringClass, String fieldName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        Object retVal = getInstance().edtService().getValueStatic(unsupported, declaringClass.getName(), fieldName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for the <b><i>Instance Method</i></b> specified by "methodName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the method will be called (with no arguments) and the return value will be returned to the caller.
     *
     * @param obj        The instance Object where (or in which's inherited objects) the regarding Method resides in
     * @param methodName The name of the Method
     * @return The return value of the Method
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Object obj, String methodName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        Object retVal = getInstance().edtService().invokeMethod(unsupported, new ObjectParcelable(obj), methodName).getObject();
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for the <b><i>Instance Method</i></b> specified by "methodName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the method will be called (with given arguments from "params") and the return value will be returned to the caller.
     *
     * @param obj         The instance Object where (or in which's inherited objects) the regarding Method resides in
     * @param methodName  The name of the Method
     * @param classParams Class Types of the arguments for calling the Method, e.g. Actor.class, float.class, int.class
     * @param params      The arguments for calling the Method
     * @return The return value of the Method
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code obj} and {@code params} parameters are supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} and {@code params} parameters at least implement the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} and {@code params} parameters as if they would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] classParams, Object[] params) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        String[] classNames = Arrays.stream(classParams).map(Class::getName).toArray(String[]::new);
        ObjectParcelable[] parcelParams = Arrays.stream(params).map(ObjectParcelable::new).toArray(ObjectParcelable[]::new);
        Object retVal = getInstance().edtService().invokeMethodWithParams(unsupported, new ObjectParcelable(obj), methodName, classNames, parcelParams);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for the <b><i>Static Method</i></b> specified by "methodName" within the given Class "declaringClass".<br>
     * Once found, the method will be called (with no arguments) and the return value will be returned to the caller.
     *
     * @param declaringClass The class where the Method resides in
     * @param methodName     The name of the Method
     * @return The return value of the Method
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Class<?> declaringClass, String methodName) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        BooleanParcelable unsupported = new BooleanParcelable();
        Object retVal = getInstance().edtService().invokeMethodStatic(unsupported, declaringClass.getName(), methodName);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for the <b><i>Static Method</i></b> specified by "methodName" within the given Class "declaringClass".<br>
     * Once found, the method will be called (with given arguments from "params") and the return value will be returned to the caller.
     *
     * @param declaringClass The class where the Method resides in
     * @param methodName     The name of the Method
     * @param classParams    Class Types of the arguments for calling the Method, e.g. Actor.class, float.class, int.class
     * @param params         The arguments for calling the Method
     * @return The return value of the Method
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @throws IllegalStateException Gets thrown when the Library is not ready yet to accept method calls.<br/>
     *                      In such case, please use {@link ReflectionLibrary#onLibraryReady onLibraryReady} Method to add a {@link LibraryCallback callback} which then processes this method. See API Notes of {@link ReflectionLibrary this class} for further details.
     * @apiNote The {@code params} parameters are supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code params} parameters at least implement the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code params} parameters as if they would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Class<?> declaringClass, String methodName, Class<?>[] classParams, Object[] params) throws RemoteException, UnsupportedOperationException, IllegalStateException {
        if (getInstance().edtService() == null) throw new IllegalStateException("Library not ready yet, please use LibraryCallback Interface!");
        String[] classNames = Arrays.stream(classParams).map(Class::getName).toArray(String[]::new);
        ObjectParcelable[] parcelParams = Arrays.stream(params).map(ObjectParcelable::new).toArray(ObjectParcelable[]::new);
        BooleanParcelable unsupported = new BooleanParcelable();
        Object retVal = getInstance().edtService().invokeMethodStaticWithParams(unsupported, declaringClass.getName(), methodName, classNames, parcelParams);
        checkMethodUnsupported("", unsupported);
        return retVal;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setBoolean(Object obj, String fieldName, boolean value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setBoolean(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setBoolean", unsupported);
            });
            return;
        }
        getInstance().edtService().setBoolean(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setBoolean", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setBoolean(Class<?> declaringClass, String fieldName, boolean value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setBooleanStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setBoolean", unsupported);
            });
            return;
        }
        getInstance().edtService().setBooleanStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setBoolean", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setByte(Object obj, String fieldName, byte value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setByte(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setByte", unsupported);
            });
            return;
        }
        getInstance().edtService().setByte(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setByte", unsupported);
   }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setByte(Class<?> declaringClass, String fieldName, byte value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setByteStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setByte", unsupported);
            });
            return;
        }
        getInstance().edtService().setByteStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setByte", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setChar(Object obj, String fieldName, char value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setChar(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setChar", unsupported);
            });
            return;
        }
        getInstance().edtService().setChar(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setChar", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setChar(Class<?> declaringClass, String fieldName, char value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setCharStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setChar", unsupported);
            });
            return;
        }
        getInstance().edtService().setCharStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setChar", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setDouble(Object obj, String fieldName, double value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setDouble(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setDouble", unsupported);
            });
            return;
        }
        getInstance().edtService().setDouble(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setDouble", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setDouble(Class<?> declaringClass, String fieldName, double value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setDoubleStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setDouble", unsupported);
            });
            return;
        }
        getInstance().edtService().setDoubleStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setDouble", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setFloat(Object obj, String fieldName, float value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setFloat(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setFloat", unsupported);
            });
            return;
        }
        getInstance().edtService().setFloat(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setFloat", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setFloat(Class<?> declaringClass, String fieldName, float value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setFloatStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setFloat", unsupported);
            });
            return;
        }
        getInstance().edtService().setFloatStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setFloat", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setInt(Object obj, String fieldName, int value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setInt(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setInt", unsupported);
            });
            return;
        }
        getInstance().edtService().setInt(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setInt", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setInt(Class<?> declaringClass, String fieldName, int value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setIntStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setInt", unsupported);
            });
            return;
        }
        getInstance().edtService().setIntStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setInt", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setLong(Object obj, String fieldName, long value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setLong(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setLong", unsupported);
            });
            return;
        }
        getInstance().edtService().setLong(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setLong", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setLong(Class<?> declaringClass, String fieldName, long value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setLongStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setLong", unsupported);
            });
            return;
        }
        getInstance().edtService().setLongStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setLong", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setShort(Object obj, String fieldName, short value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setShort(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setShort", unsupported);
            });
            return;
        }
        getInstance().edtService().setShort(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setShort", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setShort(Class<?> declaringClass, String fieldName, short value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setShortStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setShort", unsupported);
            });
            return;
        }
        getInstance().edtService().setShortStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setShort", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setString(Object obj, String fieldName, String value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setString(unsupported, new ObjectParcelable(obj), fieldName, value);
                checkMethodUnsupported("setString", unsupported);
            });
            return;
        }
        getInstance().edtService().setString(unsupported, new ObjectParcelable(obj), fieldName, value);
        checkMethodUnsupported("setString", unsupported);
    }

    /**
     * Searches for a static Field specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setString(Class<?> declaringClass, String fieldName, String value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setStringStatic(unsupported, declaringClass.getName(), fieldName, value);
                checkMethodUnsupported("setString", unsupported);
            });
            return;
        }
        getInstance().edtService().setStringStatic(unsupported, declaringClass.getName(), fieldName, value);
        checkMethodUnsupported("setString", unsupported);
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be set to the value given by "value".
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @param value     The value to set the Field to
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setValue(Object obj, String fieldName, Object value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setValue(unsupported, new ObjectParcelable(obj), fieldName, new ObjectParcelable(value));
                checkMethodUnsupported("setValue", unsupported);
            });
            return;
        }
        getInstance().edtService().setValue(unsupported, new ObjectParcelable(obj), fieldName, new ObjectParcelable(value));
        checkMethodUnsupported("setValue", unsupported);
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be set to the value given by "value".
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @param value          The value to set the Field to
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static void setValue(Class<?> declaringClass, String fieldName, Object value) throws RemoteException, UnsupportedOperationException {
        BooleanParcelable unsupported = new BooleanParcelable();
        if (getInstance().edtService() == null) {
            onLibraryReady(() -> {
                getInstance().edtService().setValueStatic(unsupported, declaringClass.getName(), fieldName, new ObjectParcelable(value));
                checkMethodUnsupported("setValue", unsupported);
            });
            return;
        }
        getInstance().edtService().setValueStatic(unsupported, declaringClass.getName(), fieldName, new ObjectParcelable(value));
        checkMethodUnsupported("setValue", unsupported);
    }

    private IEDT edtService() {
        return EDTServiceConnection.getEDTService();
    }

    /**
     * Add a new Callback to the Queue of Callbacks to be processed once the EDT Service becomes available
     *
     * @param callback {@link LibraryCallback LibraryCallback}: Instance of the {@link LibraryCallback LibraryCallback} Interface which holds the {@link LibraryCallback#onLibraryReady() onLibraryReady()} Method which will get called once the regarding library becomes available
     * @throws RemoteException Gets thrown when access to the system service fails.
     * @throws UnsupportedOperationException Gets thrown when the current device does not support this method.
     */
    public static void onLibraryReady(LibraryCallback callback) throws RemoteException, UnsupportedOperationException {
        EDTServiceConnection.getInstance().addEdtCallback(callback);
    }


}
