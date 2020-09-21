package com.casioeurope.mis.edt.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Arrays;

public class ScannerLibraryService extends Service {
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;
    private static String TAG = "EDT (ScannerLibraryService)";

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

    private IBinder doOnBind(@SuppressWarnings("unused") Intent intent) {
        logMethodEntranceExit(true);
        logMethodEntranceExit(false);
        return new ScannerLibraryImpl();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        logMethodEntranceExit(true);
        logMethodEntranceExit(false);
        return doOnBind(intent);
    }

}
