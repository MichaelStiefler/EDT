package com.casioeurope.mis.edt;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Objects;

public class EDTInitProvider extends ContentProvider {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (InitProvider)";

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

    private void doOnCreate() {
        logMethodEntranceExit(true);
        EDTServiceConnection.getInstance().bind(Objects.requireNonNull(getContext()));
        logMethodEntranceExit(false);
    }

    @Override
    public boolean onCreate() {
        logMethodEntranceExit(true);
        this.doOnCreate();
        logMethodEntranceExit(false);
        return true;
    }

    private void doAttachInfo(ProviderInfo providerInfo) {
        logMethodEntranceExit(true);
        if (providerInfo == null) {
            throw new NullPointerException("EDT InitProvider ProviderInfo cannot be null.");
        }
        // So if the authorities equal the library internal ones, the developer forgot to set his applicationId
        if ("com.casioeurope.mis.edt.service.initProvider".equals(providerInfo.authority)) {
            throw new IllegalStateException("Incorrect provider authority in manifest. Most likely due to a "
                    + "missing applicationId variable in application's build.gradle.");
        }
        logMethodEntranceExit(false);
    }

    @Override
    public void attachInfo(Context context, ProviderInfo providerInfo) {
        logMethodEntranceExit(true);
        this.doAttachInfo(providerInfo);
        super.attachInfo(context, providerInfo);
        logMethodEntranceExit(false);
    }

    @SuppressWarnings("NullableProblems")
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @SuppressWarnings("NullableProblems")
    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @SuppressWarnings("NullableProblems")
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
