package com.casioeurope.mis.edt;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.location.LocationManager;
import android.nfc.NfcAdapter;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WirelessManager {

    private static String TAG = "EDT_TOOLS (WirelessManager)";

    public static boolean enableBluetooth(boolean enable) {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter.isEnabled()){
            if (enable) return true;
            return adapter.disable();
        } else {
            if (!enable) return true;
            return adapter.enable();
        }
    }

    public static boolean enableNfc(boolean enable, Context context) {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(context);
        if (nfcAdapter == null) return false;
        if (enable == nfcAdapter.isEnabled()) return true;
        Class<?> NfcManagerClass;
        Method setNfcEnabled;
        try {
            NfcManagerClass = Class.forName(nfcAdapter.getClass().getName());
            setNfcEnabled = NfcManagerClass.getDeclaredMethod(enable ? "enable" : "disable");
            setNfcEnabled.setAccessible(true);
            return (Boolean) setNfcEnabled.invoke(nfcAdapter);
        } catch (ClassNotFoundException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (NoSuchMethodException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IllegalArgumentException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (IllegalAccessException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        } catch (InvocationTargetException e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static boolean enableGps(boolean enable, Context context) {
        if (enable) {
            String provider = Settings.Secure.getString (context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (provider == null) provider = "";
            if (provider.contains(LocationManager.GPS_PROVIDER)) return true;
            provider = provider.concat(String.format (",%s", LocationManager.GPS_PROVIDER));
            try {
                Settings.Secure.putString (context.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
                        provider);
                return true;
            } catch(Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        } else {
            String provider = Settings.Secure.getString (context.getContentResolver(),
                    Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            if (provider == null || !provider.contains(LocationManager.GPS_PROVIDER)) return true;
            List<String>providers = new ArrayList<String>(Arrays.asList(provider.split(",")));
            providers.remove(LocationManager.GPS_PROVIDER);
            provider = String.join(", ", providers);
            try {
                Settings.Secure.putString (context.getContentResolver(),
                        Settings.Secure.LOCATION_PROVIDERS_ALLOWED,
                        provider);
                return true;
            } catch(Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }
        }
        return false;
    }

    public static boolean enableWwan(boolean enable, Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager == null) return false;
            if (telephonyManager.isDataEnabled() == enable) return true;
            telephonyManager.setDataEnabled(enable);
            return true;
        } catch(Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return false;
    }

}
