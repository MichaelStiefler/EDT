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

    static final Creator<ReadWriteFileParamsParcelable> CREATOR = new Creator<ReadWriteFileParamsParcelable>() {
        public ReadWriteFileParamsParcelable createFromParcel(Parcel in) {
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
            return rwfpp;
        }

        public ReadWriteFileParamsParcelable[] newArray(int size) {
            return new ReadWriteFileParamsParcelable[size];
        }
    };

    public ReadWriteFileParamsParcelable(ReadWriteFileParams rwfp) {
        super();
        this.path = rwfp.path;
        this.data = rwfp.data;
        this.fileOffset = rwfp.fileOffset;
        this.dataOffset = rwfp.dataOffset;
        this.length = rwfp.length;
        this.options = rwfp.options;
    }

//    public ReadWriteFileParams getReadWriteFileParams() {
//        ReadWriteFileParams rwfp = new ReadWriteFileParams();
//        rwfp.path = this.path;
//        rwfp.data = this.data;
//        rwfp.fileOffset = this.fileOffset;
//        rwfp.dataOffset = this.dataOffset;
//        rwfp.length = this.length;
//        rwfp.options = this.options;
//        return rwfp;
//    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.path.toString());
        dest.writeInt(this.data.length);
        dest.writeByteArray(this.data);
        dest.writeInt(this.fileOffset);
        dest.writeInt(this.dataOffset);
        dest.writeInt(this.length);
        List<String> openOptionStrings = options == null? new ArrayList<>():options.stream().map(Object::toString).collect(Collectors.toList());
        dest.writeList(openOptionStrings);
    }
}
