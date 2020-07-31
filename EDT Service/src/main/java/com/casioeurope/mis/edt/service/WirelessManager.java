package com.casioeurope.mis.edt.service;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WirelessManager {

    private static String TAG = "EDT (WirelessManager)";
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


    public static boolean enableBluetooth(boolean enable) {
        logMethodEntranceExit(true);
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        logMethodEntranceExit(false);
        if (adapter.isEnabled()) {
            if (enable) return true;
            return adapter.disable();
        } else {
            if (!enable) return true;
            return adapter.enable();
        }
    }

    public static boolean enableNfc(boolean enable, Context context) {
        logMethodEntranceExit(true);
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (nfcAdapter == null) {
            logMethodEntranceExit(false);
            return false;
        }
        if (enable == nfcAdapter.isEnabled()) {
            logMethodEntranceExit(false);
            return true;
        }
        Class<?> NfcManagerClass;
        Method setNfcEnabled;
        try {
            NfcManagerClass = Class.forName(nfcAdapter.getClass().getName());
            setNfcEnabled = NfcManagerClass.getDeclaredMethod(enable ? "enable" : "disable");
            setNfcEnabled.setAccessible(true);
            logMethodEntranceExit(false);
            //noinspection ConstantConditions
            return (Boolean) setNfcEnabled.invoke(nfcAdapter);
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }

    @SuppressWarnings("deprecation")
    public static boolean enableGps(boolean enable, Context context) {
        logMethodEntranceExit(true);
        String provider = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        if (enable) {
            if (provider == null) provider = "";
            if (provider.contains(LocationManager.GPS_PROVIDER)) return true;
            provider = provider.concat(String.format(",%s", LocationManager.GPS_PROVIDER));
            try {
                Settings.Secure.putString(context.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
                        provider);
                logMethodEntranceExit(false);
                return true;
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            if (provider == null || !provider.contains(LocationManager.GPS_PROVIDER)) return true;
            List<String> providers = new ArrayList<>(Arrays.asList(provider.split(",")));
            providers.remove(LocationManager.GPS_PROVIDER);
            provider = TextUtils.join(", ", providers);
            try {
                Settings.Secure.putString(context.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
                        provider);
                logMethodEntranceExit(false);
                return true;
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        logMethodEntranceExit(false);
        return false;
    }

    public static boolean enableWwan(boolean enable, Context context) {
        logMethodEntranceExit(true);
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.O) { // requires Android O or later
            logMethodEntranceExit(false);
            return false;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) return false;
            if (telephonyManager.isDataEnabled() == enable) return true;
            telephonyManager.setDataEnabled(enable);
            logMethodEntranceExit(false);
            return true;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        logMethodEntranceExit(false);
        return false;
    }

}
