package com.casioeurope.mis.cdl;

import android.os.Parcel;
import android.os.Parcelable;

public class ApplicationInfo implements Parcelable {
    public String activityName;
    public String packageName;

    public ApplicationInfo() {
        this.packageName = "";
        this.activityName = "";
    }

    static final Creator<ApplicationInfo> CREATOR = new Creator<ApplicationInfo>() {
        public ApplicationInfo createFromParcel(Parcel in) {
            ApplicationInfo applicationInfo = new ApplicationInfo();
            applicationInfo.packageName = in.readString();
            applicationInfo.activityName = in.readString();
            return applicationInfo;
        }

        public ApplicationInfo[] newArray(int size) {
            return new ApplicationInfo[size];
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
}
