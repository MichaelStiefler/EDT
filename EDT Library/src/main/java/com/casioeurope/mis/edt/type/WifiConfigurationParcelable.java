package com.casioeurope.mis.edt.type;

import android.annotation.SuppressLint;
import android.net.LinkProperties;
import android.net.ProxyInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.BitSet;

@SuppressWarnings({"deprecation", "RedundantSuppression"})
public class WifiConfigurationParcelable extends android.net.wifi.WifiConfiguration implements Parcelable {

    private static BitSet readBitSet(Parcel src) {
        int cardinality = src.readInt();
        BitSet set = new BitSet();
        for (int i = 0; i < cardinality; i++) {
            set.set(src.readInt());
        }
        return set;
    }

    private static void writeBitSet(Parcel dest, BitSet set) {
        int nextSetBit = -1;

        dest.writeInt(set.cardinality());

        while ((nextSetBit = set.nextSetBit(nextSetBit + 1)) != -1) {
            dest.writeInt(nextSetBit);
        }
    }

    public enum IpAssignment {
        /* Use statically configured IP settings. Configuration can be accessed
         * with linkProperties */
        STATIC,
        /* Use dynamically configured IP settings */
        DHCP,
        /* no IP details are assigned, this is used to indicate
         * that any existing IP settings should be retained */
        UNASSIGNED
    }

    public IpAssignment ipAssignment;

    public enum ProxySettings {
        /* No proxy is to be used. Any existing proxy settings
         * should be cleared. */
        NONE,
        /* Use statically configured proxy. Configuration can be accessed
         * with linkProperties */
        STATIC,
        /* no proxy details are assigned, this is used to indicate
         * that any existing proxy settings should be retained */
        UNASSIGNED
    }

    public ProxySettings proxySettings;

    public LinkProperties linkProperties;
    public int disableReason;

    public WifiConfigurationParcelable(android.net.wifi.WifiConfiguration conf) {
        super();
        this.BSSID = conf.BSSID;
        this.FQDN = conf.FQDN;
        this.SSID = conf.SSID;
        this.allowedAuthAlgorithms = conf.allowedAuthAlgorithms;
        this.allowedGroupCiphers = conf.allowedGroupCiphers;
        this.allowedKeyManagement = conf.allowedKeyManagement;
        this.allowedPairwiseCiphers = conf.allowedPairwiseCiphers;
        this.allowedProtocols = conf.allowedProtocols;
        this.enterpriseConfig = conf.enterpriseConfig;
        this.hiddenSSID = conf.hiddenSSID;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // requires Android O or later
            this.isHomeProviderNetwork = conf.isHomeProviderNetwork;
        }
        this.networkId = conf.networkId;
        this.preSharedKey = conf.preSharedKey;
        this.priority = conf.priority;
        this.providerFriendlyName = conf.providerFriendlyName;
        this.roamingConsortiumIds = conf.roamingConsortiumIds;
        this.status = conf.status;
        this.wepKeys = conf.wepKeys;
        this.wepTxKeyIndex = conf.wepTxKeyIndex;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // requires Android O or later
            this.setHttpProxy(conf.getHttpProxy());
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(networkId);
        dest.writeInt(status);
        dest.writeInt(disableReason);
        dest.writeString(SSID);
        dest.writeString(BSSID);
        dest.writeString(preSharedKey);
        for (String wepKey : wepKeys) {
            dest.writeString(wepKey);
        }
        dest.writeInt(wepTxKeyIndex);
        dest.writeInt(priority);
        dest.writeInt(hiddenSSID ? 1 : 0);

        writeBitSet(dest, allowedKeyManagement);
        writeBitSet(dest, allowedProtocols);
        writeBitSet(dest, allowedAuthAlgorithms);
        writeBitSet(dest, allowedPairwiseCiphers);
        writeBitSet(dest, allowedGroupCiphers);

