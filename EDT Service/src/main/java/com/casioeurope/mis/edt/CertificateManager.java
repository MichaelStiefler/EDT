package com.casioeurope.mis.edt;

import android.annotation.SuppressLint;
import android.util.Log;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Objects;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@SuppressWarnings("unused")
public class CertificateManager {

    @SuppressWarnings("FieldCanBeLocal")
    private static String TAG = "EDT (CertificateManager)";
    public static final boolean LOG_METHOD_ENTRANCE_EXIT = true;

    private static void logMethodEntranceExit(boolean entrance, String... addonTags) {
        if (!LOG_METHOD_ENTRANCE_EXIT) return;
        String nameOfCurrMethod = Thread.currentThread()
                .getStackTrace()[3]
                .getMethodName();
        if (nameOfCurrMethod.startsWith("access$")) { // Inner Class called this method!
            nameOfCurrMethod = Thread.currentThread()
                    .getStackTrace()[4]
                    .getMethodName();
        }
        StringBuilder sb = new StringBuilder(addonTags.length);
        Arrays.stream(addonTags).forEach(sb::append);

        Log.v(TAG, nameOfCurrMethod + " " + sb.toString() + (entrance ? " +" : " -"));
    }

    @SuppressWarnings("unused")
    public static boolean initializeKeyStore(String storeName, String password) {
        logMethodEntranceExit(true);
        if (storeName == null || storeName.isEmpty()) storeName = KeyStore.getDefaultType();
        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance(storeName);
            keyStore.load(null, null);
            byte[] passwordBytes = password.getBytes();
            SecretKey secretKey = new SecretKeySpec(passwordBytes, 0, passwordBytes.length, "AES");
            //noinspection SpellCheckingInspection
            char[] passwordChars = "keystorepassword".toCharArray();
            KeyStore.ProtectionParameter protectionParameter = new KeyStore.PasswordProtection(passwordChars);
            KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(secretKey);
            keyStore.setEntry("secretKeyAlias", secretKeyEntry, protectionParameter);
            logMethodEntranceExit(false);
            return true;
        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return false;
    }

    @SuppressWarnings({"unused", "deprecation", "RedundantSuppression"})
    public static boolean installCACertificate(String friendlyName, String fileName) {
        logMethodEntranceExit(true);
        // Android...why do you enjoy making my life so difficult...
        try {
            @SuppressLint("PrivateApi") Class<?> keyStoreClass = Objects.requireNonNull(android.net.wifi.WifiConfiguration.class.getClassLoader()).loadClass("android.security.KeyStore");

            Method getInstanceMethod = keyStoreClass.getMethod("getInstance");
            Object keyStore = getInstanceMethod.invoke(null);

            assert keyStore != null;
            Log.d(TAG, "Got keystore " + keyStore.toString());
            // Put(Key, Value)
            Method putCertificateMethod = keyStoreClass.getMethod("put", String.class, byte[].class); // no such method!
            Log.d(TAG, "Putting...");
            RandomAccessFile file = new RandomAccessFile(fileName, "r");
            //noinspection SpellCheckingInspection
            byte[] cacert = new byte[(int)file.length()];
            file.read(cacert);
            Log.d(TAG, String.format("Certificate is %d bytes long.", cacert.length));
            putCertificateMethod.invoke(keyStore, friendlyName, cacert);
            logMethodEntranceExit(false);
            return true;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | IllegalArgumentException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return false;
    }

}
