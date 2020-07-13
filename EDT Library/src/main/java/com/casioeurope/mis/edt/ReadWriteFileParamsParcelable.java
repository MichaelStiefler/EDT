package com.casioeurope.mis.edt;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

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
    private static String TAG = "EDT (ReadWriteFileParamsParcelable)";
    private static final boolean LOG_METHOD_ENTRANCE_EXIT = true;

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrMethod + " " + sb.toString() + (entrance?" +":" -"));
    }

    static final Creator<ReadWriteFileParamsParcelable> CREATOR = new Creator<ReadWriteFileParamsParcelable>() {
        public ReadWriteFileParamsParcelable createFromParcel(Parcel in) {
            logMethodEntranceExit(true);
            ReadWriteFileParamsParcelable rwfpp = new ReadWriteFileParamsParcelable(new ReadWriteFileParams());
            rwfpp.path = Paths.get(Objects.requireNonNull(in.readString()));
            rwfpp.data = new byte[in.readInt()];
            in.readByteArray(rwfpp.data);
            rwfpp.fileOffset = in.readInt();
            rwfpp.dataOffset = in.readInt();
            rwfpp.length = in.readInt();
            @SuppressWarnings("unchecked")
            List<String> optionStrings = (List<String>)in.readArrayList(String.class.getClassLoader());
            if (optionStrings == null)
                rwfpp.options = null;
            else {
                rwfpp.options = new ArrayList<>(optionStrings.size());
                List<String> standardOpenOptionStrings = Arrays.stream(StandardOpenOption.values()).map(Enum::toString).collect(Collectors.toList());
                List<String> linkOptionStrings = Arrays.stream(LinkOption.values()).map(Enum::toString).collect(Collectors.toList());
                for (int i = 0; i < optionStrings.size(); i++) {
                    try {
                        if (standardOpenOptionStrings.contains(optionStrings.get(i)))
                            rwfpp.options.add(StandardOpenOption.valueOf(optionStrings.get(i)));
                        else if (linkOptionStrings.contains(optionStrings.get(i)))
                            rwfpp.options.add(LinkOption.valueOf(optionStrings.get(i)));
                        else
                            Log.d(TAG, String.format("OpenOption named \"%s\" unknown, will be ignored!", optionStrings.get(i)));
                    } catch (Exception e) {
                        Log.e(TAG, Log.getStackTraceString(e));
                    }
                }
            }
            logMethodEntranceExit(false);
            return rwfpp;
        }

        public ReadWriteFileParamsParcelable[] newArray(int size) {
            return new ReadWriteFileParamsParcelable[size];
        }
    };

    public ReadWriteFileParamsParcelable(ReadWriteFileParams rwfp) {
        super();
        logMethodEntranceExit(true);
        this.path = rwfp.path;
        this.data = rwfp.data;
        this.fileOffset = rwfp.fileOffset;
        this.dataOffset = rwfp.dataOffset;
        this.length = rwfp.length;
        this.options = rwfp.options;
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
        List<String> openOptionStrings = options == null? new ArrayList<>():options.stream().map(Object::toString).collect(Collectors.toList());
        dest.writeList(openOptionStrings);
        logMethodEntranceExit(false);
    }
}
