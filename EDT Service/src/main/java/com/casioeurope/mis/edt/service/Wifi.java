package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.casioeurope.mis.edt.WifiConfigurationParcelable;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class Wifi {
    private static String TAG = "EDT (Wifi)";
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

    public static boolean enableWifi(boolean enable, Context context) {
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "enableWifi(%s, %s)", enable, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            retVal = wManager.setWifiEnabled(enable);
        } catch (Exception e) {
            Log.d(TAG, "Error in enableWifi():");
            e.printStackTrace();
            retVal = false;
        }
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "enableWifi(%s, %s) = %b", enable, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

    public static boolean addNetwork(WifiConfigurationParcelable wifiConfigurationParcelable, Context context) {
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "addNetwork(%s, %s)", wifiConfigurationParcelable == null ? "null" : wifiConfigurationParcelable.SSID, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            android.net.wifi.WifiConfiguration wifiConfiguration = Objects.requireNonNull(wifiConfigurationParcelable).getWifiConfiguration();
            retVal = (wifiManager.addNetwork(wifiConfiguration) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in addNetwork():");
            e.printStackTrace();
            retVal = false;
        }
        logMethodEntranceExit(false, String.format(Locale.getDefault(), "addNetwork(%s, %s) = %b", wifiConfigurationParcelable == null ? "null" : wifiConfigurationParcelable.SSID, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

    public static boolean updateNetwork(WifiConfigurationParcelable wifiConfigurationParcelable, Context context) {
        if (wifiConfigurationParcelable == null)
            logMethodEntranceExit(true, String.format(Locale.getDefault(), "updateNetwork(null, %s)", context == null ? "null" : context.getPackageName()));
        else if (wifiConfigurationParcelable.SSID == null || wifiConfigurationParcelable.SSID.isEmpty())
            logMethodEntranceExit(true, String.format(Locale.getDefault(), "updateNetwork(%d, %s)", wifiConfigurationParcelable.networkId, context == null ? "null" : context.getPackageName()));
        else
            logMethodEntranceExit(true, String.format(Locale.getDefault(), "updateNetwork(%s, %s)", wifiConfigurationParcelable.SSID, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            android.net.wifi.WifiConfiguration wifiConfiguration = Objects.requireNonNull(wifiConfigurationParcelable).getWifiConfiguration();
            retVal = (wifiManager.updateNetwork(wifiConfiguration) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in updateNetwork():");
            e.printStackTrace();
            retVal = false;
        }
        if (wifiConfigurationParcelable == null)
            //noinspection ConstantConditions
            logMethodEntranceExit(true, String.format("updateNetwork(null, %s) = %b", context == null ? "null" : context.getPackageName(), retVal));
        else if (wifiConfigurationParcelable.SSID == null || wifiConfigurationParcelable.SSID.isEmpty())
            logMethodEntranceExit(true, String.format(Locale.getDefault(), "updateNetwork(%d, %s) = %b", wifiConfigurationParcelable.networkId, context == null ? "null" : context.getPackageName(), retVal));
        else
            logMethodEntranceExit(true, String.format(Locale.getDefault(), "updateNetwork(%s, %s) = %b", wifiConfigurationParcelable.SSID, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

    @SuppressLint("MissingPermission")
    public static boolean removeNetwork(String ssid, Context context) {
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "removeNetwork(%s, %s)", ssid, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            outerloop:
            //noinspection ConstantConditions
            do {
                for (android.net.wifi.WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                    if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                        Log.i(TAG, "removeNetwork: will remove " + wifiConfiguration.SSID);
                        retVal = wifiManager.removeNetwork(wifiConfiguration.networkId);
                        break outerloop;
                    }
                }
                retVal = false;
            } while (false);
        } catch (Exception e) {
            Log.d(TAG, "Error in removeNetwork():");
            e.printStackTrace();
            retVal = false;
        }
        logMethodEntranceExit(false, String.format(Locale.getDefault(), "removeNetwork(%s, %s) = %b", ssid, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

    public static boolean removeNetwork(int networkId, Context context) {
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "removeNetwork(%d, %s)", networkId, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            retVal = wifiManager.removeNetwork(networkId);
        } catch (Exception e) {
            Log.d(TAG, "Error in removeNetwork():");
            e.printStackTrace();
            retVal = false;
        }
        logMethodEntranceExit(false, String.format(Locale.getDefault(), "removeNetwork(%d, %s) = %b", networkId, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

    @SuppressLint("MissingPermission")
    public static boolean connectNetwork(String ssid, Context context) {
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "connectNetwork(%s, %s)", ssid, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            outerloop:
            //noinspection ConstantConditions
            do {
                for (android.net.wifi.WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                    if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                        wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                        Log.i(TAG, "connectNetwork: will enable " + wifiConfiguration.SSID);
                        wifiManager.reconnect();
                        retVal = true;
                        break outerloop;
                    }
                }
                retVal = false;
            } while (false);
        } catch (Exception e) {
            Log.d(TAG, "Error in connectNetwork():");
            e.printStackTrace();
            retVal = false;
        }
        logMethodEntranceExit(false, String.format(Locale.getDefault(), "connectNetwork(%s, %s) = %b", ssid, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

    public static boolean connectNetwork(int networkId, Context context) {
        logMethodEntranceExit(true, String.format(Locale.getDefault(), "connectNetwork(%d, %s)", networkId, context == null ? "null" : context.getPackageName()));
        boolean retVal;
        try {
            @SuppressLint("WifiManagerLeak") WifiManager wifiManager = (WifiManager) Objects.requireNonNull(context).getSystemService(Context.WIFI_SERVICE);
            retVal = wifiManager.enableNetwork(networkId, true);
            retVal &= wifiManager.reconnect();
        } catch (Exception e) {
            Log.d(TAG, "Error in connectNetwork():");
            e.printStackTrace();
            retVal = false;
        }
        logMethodEntranceExit(false, String.format(Locale.getDefault(), "connectNetwork(%d, %s) = %b", networkId, context == null ? "null" : context.getPackageName(), retVal));
        return retVal;
    }

}
