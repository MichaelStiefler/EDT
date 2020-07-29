package com.casioeurope.mis.edt.service;

import com.casioeurope.mis.edt.ObjectParcelable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.Arrays;

@SuppressWarnings("unused")
public class ReflectionManager {

    private ReflectionManager() throws Exception {
        throw new Exception("Class com.casioeurope.mis.edt.ReflectionImpl cannot be instantiated!");
    }

    // *****************************************************************************************************************************************************************************************************
    // Private implementation section.
    // Do whatever you like here but keep it private to this class.
    // *****************************************************************************************************************************************************************************************************

    static ObjectParcelable doGenericInvokeMethod(ObjectParcelable obj, String methodName, ObjectParcelable[] params) {
        return doGenericInvokeMethod(obj, null, methodName, params);
    }

    static ObjectParcelable doGenericInvokeMethod(ObjectParcelable obj, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        if (classParamNames.length != params.length) {
            InvalidParameterException ipe = new InvalidParameterException("Class Array length doesn't match Parameter Array length!");
            ipe.printStackTrace();
            return null;
        }
        return doGenericInvokeMethod(obj, null, methodName, classParamNames, params);
    }

    static ObjectParcelable doGenericInvokeMethod(String declaringClassName, String methodName, ObjectParcelable[] params) {
        return doGenericInvokeMethod(null, declaringClassName, methodName, params);
    }

    static ObjectParcelable doGenericInvokeMethod(String declaringClassName, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        if (classParamNames.length != params.length) {
            InvalidParameterException ipe = new InvalidParameterException("Class Array length doesn't match Parameter Array length!");
            ipe.printStackTrace();
            return null;
        }
        return doGenericInvokeMethod(null, declaringClassName, methodName, classParamNames, params);
    }

    static ObjectParcelable doGenericInvokeMethod(ObjectParcelable obj, String declaringClassName, String methodName, ObjectParcelable[] params) {
        return doGenericInvokeMethod(doGenericGetMethod(obj, declaringClassName, methodName, params), obj, params);
    }

    static ObjectParcelable doGenericInvokeMethod(ObjectParcelable obj, String declaringClassName, String methodName, String[] classParamNames, ObjectParcelable[] params) {
        if (classParamNames.length != params.length) {
            InvalidParameterException ipe = new InvalidParameterException("Class Array length doesn't match Parameter Array length!");
            ipe.printStackTrace();
            return null;
        }
        return doGenericInvokeMethod(doGenericGetMethod(obj, declaringClassName, methodName, classParamNames), obj, params);
    }

