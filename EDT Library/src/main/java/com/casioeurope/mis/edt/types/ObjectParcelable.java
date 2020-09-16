package com.casioeurope.mis.edt.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.casioeurope.mis.edt.BuildConfig;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ObjectParcelable implements Parcelable {

    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (ObjectParcelable)";
    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

    private String objectClassName;
    private Object obj;

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrentMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrentMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrentMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrentMethod + " " + sb.toString() + (entrance ? " +" : " -"));
    }

    public static final Creator<ObjectParcelable> CREATOR = new Creator<ObjectParcelable>() {
        public ObjectParcelable createFromParcel(Parcel in) {
            logMethodEntranceExit(true);
            String objectClassName = in.readString();
            if (objectClassName == null) return null;
            try {
                Class<?> objectClass = Class.forName(objectClassName);
                if (Parcelable.class.isAssignableFrom(objectClass)) {
                    Class<?> objectCreatorClass = Class.forName(objectClassName + "$CREATOR");
                    Method objectCreateFromParcelMethod = objectCreatorClass.getMethod("createFromParcel", Parcel.class);
                    ObjectParcelable objectParcelable = (ObjectParcelable) objectCreateFromParcelMethod.invoke(objectCreatorClass, in);
                    if (objectParcelable != null) {
                        objectParcelable.objectClassName = objectClassName;
                    }
                    logMethodEntranceExit(false);
                    return objectParcelable;
                } else {
                    if (!Serializable.class.isAssignableFrom(objectClass)) {
                        Log.w(TAG, String.format("Trying to read object of type %s to Parcel, but Object is neither declared as Parcelable nor Serializable!", objectClassName));
                    }
                    byte[] objectBuffer = new byte[in.dataAvail()];
                    try (
                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBuffer);
                            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {
                        ObjectParcelable objectParcelable = new ObjectParcelable();
                        objectParcelable.objectClassName = objectClassName;
                        objectParcelable.obj = objectInputStream.readObject();
                        return objectParcelable;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            logMethodEntranceExit(false);
            return null;
        }

        public ObjectParcelable[] newArray(int size) {
            return new ObjectParcelable[size];
        }
    };

    public ObjectParcelable() {
    }

    public ObjectParcelable(Object obj) {
        super();
        logMethodEntranceExit(true);
        if (obj != null) {
            this.objectClassName = obj.getClass().getName();
            this.obj = obj;
        }
        logMethodEntranceExit(false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        logMethodEntranceExit(true);
        dest.writeString(this.objectClassName);

        if (this.obj instanceof Parcelable) {
            ((Parcelable) this.obj).writeToParcel(dest, flags);
        } else {
            if (!(this.obj instanceof Serializable)) {
                Log.w(TAG, String.format("Trying to write object of type %s to Parcel, but Object is neither declared as Parcelable nor Serializable!", this.objectClassName));
            }
            try (
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(this.obj);
                dest.writeByteArray(byteArrayOutputStream.toByteArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logMethodEntranceExit(false);
    }

    public Object getObject() {
        try {
            Class<?> objectClass = Class.forName(this.objectClassName);
            return objectClass.cast(this.obj);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
