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

public class APNParcelable extends APN implements Parcelable {

    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (APNParcelable)";
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

    static final Creator<APNParcelable> CREATOR = new Creator<APNParcelable>() {
        public APNParcelable createFromParcel(Parcel in) {
            logMethodEntranceExit(true);
            APNParcelable apnp = new APNParcelable(new APN());
            apnp.id = in.readInt();
            apnp.name = in.readString();
            apnp.user = in.readString();
            apnp.password = in.readString();
            apnp.apn = in.readString();
            apnp.mcc = in.readString();
            apnp.mnc = in.readString();
            apnp.type = in.readString();
            apnp.server = in.readString();
            apnp.proxy = in.readString();
            apnp.port = in.readString();
            apnp.mmsProxy = in.readString();
            apnp.mmsPort = in.readString();
            apnp.mmsc = in.readString();
            apnp.current = in.readString();
            apnp.authType = in.readInt();
            logMethodEntranceExit(false);
            return apnp;
        }

        public APNParcelable[] newArray(int size) {
            return new APNParcelable[size];
        }
    };

    public APNParcelable(APN apn) {
        super();
        logMethodEntranceExit(true);
        this.id = apn.id;
        this.name = apn.name;
        this.user = apn.user;
        this.password = apn.password;
        this.apn = apn.apn;
        this.mcc = apn.mcc;
        this.mnc = apn.mnc;
        this.type = apn.type;
        this.server = apn.server;
        this.proxy = apn.proxy;
        this.port = apn.port;
        this.mmsProxy = apn.mmsProxy;
        this.mmsPort = apn.mmsPort;
        this.mmsc = apn.mmsc;
        this.current = apn.current;
        this.authType = apn.authType;
        logMethodEntranceExit(false);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        logMethodEntranceExit(true);
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.user);
        dest.writeString(this.password);
        dest.writeString(this.apn);
        dest.writeString(this.mcc);
        dest.writeString(this.mnc);
        dest.writeString(this.type);
        dest.writeString(this.server);
        dest.writeString(this.proxy);
        dest.writeString(this.port);
        dest.writeString(this.mmsProxy);
        dest.writeString(this.mmsPort);
        dest.writeString(this.mmsc);
        dest.writeString(this.current);
        dest.writeInt(this.authType);
        logMethodEntranceExit(false);
    }

    public APN getAPN(){
        APN apn = new APN();
        apn.id = this.id;
        apn.name = this.name;
        apn.user = this.user;
        apn.password = this.password;
        apn.apn = this.apn;
        apn.mcc = this.mcc;
        apn.mnc = this.mnc;
        apn.type = this.type;
        apn.server = this.server;
        apn.proxy = this.proxy;
        apn.port = this.port;
        apn.mmsProxy = this.mmsProxy;
        apn.mmsPort = this.mmsPort;
        apn.mmsc = this.mmsc;
        apn.current = this.current;
        apn.authType = this.authType;
        return apn;
    }

}
