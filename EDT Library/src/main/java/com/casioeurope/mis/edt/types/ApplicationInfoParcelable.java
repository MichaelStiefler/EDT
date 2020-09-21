package com.casioeurope.mis.edt.types;

import android.os.Parcel;
import android.os.Parcelable;

public class ApplicationInfoParcelable extends ApplicationInfo implements Parcelable {

    public ApplicationInfoParcelable(String packageName, String activityName) {
        super(packageName, activityName);
    }

    public ApplicationInfoParcelable(ApplicationInfo appInfo) {
        this.packageName = appInfo.packageName;
        this.activityName = appInfo.activityName;
    }

    public static final Creator<ApplicationInfoParcelable> CREATOR = new Creator<ApplicationInfoParcelable>() {
        public ApplicationInfoParcelable createFromParcel(Parcel in) {
            return new ApplicationInfoParcelable(in.readString(), in.readString());
        }

        public ApplicationInfoParcelable[] newArray(int size) {
            return new ApplicationInfoParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.packageName);
        parcel.writeString(this.activityName);
    }

    public void copyTo(ApplicationInfo appInfo) {
        appInfo.packageName = this.packageName;
        appInfo.activityName = this.activityName;
    }
}
