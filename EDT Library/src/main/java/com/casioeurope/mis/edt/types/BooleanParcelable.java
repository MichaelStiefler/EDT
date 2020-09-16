package com.casioeurope.mis.edt.types;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.casioeurope.mis.edt.BuildConfig;

import java.util.Arrays;

@SuppressWarnings("unused")
public class BooleanParcelable implements Parcelable {
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (BooleanParcelable)";
    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

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

    private boolean value;

    public static final Creator<BooleanParcelable> CREATOR = new Creator<BooleanParcelable>() {
        public BooleanParcelable createFromParcel(Parcel in) {
            logMethodEntranceExit(true);
            BooleanParcelable booleanParcelable = new BooleanParcelable(in.readByte() != 0);
            logMethodEntranceExit(false);
            return booleanParcelable;
        }

        public BooleanParcelable[] newArray(int size) {
            return new BooleanParcelable[size];
        }
    };

    public BooleanParcelable() {
        logMethodEntranceExit(true);
        this.value = false;
        logMethodEntranceExit(false);
    }

    public BooleanParcelable(boolean value) {
        logMethodEntranceExit(true);
        this.value = value;
        logMethodEntranceExit(false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        logMethodEntranceExit(true);
        dest.writeByte(this.value?(byte)1:(byte)0);
        logMethodEntranceExit(false);
    }

    public void readFromParcel(Parcel in) {
        logMethodEntranceExit(true);
        this.value = in.readByte() != 0;
        logMethodEntranceExit(false);
    }

    public boolean getValue() {
        return this.value;
    }

    public boolean isUnsupported() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