    private static ObjectParcelable doGenericInvokeMethod(Method method, ObjectParcelable obj, ObjectParcelable[] params) {
        Object requiredObj = null;
        if (method != null) {
            try {
                Object[] objectArray = Arrays.stream(params).map(ObjectParcelable::getObject).toArray(Object[]::new);
                requiredObj = method.invoke(obj.getObject(), objectArray);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return new ObjectParcelable(requiredObj);
    }

    private static Method doGenericGetMethod(ObjectParcelable obj, String declaringClassName, String methodName, ObjectParcelable[] params) {
        Method method = null;
        Class<?> declaringClass;
        try {
            declaringClass = Class.forName(declaringClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(String.format("Class %s could not be found!", declaringClassName));
        }
        Class<?>[] classArray = Arrays.stream(params).map(p -> p.getObject().getClass()).toArray(Class[]::new);
        if (obj != null) {
            Class<?> objClass = obj.getObject().getClass();
            do {
                if (objClass == null) break;
                try {
                    method = objClass.getDeclaredMethod(methodName, classArray);
                    method.setAccessible(true);
                } catch (Exception ignored) {
                }
                objClass = objClass.getSuperclass();
            } while (method == null);
        } else {
            try {
                method = declaringClass.getDeclaredMethod(methodName, classArray);
                method.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    private static Method doGenericGetMethod(ObjectParcelable obj, String declaringClassName, String methodName, String[] classParamNames) {
        Method method = null;
        Class<?> declaringClass;
        Class<?>[] classParams;
        try {
            declaringClass = Class.forName(declaringClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(String.format("Class %s could not be found!", declaringClassName));
        }
        classParams = Arrays.stream(classParamNames).map(p -> {
                try {
                    return Class.forName(p);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    throw new IllegalArgumentException(String.format("Class %s could not be found!", p));
                }
            }).toArray(Class[]::new);
        if (obj != null) {
            Class<?> objClass = obj.getClass();
            do {
                if (objClass == null) {
                    break;
                }
                try {
                    method = objClass.getDeclaredMethod(methodName, classParams);
                    method.setAccessible(true);
                } catch (Exception ignored) {
                }
                objClass = objClass.getSuperclass();
            } while (method == null);
        } else {
            try {
                method = declaringClass.getDeclaredMethod(methodName, classParams);
                method.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return method;
    }

    static Field doGenericGetField(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = null;
        if (obj != null) {
            Class<?> objClass = obj.getClass();
            do {
                if (objClass == null) {
                    break;
                }
                try {
                    field = objClass.getDeclaredField(fieldName);
                    field.setAccessible(true);
                } catch (Exception ignored) {
                }
                objClass = objClass.getSuperclass();
            } while (field == null);
        } else {
            Class<?> declaringClass;
            try {
                declaringClass = Class.forName(declaringClassName);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                throw new IllegalArgumentException(String.format("Class %s could not be found!", declaringClassName));
            }
            try {
                field = declaringClass.getDeclaredField(fieldName);
                field.setAccessible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return field;
    }

    static ObjectParcelable doGenericGetFieldValue(ObjectParcelable obj, String methodName) {
        return doGenericGetFieldValue(obj, null, methodName);
    }

    static ObjectParcelable doGenericGetFieldValue(String declaringClassName, String methodName) {
        return doGenericGetFieldValue(null, declaringClassName, methodName);
    }

    private static ObjectParcelable doGenericGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        Object requiredObj = null;
        if (field != null) {
            try {
                requiredObj = field.get(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return new ObjectParcelable(requiredObj);
    }

    static String doGetString(ObjectParcelable obj, String methodName) {
        return doGetString(obj, null, methodName);
    }

    static String doGetString(String declaringClassName, String methodName) {
        return doGetString(null, declaringClassName, methodName);
    }

    private static String doGetString(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        Object requiredObj = null;
        if (field != null) {
            try {
                requiredObj = field.get(obj.getObject());
                if (!(requiredObj instanceof String)) throw new IllegalArgumentException("Only Fields of String Type can be accessed through EDT GetString method!");
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return requiredObj==null?null:requiredObj.toString();
    }

    static boolean doBooleanGetFieldValue(ObjectParcelable obj, String methodName) {
        return doBooleanGetFieldValue(obj, null, methodName);
    }

    static boolean doBooleanGetFieldValue(String declaringClassName, String methodName) {
        return doBooleanGetFieldValue(null, declaringClassName, methodName);
    }

    static boolean doBooleanGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        boolean retVal = false;
        if (field != null) {
            try {
                retVal = field.getBoolean(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static byte doByteGetFieldValue(ObjectParcelable obj, String methodName) {
        return doByteGetFieldValue(obj, null, methodName);
    }

    static byte doByteGetFieldValue(String declaringClassName, String methodName) {
        return doByteGetFieldValue(null, declaringClassName, methodName);
    }

    static byte doByteGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        byte retVal = 0;
        if (field != null) {
            try {
                retVal = field.getByte(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static char doCharGetFieldValue(ObjectParcelable obj, String methodName) {
        return doCharGetFieldValue(obj, null, methodName);
    }

    static char doCharGetFieldValue(String declaringClassName, String methodName) {
        return doCharGetFieldValue(null, declaringClassName, methodName);
    }

    static char doCharGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        char retVal = 0;
        if (field != null) {
            try {
                retVal = field.getChar(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static double doDoubleGetFieldValue(ObjectParcelable obj, String methodName) {
        return doDoubleGetFieldValue(obj, null, methodName);
    }

    static double doDoubleGetFieldValue(String declaringClassName, String methodName) {
        return doDoubleGetFieldValue(null, declaringClassName, methodName);
    }

    static double doDoubleGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        double retVal = 0;
        if (field != null) {
            try {
                retVal = field.getDouble(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static float doFloatGetFieldValue(ObjectParcelable obj, String methodName) {
        return doFloatGetFieldValue(obj, null, methodName);
    }

    static float doFloatGetFieldValue(String declaringClassName, String methodName) {
        return doFloatGetFieldValue(null, declaringClassName, methodName);
    }

    static float doFloatGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        float retVal = 0;
        if (field != null) {
            try {
                retVal = field.getFloat(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static int doIntGetFieldValue(ObjectParcelable obj, String methodName) {
        return doIntGetFieldValue(obj, null, methodName);
    }

    static int doIntGetFieldValue(String declaringClassName, String methodName) {
        return doIntGetFieldValue(null, declaringClassName, methodName);
    }

    static int doIntGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        int retVal = 0;
        if (field != null) {
            try {
                retVal = field.getInt(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static long doLongGetFieldValue(ObjectParcelable obj, String methodName) {
        return doLongGetFieldValue(obj, null, methodName);
    }

    static long doLongGetFieldValue(String declaringClassName, String methodName) {
        return doLongGetFieldValue(null, declaringClassName, methodName);
    }

    static long doLongGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        long retVal = 0;
        if (field != null) {
            try {
                retVal = field.getLong(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static short doShortGetFieldValue(ObjectParcelable obj, String methodName) {
        return doShortGetFieldValue(obj, null, methodName);
    }

    static short doShortGetFieldValue(String declaringClassName, String methodName) {
        return doShortGetFieldValue(null, declaringClassName, methodName);
    }

    static short doShortGetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        short retVal = 0;
        if (field != null) {
            try {
                retVal = field.getShort(obj.getObject());
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return retVal;
    }

    static String doGenericGetFieldType(ObjectParcelable obj, String methodName) {
        return doGenericGetFieldType(obj, null, methodName);
    }

    static String doGenericGetFieldType(String declaringClassName, String methodName) {
        return doGenericGetFieldType(null, declaringClassName, methodName);
    }

    static String doGenericGetFieldType(ObjectParcelable obj, String declaringClassName, String fieldName) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        String retVal = null;
        if (field != null) {
            try {
                retVal = field.getType().getName();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return retVal;
    }

    static void doGenericSetFieldValue(ObjectParcelable obj, String methodName, Object value) {
        doGenericSetFieldValue(obj, null, methodName, value);
    }

    static void doGenericSetFieldValue(String declaringClassName, String methodName, Object value) {
        doGenericSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doGenericSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, Object value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.set(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    static void doBooleanSetFieldValue(ObjectParcelable obj, String methodName, boolean value) {
        doBooleanSetFieldValue(obj, null, methodName, value);
    }

    static void doBooleanSetFieldValue(String declaringClassName, String methodName, boolean value) {
        doBooleanSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doBooleanSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, boolean value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setBoolean(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doByteSetFieldValue(ObjectParcelable obj, String methodName, byte value) {
        doByteSetFieldValue(obj, null, methodName, value);
    }

    static void doByteSetFieldValue(String declaringClassName, String methodName, byte value) {
        doByteSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doByteSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, byte value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setByte(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doCharSetFieldValue(ObjectParcelable obj, String methodName, char value) {
        doCharSetFieldValue(obj, null, methodName, value);
    }

    static void doCharSetFieldValue(String declaringClassName, String methodName, char value) {
        doCharSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doCharSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, char value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setChar(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doDoubleSetFieldValue(ObjectParcelable obj, String methodName, double value) {
        doDoubleSetFieldValue(obj, null, methodName, value);
    }

    static void doDoubleSetFieldValue(String declaringClassName, String methodName, double value) {
        doDoubleSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doDoubleSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, double value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setDouble(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doFloatSetFieldValue(ObjectParcelable obj, String methodName, float value) {
        doFloatSetFieldValue(obj, null, methodName, value);
    }

    static void doFloatSetFieldValue(String declaringClassName, String methodName, float value) {
        doFloatSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doFloatSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, float value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setFloat(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doIntSetFieldValue(ObjectParcelable obj, String methodName, int value) {
        doIntSetFieldValue(obj, null, methodName, value);
    }

    static void doIntSetFieldValue(String declaringClassName, String methodName, int value) {
        doIntSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doIntSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, int value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setInt(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doLongSetFieldValue(ObjectParcelable obj, String methodName, long value) {
        doLongSetFieldValue(obj, null, methodName, value);
    }

    static void doLongSetFieldValue(String declaringClassName, String methodName, long value) {
        doLongSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doLongSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, long value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setLong(obj.getObject(), value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    static void doShortSetFieldValue(ObjectParcelable obj, String methodName, int value) {
        doShortSetFieldValue(obj, null, methodName, value);
    }

    static void doShortSetFieldValue(String declaringClassName, String methodName, int value) {
        doShortSetFieldValue(null, declaringClassName, methodName, value);
    }

    static void doShortSetFieldValue(ObjectParcelable obj, String declaringClassName, String fieldName, int value) {
        Field field = doGenericGetField(obj, declaringClassName, fieldName);
        if (field != null) {
            try {
                field.setShort(obj.getObject(), (short)value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
