package com.casioeurope.mis.edt.type;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.casioeurope.mis.edt.BuildConfig;

import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReadWriteFileParamsParcelable extends ReadWriteFileParams implements Parcelable {

    @SuppressWarnings("FieldCanBeLocal")
    private static final String TAG = "EDT (ReadWriteFileParamsParcelable)";
    private static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

    @SuppressLint("LongLogTag")
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

    public static final Creator<ReadWriteFileParamsParcelable> CREATOR = new Creator<ReadWriteFileParamsParcelable>() {
        @SuppressLint("LongLogTag")
        public ReadWriteFileParamsParcelable createFromParcel(Parcel in) {
            logMethodEntranceExit(true);
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
                logMethodEntranceExit(false);
                return null;
            }
            ReadWriteFileParamsParcelable readWriteFileParamsParcelable = new ReadWriteFileParamsParcelable(new ReadWriteFileParams());
            readWriteFileParamsParcelable.path = Paths.get(Objects.requireNonNull(in.readString()));
            readWriteFileParamsParcelable.data = new byte[in.readInt()];
            in.readByteArray(readWriteFileParamsParcelable.data);
            readWriteFileParamsParcelable.fileOffset = in.readInt();
            readWriteFileParamsParcelable.dataOffset = in.readInt();
            readWriteFileParamsParcelable.length = in.readInt();
            @SuppressWarnings("unchecked")
            List<String> optionStrings = (List<String>) in.readArrayList(String.class.getClassLoader());
            if (optionStrings == null)
                readWriteFileParamsParcelable.options = null;
            else {
                readWriteFileParamsParcelable.options = new ArrayList<>(optionStrings.size());
                List<String> standardOpenOptionStrings = Arrays.stream(StandardOpenOption.values()).map(Enum::toString).collect(Collectors.toList());
                List<String> linkOptionStrings = Arrays.stream(LinkOption.values()).map(Enum::toString).collect(Collectors.toList());
                for (int i = 0; i < optionStrings.size(); i++) {
                    try {
                        if (standardOpenOptionStrings.contains(optionStrings.get(i)))
                            readWriteFileParamsParcelable.options.add(StandardOpenOption.valueOf(optionStrings.get(i)));
                        else if (linkOptionStrings.contains(optionStrings.get(i)))
                            readWriteFileParamsParcelable.options.add(LinkOption.valueOf(optionStrings.get(i)));
                        else
                            Log.d(TAG, String.format("OpenOption named \"%s\" unknown, will be ignored!", optionStrings.get(i)));
                    } catch (Exception e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }
                }
            }
            logMethodEntranceExit(false);
            return readWriteFileParamsParcelable;
        }

        public ReadWriteFileParamsParcelable[] newArray(int size) {
            return new ReadWriteFileParamsParcelable[size];
        }
    };

    public ReadWriteFileParamsParcelable(ReadWriteFileParams readWriteFileParams) {
        super();
        logMethodEntranceExit(true);
        this.path = readWriteFileParams.path;
        this.data = readWriteFileParams.data;
        this.fileOffset = readWriteFileParams.fileOffset;
        this.dataOffset = readWriteFileParams.dataOffset;
        this.length = readWriteFileParams.length;
        this.options = readWriteFileParams.options;
        logMethodEntranceExit(false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        logMethodEntranceExit(true);
        dest.writeString(this.path.toString());
        dest.writeInt(this.data.length);
        dest.writeByteArray(this.data);
        dest.writeInt(this.fileOffset);
        dest.writeInt(this.dataOffset);
        dest.writeInt(this.length);
        List<String> openOptionStrings = options == null ? new ArrayList<>() : options.stream().map(Object::toString).collect(Collectors.toList());
        dest.writeList(openOptionStrings);
        logMethodEntranceExit(false);
    }
}
