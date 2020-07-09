package com.casioeurope.mis.edt.Wifi;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.casioeurope.mis.edt.WifiConfigurationParcelable;

@SuppressWarnings("deprecation")
public class Wifi {
    private static String TAG = "EDT_TOOLS (Wifi)";

    public static boolean enableWifi(boolean enable, Context context) {
        Log.d(TAG, "enableWifi(" + enable + ")");
        try {
            WifiManager wManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return wManager.setWifiEnabled(enable);
        } catch (Exception e) {
            Log.d(TAG, "Error in enableWifi():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addNetwork(WifiConfigurationParcelable wifiConfigurationParcelable, Context context) {
        Log.d(TAG, "addNetwork(" + wifiConfigurationParcelable.SSID + ")");
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            android.net.wifi.WifiConfiguration wifiConfiguration = wifiConfigurationParcelable.getWifiConfiguration();
            return (wifiManager.addNetwork(wifiConfiguration) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in addNetwork():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateNetwork(android.net.wifi.WifiConfiguration wifiConfiguration, Context context) {
        if (wifiConfiguration == null && (wifiConfiguration.SSID == null || wifiConfiguration.SSID.isEmpty()) && wifiConfiguration.networkId < 1) return false;
        Log.d(TAG, "updateNetwork()");
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.updateNetwork(wifiConfiguration) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in updateNetwork():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeNetwork(String ssid, Context context) {
        Log.d(TAG, "removeNetwork(" + ssid + ")");
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            for (android.net.wifi.WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                    Log.i(TAG, "removeNetwork: will remove " + wifiConfiguration.SSID);
                    return wifiManager.removeNetwork(wifiConfiguration.networkId);
                }
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "Error in removeNetwork():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeNetwork(int networkId, Context context) {
        Log.d(TAG, "removeNetwork(Id " + networkId + ")");
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return wifiManager.removeNetwork(networkId);
        } catch (Exception e) {
            Log.d(TAG, "Error in removeNetwork():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean connectNetwork(String ssid, Context context) {
        Log.d(TAG, "connectNetwork(" + ssid + ")");
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            for (android.net.wifi.WifiConfiguration wifiConfiguration : wifiManager.getConfiguredNetworks()) {
                if (wifiConfiguration.SSID.equals("\"" + ssid + "\"")) {
                    wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                    Log.i(TAG, "connectNetwork: will enable " + wifiConfiguration.SSID);
                    wifiManager.reconnect();
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            Log.d(TAG, "Error in connectNetwork():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean connectNetwork(int networkId, Context context) {
        Log.d(TAG, "connectNetwork(Id " + networkId + ")");
        try {
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            boolean retVal = wifiManager.enableNetwork(networkId, true);
            retVal &= wifiManager.reconnect();
            return retVal;
        } catch (Exception e) {
            Log.d(TAG, "Error in connectNetwork():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addNetworkOpen(String networkSSID, Context context) {
        try {
            android.net.wifi.WifiConfiguration conf = new android.net.wifi.WifiConfiguration();
            conf.SSID =  String.format("\"%s\"", networkSSID);
            conf.allowedKeyManagement.set(android.net.wifi.WifiConfiguration.KeyMgmt.NONE);
            conf.allowedAuthAlgorithms.set(android.net.wifi.WifiConfiguration.AuthAlgorithm.OPEN);
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.addNetwork(conf) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in enableWifi():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addNetworkWep(String networkSSID, String[] wepKeys, int wepTxKeyIndex, int keyLen, Context context) {
        try {
            if (wepTxKeyIndex < 0 || wepTxKeyIndex > 3) return false;
            if (wepTxKeyIndex >= wepKeys.length) return false;
            if (keyLen != 40 && keyLen != 104) return false;
            android.net.wifi.WifiConfiguration conf = new android.net.wifi.WifiConfiguration();
            conf.SSID = "\"" + networkSSID + "\"";
            for (int i=0; i<wepKeys.length; i++) conf.wepKeys[i]=wepKeys[i];
            conf.wepTxKeyIndex = wepTxKeyIndex;
            conf.allowedKeyManagement.set(android.net.wifi.WifiConfiguration.KeyMgmt.NONE);
            conf.allowedGroupCiphers.set(keyLen == 40 ? android.net.wifi.WifiConfiguration.GroupCipher.WEP40 : android.net.wifi.WifiConfiguration.GroupCipher.WEP104);
            conf.allowedAuthAlgorithms.set(android.net.wifi.WifiConfiguration.AuthAlgorithm.OPEN);
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.addNetwork(conf) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in enableWifi():");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addNetworkWpaPsk(String networkSSID, String networkPass, Context context) {
        try {
            android.net.wifi.WifiConfiguration conf = new android.net.wifi.WifiConfiguration();
            conf.SSID =  String.format("\"%s\"", networkSSID);
            conf.preSharedKey = String.format("\"%s\"", networkPass);
            conf.allowedAuthAlgorithms.set(android.net.wifi.WifiConfiguration.AuthAlgorithm.OPEN);
            WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
            return (wifiManager.addNetwork(conf) != -1);
        } catch (Exception e) {
            Log.d(TAG, "Error in enableWifi():");
            e.printStackTrace();
            return false;
        }
    }

}
