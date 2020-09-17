package com.casioeurope.mis.edt;

import android.os.RemoteException;

import com.casioeurope.mis.edt.types.ObjectParcelable;

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
 */
@SuppressWarnings({"unused", "RedundantSuppression"})
public class ReflectionLibrary {

    private static ReflectionLibrary instance;

    private ReflectionLibrary() {
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
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getBoolean(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getBoolean(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static boolean getBoolean(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getBooleanStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static byte getByte(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getByte(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static byte getByte(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getByteStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static char getChar(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getChar(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static char getChar(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getCharStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static double getDouble(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getDouble(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static double getDouble(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getDoubleStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static float getFloat(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getFloat(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static float getFloat(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getFloatStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
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
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static int getInt(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getInt(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static int getInt(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getIntStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static long getLong(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getLong(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static long getLong(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getLongStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static short getShort(Object obj, String fieldName) {
        try {
            return (short) getInstance().edtService().getShort(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static short getShort(Class<?> declaringClass, String fieldName) {
        try {
            return (short) getInstance().edtService().getShortStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static String getString(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getString(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static String getString(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getStringStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field type will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field type
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Class<?> getType(Object obj, String fieldName) {
        try {
            return Class.forName(getInstance().edtService().getType(new ObjectParcelable(obj), fieldName));
        } catch (RemoteException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field type will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field type
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Class<?> getType(Class<?> declaringClass, String fieldName) {
        try {
            return Class.forName(getInstance().edtService().getTypeStatic(declaringClass.getName(), fieldName));
        } catch (RemoteException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for an <b><i>Instance Field</i></b> specified by "fieldName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the Field value will be returned to the caller.
     *
     * @param obj       The instance Object where (or in which's inherited objects) the regarding Field resides in
     * @param fieldName The name of the Field
     * @return The Field value
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object getValue(Object obj, String fieldName) {
        try {
            return getInstance().edtService().getValue(new ObjectParcelable(obj), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for a <b><i>Static Field</i></b> specified by "fieldName" within the class "declaringClass".<br>
     * If found, the Field value will be returned to the caller.
     *
     * @param declaringClass The class where the Field resides in
     * @param fieldName      The name of the Field
     * @return The Field value
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object getValue(Class<?> declaringClass, String fieldName) {
        try {
            return getInstance().edtService().getValueStatic(declaringClass.getName(), fieldName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for the <b><i>Instance Method</i></b> specified by "methodName" within the given Object "obj" back through it's inheritance chain.<br>
     * Once found, the method will be called (with no arguments) and the return value will be returned to the caller.
     *
     * @param obj        The instance Object where (or in which's inherited objects) the regarding Method resides in
     * @param methodName The name of the Method
     * @return The return value of the Method
     * @apiNote The {@code obj} parameter is supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} parameter at least implements the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} parameter as if it would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Object obj, String methodName) {
        try {
            return getInstance().edtService().invokeMethod(new ObjectParcelable(obj), methodName).getObject();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
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
     * @apiNote The {@code obj} and {@code params} parameters are supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code obj} and {@code params} parameters at least implement the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code obj} and {@code params} parameters as if they would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] classParams, Object[] params) {
        try {
            String[] classNames = Arrays.stream(classParams).map(Class::getName).toArray(String[]::new);
            ObjectParcelable[] parcelParams = Arrays.stream(params).map(ObjectParcelable::new).toArray(ObjectParcelable[]::new);
            return getInstance().edtService().invokeMethodWithParams(new ObjectParcelable(obj), methodName, classNames, parcelParams);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Searches for the <b><i>Static Method</i></b> specified by "methodName" within the given Class "declaringClass".<br>
     * Once found, the method will be called (with no arguments) and the return value will be returned to the caller.
     *
     * @param declaringClass The class where the Method resides in
     * @param methodName     The name of the Method
     * @return The return value of the Method
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Class<?> declaringClass, String methodName) {
        try {
            return getInstance().edtService().invokeMethodStatic(declaringClass.getName(), methodName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
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
     * @apiNote The {@code params} parameters are supposed to implement the {@link android.os.Parcelable Parcelable} Interface.<br/>
     * Should this not be the case, it's recommended that the {@code params} parameters at least implement the {@link java.io.Serializable Serializable} Interface.<br/>
     * Should this not be the case either, the method will try to treat the {@code params} parameters as if they would implement the {@link java.io.Serializable Serializable} Interface.
     */
    @SuppressWarnings({"unused", "RedundantSuppression"})
    public static Object invokeMethod(Class<?> declaringClass, String methodName, Class<?>[] classParams, Object[] params) {
        try {
            String[] classNames = Arrays.stream(classParams).map(Class::getName).toArray(String[]::new);
            ObjectParcelable[] parcelParams = Arrays.stream(params).map(ObjectParcelable::new).toArray(ObjectParcelable[]::new);
            return getInstance().edtService().invokeMethodStaticWithParams(declaringClass.getName(), methodName, classNames, parcelParams);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
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
    public static void setBoolean(Object obj, String fieldName, boolean value) {
        try {
            getInstance().edtService().setBoolean(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setBoolean(Class<?> declaringClass, String fieldName, boolean value) {
        try {
            getInstance().edtService().setBooleanStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setByte(Object obj, String fieldName, byte value) {
        try {
            getInstance().edtService().setByte(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setByte(Class<?> declaringClass, String fieldName, byte value) {
        try {
            getInstance().edtService().setByteStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setChar(Object obj, String fieldName, char value) {
        try {
            getInstance().edtService().setChar(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setChar(Class<?> declaringClass, String fieldName, char value) {
        try {
            getInstance().edtService().setCharStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setDouble(Object obj, String fieldName, double value) {
        try {
            getInstance().edtService().setDouble(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setDouble(Class<?> declaringClass, String fieldName, double value) {
        try {
            getInstance().edtService().setDoubleStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setFloat(Object obj, String fieldName, float value) {
        try {
            getInstance().edtService().setFloat(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setFloat(Class<?> declaringClass, String fieldName, float value) {
        try {
            getInstance().edtService().setFloatStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setInt(Object obj, String fieldName, int value) {
        try {
            getInstance().edtService().setInt(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setInt(Class<?> declaringClass, String fieldName, int value) {
        try {
            getInstance().edtService().setIntStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setLong(Object obj, String fieldName, long value) {
        try {
            getInstance().edtService().setLong(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setLong(Class<?> declaringClass, String fieldName, long value) {
        try {
            getInstance().edtService().setLongStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setShort(Object obj, String fieldName, short value) {
        try {
            getInstance().edtService().setShort(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setShort(Class<?> declaringClass, String fieldName, short value) {
        try {
            getInstance().edtService().setShortStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setString(Object obj, String fieldName, String value) {
        try {
            getInstance().edtService().setString(new ObjectParcelable(obj), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setString(Class<?> declaringClass, String fieldName, String value) {
        try {
            getInstance().edtService().setStringStatic(declaringClass.getName(), fieldName, value);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setValue(Object obj, String fieldName, Object value) {
        try {
            getInstance().edtService().setValue(new ObjectParcelable(obj), fieldName, new ObjectParcelable(value));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
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
    public static void setValue(Class<?> declaringClass, String fieldName, Object value) {
        try {
            getInstance().edtService().setValueStatic(declaringClass.getName(), fieldName, new ObjectParcelable(value));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private IEDT edtService() {
        return EDTServiceConnection.getInstance().getEDTService();
    }

}
