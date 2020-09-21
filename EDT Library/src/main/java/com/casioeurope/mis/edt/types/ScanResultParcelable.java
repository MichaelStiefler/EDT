package com.casioeurope.mis.edt.types;

import android.os.Parcel;
import android.os.Parcelable;

public class ScanResultParcelable extends ScanResult implements Parcelable {

    public ScanResultParcelable() {
        this.clear();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public ScanResultParcelable(ScanResult scanResult) {
        this.symbologyID = scanResult.symbologyID;
        this.length = scanResult.length;
        this.time = scanResult.time;
        this.value = scanResult.value;
        this.aimID = scanResult.aimID;
        this.aimModifier = scanResult.aimModifier;
        this.symbologyName = scanResult.symbologyName;
    }

    public static final Creator<ScanResultParcelable> CREATOR = new Creator<ScanResultParcelable>() {
        public ScanResultParcelable createFromParcel(Parcel in) {
            ScanResultParcelable scanResultParcelable = new ScanResultParcelable();
            scanResultParcelable.symbologyID = in.readInt();
            scanResultParcelable.length = in.readInt();
            scanResultParcelable.time = in.readInt();
            int valueLength = in.readInt();
            if (valueLength > 0) {
                scanResultParcelable.value = new byte[valueLength];
                in.readByteArray(scanResultParcelable.value);
            } else {
                scanResultParcelable.value = null;
            }
            scanResultParcelable.aimID = in.readByte();
            scanResultParcelable.aimModifier = in.readByte();
            scanResultParcelable.symbologyName = in.readString();
            return scanResultParcelable;
        }

        public ScanResultParcelable[] newArray(int size) {
            return new ScanResultParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.symbologyID);
        parcel.writeInt(this.length);
        parcel.writeInt(this.time);
        if (this.value != null) {
            parcel.writeInt(this.value.length);
            parcel.writeByteArray(this.value);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeByte(this.aimID);
        parcel.writeByte(this.aimModifier);
        parcel.writeString(this.symbologyName);
    }

    public void readFromParcel(Parcel in) {
        this.symbologyID = in.readInt();
        this.length = in.readInt();
        this.time = in.readInt();
        int valueLength = in.readInt();
        if (valueLength > 0) {
            this.value = new byte[valueLength];
            in.readByteArray(this.value);
        } else {
            this.value = null;
        }
        this.aimID = in.readByte();
        this.aimModifier = in.readByte();
        this.symbologyName = in.readString();
    }

    @SuppressWarnings({"unused", "RedundantSuppression"})
    public void copyTo(ScanResult scanResult) {
        scanResult.symbologyID = this.symbologyID;
        scanResult.length = this.length;
        scanResult.time = this.time;
        scanResult.value = this.value;
        scanResult.aimID = this.aimID;
        scanResult.aimModifier = this.aimModifier;
        scanResult.symbologyName = this.symbologyName;
    }
}
