package com.casioeurope.mis.edt;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;

public class Launcher {
    private static String TAG = "EDT (Launcher)";

    @SuppressWarnings("deprecation")
    public static boolean setDefaultLauncher(String packageName, Context context) {
        Log.d(TAG, "setDefaultLauncher(" + packageName + ")");
        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
            String currentHomePackage = resolveInfo.activityInfo.packageName;

            if (currentHomePackage.equals(packageName)) return true;

            Intent newLaunchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            String newClassName = newLaunchIntent.getComponent().getClassName();
            Intent oldLaunchIntent = context.getPackageManager().getLaunchIntentForPackage(currentHomePackage);
            String oldClassName = oldLaunchIntent.getComponent().getClassName();
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
            return true;
        } catch (Exception e) {
            Log.d(TAG, "Error in setDefaultLauncher():");
            e.printStackTrace();
        }
        return false;
    }
}