        dest.writeParcelable(enterpriseConfig, flags);
        dest.writeString(ipAssignment == null ? "" : ipAssignment.toString());
        dest.writeString(proxySettings == null ? "" : proxySettings.toString());
        dest.writeParcelable(linkProperties, flags);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // requires Android O or later
            dest.writeParcelable(getHttpProxy(), flags);
        } else {
            try {
                Method getHttpProxyMethod = android.net.wifi.WifiConfiguration.class.getDeclaredMethod("getHttpProxy");
                getHttpProxyMethod.setAccessible(true);
                ProxyInfo proxyInfo = (ProxyInfo)getHttpProxyMethod.invoke(this);
                dest.writeParcelable(proxyInfo, flags);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    public android.net.wifi.WifiConfiguration getWifiConfiguration() {
        android.net.wifi.WifiConfiguration conf = new android.net.wifi.WifiConfiguration();
        conf.BSSID = this.BSSID;
        conf.FQDN = this.FQDN;
        conf.SSID = this.SSID;
        conf.allowedAuthAlgorithms = this.allowedAuthAlgorithms;
        conf.allowedGroupCiphers = this.allowedGroupCiphers;
        conf.allowedKeyManagement = this.allowedKeyManagement;
        conf.allowedPairwiseCiphers = this.allowedPairwiseCiphers;
        conf.allowedProtocols = this.allowedProtocols;
        conf.enterpriseConfig = this.enterpriseConfig;
        conf.hiddenSSID = this.hiddenSSID;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // requires Android O or later
            conf.isHomeProviderNetwork = this.isHomeProviderNetwork;
        }
        conf.networkId = this.networkId;
        conf.preSharedKey = this.preSharedKey;
        conf.priority = this.priority;
        conf.providerFriendlyName = this.providerFriendlyName;
        conf.roamingConsortiumIds = this.roamingConsortiumIds;
        conf.status = this.status;
        conf.wepKeys = this.wepKeys;
        conf.wepTxKeyIndex = this.wepTxKeyIndex;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // requires Android O or later
            conf.setHttpProxy(this.getHttpProxy());
        } else {
            try {
                Method getHttpProxyMethod = android.net.wifi.WifiConfiguration.class.getDeclaredMethod("getHttpProxy");
                Method setHttpProxyMethod = android.net.wifi.WifiConfiguration.class.getDeclaredMethod("setHttpProxy", ProxyInfo.class);
                getHttpProxyMethod.setAccessible(true);
                setHttpProxyMethod.setAccessible(true);
                ProxyInfo proxyInfo = (ProxyInfo)getHttpProxyMethod.invoke(this);
                setHttpProxyMethod.invoke(conf, proxyInfo);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return conf;
    }

    public static final Creator<WifiConfigurationParcelable> CREATOR = new Creator<WifiConfigurationParcelable>() {
        @SuppressLint("ParcelClassLoader")
        @Override
        public WifiConfigurationParcelable createFromParcel(Parcel in) {
            //WifiConfigurationParcelable config = (WifiConfigurationParcelable)new android.net.wifi.WifiConfiguration();
            WifiConfigurationParcelable config = new WifiConfigurationParcelable(new android.net.wifi.WifiConfiguration());
            config.networkId = in.readInt();
            config.status = in.readInt();
            config.disableReason = in.readInt();
            config.SSID = in.readString();
            config.BSSID = in.readString();
            config.preSharedKey = in.readString();
            for (int i = 0; i < config.wepKeys.length; i++) {
                config.wepKeys[i] = in.readString();
            }
            config.wepTxKeyIndex = in.readInt();
            config.priority = in.readInt();
            config.hiddenSSID = in.readInt() != 0;
            config.allowedKeyManagement = readBitSet(in);
            config.allowedProtocols = readBitSet(in);
            config.allowedAuthAlgorithms = readBitSet(in);
            config.allowedPairwiseCiphers = readBitSet(in);
            config.allowedGroupCiphers = readBitSet(in);
            config.enterpriseConfig = in.readParcelable(null);
            String temp = in.readString();
            if (temp != null && !temp.isEmpty()) config.ipAssignment = IpAssignment.valueOf(temp);
            temp = in.readString();
            if (temp != null && !temp.isEmpty()) config.proxySettings = ProxySettings.valueOf(temp);
            config.linkProperties = in.readParcelable(null);
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) { // requires Android O or later
                config.setHttpProxy(in.readParcelable(null));
            } else {
                try {
                    Method setHttpProxyMethod = android.net.wifi.WifiConfiguration.class.getDeclaredMethod("setHttpProxy", ProxyInfo.class);
                    setHttpProxyMethod.setAccessible(true);
                    ProxyInfo proxyInfo = (ProxyInfo)in.readParcelable(null);
                    setHttpProxyMethod.invoke(config, proxyInfo);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return config;
        }

        @Override
        public WifiConfigurationParcelable[] newArray(int size) {
            return new WifiConfigurationParcelable[size];
        }
    };
}