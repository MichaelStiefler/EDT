package com.casioeurope.mis.edt.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.security.KeyChain;
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

@SuppressWarnings({"unused", "RedundantSuppression"})
public class CertificateManager {

    @SuppressWarnings("FieldCanBeLocal")
    private static final String TAG = "EDT (CertificateManager)";
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

    @SuppressWarnings({"unused", "RedundantSuppression"})
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
    public static boolean installCACertificate(String friendlyName, String fileName, Context context) {
        logMethodEntranceExit(true);
        // Android...why do you enjoy making my life so difficult...
        try {
            // Supply context, e.g. from "Context context = getApplicationContext();"
            // String fileName points to the file holding the certificate to be installed. pem/der/pfx tested.
            RandomAccessFile file = new RandomAccessFile(fileName, "r");
            //noinspection SpellCheckingInspection
            byte[] certificateBytes = new byte[(int) file.length()];
            file.read(certificateBytes);

            @SuppressLint("PrivateApi") Class<?> keyChainConnectionClass = Objects.requireNonNull(context.getClassLoader()).loadClass("android.security.KeyChain$KeyChainConnection");
            @SuppressLint("PrivateApi") Class<?> iKeyChainServiceClass = Objects.requireNonNull(context.getClassLoader()).loadClass("android.security.IKeyChainService");

            @SuppressWarnings("JavaReflectionMemberAccess") Method keyChainBindMethod = KeyChain.class.getMethod("bind", Context.class);
            Method keyChainConnectionGetServiceMethod = keyChainConnectionClass.getMethod("getService");
            Object keyChainConnectionObject = keyChainBindMethod.invoke(null, context);
            Object iKeyChainServiceObject = keyChainConnectionGetServiceMethod.invoke(keyChainConnectionObject);

            Method installCaCertificate = iKeyChainServiceClass.getDeclaredMethod("installCaCertificate", byte[].class);
            //noinspection PrimitiveArrayArgumentToVarargsMethod
            installCaCertificate.invoke(iKeyChainServiceObject, certificateBytes);

            logMethodEntranceExit(false);
            return true;
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | IllegalArgumentException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        logMethodEntranceExit(false);
        return false;
    }

}
