package com.casioeurope.mis.edt.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class Browser {

    private static final String TAG = "EDT (Browser)";
    private static final String PREF_HOMEPAGE = "homepage";
    private static final String PREF_SAVE_FORMDATA = "save_formdata";
    private static final String PREF_REMEMBER_PASSWORDS = "remember_passwords";

    public static boolean setDefaultHomePage(String homePage, Context context) {
        try {
            Context browserContext = context.createPackageContext("com.android.browser", Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sp = browserContext.getSharedPreferences("com.android.browser_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(PREF_HOMEPAGE, homePage);
            return editor.commit();
        } catch (Exception e) {
            Log.d(TAG, "Error in setDefaultHomePage():");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean rememberPasswords(boolean enable, Context context) {
        try {
            Context browserContext = context.createPackageContext("com.android.browser", Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sp = browserContext.getSharedPreferences("com.android.browser_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(PREF_REMEMBER_PASSWORDS, enable);
            return editor.commit();
        } catch (Exception e) {
            Log.d(TAG, "Error in setDefaultHomePage():");
            e.printStackTrace();
        }
        return false;
    }

    public static boolean saveFormData(boolean enable, Context context) {
        try {
            Context browserContext = context.createPackageContext("com.android.browser", Context.CONTEXT_IGNORE_SECURITY);
            SharedPreferences sp = browserContext.getSharedPreferences("com.android.browser_preferences", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean(PREF_SAVE_FORMDATA, enable);
            return editor.commit();
        } catch (Exception e) {
            Log.d(TAG, "Error in setDefaultHomePage():");
            e.printStackTrace();
        }
        return false;
    }
}
