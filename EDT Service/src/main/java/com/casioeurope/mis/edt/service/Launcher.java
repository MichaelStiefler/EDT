package com.casioeurope.mis.edt.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

import java.util.Arrays;
import java.util.Objects;

public class Launcher {
    private static String TAG = "EDT (Launcher)";
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = BuildConfig.DEBUG;

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

    @SuppressWarnings("deprecation")
    public static boolean setDefaultLauncher(String packageName, Context context) {
        logMethodEntranceExit(true, String.format("setDefaultLauncher(%s, %s)", packageName, context.getPackageName()));
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String currentHomePackage = Objects.requireNonNull(resolveInfo).activityInfo.packageName;

            if (currentHomePackage.equals(packageName)) return true;

            Intent newLaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            String newClassName = Objects.requireNonNull(Objects.requireNonNull(newLaunchIntent).getComponent()).getClassName();
            Intent oldLaunchIntent = context.getPackageManager().getLaunchIntentForPackage(currentHomePackage);
            String oldClassName = Objects.requireNonNull(Objects.requireNonNull(oldLaunchIntent).getComponent()).getClassName();
            ComponentName oldComponent =
                    new ComponentName(currentHomePackage, oldClassName);
            ComponentName newComponent =
                    new ComponentName(packageName, newClassName);
            ComponentName[] components = new ComponentName[]{oldComponent, newComponent};
            context.getPackageManager().clearPackagePreferredActivities(currentHomePackage);

            IntentFilter filter = new IntentFilter(Intent.ACTION_MAIN);
            filter.addCategory(Intent.CATEGORY_HOME);
            filter.addCategory(Intent.CATEGORY_DEFAULT);
            int bestMatch = IntentFilter.MATCH_CATEGORY_EMPTY;

            context.getPackageManager().addPreferredActivity(filter, bestMatch, components, newComponent);
            logMethodEntranceExit(false, String.format("setDefaultLauncher(%s, %s) = true", packageName, context.getPackageName()));
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in setDefaultLauncher():");
            e.printStackTrace();
        }
        logMethodEntranceExit(false, String.format("setDefaultLauncher(%s, %s) = false", packageName, context.getPackageName()));
        return false;
    }
}
